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

package org.wso2.carbon.identity.application.authenticator.push.dao.impl;

import org.wso2.carbon.identity.application.authenticator.push.dao.DeviceDAO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class performs DAO operations related to Push device store which stores the device IDs
 * against the usernames.
 */
public class DeviceDAOImpl implements DeviceDAO {

    private static DeviceDAOImpl deviceDAO = new DeviceDAOImpl();
    private Map<String, List<String>> deviceIDStore = new HashMap<>();

    private DeviceDAOImpl() {

        List<String> yasaraDevices = new ArrayList<>();
        yasaraDevices.add("cbK57eqxofQ:APA91bEJln5WTOzM3hqhDlwPVlj3FXbBaUqQRgRwNGpTSOm" +
                "YcSXSPaazm4WBeVGYnPcwVJc6FtxzvT4QsRV83akYaUXk6fKEsh0tFs9n4ZEiy_TKFUWPoLPnKkkWj_52Z5x1X788LCn9");
        deviceIDStore.put("yasara", yasaraDevices);
    }

    public static DeviceDAOImpl getInstance() {

        return deviceDAO;
    }

    @Override
    public void enrollDevice(String username, String deviceID, String deviceDescription) {

    }

    @Override
    public void unenrollDevice(String username, String deviceID) {

    }

    @Override
    public List<String> getDeviceID(String username) {

        return (List<String>) deviceIDStore.get(username);
    }
}
