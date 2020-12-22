export interface AuthDataInterface {
    deviceId: string;
    challenge: string;
    authUrl: string;
    privateKey: string;
}
export interface AuthRequestInterface extends AuthDataInterface {
    connectionCode?: string;
    displayName?: string;
    username?: string;
    organization?: string;
    applicationName?: string;
    applicationUrl?: string;
    deviceName?: string;
    browserName?: string;
    ipAddress?: string;
    location?: string;
    expiryTime?: string;
    challenge: string;
    deviceId: string;
    sessionDataKey?: string;
    authUrl: string;
    privateKey: string;
}
