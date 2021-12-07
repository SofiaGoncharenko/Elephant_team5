package edu.sumdu.tss.elephant.helper.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LangTest {

    public static String test = "ua";

    @Test
    public void testByValue() {
        Lang result = Lang.byValue(test);
        assertEquals(test, result);
    }
}
