import { AccountsInterface } from "../models/accounts";
/**
 * Class for all the functionality related to accounts
 */
export declare class Accounts {
    private static accountsList;
    /**
     * Constructor for the Accounts class
     */
    constructor();
    /**
     * Enrol the device with the WSO2 Identity Server
     *
     * @param regRequest body of the scanned QR code
     */
    addAccount(regRequest: any): void;
    /**
     * Revoke the enrolement of the device from the Identity Server
     *
     * @param accountID unique ID to identify the account
     */
    removeAccount(accountID: string, privateKey: string): void;
    /**
     * Get an account from the dedicated database
     *
     * @param accountID unique ID to identify the account
     */
    getAccount(accountID: string): any;
    /**
     * Returns the list of saved accounts
     */
    getAccounts(): Array<AccountsInterface>;
    /**
     * Organizes the data into a complex object
     *
     * @param regRequest JSON string from the QR code
     */
    private processDiscoveryData;
    /**
     * Send the request to the IS to enroll the device
     *
     * @param request contains the parameters sent to the IS
     */
    private sendEnrolementRequest;
}
