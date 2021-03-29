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

package org.wso2.carbon.identity.application.authenticator.push.dao;

import java.util.List;

/**
 * This interface performs DAO operations related to Push device store.
 */
public interface DeviceDAO {

    /**
     * Enrolls the device in Push device store when username, device ID
     * and device description are provided by the user.
     */
    void enrollDevice(String username, String deviceID, String deviceDescription);

    /**
     * Unenrolls a device that is already enrolled in Push device store
     * when username and device ID are provided by the user.
     */
    void unenrollDevice(String username, String deviceID);

    /**
     * Returns the device ID from the Push device store when username is provided.
     */
    List<String> getDeviceID(String username);
}
