import { AuthRequestInterface } from "../models/authRequest";
import { Alert } from "react-native";

import { Crypto } from "../utils/crypto";
import { RequestSender } from "../utils/requestSender";

import AsyncStorage from "@react-native-community/async-storage";
import { AccountsInterface } from "src/models/index";
import { Accounts } from "./accounts";
import { DateTime } from "../utils/dateTime";
import { KJUR } from "jsrsasign";
import uuid from "uuid-random";

let privateKey: string;

const getData = async () => {
  try {
    const value = await AsyncStorage.getItem("privateKey");
    if (value !== null) {
      // value previously stored
      privateKey = value;
    }
  } catch (e) {
    // error reading value
    console.log("No private key available");
  }
};

getData();

export class Authorization {
  /**
   * Constructor for the authorization class
   */
  constructor() {}

  /**
   * Organizes the JSON object as a complex object
   *
   * @param request JSON object of the request
   */
  public static processAuthRequest(
    request: any
    // accounts: any
  ): AuthRequestInterface {
    let authRequest: AuthRequestInterface;

    // let account: AccountsInterface = Accounts.getAccount(request.data.deviceID);
    // Above commented lines are for handling multiple accounts

    // TODO: Dynamically set the auth URL

    if (
      request.data.deviceId &&
      request.data.challenge &&
      request.data.sessionDataKey
    ) {
      authRequest = {
        deviceId: request.data.deviceId,
        challenge: request.data.challenge,
        sessionDataKey: request.sessionDataKey,
        authUrl:
          "https://192.168.1.112:9443/biometric-auth?initiator=mobile" +
          "&sessionDataKey=" +
          request.data.sessionDataKey +
          "&challenge=" +
          request.data.challenge,
        privateKey: privateKey,
        connectionCode: (
          request.data.sessionDataKey.substring(0, 4) +
          " - " +
          request.data.sessionDataKey.substring(4, 8)
        ).toUpperCase(),
      };
    } else {
      throw new Error("One or more required parameters missing");
    }

    if (request.data.displayName) {
      authRequest.displayName = request.data.displayName;
    }

    if (request.data.username) {
      authRequest.username = request.data.username;
    }

    if (request.data.organization) {
      authRequest.organization = request.data.organization;
    }

    if (request.data.applicationName) {
      authRequest.applicationName = request.data.applicationName;
    }

    if (request.data.applicationUrl) {
      authRequest.applicationUrl = request.data.applicationUrl;
    }

    if (request.data.deviceName) {
      authRequest.deviceName = request.data.deviceName;
    }

    if (request.data.browserName) {
      authRequest.browserName = request.data.browserName;
    }

    if (request.data.ipAddress) {
      authRequest.ipAddress = request.data.ipAddress;
    }

    if (request.data.location) {
      authRequest.location = request.data.location;
    }

    // TODO: Handle expiry time and add here

    return authRequest;
  }

  /**
   * Returns the timestamp which the request will expire at
   *
   * @param requestInitTime the time the request was initialized in the IS
   */
  public getRequestExpiryTime(requestInitTime: any) {
    // TODO: Add the code here
    // TODO: Decide the final datatype/library to be used for timestamp
  }

  /**
   * Send the request to the IS to allow or deny authorization
   *
   * @param authRequest complex object for the request
   * @param response authorisation response given by the user
   */
  public static async sendAuthRequest(
    authRequest: AuthRequestInterface,
    response: string
  ): Promise<any> {
    console.log("challenge: " + authRequest.challenge);

    let timestamp = new DateTime();

    let signature = Crypto.signChallenge(
      authRequest.privateKey,
      authRequest.challenge
    );

    let jwt = KJUR.jws.JWS.sign(
      null,
      { alg: "RS256" },
      {
        jti: uuid(),
        sub: authRequest.username + "@" + authRequest.organization,
        iss: "wso2verify",
        aud: "https://localhost:9443/t/" + authRequest.organization + "/",
        nbf: KJUR.jws.IntDate.get("now"),
        exp: KJUR.jws.IntDate.get("now + 1hour"),
        iat: KJUR.jws.IntDate.get("now"),
        sid: authRequest.sessionDataKey,
        did: authRequest.deviceId,
        chg: authRequest.challenge,
        res: response,
      },
      authRequest.privateKey
    );

    let headers = {
      Accept: "application/x-www-form-urlencoded",
      "Content-Type": "application/x-www-form-urlencoded",
      Bearer: jwt,
    };

    let authRequestBody: any = {
      auth_status: response,
      signature: signature,
      deviceId: authRequest.deviceId,
      jwt: jwt,
    };

    let formBody = Object.keys(authRequestBody)
      .map(
        (key) =>
          encodeURIComponent(key) +
          "=" +
          encodeURIComponent(authRequestBody[key])
      )
      .join("&");

    console.log("Form Body: " + formBody);
    console.log("Request URL: " + authRequest.authUrl);
    console.log(authRequestBody);

    let request = new RequestSender();
    let result: Promise<string> = request.sendRequest(
      // authRequest.authUrl,
      "https://enx6srhygagwwxs.m.pipedream.net",
      "POST",
      headers,
      formBody
    );

    // if (result == "OK" && response == "SUCCESSFUL") {
    //   authRequest.authenticationStatus = "Accepted";
    //   console.log("Auth is OK and Accepted");
    // } else if (result == "OK" && response == "DENIED") {
    //   authRequest.authenticationStatus = "Denied";
    //   console.log("Auth is OK and Denied");
    // } else {
    //   console.log("Auth response has a problem. Check! " + String(result));
    // }

    authRequest.requestTime = timestamp.getDateTime();

    // return JSON.stringify({ res: result, data: authRequest });

    return result.then((result) => {
      console.log("Response test: " + result);
      if (result == "OK" && response == "SUCCESSFUL") {
        authRequest.authenticationStatus = "Accepted";
        console.log("Auth is OK and Accepted");
      } else if (result == "OK" && response == "DENIED") {
        authRequest.authenticationStatus = "Denied";
        console.log("Auth is OK and Denied");
      } else {
        console.log("Auth response has a problem. Check! " + String(result));
      }
      console.log(authRequest.authenticationStatus);
      return JSON.stringify({ res: result, data: authRequest });
    });
  }

  /**
   * Checks if a value is null
   *
   * @param value value to be checked for null
   */
  private isNotNull(value: any): boolean {
    if (value != null) {
      return true;
    } else {
      return false;
    }
  }

  public static updateSavedData() {
    getData();
  }

  // TODO: Handle the data update properly
}
