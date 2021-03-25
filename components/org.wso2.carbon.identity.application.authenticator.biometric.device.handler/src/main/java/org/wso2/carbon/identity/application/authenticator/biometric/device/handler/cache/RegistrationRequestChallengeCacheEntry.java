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

package org.wso2.carbon.identity.application.authenticator.biometric.device.handler.cache;

import org.wso2.carbon.identity.application.common.cache.CacheEntry;

import java.util.UUID;

/**
 * Biometric device handler cache entry.
 */
public class RegistrationRequestChallengeCacheEntry extends CacheEntry {
    private UUID challenge;
    private String username;
//    private String userStore;
    // TODO: Remove userstore attribute
    private String tenantDomain;
    private boolean registered;

    public RegistrationRequestChallengeCacheEntry(UUID challenge, String username, //String userStore,
                                                  String tenantDomain, boolean registered) {
        this.challenge = challenge;
        this.username = username;
//        this.userStore = userStore;
        this.tenantDomain = tenantDomain;
        this.registered = registered;
    }

    public UUID getChallenge() {
        return challenge;
    }

    public void setChallenge(UUID challenge) {
        this.challenge = challenge;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

//    public String getUserStore() {
//        return userStore;
//    }
//
//    public void setUserStore(String userStore) {
//        this.userStore = userStore;
//    }

    public String getTenantDomain() {
        return tenantDomain;
    }

    public void setTenantDomain(String tenantDomain) {
        this.tenantDomain = tenantDomain;
    }

    public boolean isRegistered() {
        return registered;
    }

    public void setRegistered(boolean registered) {
        this.registered = registered;
    }
}
