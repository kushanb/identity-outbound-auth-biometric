import { AuthRequestInterface } from "../models/authRequest";
export declare class Authorization {
    /**
     * Constructor for the authorization class
     */
    constructor();
    /**
     * Organizes the JSON object as a complex object
     *
     * @param request JSON object of the request
     */
    static processAuthRequest(request: any): AuthRequestInterface;
    /**
     * Returns the timestamp which the request will expire at
     *
     * @param requestInitTime the time the request was initialized in the IS
     */
    getRequestExpiryTime(requestInitTime: any): void;
    /**
     * Send the request to the IS to allow or deny authorization
     *
     * @param authRequest complex object for the request
     * @param response authorisation response given by the user
     */
    static sendAuthRequest(authRequest: AuthRequestInterface, response: string): void;
    /**
     * Checks if a value is null
     *
     * @param value value to be checked for null
     */
    private isNotNull;
}
