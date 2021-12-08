package edu.sumdu.tss.elephant.helper.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HttpExceptionTest {

    private static final Integer CORRECT_CODE = 500;

    @Test
    @DisplayName("Test for getCode")
    void getCode() {
        HttpException exception = new HttpException();
        assertEquals(exception.getCode(), CORRECT_CODE);
    }
}