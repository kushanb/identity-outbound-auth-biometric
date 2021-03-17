package org.wso2.carbon.identity.application.authenticator.biometric.validator;

import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.apache.commons.lang.StringUtils;
//import org.wso2.carbon.core.util.KeyStoreManager;
import org.wso2.carbon.identity.application.authenticator.biometric.device.handler.DeviceHandler;
import org.wso2.carbon.identity.application.authenticator.biometric.device.handler.impl.DeviceHandlerImpl;
import org.wso2.carbon.identity.core.util.IdentityTenantUtil;
//import org.wso2.carbon.identity.oauth.config.OAuthServerConfiguration;
//import org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.text.ParseException;
import java.util.Base64;
import java.util.Date;

/**
 * JWT Access toke validator for Push authentication
 */
public class PushJWTValidator {

    private static final String DOT_SEPARATOR = ".";

    /**
     * Validate JWT
     *
     * @param code
     * @param publicKey
     * @param challenge
     * @return
     * @throws IdentityPushException
     */
    public boolean validate(String code, String publicKey, String challenge)
            throws IdentityPushException {

        if (!isJWT(code)) {
            return false;
        }

        try {
            SignedJWT signedJWT = SignedJWT.parse(code);
            JWTClaimsSet claimsSet = signedJWT.getJWTClaimsSet();
            if (claimsSet == null) {
                throw new IdentityPushException("Claim values are empty in the given code.");
            }
            // JWT Signature validation
            if (!validateSignature(publicKey, signedJWT)) {
                return false;
            } else {
                // Challenge correlation validation
                if(!claimsSet.getClaim("chg").equals(challenge)) {
                    return false;
                }
            }
            // TODO: Do the validations below
            // Validation of expiry time
            if (!checkExpirationTime(claimsSet.getExpirationTime())) {
                return false;
            }
            // Validation of active time
            checkNotBeforeTime(claimsSet.getNotBeforeTime());
        } catch (ParseException e) {
            throw new IdentityPushException("Error while validating code", e);
        }
        return true;
    }

    // TODO: Remove redundant method
//    public String getusername(String code) {
//
//        try {
//            SignedJWT signedJWT = SignedJWT.parse(code);
//            JWTClaimsSet claimsSet = signedJWT.getJWTClaimsSet();
//            return claimsSet.getSubject();
//
//        } catch (ParseException e) {
//            return null;
//        }
//
//    }

    /**
     * Validate the signature of the JWT
     *
     * @param publicKeyStr
     * @param signedJWT
     * @return
     */
    public static boolean validateSignature(String publicKeyStr, SignedJWT signedJWT) {

        try {
//            String deviceId = signedJWT.getPayload();
//            DeviceHandler handler = new DeviceHandlerImpl();
//            String publicKeyStr = handler.getPublicKey(deviceId);
//            int tenantId = IdentityTenantUtil.getTenantId(tenantDomain);
//            KeyStoreManager keyStoreManager = KeyStoreManager.getInstance(tenantId);
//            RSAPublicKey publicKey = (RSAPublicKey) publicKeyString;
            byte[] publicKeyData = Base64.getDecoder().decode(publicKeyStr);
            X509EncodedKeySpec spec = new X509EncodedKeySpec(publicKeyData);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            RSAPublicKey publicKey = (RSAPublicKey) kf.generatePublic(spec);
//            if (!tenantDomain.equals("carbon.super")) {
//                String ksName = tenantDomain.trim().replace(".", "-");
//                String jksName = ksName + ".jks";
//                publicKey = (RSAPublicKey) keyStoreManager.getKeyStore(jksName).getCertificate(tenantDomain)
//                        .getPublicKey();
//            } else {
//                publicKey = (RSAPublicKey) keyStoreManager.getDefaultPublicKey();
//            }

            JWSVerifier verifier = new RSASSAVerifier(publicKey);
            return signedJWT.verify(verifier);

        } catch (Exception var10) {
            return false;
        }
    }

    // TODO: Have a common method to get the jwt claims. Call that method with the claim name as an argument
    public String getDeviceId(String jwt) {
        try {
            if(!isJWT(jwt)){
                return null;
            } else {
                SignedJWT signedJWT = SignedJWT.parse(jwt);
                JWTClaimsSet claimSet = signedJWT.getJWTClaimsSet();
                if(claimSet != null) {
                    return (String) claimSet.getClaim("did");
                } else {
                    return null;
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getSessionDataKey(String jwt) {
        try {
            if(!isJWT(jwt)) {
                return null;
            } else {
                SignedJWT signedJWT = SignedJWT.parse(jwt);
                JWTClaimsSet claimSet = signedJWT.getJWTClaimsSet();
                if(claimSet != null) {
                    return (String) claimSet.getClaim("sid");
                } else {
                    return null;
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getAuthStatus(String jwt) {
        try {
            if(!isJWT(jwt)) {
                return null;
            } else {
                SignedJWT signedJWT = SignedJWT.parse(jwt);
                JWTClaimsSet claimSet = signedJWT.getJWTClaimsSet();
                if(claimSet != null) {
                    return (String) claimSet.getClaim("res");
                } else {
                    return null;
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    private boolean checkExpirationTime(Date expirationTime) {

//        long timeStampSkewMillis = OAuthServerConfiguration.getInstance().getTimeStampSkewInSeconds() * 1000;
//        long expirationTimeInMillis = expirationTime.getTime();
//        long currentTimeInMillis = System.currentTimeMillis();
//        if ((currentTimeInMillis + timeStampSkewMillis) > expirationTimeInMillis) {
//
//            return false;
//        }

        return true;
    }

    private boolean checkNotBeforeTime(Date notBeforeTime) throws IdentityPushException {

//        if (notBeforeTime != null) {
//            long timeStampSkewMillis = OAuthServerConfiguration.getInstance().getTimeStampSkewInSeconds() * 1000;
//            long notBeforeTimeMillis = notBeforeTime.getTime();
//            long currentTimeInMillis = System.currentTimeMillis();
//            if (currentTimeInMillis + timeStampSkewMillis < notBeforeTimeMillis) {
//
//                throw new IdentityPushException("Token is used before Not_Before_Time.");
//            }
//
//        }
        return true;
    }

    /**
     * Validate legitimacy of JWT
     *
     * @param tokenIdentifier
     * @return
     */
    private boolean isJWT(String tokenIdentifier) {
        // JWT token contains 3 base64 encoded components separated by periods.
        return StringUtils.countMatches(tokenIdentifier, DOT_SEPARATOR) == 2;
    }

}