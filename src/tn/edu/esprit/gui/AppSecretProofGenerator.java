/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.edu.esprit.gui;

/**
 *
 * @author azizbramli
 */
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class AppSecretProofGenerator {

  private static final String HMAC_ALGORITHM = "HmacSHA256";

  public static String generate(String appSecret, String accessToken) 
          throws NoSuchAlgorithmException, InvalidKeyException {
    String message = accessToken;
    Mac hmac = Mac.getInstance(HMAC_ALGORITHM);
    SecretKeySpec keySpec = new SecretKeySpec(appSecret.getBytes(), HMAC_ALGORITHM);
    hmac.init(keySpec);
    byte[] hmacResult = hmac.doFinal(message.getBytes());
    return bytesToHexString(hmacResult);
  }

  private static String bytesToHexString(byte[] bytes) {
    StringBuilder builder = new StringBuilder();
    for (byte b : bytes) {
      builder.append(String.format("%02x", b));
    }
    return builder.toString();
  }


}

