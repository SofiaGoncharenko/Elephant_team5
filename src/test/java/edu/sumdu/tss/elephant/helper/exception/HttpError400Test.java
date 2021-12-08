package edu.sumdu.tss.elephant.helper.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HttpError400Test {

    private static final Integer CORRECT_CODE = 400;

    @Test
    @DisplayName("Test for getCode")
    void getCode() {
        HttpError400 error = new HttpError400();
        assertEquals(error.getCode(), CORRECT_CODE);
    }
}