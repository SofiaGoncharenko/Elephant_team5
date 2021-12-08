package edu.sumdu.tss.elephant.helper.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ValidatorHelperTest {

    //add values
    private static final List<String> VALID_PASSWORDS = new ArrayList<String>(Arrays.asList("", ""));
    private static final List<String> VALID_EMAILS = new ArrayList<String>(Arrays.asList("", ""));
    private static final List<String> INVALID_PASSWORDS = new ArrayList<String>(Arrays.asList("", ""));
    private static final List<String> INVALID_EMAILS = new ArrayList<String>(Arrays.asList("", ""));

    @Test
    @DisplayName("Test for isValidPassword")
    void isValidPasswordTest() {
        for (String vPassword: VALID_PASSWORDS) {
            assertTrue(ValidatorHelper.isValidPassword(vPassword));
        }
        for (String invPassword: INVALID_PASSWORDS) {
            assertFalse(ValidatorHelper.isValidPassword(invPassword));
        }
    }

    @Test
    @DisplayName("Test for isValidMail")
    void isValidMailTest() {
        for (String vEmail: VALID_EMAILS) {
            assertTrue(ValidatorHelper.isValidPassword(vEmail));
        }
        for (String invEmail: INVALID_EMAILS) {
            assertFalse(ValidatorHelper.isValidPassword(invEmail));
        }
    }
}