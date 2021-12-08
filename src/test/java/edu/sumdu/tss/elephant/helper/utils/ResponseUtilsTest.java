package edu.sumdu.tss.elephant.helper.utils;

import edu.sumdu.tss.elephant.helper.ViewHelper;
import io.javalin.http.Context;
import io.javalin.http.util.ContextUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class ResponseUtilsTest {

    private static final String STATUS_KEY = "status";
    private static final String MESSAGE_KEY = "message";

    private static final String SUCCESS_MESSAGE = "This is success message!";
    private static final String ERROR_MESSAGE = "This is success message!";
    private static final String SUCCESS_ANSWER = "Ok";
    private static final String ERROR_ANSWER = "Error";

    @Test
    @DisplayName("Test for success")
    void success() {
        HashMap<String, String> result = (HashMap<String, String>) ResponseUtils.success(SUCCESS_MESSAGE);
        assertEquals(result.get(STATUS_KEY), SUCCESS_ANSWER);
        assertEquals(result.get(MESSAGE_KEY), SUCCESS_MESSAGE);
    }

    @Test
    @DisplayName("Test for error")
    void error() {
        HashMap<String, String> result = (HashMap<String, String>) ResponseUtils.success(ERROR_MESSAGE);
        assertEquals(result.get(STATUS_KEY), ERROR_ANSWER);
        assertEquals(result.get(MESSAGE_KEY), ERROR_MESSAGE);
    }

    @Test
    @DisplayName("Test for flush_flash")
    void flush_flash() {
        HttpServletRequest servletRequest = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        when(servletRequest.getSession()).thenReturn(session);
        HttpServletResponse servletResponse = mock(HttpServletResponse.class);
        Context context = spy(ContextUtil.init(servletRequest, servletResponse));
        ResponseUtils.flush_flash(context);
        for (var key : ViewHelper.FLASH_KEY) {
            verify(context).sessionAttribute(eq(key), eq(null));
        }
    }
}
