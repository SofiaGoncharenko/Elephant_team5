package edu.sumdu.tss.elephant.helper.utils;

import edu.sumdu.tss.elephant.helper.ViewHelper;
import io.javalin.http.Context;
import io.javalin.http.util.ContextUtil;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class ResponseUtilsTest {
    @Test
    void success() {
        String message = "User validated successfully.";
        String status = "ok";
        HashMap<String,String> response = (HashMap<String, String>) ResponseUtils.success(message);
        assertEquals(message, response.get("message"));
        assertTrue(status.equalsIgnoreCase(response.get("status")));
    }

    @Test
    void error() {
        String message = "Can't validate user.";
        String status = "error";
        HashMap<String,String> response = (HashMap<String, String>) ResponseUtils.error(message);
        assertEquals(message, response.get("message"));
        assertTrue(status.equalsIgnoreCase(response.get("status")));
    }

    @Test
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
