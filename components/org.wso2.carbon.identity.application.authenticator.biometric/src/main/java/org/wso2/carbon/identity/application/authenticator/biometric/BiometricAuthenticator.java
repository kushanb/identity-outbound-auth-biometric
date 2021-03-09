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

package org.wso2.carbon.identity.application.authenticator.biometric;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.wso2.carbon.identity.application.authentication.framework.AbstractApplicationAuthenticator;
import org.wso2.carbon.identity.application.authentication.framework.FederatedApplicationAuthenticator;
import org.wso2.carbon.identity.application.authentication.framework.context.AuthenticationContext;
import org.wso2.carbon.identity.application.authentication.framework.exception.AuthenticationFailedException;
import org.wso2.carbon.identity.application.authentication.framework.inbound.InboundConstants;
import org.wso2.carbon.identity.application.authentication.framework.model.AuthenticatedUser;
import org.wso2.carbon.identity.application.authenticator.biometric.cache.AuthContextCache;
import org.wso2.carbon.identity.application.authenticator.biometric.cache.AuthContextCacheEntry;
import org.wso2.carbon.identity.application.authenticator.biometric.cache.AuthContextcacheKey;
import org.wso2.carbon.identity.application.authenticator.biometric.device.handler.DeviceHandler;
import org.wso2.carbon.identity.application.authenticator.biometric.device.handler.exception.BiometricDeviceHandlerClientException;
import org.wso2.carbon.identity.application.authenticator.biometric.device.handler.exception.BiometricdeviceHandlerServerException;
import org.wso2.carbon.identity.application.authenticator.biometric.device.handler.impl.DeviceHandlerImpl;
import org.wso2.carbon.identity.application.authenticator.biometric.device.handler.model.Device;
import org.wso2.carbon.identity.application.authenticator.biometric.internal.BiometricAuthenticatorServiceComponent;
import org.wso2.carbon.identity.application.authenticator.biometric.notification.handler.impl.FirebasePushNotificationSenderImpl;
import org.wso2.carbon.identity.application.authenticator.biometric.validator.PushJWTValidator;
import org.wso2.carbon.identity.application.common.model.Property;
import org.wso2.carbon.identity.core.util.IdentityTenantUtil;
import org.wso2.carbon.identity.core.util.IdentityUtil;
import org.wso2.carbon.user.api.UserStoreException;
import org.wso2.carbon.user.api.UserStoreManager;
import org.wso2.carbon.user.core.UserCoreConstants;
import org.wso2.carbon.user.api.UserRealm;
import org.wso2.carbon.user.core.service.RealmService;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;
import java.sql.SQLException;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua_parser.Parser;
import ua_parser.Client;
import java.net.*;
import java.io.*;
/**
 * This is the class that implements the biometric authenticator feature.
 */
