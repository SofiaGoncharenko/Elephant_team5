package edu.sumdu.tss.elephant.helper.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NotFoundExceptionTest {

    private static final Integer CORRECT_CODE = 404;

    @Test
    @DisplayName("Test for getCode")
    void getCode() {
        NotFoundException exception = new NotFoundException("fds");
        assertEquals(exception.getCode(), CORRECT_CODE);
    }
}