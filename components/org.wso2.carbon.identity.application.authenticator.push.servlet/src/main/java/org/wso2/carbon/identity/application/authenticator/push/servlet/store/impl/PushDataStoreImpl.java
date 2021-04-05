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

package org.wso2.carbon.identity.application.authenticator.push.servlet.store.impl;

import org.wso2.carbon.identity.application.authenticator.push.servlet.store.PushDataStore;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Updates a hash-map which stores session data key against signed challenge from the mobile application.
 */
public class PushDataStoreImpl implements PushDataStore, Serializable {

    private static final long serialVersionUID = 8385881451715660472L;
    private static PushDataStoreImpl pushDataStoreInstance = new PushDataStoreImpl();
    private Map<String, String> pushDataStore = new HashMap<>();

    public static PushDataStoreImpl getInstance() {

        return pushDataStoreInstance;
    }

    @Override
    public String getSignedChallenge(String sessionDataKey) {

        return pushDataStore.get(sessionDataKey);
    }

    @Override
    public String getAuthStatus(String sessionDataKey) {
       return pushDataStore.get(sessionDataKey + "status");
    }

    @Override
    public void addPushData(String sessionDataKey, String authStatus, String token) {
        pushDataStore.put(sessionDataKey + "status", authStatus);
        pushDataStore.put(sessionDataKey + "token", token);
    }

    @Override
    public void removePushData(String sessionDataKey) {

        pushDataStore.remove(sessionDataKey);
        // TODO: Remove cache once the auth flow gets completed
    }

    @Override
    public String getSignature(String sessionDataKey) {
        return pushDataStore.get(sessionDataKey + "signature");
    }

    @Override
    public String getDeviceId(String sessionDataKey) {
        return pushDataStore.get(sessionDataKey + "deviceId");
    }

    @Override
    public String getToken(String sessionDataKey) {
        return pushDataStore.get(sessionDataKey + "token");
    }
}
