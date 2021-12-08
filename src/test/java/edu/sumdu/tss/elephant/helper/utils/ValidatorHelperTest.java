package edu.sumdu.tss.elephant.helper.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ValidatorHelperTest {

    private static final List<String> VALID_PASSWORDS = new ArrayList<String>(Arrays.asList("1dfdsdsa@s3QAQA", "1wqqe@sad123AA"));
    private static final List<String> VALID_EMAILS = new ArrayList<String>(Arrays.asList("hohoho@gmail.com", "ho.ho@ukr.net"));
    private static final List<String> INVALID_PASSWORDS = new ArrayList<String>(Arrays.asList("123456", "111111111111111111111111111111111"));
    private static final List<String> INVALID_EMAILS = new ArrayList<String>(Arrays.asList("dsfsd", "qqq.aaa", "fsf@asdd"));

    @Test
    @DisplayName("Test for isValidPassword when password is valid")
    void isValidPasswordTest() {
        for (String vPassword: VALID_PASSWORDS) {
            assertTrue(ValidatorHelper.isValidPassword(vPassword));
        }
    }

    @Test
    @DisplayName("Test for isValidPassword when password doesn't valid")
    void isInvalidPasswordTest() {
        for (String invPassword: INVALID_PASSWORDS) {
            assertFalse(ValidatorHelper.isValidPassword(invPassword));
        }
    }

    @Test
    @DisplayName("Test for isValidMail when email is valid")
    void isValidMailTest() {
        for (String vEmail: VALID_EMAILS) {
            assertTrue(ValidatorHelper.isValidMail(vEmail));
        }
    }

    //fails for fsf@asdd
    @Test
    @DisplayName("[must fail] Test for isValidMail when email doesn't valid")
    void isInvalidMailTest() {
        for (String invEmail: INVALID_EMAILS) {
            assertFalse(ValidatorHelper.isValidMail(invEmail));
        }
    }
}