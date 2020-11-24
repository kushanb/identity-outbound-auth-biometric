//import {RNFirebase} from 'react-native-firebase';
//import {Timestamp} from 'react-native-firebase/firestore';

/**
 * Interface for authentication reqiest model
 */
export interface AuthRequestInterface {
  connectionCode: string;
  displayName?: string;
  username: string;
  tenantDomain: string; // Is this needed?
  organisation: string;
  applicationName: string;
  applicationUrl: string;
  deviceName?: string;
  browserName?: string;
  ipAddress: string;
  location?: string;
  //requestInitTime: Timestamp;
}