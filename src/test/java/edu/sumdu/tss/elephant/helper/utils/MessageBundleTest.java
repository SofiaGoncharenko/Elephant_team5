package edu.sumdu.tss.elephant.helper.utils;

import edu.sumdu.tss.elephant.helper.Keys;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.text.MessageFormat;

import static org.junit.jupiter.api.Assertions.*;

class MessageBundleTest {
    private static final String LANGUAGE_TAG_UK = "UK";
    private static final String LANGUAGE_TAG_EN = "EN";
    private static final String VALID_MESSAGE_NAME ="validation.mail.invalid";
    private static final String INVALID_MESSAGE_NAME ="validation.mail.invalid22";
    private static final String VALID_MESSAGE_UK ="Це дійсна пошта?";
    private static final String VALID_MESSAGE_EN ="Is it a valid mail?";
    private static final String MESSAGE_NOT_FOUND ="I18n not found:";
    private static final Object[] SOME_OBJECTS = new Object[] {"sdf", "dfsd", "wdwqwe"};

    @BeforeAll
    static void setUp() {
        Keys.loadParams(new File("config.properties"));
    }

    @Test
    void getUkValid() {
        MessageBundle messageBundle = new MessageBundle(LANGUAGE_TAG_UK);
        assertEquals(messageBundle.get(VALID_MESSAGE_NAME), VALID_MESSAGE_UK);
    }

    @Test
    void getUkInvalid() {
        MessageBundle messageBundle = new MessageBundle(LANGUAGE_TAG_UK);
        assertTrue(messageBundle.get(INVALID_MESSAGE_NAME).contains(MESSAGE_NOT_FOUND));
    }

    @Test
    void getEnValid() {
        MessageBundle messageBundle = new MessageBundle(LANGUAGE_TAG_EN);
        assertEquals(messageBundle.get(VALID_MESSAGE_NAME), VALID_MESSAGE_EN);
    }

    @Test
    void getEnInvalid() {
        MessageBundle messageBundle = new MessageBundle(LANGUAGE_TAG_EN);
        assertTrue(messageBundle.get(INVALID_MESSAGE_NAME).contains(MESSAGE_NOT_FOUND));
    }

    @Test
    void getMultipleValidation() {
        MessageBundle messageBundle = new MessageBundle(LANGUAGE_TAG_EN);
        String result = messageBundle.get(VALID_MESSAGE_EN, SOME_OBJECTS);
        String expectedResult = MessageFormat.format(messageBundle.get(VALID_MESSAGE_EN), SOME_OBJECTS);
        assertEquals(result, expectedResult);
    }
}