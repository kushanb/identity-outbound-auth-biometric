package org.wso2.carbon.identity.application.authenticator.biometric.validator;

//import com.nimbusds.jose.JOSEException;
//import com.nimbusds.jose.JWSHeader;
//import com.nimbusds.jose.JWSVerifier;
//import com.nimbusds.jose.crypto.RSASSAVerifier;
//import com.nimbusds.jwt.JWTClaimsSet;
//import com.nimbusds.jwt.SignedJWT;
//import org.apache.commons.lang.StringUtils;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.wso2.carbon.context.PrivilegedCarbonContext;
//import org.wso2.carbon.identity.application.common.model.FederatedAuthenticatorConfig;
//import org.wso2.carbon.identity.application.common.model.IdentityProvider;
//import org.wso2.carbon.identity.application.common.util.IdentityApplicationConstants;
//import org.wso2.carbon.identity.application.common.util.IdentityApplicationManagementUtil;
//import org.wso2.carbon.identity.oauth.config.OAuthServerConfiguration;
//import org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception;
//import org.wso2.carbon.idp.mgt.IdentityProviderManagementException;
//import org.wso2.carbon.idp.mgt.IdentityProviderManager;
//import org.wso2.carbon.utils.multitenancy.MultitenantConstants;
//
//import java.security.PublicKey;
//import java.security.cert.CertificateException;
//import java.security.cert.X509Certificate;
//import java.security.interfaces.RSAPublicKey;
//import java.text.ParseException;
//import java.util.Date;
//import java.util.List;

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

    public boolean validate(String code, String publicKey)
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
            if (!validateSignature(publicKey, signedJWT)) {
                return false;
            }
            // TODO: Do the validations below
            if (!checkExpirationTime(claimsSet.getExpirationTime())) {
                return false;
            }
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

    public String getDeviceId(String jwt) {
        try {
            if(!isJWT(jwt)){
                return null;
            } else {
                SignedJWT signedJWT = SignedJWT.parse(jwt);
                JWTClaimsSet claimSet = signedJWT.getJWTClaimsSet();
                if(claimSet != null) {
                    return (String) claimSet.getClaim("aid");
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

    private boolean isJWT(String tokenIdentifier) {
        // JWT token contains 3 base64 encoded components separated by periods.
        return StringUtils.countMatches(tokenIdentifier, DOT_SEPARATOR) == 2;
    }

}
//    private static final String ALGO_PREFIX = "RS";
//    private static final Log log = LogFactory.getLog(PushJWTValidator.class);
//    private static final String OIDC_IDP_ENTITY_ID = "IdPEntityId";
//    private static final String DOT_SEPARATOR = ".";
//
////    @Override
//    public boolean validateAccessToken(PushTokenValidationMessageContext validationReqDTO)
//            throws IdentityPushException {
//
//        if (!isJWT(validationReqDTO.getRequestDTO().getAccessToken().getIdentifier())) {
//            return false;
//        }
//
//        try {
//            SignedJWT signedJWT = getSignedJWT(validationReqDTO);
//            JWTClaimsSet claimsSet = signedJWT.getJWTClaimsSet();
//            if (claimsSet == null) {
//                throw new IdentityPushException("Claim values are empty in the given Token.");
//            }
//
//            if (!validateRequiredFields(claimsSet)) {
//                return false;
//            }
//
//            IdentityProvider identityProvider = getResidentIDPForIssuer(claimsSet.getIssuer());
//
//            if (!validateSignature(signedJWT, identityProvider)) {
//                return false;
//            }
//            if (!checkExpirationTime(claimsSet.getExpirationTime())) {
//                return false;
//            }
//            checkNotBeforeTime(claimsSet.getNotBeforeTime());
//        } catch (JOSEException | ParseException e) {
//            throw new IdentityPushException("Error while validating Token.", e);
//        }
//        return true;
//    }
//
////    @Override
//    public String getTokenType() {
//        return "JWT";
//    }
//
//    /**
//     * The default implementation resolves one certificate to Identity Provider and ignores the JWT header.
//     * Override this method, to resolve and enforce the certificate in any other way
//     * such as x5t attribute of the header.
//     *
//     * @param header The JWT header. Some of the x attributes may provide certificate information.
//     * @param idp    The identity provider, if you need it.
//     * @return the resolved X509 Certificate, to be used to validate the JWT signature.
//     * @throws IdentityPushException something goes wrong.
//     */
//    protected X509Certificate resolveSignerCertificate(JWSHeader header,
//                                                       IdentityProvider idp) throws IdentityPushException {
//        X509Certificate x509Certificate;
//        String tenantDomain = getTenantDomain();
//        try {
//            x509Certificate = (X509Certificate) IdentityApplicationManagementUtil
//                    .decodeCertificate(idp.getCertificate());
//        } catch (CertificateException e) {
//            throw new IdentityPushException("Error occurred while decoding public certificate of Identity Provider "
//                    + idp.getIdentityProviderName() + " for tenant domain " + tenantDomain, e);
//        }
//        return x509Certificate;
//    }
//
//    private SignedJWT getSignedJWT(PushTokenValidationMessageContext validationReqDTO) throws ParseException {
//        return SignedJWT.parse(validationReqDTO.getRequestDTO().getAccessToken().getIdentifier());
//    }
//
//    private String resolveSubject(JWTClaimsSet claimsSet) {
//        return claimsSet.getSubject();
//    }
//
//    private IdentityProvider getResidentIDPForIssuer(String jwtIssuer) throws IdentityPushException {
//
//        String tenantDomain = getTenantDomain();
//        String issuer = StringUtils.EMPTY;
//        IdentityProvider residentIdentityProvider;
//        try {
//            residentIdentityProvider = IdentityProviderManager.getInstance().getResidentIdP(tenantDomain);
//        } catch (IdentityProviderManagementException e) {
//            String errorMsg =
//                    String.format("Error while getting Resident Identity Provider of '%s' tenant.", tenantDomain);
//            throw new IdentityPushException(errorMsg, e);
//        }
//        FederatedAuthenticatorConfig[] fedAuthnConfigs = residentIdentityProvider.getFederatedAuthenticatorConfigs();
//        FederatedAuthenticatorConfig oauthAuthenticatorConfig =
//                IdentityApplicationManagementUtil.getFederatedAuthenticator(fedAuthnConfigs,
//                        IdentityApplicationConstants.Authenticator.OIDC.NAME);
//        if (oauthAuthenticatorConfig != null) {
//            issuer = IdentityApplicationManagementUtil.getProperty(oauthAuthenticatorConfig.getProperties(),
//                    OIDC_IDP_ENTITY_ID).getValue();
//        }
//
//        if (!jwtIssuer.equals(issuer)) {
//            throw new IdentityPushException("No Registered IDP found for the token with issuer name : " + jwtIssuer);
//        }
//        return residentIdentityProvider;
//    }
//
//    private boolean validateSignature(SignedJWT signedJWT, IdentityProvider idp)
//            throws JOSEException, IdentityPushException {
//
//        JWSVerifier verifier = null;
//        JWSHeader header = signedJWT.getHeader();
//        X509Certificate x509Certificate = resolveSignerCertificate(header, idp);
//        if (x509Certificate == null) {
//            throw new IdentityPushException("Unable to locate certificate for Identity Provider: " + idp
//                    .getDisplayName());
//        }
//
//        String alg = signedJWT.getHeader().getAlgorithm().getName();
//        if (StringUtils.isEmpty(alg)) {
//            throw new IdentityPushException("Algorithm must not be null.");
//
//        } else {
//            if (log.isDebugEnabled()) {
//                log.debug("Signature Algorithm found in the Token Header: " + alg);
//            }
//            if (alg.indexOf(ALGO_PREFIX) == 0) {
//                // At this point 'x509Certificate' will never be null.
//                PublicKey publicKey = x509Certificate.getPublicKey();
//                if (publicKey instanceof RSAPublicKey) {
//                    verifier = new RSASSAVerifier((RSAPublicKey) publicKey);
//                } else {
//                    throw new IdentityPushException("Public key is not an RSA public key.");
//                }
//            } else {
//                if (log.isDebugEnabled()) {
//                    log.debug("Signature Algorithm not supported yet: " + alg);
//                }
//            }
//            if (verifier == null) {
//                throw new IdentityPushException("Could not create a signature verifier for algorithm type: " + alg);
//            }
//        }
//
//        boolean isValid = signedJWT.verify(verifier);
//        if (log.isDebugEnabled()) {
//            log.debug("Signature verified: " + isValid);
//        }
//        return isValid;
//    }
//
//    private boolean checkExpirationTime(Date expirationTime) {
//        long timeStampSkewMillis = OAuthServerConfiguration.getInstance().getTimeStampSkewInSeconds() * 1000;
//        long expirationTimeInMillis = expirationTime.getTime();
//        long currentTimeInMillis = System.currentTimeMillis();
//        if ((currentTimeInMillis + timeStampSkewMillis) > expirationTimeInMillis) {
//            if (log.isDebugEnabled()) {
//                log.debug("Token is expired." +
//                        ", Expiration Time(ms) : " + expirationTimeInMillis +
//                        ", TimeStamp Skew : " + timeStampSkewMillis +
//                        ", Current Time : " + currentTimeInMillis + ". Token Rejected and validation terminated.");
//            }
//            return false;
//        }
//
//        if (log.isDebugEnabled()) {
//            log.debug("Expiration Time(exp) of Token was validated successfully.");
//        }
//        return true;
//    }
//
//    private boolean checkNotBeforeTime(Date notBeforeTime) throws IdentityPushException {
//
//        if (notBeforeTime != null) {
//            long timeStampSkewMillis = OAuthServerConfiguration.getInstance().getTimeStampSkewInSeconds() * 1000;
//            long notBeforeTimeMillis = notBeforeTime.getTime();
//            long currentTimeInMillis = System.currentTimeMillis();
//            if (currentTimeInMillis + timeStampSkewMillis < notBeforeTimeMillis) {
//                if (log.isDebugEnabled()) {
//                    log.debug("Token is used before Not_Before_Time." +
//                            ", Not Before Time(ms) : " + notBeforeTimeMillis +
//                            ", TimeStamp Skew : " + timeStampSkewMillis +
//                            ", Current Time : " + currentTimeInMillis + ". Token Rejected and validation terminated.");
//                }
//                throw new IdentityPushException("Token is used before Not_Before_Time.");
//            }
//            if (log.isDebugEnabled()) {
//                log.debug("Not Before Time(nbf) of Token was validated successfully.");
//            }
//        }
//        return true;
//    }
//
//    private boolean validateRequiredFields(JWTClaimsSet claimsSet) throws IdentityPushException {
//
//        String subject = resolveSubject(claimsSet);
//        List<String> audience = claimsSet.getAudience();
//        String jti = claimsSet.getJWTID();
//        if (StringUtils.isEmpty(claimsSet.getIssuer()) || StringUtils.isEmpty(subject) ||
//                claimsSet.getExpirationTime() == null || audience == null || jti == null) {
//            if (log.isDebugEnabled()) {
//                log.debug("Mandatory fields(Issuer, Subject, Expiration time," +
//                        " jtl, Audience or Session ID) are empty in the given Token.");
//            }
//            return false;
//        }
//        return true;
//    }
//
//    private String getTenantDomain() {
//        String tenantDomain = PrivilegedCarbonContext.getThreadLocalCarbonContext().getTenantDomain();
//        if (StringUtils.isEmpty(tenantDomain)) {
//            tenantDomain = MultitenantConstants.SUPER_TENANT_DOMAIN_NAME;
//        }
//        return tenantDomain;
//    }
//
//    /**
//     * Return true if the token identifier is JWT.
//     *
//     * @param tokenIdentifier String JWT token identifier.
//     * @return  true for a JWT token.
//     */
//    private boolean isJWT(String tokenIdentifier) {
//        // JWT token contains 3 base64 encoded components separated by periods.
//        return StringUtils.countMatches(tokenIdentifier, DOT_SEPARATOR) == 2;
//    }
//}
