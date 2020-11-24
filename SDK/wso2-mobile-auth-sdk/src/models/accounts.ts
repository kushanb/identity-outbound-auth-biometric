/**
 * Interface for the registered Account
 */
export interface AccountsInterface {
    deviceID: string;
    username: string;
    displayName: string;
    tenantDomain?: string;
    organization?: string;
    userstore?: string; //is this needed?
    authURL?: string;
    privateKey?: string;
  }