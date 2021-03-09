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

package org.wso2.carbon.identity.application.authenticator.biometric.servlet.store;

/**
 * Updates a hash-map which stores session data key against signed challenge from the mobile application.
 */
public interface BiometricDataStore {

    /**
     * Returns the signed challenge stored against the session data key in biometric data store.
     */
    String getSignedChallenge(String sessionDataKey);

    /**
     * Returns the authentication status stored against the session data key in biometric data store.
     */
    String getAuthStatus(String sessionDataKey);

    /**
     * Adds a new record of session data key against signed challenge to the biometric data store.
     */
    void addBiometricData(String sessionDataKey, String authStatus, String token);

    /**
     * Removes the record with the given session data key in biometric data store.
     */
    void removeBiometricData(String sessionDataKey);

    String getSignature(String sessionDataKey);

    String getDeviceId(String sessionDataKey);

    String getToken(String sessionDataKey);
}
