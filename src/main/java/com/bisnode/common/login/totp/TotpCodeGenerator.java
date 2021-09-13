package com.bisnode.common.login.totp;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;

import static com.google.common.io.BaseEncoding.base32;
import static com.google.common.io.BaseEncoding.base64;

public class TotpCodeGenerator {

    public String generateKey(String secret) throws NoSuchAlgorithmException, InvalidKeyException {
        return generateOneTimeKey(secret);
    }

    private String generateOneTimeKey(String secret) throws InvalidKeyException, NoSuchAlgorithmException {
        int generateSecrect = generateSecrectFromBase64(base32toBase64(secret));
        return intKeyToString(generateSecrect);
    }

    private int generateSecrectFromBase64(String key) throws InvalidKeyException, NoSuchAlgorithmException {
        Long timeInterval = 60L;
        byte[] byteKey = java.util.Base64.getDecoder().decode(key);
        return generateTotp(byteKey, timeInterval);
    }

    private String base32toBase64(String base32Code){
        return base64().encode( base32().decode(base32Code));
    }

    private String intKeyToString(int key){
        return String.format("%06d", key);
    }

    private static int generateTotp(byte[] secret, long timeInterval) throws NoSuchAlgorithmException, InvalidKeyException {
        long now = Instant.now().getEpochSecond();
        long counter = (now / timeInterval);
        return generateHotp(secret, counter);
    }

    // Code mostly taken from https://github.com/jchambers/java-otp/blob/master/src/main/java/com/eatthepath/otp/HmacOneTimePasswordGenerator.java
    private static int generateHotp(byte[] secret, long counter) throws NoSuchAlgorithmException, InvalidKeyException {
        final Mac mac = Mac.getInstance("HmacSHA1");
        Key key = new SecretKeySpec(secret, "RAW");
        mac.init(key);
        int modDivisor = 1_000_000; // Divisor used for 6 digit password length
        final ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.putLong(0, counter);
        final byte[] hmac = mac.doFinal(buffer.array());
        final int offset = hmac[hmac.length - 1] & 0x0f;
        for (int i = 0; i < 4; i++) {
            // Note that we're re-using the first four bytes of the buffer here; we just ignore the latter four from
            // here on out.
            buffer.put(i, hmac[i + offset]);
        }
        final int hotp = buffer.getInt(0) & 0x7fffffff;
        int value = hotp % modDivisor;
        return value;
    }
}