public class BiometricAuthenticator extends AbstractApplicationAuthenticator
        implements FederatedApplicationAuthenticator {

    private static final long serialVersionUID = 8272421416671799253L;
    private static final Log log = LogFactory.getLog(BiometricAuthenticator.class);

    @Override
    public String getFriendlyName() {

        return BiometricAuthenticatorConstants.AUTHENTICATOR_FRIENDLY_NAME;
    }

    @Override
    public boolean canHandle(HttpServletRequest request) {
        return request.getParameter(BiometricAuthenticatorConstants.SIGNED_CHALLENGE) != null;
    }

    @Override
    public String getContextIdentifier(javax.servlet.http.HttpServletRequest request) {

        return request.getParameter(InboundConstants.RequestProcessor.CONTEXT_KEY);
    }

    @Override
    public String getName() {

        return BiometricAuthenticatorConstants.AUTHENTICATOR_NAME;
    }

    @Override
    protected void initiateAuthenticationRequest(HttpServletRequest request, HttpServletResponse response,
                                                 AuthenticationContext context) throws AuthenticationFailedException {
        DeviceHandler deviceHandler = new DeviceHandlerImpl();
        AuthenticatedUser user = context.getSequenceConfig().getStepMap().
                get(context.getCurrentStep() - 1).getAuthenticatedUser();
        String sessionDataKey = request.getParameter(InboundConstants.RequestProcessor.CONTEXT_KEY);
        request.getRemoteAddr();
        try {
            ArrayList<Device> deviceList = deviceHandler.listDevices(user.getUserName(), user.getUserStoreDomain(),
                    user.getTenantDomain());
            request.getSession().setAttribute("devices-list", deviceList);
            JSONObject object;
            JSONArray array = new JSONArray();

            for (Device device : deviceList
            ) {
                object = new JSONObject();
                object.put("deviceId", device.getDeviceId());
                object.put("deviceName", device.getDeviceName());
                object.put("deviceModel", device.getDeviceModel());
                object.put("lastUsedTime", device.getLastUsedTime().toString());
                array.add(object);
            }

            AuthContextCache.getInstance().addToCacheByRequestId(new AuthContextcacheKey(sessionDataKey),
                    new AuthContextCacheEntry(context));

            if (deviceList.size() == 1) {
                sendRequest(request, response, deviceList.get(0).getDeviceId(), sessionDataKey);
            } else {

                String string = JSONArray.toJSONString(array);
                String devicesPage = getDevicesPage(context) + "?sessionDataKey=" + URLEncoder.encode(sessionDataKey,
                        StandardCharsets.UTF_8.name()) + "&devices=" + URLEncoder.encode(string,
                        StandardCharsets.UTF_8.name());
                response.sendRedirect(devicesPage);
            }


        } catch (IOException e) {
            log.error("Error when trying to redirect to biometricdevices.jsp page", e);
        } catch (BiometricdeviceHandlerServerException e) {
            log.error("Error when trying to redirect to biometricdevices.jsp page", e);
        } catch (BiometricDeviceHandlerClientException e) {
            log.error("Error when trying to redirect to biometricdevices.jsp page", e);
        } catch (SQLException e) {
            log.error("Error when trying to redirect to biometricdevices.jsp page", e);
        } catch (UserStoreException e) {
            log.error("Error when trying to redirect to biometricdevices.jsp page", e);
        }

    }


    /**
     * Get the biometricdevices.jsp page from authentication.xml file or use the wait page from constant file.
     *
     * @param context the AuthenticationContext
     * @return the waitPage
     * @throws AuthenticationFailedException
     */
    private String getDevicesPage(AuthenticationContext context) throws AuthenticationFailedException {

        String devicesPage = getDevicesPageFromXMLFile(context);
        if (StringUtils.isEmpty(devicesPage)) {
            devicesPage = BiometricAuthenticatorConstants.DEVICES_PAGE;
            if (log.isDebugEnabled()) {
                log.debug("Default authentication endpoint context is used.");
            }
        }
        return devicesPage;
    }

    /**
     * Get the wait.jsp page url from the application-authentication.xml file.
     *
     * @param context the AuthenticationContext
     * @return waitPage
     */
    public static String getDevicesPageFromXMLFile(AuthenticationContext context) {

        return getConfiguration(context, BiometricAuthenticatorConstants.BIOMETRIC_AUTHENTICATION_ENDPOINT_DEVICES_URL);
    }

    /**
     * Get the wait.jsp page from authentication.xml file or use the wait page from constant file.
     *
     * @param context the AuthenticationContext
     * @return the waitPage
     * @throws AuthenticationFailedException
     */
    private String getWaitPage(AuthenticationContext context) throws AuthenticationFailedException {

        String waitPage = getWaitPageFromXMLFile(context);
        if (StringUtils.isEmpty(waitPage)) {
            waitPage = BiometricAuthenticatorConstants.WAIT_PAGE;
            if (log.isDebugEnabled()) {
                log.debug("Default authentication endpoint context is used.");
            }
        }
        return waitPage;
    }

    /**
     * Get the wait.jsp page url from the application-authentication.xml file.
     *
     * @param context the AuthenticationContext
     * @return waitPage
     */
    public static String getWaitPageFromXMLFile(AuthenticationContext context) {

        return getConfiguration(context, BiometricAuthenticatorConstants.BIOMETRIC_AUTHENTICATION_ENDPOINT_WAIT_URL);
    }

    /**
     * Read configurations from application-authentication.xml for given authenticator.
     *
     * @param context    Authentication Context.
     * @param configName Name of the config.
     * @return Config value.
     */
    public static String getConfiguration(AuthenticationContext context, String configName) {

        String configValue = null;
        if ((context.getProperty(configName)) != null) {
            configValue = String.valueOf(context.getProperty(configName));
        }
        if (log.isDebugEnabled()) {
            log.debug("Config value for key " + configName + " : " +
                    configValue);
        }
        return configValue;
    }

    @SuppressWarnings("checkstyle:LineLength")
    @Override
    protected void processAuthenticationResponse(HttpServletRequest httpServletRequest, HttpServletResponse
            httpServletResponse, AuthenticationContext authenticationContext) throws AuthenticationFailedException {

        AuthenticatedUser user = authenticationContext.getSequenceConfig().
                getStepMap().get(authenticationContext.getCurrentStep() - 1).getAuthenticatedUser();

//        String jwt = httpServletResponse.getHeader("Bearer");
        String jwt = httpServletRequest.getParameter("token");
        String serverChallenge = httpServletRequest.getParameter("signedChallenge");

        try {
            if (validateSignature(jwt, serverChallenge)) {
                authenticationContext.setSubject(user);
            } else {
                authenticationContext.setProperty(BiometricAuthenticatorConstants.AUTHENTICATION_STATUS, true);
                throw new AuthenticationFailedException("Authentication failed! Could not verify signature.", user);
            }

        } catch (IOException e) {
            log.error("IO exception while trying to validate signature ", e);
        } catch (SQLException e) {
            log.error("SQL Exception while trying to get public key", e);
        }
    }

    @Override
    public List<Property> getConfigurationProperties() {

        List<Property> configProperties = new ArrayList<>();

        String firebaseServerKey = "Firebase Server Key";
        Property serverKeyProperty = new Property();
        serverKeyProperty.setName(BiometricAuthenticatorConstants.SERVER_KEY);
        serverKeyProperty.setDisplayName(firebaseServerKey);
        serverKeyProperty.setDescription("Enter the firebase server key ");
        serverKeyProperty.setDisplayOrder(0);
        serverKeyProperty.setRequired(false);
        serverKeyProperty.setConfidential(true);
        configProperties.add(serverKeyProperty);

        String fcmUrl = "Firebase url";
        Property fcmUrlProperty = new Property();
        fcmUrlProperty.setName(BiometricAuthenticatorConstants.FCM_URL);
        fcmUrlProperty.setDisplayName(fcmUrl);
        fcmUrlProperty.setDescription("Enter the url of firebase endpoint ");
        fcmUrlProperty.setDisplayOrder(1);
        fcmUrlProperty.setConfidential(true);
        configProperties.add(fcmUrlProperty);
        return configProperties;
    }

    public void sendRequest(HttpServletRequest request, HttpServletResponse response,
                            String deviceid, String key) throws IOException {
        DeviceHandler deviceHandler = new DeviceHandlerImpl();
        Device device = null;
        try {
            device = deviceHandler.getDevice(deviceid);
        } catch (BiometricDeviceHandlerClientException e) {
            log.error("Error when trying to get device information", e);
        } catch (SQLException e) {
            log.error("Error when trying to get device information", e);
        } catch (BiometricdeviceHandlerServerException e) {
            log.error("Error when trying to get device information", e);
        }
        AuthenticationContext context = AuthContextCache.getInstance().getValueFromCacheByRequestId
                (new AuthContextcacheKey(key)).getAuthenticationContext();

        AuthenticatedUser user = context.getSequenceConfig().getStepMap().
                get(context.getCurrentStep() - 1).getAuthenticatedUser();
        String username = user.getUserName();
        Map<String, String> authenticatorProperties = context.getAuthenticatorProperties();
        String serverKey = authenticatorProperties.get(BiometricAuthenticatorConstants.SERVER_KEY);
        String fcmUrl = authenticatorProperties.get(BiometricAuthenticatorConstants.FCM_URL);
//        String hostname = IdentityUtil.getHostName();
        String hostname = request.getRemoteAddr();


        String serviceProviderName = context.getServiceProviderName();

        String message = username + " is trying to log into " + serviceProviderName + " at " + hostname;
        // TODO: Use a better message for the push notification
        String sessionDataKey = request.getParameter(InboundConstants.RequestProcessor.CONTEXT_KEY);
        UUID challenge = UUID.randomUUID();
        String randomChallenge = challenge.toString();
        context.setProperty(BiometricAuthenticatorConstants.BIOMETRIC_AUTH_CHALLENGE, randomChallenge);

        String pushId = device.getPushId();

//        URL whatismyip = new URL("http://checkip.amazonaws.com");
//        BufferedReader in = new BufferedReader(new InputStreamReader(
//                whatismyip.openStream()));
//
//        String userIp = in.readLine(); //you get the IP as a String


        Map<String, String> userClaims = null;
        try {
            userClaims = getUserClaimValues(user, context);
        } catch (AuthenticationFailedException e) {
            log.error("User Claims Error", e);
        }

        String fullName =
                userClaims.get(BiometricAuthenticatorConstants.FIRST_NAME_CLAIM) + " " +
                userClaims.get(BiometricAuthenticatorConstants.LAST_NAME_CLAIM);
        String organization = user.getTenantDomain();

//        String fullName = "John Doe";
//        String organization = "WSO2";
//        String ipAddr = request;

        String userAgentString = request.getHeader("user-agent");
        Parser uaParser = new Parser();
        Client uaClient = uaParser.parse(userAgentString);

        String userOS = uaClient.os.family;
        String userBrowser = uaClient.userAgent.family;


        FirebasePushNotificationSenderImpl pushNotificationSender = FirebasePushNotificationSenderImpl.getInstance();
        pushNotificationSender.init(serverKey, fcmUrl);
        try {
            pushNotificationSender.sendPushNotification(deviceid, pushId, message, randomChallenge, sessionDataKey,
                    username, fullName, organization, serviceProviderName, hostname, userOS, userBrowser);
        } catch (AuthenticationFailedException e) {
            log.error("Authentication Error", e);
        }

        try {
            String waitPage = BiometricAuthenticatorConstants.WAIT_PAGE
                    + "?sessionDataKey="
                    + URLEncoder.encode(sessionDataKey, StandardCharsets.UTF_8.name())
                    + "&challenge="
                    + URLEncoder.encode(String.valueOf(challenge), StandardCharsets.UTF_8.name());
            response.sendRedirect(waitPage);
        } catch (IOException e) {
            log.error("Error when trying to redirect to wait.jsp page", e);
        }

    }

    private boolean validateSignature(String jwt, String challenge)
            throws IOException, SQLException {
        boolean isValid = false;
        DeviceHandler handler = new DeviceHandlerImpl();

        // TODO: move public key retrieval to validator
        PushJWTValidator validator = new PushJWTValidator();
        String deviceId = validator.getDeviceId(jwt);
        String publicKeyStr = handler.getPublicKey(deviceId);
//        signature = signature.replaceAll("\\s", "+");
//        byte[] signatureBytes = Base64.getDecoder().decode(signature);
//        Signature sign = null;
        try {
            isValid = validator.validate(jwt, publicKeyStr, challenge);
//            sign = Signature.getInstance("SHA256withRSA");
//            byte[] publicKeyData = Base64.getDecoder().decode(publicKeyStr);
//            X509EncodedKeySpec spec = new X509EncodedKeySpec(publicKeyData);
//            KeyFactory kf = KeyFactory.getInstance("RSA");
//            PublicKey publicKey = kf.generatePublic(spec);
//            sign.initVerify(publicKey);
//            sign.update(challenge.getBytes());
//            isvalid = sign.verify(signatureBytes);
            // TODO: Handle verifying the challenge
        } catch (Exception e) {
            log.error("Error when validating signature", e);

        }
        return isValid;
    }

    private Map<String, String> getUserClaimValues(
            AuthenticatedUser authenticatedUser, AuthenticationContext context)
            throws AuthenticationFailedException {

        Map<String, String> claimValues;
        try {
            UserRealm userRealm = getUserRealm(authenticatedUser, context);
            UserStoreManager userStoreManager = userRealm.getUserStoreManager();
            claimValues = userStoreManager.getUserClaimValues(IdentityUtil.addDomainToName(
                    authenticatedUser.getUserName(), authenticatedUser.getUserStoreDomain()), new String[]{
                            BiometricAuthenticatorConstants.FIRST_NAME_CLAIM,
                            BiometricAuthenticatorConstants.LAST_NAME_CLAIM},
                    UserCoreConstants.DEFAULT_PROFILE);
        } catch (UserStoreException e) {
            log.error("Error while reading user claims", e);
            String errorMessage = String.format("Failed to read user claims for user : %s.", authenticatedUser);
            throw new AuthenticationFailedException(errorMessage, e);
        }
        return claimValues;
    }

    /**
     * Get the user realm of the logged in user.
     *
     * @param authenticatedUser Authenticated user.
     * @return The userRealm.
     * @throws AuthenticationFailedException Exception on authentication failure.
     */
    private UserRealm getUserRealm(
            AuthenticatedUser authenticatedUser, AuthenticationContext context)
            throws AuthenticationFailedException {

        UserRealm userRealm = null;
        try {
            if (authenticatedUser != null) {
                String tenantDomain = authenticatedUser.getTenantDomain();
                int tenantId = IdentityTenantUtil.getTenantId(tenantDomain);
                RealmService realmService = BiometricAuthenticatorServiceComponent.getRealmService();
//                RealmService realmService = null;// = (RealmService) context.getOSGiService(RealmService.class, null);
                userRealm = realmService.getTenantUserRealm(tenantId);
            }
        } catch (UserStoreException e) {
            throw new AuthenticationFailedException("Cannot find the user realm.", e);
        }
        return userRealm;
    }


}
