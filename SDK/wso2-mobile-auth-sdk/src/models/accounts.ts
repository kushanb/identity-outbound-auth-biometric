/**
 * Interface for the registered Account
 */
export interface AccountsInterface {
  deviceID: string;
  username?: string;
  displayName?: string;
  tenantDomain?: string;
  userstore?: string;
  authURL?: string;
  privateKey?: string;
}