import { AuthRequestInterface } from "../models/authRequest";
import { Alert } from "react-native";

import { Crypto } from "../utils/crypto";
import { RequestSender } from "../utils/requestSender";

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
  public static processAuthRequest(request: any): AuthRequestInterface {
    let authRequest: AuthRequestInterface;

    if (
      request.data.deviceId &&
      request.data.challenge &&
      request.data.sessionDataKey
    ) {
      authRequest = {
        deviceId: request.data.deviceId,
        challenge: request.data.challenge,
        authUrl:
          "https://192.168.1.112:9443/biometric-auth?initiator=mobile" +
          "&sessionDataKey=" +
          request.data.sessionDataKey +
          "&challenge=" +
          request.data.challenge,
        privateKey:
          "-----BEGIN PRIVATE KEY-----\r\n" +
          "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAKpWmCmqfcwIre4l\r\n" +
          "7Mth5I2JwOuQ7QSvYjTT5dVMcuEmkAhu3VT+Z4ubUx9ISi2H2xsRnapMDaDTCN7y\r\n" +
          "0EerUgA3iPaW4s2UUlidndfrUyN55EKBx80Sn7721GwuE4OVH4Ei9d3QXIhbaao0\r\n" +
          "ny3wTcmA6Ug+xm34nrr8pzkiMxzFAgMBAAECgYBmV8D0x8rtXAmknIS5bge0Qp/2\r\n" +
          "6Qx/VCDdPLNKhLRBMro3VuUEvBXae2hl0zk+QfITQXJJb4iAqEscKSea6ARLvrQE\r\n" +
          "ZqRgcUKsReYjNY81U/dgoCoDrBFLB2BL1rEGwzBk+GwWLIKYe9En9lZU8xe5f8aC\r\n" +
          "WJbbCLC98vjxU20wAQJBAPZZ3E+X+8QXz8+ENhp8bBM7RQPLmS+lOmP2a8WRKWfn\r\n" +
          "OLRali5u5fOuQ6HJ0C8yCoXRRmaaPmwymjOCcO0of8ECQQCxAo+L/oZcORgXu4lL\r\n" +
          "XKB+0JfQGA5LfTp0ALlrVpNB+lYbEcVXFDHCRe8188WB/Po5nIS1/knD5Nr2WUkd\r\n" +
          "cB4FAkEAz17fPETkkrG0patWW79Gvn2S39n/zgKY/1KMb9d88lDKA8+JQfbqLNER\r\n" +
          "b1xRmZjXEMQULgRC7ZM/CIMSCiCgQQJBAIMjO4fizFX8sn26oW+ksv7guOghOk3w\r\n" +
          "++I/Ox9rp3NE7I/nbYUi3+5m817MN/IiopsV+zf9qVao8RN7eHCHxKUCQCSMect/\r\n" +
          "lRBoBm0GioqihmoQmFcUewpYryMCTmu9S/nq2QUj4xNUHuMU+ZSjqYpmffcp3iVS\r\n" +
          "M+ixVx2iE3n25B0=\r\n" +
          "-----END PRIVATE KEY-----",
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
  public static sendAuthRequest(
    authRequest: AuthRequestInterface,
    response: string
  ) {
    console.log("challenge: " + authRequest.challenge);
    let signature = Crypto.signChallenge(
      authRequest.privateKey,
      authRequest.challenge
    );

    let headers = {
      Accept: "application/x-www-form-urlencoded",
      "Content-Type": "application/x-www-form-urlencoded",
    };

    let authRequestBody: any = {
      auth_status: response,
      signature: signature,
      deviceId: authRequest.deviceId,
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
    request.sendRequest(authRequest.authUrl, "POST", headers, formBody);
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
}
