import { RegistrationRequestInterface } from "../models/registrationRequest";
import { AccountsInterface } from "../models/accounts";
import { DiscoveryDataInterface } from "../models/discoveryData";
import { DeviceInformation } from "../utils/deviceInformation";
import { Crypto } from "../utils/crypto";
import { fetch } from "react-native-ssl-pinning";
import uuid from "uuid-random";
import { RequestSender } from "../utils/requestSender";

/**
 * Class for all the functionality related to accounts
 */
export class Accounts {
  private static accountsList: Array<AccountsInterface>;
  //   private fcmToken: string = firebaseInstance.instanceId;

  /**
   * Constructor for the Accounts class
   */
  constructor() {
    if (Accounts.accountsList == null) {
      Accounts.accountsList = [];
    }
  }

  /*
   * API functions
   */

  /**
   * Enrol the device with the WSO2 Identity Server
   *
   * @param regRequest body of the scanned QR code
   */
  public addAccount(regRequest: any) {
    console.log("Add Account function");
    let discoveryData = this.processDiscoveryData(regRequest);
    console.log("Discovery Data Processed");

    let keypair: any = Crypto.generateKeypair();
    console.log("Keypair:", keypair);
    let signedChallenege: string = Crypto.signChallenge(
      keypair.prvKey,
      regRequest.challenge
    );

    let request: RegistrationRequestInterface;
    request = {
      id: discoveryData.id,
      pushID:
        "fQrUKjM_TyWc2w4QrK8vyB:APA91bFsNHubZZJJnhrxD_VGz0zVnepwfL1PTxxOzPGu-qk6g3qbgfbNT8AsXBjPlrt3Rpc5OF94wnIJs2Iu_AksVNs4Ii8qMhOVETeBE3EEMfz3_Ou0Y2bJ5gyTboIpSI3e5Ugd98WX",
      publicKey: keypair.pubKey,
      signature: signedChallenege,
    };

    request.deviceName = DeviceInformation.getDeviceName();
    request.model = DeviceInformation.getDeviceModel();

    let regRequestBody: any = {
      id: request.id,
      model: request.model,
      name: request.deviceName,
      publickey: request.publicKey,
      pushId: request.pushID,
      signature: request.signature,
    };

    console.log("Request Body:", JSON.stringify(regRequestBody));

    try {
      this.sendEnrolementRequest(regRequestBody);
    } catch (error) {
      console.log(error);
    }

    let requestMethod = "POST";

    let headers = {
      Accept: "application/json",
      "Content-Type": "application/json",
    };

    let newRequest: RequestSender = new RequestSender();
    newRequest.sendRequest(
      discoveryData.registrationUrl,
      requestMethod,
      headers,
      JSON.stringify(regRequestBody)
    );
  }

  /**
   * Revoke the enrolement of the device from the Identity Server
   *
   * @param accountID unique ID to identify the account
   */
  public removeAccount(accountID: string, privateKey: string): void {
    console.log("Remove account function");
    // TODO: Complete the body
    let challenge = uuid();
    let signature = Crypto.signChallenge(privateKey, challenge);

    console.log("Remove Account Challenge: " + challenge);
    console.log("Remove Account Sig: " + signature);

    let delRequestBody: any = {
      deviceId: accountID,
      ACTION: "DELETE",
      signature: signature,
      challenge: challenge,
    };

    let formBody = Object.keys(delRequestBody)
      .map(
        (key) =>
          encodeURIComponent(key) +
          "=" +
          encodeURIComponent(delRequestBody[key])
      )
      .join("&");

    // TODO: Add the correct implementation to take the url from the saved data
    let url = "https://192.168.1.112:9443/biometric-auth";
    let headers = {
      "Content-Type": "application/x-www-form-urlencoded",
    };
    let requestMethod = "POST";

    console.log("Delete body:" + JSON.stringify(delRequestBody));
    console.log("Delete Form Body: " + formBody);

    let request: RequestSender = new RequestSender();
    request.sendRequest(url, requestMethod, headers, formBody);
  }

  /**
   * Get an account from the dedicated database
   *
   * @param accountID unique ID to identify the account
   */
  getAccount(accountID: string): any {
    // TODO: Add the body
    return null;
  }

  /**
   * Returns the list of saved accounts
   */
  getAccounts(): Array<AccountsInterface> {
    // TODO: Check how the db usage can be added here
    return Accounts.accountsList;
  }

  /*
   *  Internal functions
   */

  /**
   * Organizes the data into a complex object
   *
   * @param regRequest JSON string from the QR code
   */
  private processDiscoveryData(regRequest: any): DiscoveryDataInterface {
    // TODO: Change structure once the API on the IS is corrected

    console.log("Process Discovery data");
    console.log("Reg Request: " + regRequest.id);
    console.log("Reg Request ID: " + JSON.stringify(regRequest.username));

    let discoveryData: DiscoveryDataInterface;

    if (
      regRequest.id &&
      regRequest.username &&
      regRequest.registrationURL &&
      regRequest.authenticationURL &&
      regRequest.challenge
    ) {
      discoveryData = {
        id: regRequest.id,
        username: regRequest.username,
        registrationUrl: regRequest.registrationURL,
        authenticationUrl: regRequest.authenticationURL,
        challenge: regRequest.challenge,
      };
    } else {
      throw new Error("One or more required parameters missing");
    }

    if (regRequest.tenantDomain) {
      discoveryData.tenantDomain = regRequest.tenantDomain;
    }

    if (regRequest.userstoreDomain) {
      discoveryData.userstoreDomain = regRequest.userstoreDomain;
    }

    console.log("Discovery data test: " + discoveryData.challenge);
    return discoveryData;
  }

  /**
   * Send the request to the IS to enroll the device
   *
   * @param request contains the parameters sent to the IS
   */
  private sendEnrolementRequest(request: any) {
    // TODO: change to url from discovery data
    console.log("Send enrolement request function");

    fetch(
      "https://192.168.1.112:9443/t/carbon.super/api/users/v1/me/biometricdevice",
      {
        method: "POST",
        disableAllSecurity: true,
        sslPinning: {
          certs: ["wso2carbon"],
        },
        headers: {
          Accept: "application/json",
          "Content-Type": "application/json",
        },
        body: JSON.stringify(request),
      }
    )
      .then((response) => {
        console.log(`response received ${response}`);
      })
      .catch((err) => {
        console.log(`error: ${err}`);
      });
  }
}

// TODO: identify how to send the data to be saved on the device to the user
