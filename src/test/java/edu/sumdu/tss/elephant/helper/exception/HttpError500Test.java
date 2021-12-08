package edu.sumdu.tss.elephant.helper.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HttpError500Test {
    private static final Integer CORRECT_CODE = 500;

    @Test
    @DisplayName("Test for getCode")
    void getCode() {
        HttpError500 exception = new HttpError500();
        assertEquals(exception.getCode(), CORRECT_CODE);
    }

}