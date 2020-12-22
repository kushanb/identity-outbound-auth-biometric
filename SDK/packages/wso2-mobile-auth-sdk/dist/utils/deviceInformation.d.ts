export declare class DeviceInformation {
    private static deviceName;
    private static deviceBrand;
    private static deviceModel;
    /**
     * Constructor for device information class
     * Assigns the values to the static variables for later use
     */
    constructor();
    /**
     * Returns the name of the device
     *
     * @returns device name
     */
    static getDeviceName(): string;
    /**
     * Returns the model name of the device
     *
     * @returns device model
     */
    static getDeviceModel(): string;
    /**
     * Returns the brand name of the device
     *
     * @returns device brand
     */
    static getDeviceBrand(): string;
}
