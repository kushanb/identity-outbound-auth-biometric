/*
 * Copyright (c) 2020, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
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

package org.wso2.carbon.identity.api.user.push.device.handler.v1.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.wso2.carbon.identity.api.user.push.device.common.util.PushDeviceApiConstants;
import org.wso2.carbon.identity.api.user.push.device.handler.v1.MeApiService;
import org.wso2.carbon.identity.api.user.push.device.handler.v1.core.PushDeviceHandlerService;
import org.wso2.carbon.identity.api.user.push.device.handler.v1.model.PatchDTO;
import org.wso2.carbon.identity.api.user.push.device.handler.v1.model.RegistrationRequestDTO;
import org.wso2.carbon.identity.api.user.push.device.handler.v1.model.RemoveRequestDTO;
import org.wso2.carbon.identity.api.user.push.device.handler.v1.model.StatusDTO;

import java.text.MessageFormat;
import javax.ws.rs.core.Response;

/**
 * Implementation class of Push device Handler User APIs.
 */
public class MeApiServiceImpl implements MeApiService {

    private static final Log log = LogFactory.getLog(MeApiServiceImpl.class);

    @Autowired
    private PushDeviceHandlerService pushDeviceHandlerService;

    @Override
    public Response mePushAuthDevicesDeviceIdDelete(String deviceId) {

        if (log.isDebugEnabled()) {
            log.debug(MessageFormat.format("Removing device : {0} ", deviceId));
        }
        pushDeviceHandlerService = new PushDeviceHandlerService();
        pushDeviceHandlerService.unregisterDevice(deviceId);
        return Response.ok().build();
    }

    @Override
    public Response mePushAuthDevicesDeviceIdGet(String deviceId) {

        if (log.isDebugEnabled()) {
            log.debug(MessageFormat.format("Fetching data of device : {0}", deviceId));
        }
        pushDeviceHandlerService = new PushDeviceHandlerService();
        return Response.ok().entity(pushDeviceHandlerService.getDevice(deviceId)).build();
    }

    @Override
    public Response mePushAuthDevicesDeviceIdPut(String deviceId, PatchDTO patch) {

        if (log.isDebugEnabled()) {
            log.debug(MessageFormat.format("The device name could not be modified of device : {0} ", deviceId));
        }

        StatusDTO statusDTO = new StatusDTO();
        if (patch.getPath().equals("/edit-device")) {
            pushDeviceHandlerService = new PushDeviceHandlerService();
            pushDeviceHandlerService.editDeviceName(deviceId, patch.getValue());

            statusDTO.setStatus(PushDeviceApiConstants.RESULT_SUCCESSFUL);
            statusDTO.setDeviceId(deviceId);
            statusDTO.setOperation(PushDeviceApiConstants.OPERATION_UPDATE);
            return Response.ok().entity(statusDTO).build();
        } else {
            statusDTO.setStatus(PushDeviceApiConstants.RESULT_FAILED);
            statusDTO.setDeviceId(deviceId);
            statusDTO.setOperation(PushDeviceApiConstants.OPERATION_UPDATE);
            return Response.status(Response.Status.BAD_REQUEST).entity(statusDTO).build();
        }
    }

    @Override
    public Response mePushAuthDevicesDeviceIdRemovePost(String deviceId, RemoveRequestDTO removeRequestDTO) {

        String token = removeRequestDTO.getToken();

        pushDeviceHandlerService = new PushDeviceHandlerService();
        return Response.ok().entity(pushDeviceHandlerService.unregisterDeviceMobile(deviceId, token)).build();
    }

    @Override
    public Response mePushAuthDevicesGet() {

        if (log.isDebugEnabled()) {
            log.debug("Retrieving all devices of user ");
        }
        pushDeviceHandlerService = new PushDeviceHandlerService();
        return Response.ok().entity(pushDeviceHandlerService.listDevices()).build();
    }

    @Override
    public Response mePushAuthDevicesPost(RegistrationRequestDTO registrationRequest) {

        if (log.isDebugEnabled() && registrationRequest != null) {
            log.debug("Received registration request from mobile device");
        }
        if (registrationRequest != null) {
            pushDeviceHandlerService = new PushDeviceHandlerService();
            return Response.ok().entity(pushDeviceHandlerService.registerDevice(registrationRequest)).build();
        } else {
            StatusDTO statusDTO = new StatusDTO();
            statusDTO.setOperation(PushDeviceApiConstants.OPERATION_REGISTER);
            statusDTO.setStatus(PushDeviceApiConstants.RESULT_FAILED);
            return Response.status(Response.Status.BAD_REQUEST).entity(statusDTO).build();
        }
    }

    @Override
    public Response mePushAuthDiscoveryDataGet() {

        if (log.isDebugEnabled()) {
            log.debug("Fetching data to generate QR code");
        }
        pushDeviceHandlerService = new PushDeviceHandlerService();
        return Response.ok().entity(pushDeviceHandlerService.getDiscoveryData()).build();
    }
}
