package edu.sumdu.tss.elephant.helper.utils;

import edu.sumdu.tss.elephant.model.User;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringUtilsTest {

    private static final String TEXT = "This test for the, replaceLast method, just check it";
    private static final String TEXT_FIRST_RESULT = "This test for the, replaceLast method|Replace| just check it";
    private static final String TEXT_SECOND_RESULT = "This test for the, replaceLast method, just c|Replace|ck it";
    private static final String REPLACED_FIRST = ",";
    private static final String REPLACED_SECOND = ",he";
    private static final String FOR_REPLACE = "|Replace|";

    @Test
    @DisplayName("Test for randomAlphaString")
    void randomAlphaStringTest() {
        String alphaString = StringUtils.randomAlphaString(User.API_KEY_SIZE);
        assertEquals(User.API_KEY_SIZE, alphaString.length());
    }

    //nothing to test
    @Disabled
    @Test
    void uuid() {
    }

    @Test
    @DisplayName("Test for replaceLast")
    void replaceLastTest() {
        assertEquals(TEXT_FIRST_RESULT, StringUtils.replaceLast(TEXT, REPLACED_FIRST, FOR_REPLACE));
        assertEquals(TEXT_SECOND_RESULT, StringUtils.replaceLast(TEXT, REPLACED_SECOND, FOR_REPLACE));
    }
}