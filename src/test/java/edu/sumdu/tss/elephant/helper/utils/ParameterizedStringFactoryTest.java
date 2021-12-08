package edu.sumdu.tss.elephant.helper.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ParameterizedStringFactoryTest {

    public static String test = "select :test from :table";

    @Test
    @DisplayName("Test for addParameter first")
    void addParameterTest1() {
        ParameterizedStringFactory instance = new ParameterizedStringFactory(test);
        String actual = instance.addParameter("test", "TEST").addParameter("table", "TABLE").toString();
        assertEquals(actual, "select TEST from TABLE");
    }

    @Test
    @DisplayName("[must fail] Test for addParameter second")
    void addParameterTest2() {
        ParameterizedStringFactory instance = new ParameterizedStringFactory(test);
        String actual = instance.addParameter("te", "TEST").addParameter("table", "TABLE").toString();
        assertEquals(actual, "select TEST from TABLE");
    }

    @Test
    @DisplayName("Test for toString")
    void testToString() {
        ParameterizedStringFactory instance = new ParameterizedStringFactory(test);
        assertEquals(test, instance.toString());
    }
}