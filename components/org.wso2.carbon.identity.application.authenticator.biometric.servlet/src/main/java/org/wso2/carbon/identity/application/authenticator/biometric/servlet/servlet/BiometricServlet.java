/*
 * Copyright (c) 2019, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
 */

package org.wso2.carbon.identity.application.authenticator.biometric.servlet.servlet;

import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.identity.application.authentication.framework.context.AuthenticationContext;
import org.wso2.carbon.identity.application.authentication.framework.inbound.InboundConstants;
import org.wso2.carbon.identity.application.authenticator.biometric.BiometricAuthenticator;
import org.wso2.carbon.identity.application.authenticator.biometric.cache.AuthContextCache;
import org.wso2.carbon.identity.application.authenticator.biometric.cache.AuthContextCacheEntry;
import org.wso2.carbon.identity.application.authenticator.biometric.cache.AuthContextcacheKey;
import org.wso2.carbon.identity.application.authenticator.biometric.dto.AuthDataDTO;
import org.wso2.carbon.identity.application.authenticator.biometric.validator.PushJWTValidator;
import org.wso2.carbon.identity.application.authenticator.biometric.device.handler.DeviceHandler;
import org.wso2.carbon.identity.application.authenticator.biometric.device.handler.exception.BiometricDeviceHandlerClientException;
import org.wso2.carbon.identity.application.authenticator.biometric.device.handler.exception.BiometricdeviceHandlerServerException;
import org.wso2.carbon.identity.application.authenticator.biometric.device.handler.impl.DeviceHandlerImpl;
import org.wso2.carbon.identity.application.authenticator.biometric.servlet.BiometricServletConstants;
import org.wso2.carbon.identity.application.authenticator.biometric.servlet.model.WaitStatus;
import org.wso2.carbon.identity.application.authenticator.biometric.servlet.store.impl.BiometricDataStoreImpl;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

/**
 * The Biometric Servlet class manages the status of the biometric authentication process with the session data key,
 * by processing the responses from the biometric authenticator devices(mobile devices) and replying to the polling
 * requests from the web client.
 */
public class BiometricServlet extends HttpServlet {

