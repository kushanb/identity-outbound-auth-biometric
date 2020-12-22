import DeviceInfo from 'react-native-device-info';
export class DeviceInformation {
    /**
     * Constructor for device information class
     * Assigns the values to the static variables for later use
     */
    constructor() {
        console.log("Device info initialized");
        // TODO: Is there a requirement to check if the device name has changed?
        // Adding device name
        if (DeviceInformation.deviceName == null) {
            console.log("Constructor called to add details.");
            DeviceInfo.getDeviceName()
                .then((deviceName) => {
                console.log('Device Name: ' + deviceName);
                DeviceInformation.deviceName = deviceName;
            })
                .catch((err) => {
                console.log('Get device name: ' + err);
            });
        }
        else {
            console.log("Device info already added.");
        }
        // Adding device brand
        if (DeviceInformation.deviceBrand == null) {
            console.log("Adding device model");
            DeviceInformation.deviceBrand = DeviceInfo.getBrand();
        }
        else {
            console.log("Model already added.");
        }
        // Adding device model
        if (DeviceInformation.deviceModel == null) {
            console.log("Adding device model");
            DeviceInformation.deviceModel = DeviceInfo.getModel();
        }
    }
    /**
     * Returns the name of the device
     *
     * @returns device name
     */
    static getDeviceName() {
        return this.deviceName;
    }
    /**
     * Returns the model name of the device
     *
     * @returns device model
     */
    static getDeviceModel() {
        return DeviceInformation.deviceModel;
    }
    /**
     * Returns the brand name of the device
     *
     * @returns device brand
     */
    static getDeviceBrand() {
        return DeviceInformation.deviceBrand;
    }
}
