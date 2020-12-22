/**
 * Interface for Registration request model
 */
export interface RegistrationRequestInterface {
    id: string;
    deviceName?: string;
    model?: string;
    pushID: string;
    publicKey: string;
    signature: string;
}
