/**
 * Interface for the Discovery Data model
 */
export interface DiscoveryDataInterface {
    id: string;
    username: string;
    tenantDomain: string;
    userstoreDomain: string;
    registrationURL: string;
    authenticationURL: string;
    challenege: string;
  }
  