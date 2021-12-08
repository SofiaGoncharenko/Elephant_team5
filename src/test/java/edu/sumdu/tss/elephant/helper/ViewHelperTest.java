package edu.sumdu.tss.elephant.helper;

import edu.sumdu.tss.elephant.controller.AbstractController;
import edu.sumdu.tss.elephant.helper.exception.HttpException;
import edu.sumdu.tss.elephant.helper.utils.ExceptionUtils;
import edu.sumdu.tss.elephant.helper.utils.StringUtils;
import edu.sumdu.tss.elephant.model.Database;
import edu.sumdu.tss.elephant.model.UserService;
import io.javalin.http.HandlerType;
import io.javalin.http.util.ContextUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import io.javalin.http.Context;

import javax.swing.text.html.parser.Parser;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

class ViewHelperTest {

    private static final String TEST_MESSAGE = "Test message";
    private static final String BREAD_CRUMB_WHEN_EMPTY_RESULT = "<a href='/home'><ion-icon name=\"home-outline\"></ion-icon></a>";
    private static final String BREAD_CRUMB_WHEN_NOT_EMPTY_RESULT = "NOT_EMPTY";
    private static final int TOTAL_PAGE_NUMBER = 50;
    private static final int CURRENT_PAGE_NUMBER = 10;
    private static Context CONTEXT;

    @BeforeEach
    void setUp() {
        var database = DBPool.getConnection().open().createQuery("select * from databases limit 1").executeAndFetchFirst(Database.class);
        var user = UserService.newDefaultUser();
        user.setUsername(database.getOwner());

        var magicValue = StringUtils.uuid();
        var query = String.format("select '%s'", magicValue);

        MockHttpServletRequest request = new MockHttpServletRequest("POST","/");
        request.setMethod("POST");
        request.setContent("".getBytes(StandardCharsets.UTF_8));

        MockHttpServletResponse response = new MockHttpServletResponse();

        CONTEXT = spy(ContextUtil.init(request, response,
                "/",
                Map.of("database", database.getName()), //Params form query
                HandlerType.POST,
                Map.of(ContextUtil.maxRequestSizeKey,1_000_000L)));

        doReturn(user).when(CONTEXT).sessionAttribute(Keys.SESSION_CURRENT_USER_KEY);
        doReturn(query).when(CONTEXT).formParam("query");
    }

    //two methods with same logic for now
    @Test
    void userError() {
        try {
            throw new HttpException(TEST_MESSAGE);
        } catch (HttpException e) {
            ViewHelper.userError(e, CONTEXT);

            var model = AbstractController.currentModel(CONTEXT);

            assertEquals(model.get("code"), e.getCode());
            assertEquals(model.get("code"), e.getCode());
            assertEquals(model.get("code"), e.getCode());
            if (!Keys.isProduction()) {
                assertEquals(model.get("stacktrace"), ExceptionUtils.stacktrace(e));
            }
            assertEquals(CONTEXT.status(), e.getCode());
        }
    }

    @Test
    @DisplayName("Test for breadcrumb when empty")
    void breadcrumbEmpty() {
        ViewHelper.breadcrumb(CONTEXT);
        ArrayList<String> result = CONTEXT.sessionAttribute(Keys.BREADCRUMB_KEY);
        assertTrue(result != null && !result.isEmpty());
        assertEquals(result.get(0), BREAD_CRUMB_WHEN_EMPTY_RESULT);
    }

    @Test
    @DisplayName("Test for breadcrumb when not empty")
    void breadcrumbNotEmpty() {
        CONTEXT.sessionAttribute(
                Keys.BREADCRUMB_KEY,
                new ArrayList<>(Collections.singleton(BREAD_CRUMB_WHEN_NOT_EMPTY_RESULT))
        );
        ViewHelper.breadcrumb(CONTEXT);
        ArrayList<String> result = CONTEXT.sessionAttribute(Keys.BREADCRUMB_KEY);
        assertTrue(result != null && !result.isEmpty());
        assertEquals(result.get(0), BREAD_CRUMB_WHEN_NOT_EMPTY_RESULT);
    }

    @Test
    @DisplayName("Test for cleanupSession")
    void cleanupSession() {
        ViewHelper.cleanupSession(CONTEXT);
        assertNull(CONTEXT.sessionAttribute(Keys.MODEL_KEY));
        assertNull(CONTEXT.sessionAttribute(Keys.DB_KEY));
        assertNull(CONTEXT.sessionAttribute(Keys.BREADCRUMB_KEY));
    }

    //do later
    @Test
    void defaultVariables() {
    }

    @Test
    @DisplayName("Test for pager")
    void pager() {
        String result = ViewHelper.pager(TOTAL_PAGE_NUMBER, CURRENT_PAGE_NUMBER);
        int cursor = result.indexOf("page-item active");
        Pattern pattern = Pattern.compile(">[0-9]+<");
        Matcher matcher = pattern.matcher(result.substring(cursor));
        if (matcher.find()) {
            String stringWithNumber = matcher.group();
            int number = Integer.parseInt(stringWithNumber.substring(1,stringWithNumber.length() - 1));
            assertEquals(number, CURRENT_PAGE_NUMBER);
        } else {
            fail();
        }
    }

    @Test
    @DisplayName("Test for softError")
    void softError() {
        ViewHelper.softError(TEST_MESSAGE, CONTEXT);
        assertEquals(CONTEXT.sessionAttribute(Keys.ERROR_KEY), TEST_MESSAGE);
    }

    //need?
    @Test
    void redirectBack() {
    }
}