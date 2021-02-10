/**
 * Interface for the Discovery Data model
 */
export interface DiscoveryDataInterface {
    id: string;
    username: string;
    tenantDomain?: string;
    userstoreDomain?: string;
    registrationUrl: string;
    authenticationUrl: string;
    challenge: string;
  }
  