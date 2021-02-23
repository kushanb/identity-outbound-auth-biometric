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
 */

package org.wso2.carbon.identity.application.authenticator.biometric.device.handler.model;

import java.io.Serializable;
import java.util.UUID;

/**
 * This class contains the attributes sent to generate the qr code .
 */
public class DiscoveryData implements Serializable {
    String deviceId;
    String username;
    String fullName;
    String tenantDomain;
    String userStore;
    UUID challenge;
    String registrationUrl;
    String authenticationUrl;


    public DiscoveryData(String deviceId, String username, String fullName, String tenantDomain, String userStore, UUID challenge,
                         String registrationUrl, String authenticationUrl) {
        this.deviceId = deviceId;
        this.username = username;
        this.fullName = fullName;
        this.tenantDomain = tenantDomain;
        this.userStore = userStore;
        this.challenge = challenge;
        this.registrationUrl = registrationUrl;
        this.authenticationUrl = authenticationUrl;
    }

    public DiscoveryData() {
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTenantDomain() {
        return tenantDomain;
    }

    public void setTenantDomain(String tenantDomain) {
        this.tenantDomain = tenantDomain;
    }

    public String getUserStore() {
        return userStore;
    }

    public void setUserStore(String userStore) {
        this.userStore = userStore;
    }

    public UUID getChallenge() {
        return challenge;
    }

    public void setChallenge(UUID challenge) {
        this.challenge = challenge;
    }

    public String getRegistrationUrl() {
        return registrationUrl;
    }

    public void setRegistrationUrl(String registrationUrl) {
        this.registrationUrl = registrationUrl;
    }

    public String getAuthenticationUrl() {
        return authenticationUrl;
    }

    public void setAuthenticationUrl(String authenticationUrl) {
        this.authenticationUrl = authenticationUrl;
    }
}