    private static final long serialVersionUID = -2050679246736808648L;
    private static final Log log = LogFactory.getLog(BiometricServlet.class);
    private BiometricDataStoreImpl biometricDataStoreInstance = BiometricDataStoreImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String action = null;
        action = request.getParameter("ACTION");
        String key = request.getParameter("sessionDataKey");
        if (action == null) {
            action = "WaitResponse";
        }
        switch (action) {
            case "Authenticate": {
                String deviceId = request.getParameter("deviceId");
                BiometricAuthenticator authenticator = new BiometricAuthenticator();
                authenticator.sendRequest(request, response, deviceId, key);
                break;
            }
            case "WaitResponse": {
                if (!(request.getParameterMap().containsKey(BiometricServletConstants.INITIATOR))) {
                    if (log.isDebugEnabled()) {
                        log.debug("Invalid request as the query parameter for initiator is missing.");
                    }
                } else {
                    // If the initiator is not null, else block is executed.
                    String initiator = request.getParameter(BiometricServletConstants.INITIATOR);
                    if (!(BiometricServletConstants.WEB.equals(initiator) && request.getParameterMap()
                            .containsKey(InboundConstants.RequestProcessor.CONTEXT_KEY))) {
                        if (log.isDebugEnabled()) {
                            log.debug("Unsupported HTTP GET request or session data key is null.");
                        }
                    } else {
                        // If the initiator is equal to WEB and if the query parameter session data
                        // key is not null, else block is executed.
                        handleWebResponse(request, response);
                    }
                }
                break;
            }
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String action = request.getParameter("ACTION");
        if (action == null) {
            action = "AUTH_REQUEST";
        }
        if (action.equals("DELETE")) {
            try {
                deleteDevice(request, response);
            } catch (BiometricDeviceHandlerClientException e) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Device not found");
            } catch (BiometricdeviceHandlerServerException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Server Error");
            } catch (SQLException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "SQL Exception");
            }
        } else {
            if (!request.getParameterMap().containsKey(BiometricServletConstants.INITIATOR)) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Query parameter for initiator is missing.");
            }
            String initiator = request.getParameter(BiometricServletConstants.INITIATOR);

            if (!BiometricServletConstants.MOBILE.equals(initiator)) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unsupported HTTP request from a mobile device.");
                return;
            }
            handleMobileResponse(request, response);
        }

    }

    private void handleWebResponse(HttpServletRequest request, HttpServletResponse response) throws IOException {

        WaitStatus waitResponse = new WaitStatus();
        String sessionDataKeyWeb = request.getParameter(InboundConstants.RequestProcessor.CONTEXT_KEY);
        String signedChallengeExtracted = biometricDataStoreInstance.getSignedChallenge(sessionDataKeyWeb);
        String authStatus = biometricDataStoreInstance.getAuthStatus(sessionDataKeyWeb);
        String token = biometricDataStoreInstance.getToken(sessionDataKeyWeb);
        // TODO: Validate if JWT is available instead
        if (StringUtils.isEmpty(token)) {
            if (log.isDebugEnabled()) {
                log.debug("Signed challenge sent from the mobile application is null.");
            }

        } else {
            // If the signed challenge sent from the mobile application is not null,else block is executed..
            response.setStatus(HttpServletResponse.SC_OK);
            waitResponse.setStatus(BiometricServletConstants.Status.COMPLETED.name());
            waitResponse.setChallenge(signedChallengeExtracted);
            waitResponse.setAuthStatus(authStatus);
            waitResponse.setToken(token);
            biometricDataStoreInstance.removeBiometricData(sessionDataKeyWeb);
            response.setContentType(MediaType.APPLICATION_JSON);
            String json = new Gson().toJson(waitResponse);
            if (log.isDebugEnabled()) {
                log.debug("Json Response to the wait page: " + json);
            }
            try (PrintWriter out = response.getWriter()) {
                out.print(json);
                out.flush();
            }
        }
    }

    private void handleMobileResponse(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (!(request.getParameterMap().containsKey("jwt"))) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Received session data key and/or signed" +
                    " challenge is null.");
        // TODO: Do validations to check if JWT is available
        } else {
            // If the query parameters session data key and challenge are not null, else block is executed..
            // TODO: Get all the below parameters from JWT in header
            PushJWTValidator validator = new PushJWTValidator();
            String token = request.getParameter("jwt");
            String sessionDataKey = validator.getSessionDataKey(token);

            AuthenticationContext context = AuthContextCache.getInstance().getValueFromCacheByRequestId
                    (new AuthContextcacheKey(sessionDataKey)).getAuthenticationContext();

            AuthDataDTO authDataDTO = (AuthDataDTO) context.getProperty("authData");
            authDataDTO.setAuthToken(token);
            context.setProperty("authData", authDataDTO);
            AuthContextCache.getInstance().addToCacheByRequestId(new AuthContextcacheKey(sessionDataKey),
                    new AuthContextCacheEntry(context));

            String sessionDataKeyMobile = validator.getSessionDataKey(token);
            String status = validator.getAuthStatus(token);
            biometricDataStoreInstance.addBiometricData(sessionDataKeyMobile, status, token);
            response.setStatus(HttpServletResponse.SC_OK);
            if (log.isDebugEnabled()) {
                log.debug("Session data key received from the mobile application: " + sessionDataKeyMobile);
            }
        }
    }

    private void deleteDevice(HttpServletRequest request, HttpServletResponse response)
            throws BiometricDeviceHandlerClientException, BiometricdeviceHandlerServerException, SQLException {
        DeviceHandler deviceHandler = new DeviceHandlerImpl();
        deviceHandler.unregisterDevice(request.getParameter("deviceId"));
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
