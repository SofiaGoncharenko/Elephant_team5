package edu.sumdu.tss.elephant.helper.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LangTest {

    private static final String TEST = "ua";
    private static final Lang RESULT = Lang.UK;

    @Test
    public void testByValue() {
        Lang result = Lang.byValue(TEST);
        assertEquals(result, RESULT);
    }
}
