package edu.sumdu.tss.elephant.helper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

import static org.junit.jupiter.api.Assertions.*;

class HmacTest {
    private static final String HMAC_SHA384 = "HmacSHA384";
    private static final String TEST_DATA = "Test data!";
    private static final String TEST_KEY = "124dsd124dsq778";


    @Test
    @DisplayName("Test for calculate")
    void calculate() {
        try {
            assertEquals(Hmac.calculate(TEST_DATA, TEST_KEY), calculate(TEST_DATA, TEST_KEY));
        } catch (Throwable e) {
            fail();
        }
    }

    public static String calculate(String data, String key)
            throws NoSuchAlgorithmException, InvalidKeyException {
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), HMAC_SHA384);
        Mac mac = Mac.getInstance(HMAC_SHA384);
        mac.init(secretKeySpec);

        byte[] bytes = mac.doFinal(data.getBytes());
        Formatter formatter = new Formatter();
        for (byte b : bytes) {
            formatter.format("%02x", b);
        }
        return formatter.toString();
    }
}