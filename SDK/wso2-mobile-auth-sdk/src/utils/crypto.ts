import {KEYUTIL, KJUR} from 'jsrsasign';
import { getLogger } from "log4js";

export class Crypto {
  /**
   * Returns the generated keypair in PEM format
   */
  public static generateKeypair(): any {
    let keyPair = KEYUTIL.generateKeypair('RSA', 1024);
    return {
      pubKey: KEYUTIL.getPEM(keyPair.pubKeyObj),
      prvKey: KEYUTIL.getPEM(keyPair.prvKeyObj),
    };
  }

  /**
   * Signs the challenge and returns the signed string
   *
   * @param prvKey private key to sign the challenge
   * @param challenege string containing the challenge
   */
  public static signChallenge(prvKey: any, challenege: any): string {
    // TODO: needs to be tested on the device
    const logger = getLogger();
    logger.level = "debug";

    let sig: any = new KJUR.crypto.Signature({alg: 'SHA256withRSA'});
    sig.init(prvKey);
    sig.updateString(challenege);
    try {
      return sig.sign();
    } catch (err) {
      logger.debug('Sign Falied: ' + err);
    }
    return "Error"; // TODO: Handle this error
  }
}