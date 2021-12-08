package edu.sumdu.tss.elephant.middleware;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CSRFTokenServiceTest {
    private static final String SESSION_ID = "ewefwfwef";
    private static final String SITE_WIDE_TOKEN = "test token";

    //Wrong logic, write more tests after change
    @Test
    @DisplayName("Test for getSiteWideToken when siteWideToken is not null")
    void getSiteWideTokenNotNull() {
        assertEquals(CSRFTokenService.getSiteWideToken(), SITE_WIDE_TOKEN);
    }

    @Test
    @DisplayName("Test for validateToken Valid")
    void validateTokenValid() {
        String token = CSRFTokenService.generateToken(SESSION_ID);
        assertTrue(CSRFTokenService.validateToken(token, SESSION_ID));
    }

    @Test
    @DisplayName("Test for validateToken Invalid")
    void validateTokenInvalid() {
        String token = CSRFTokenService.generateToken(SESSION_ID);

        long time = System.currentTimeMillis() + 100;
        int splitter = token.lastIndexOf("-");
        String wrongToken = token.substring(0, splitter) + '-' + time;

        assertFalse(CSRFTokenService.validateToken(wrongToken, SESSION_ID));
    }
}