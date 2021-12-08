package edu.sumdu.tss.elephant.helper.utils;

import edu.sumdu.tss.elephant.helper.DBPool;
import edu.sumdu.tss.elephant.helper.Keys;
import edu.sumdu.tss.elephant.model.Database;
import edu.sumdu.tss.elephant.model.UserService;
import io.javalin.core.validation.ValidationError;
import io.javalin.http.Context;
import io.javalin.http.HandlerType;
import io.javalin.http.util.ContextUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.xml.bind.ValidationException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

class ExceptionUtilsTest {
    private static final String MESSAGE_FOR_STACKTRACE = "This is my message!";
    private static final String SQL_UNIQ_EXCEPTION_MESSAGE = "This message: duplicate key value violates unique constraint";
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

    @Test
    void validationMessages() {
        try {
            throw new io.javalin.core.validation.ValidationException((Map<String, ? extends List<ValidationError<Object>>>) new RuntimeException());
        } catch (RuntimeException e) {
            String result = ExceptionUtils.validationMessages((io.javalin.core.validation.ValidationException) e);
            var errors = ((io.javalin.core.validation.ValidationException) e).getErrors();
            for (var entry : errors.entrySet()) {
                if(!result.contains(entry.getKey())) {
                    fail();
                }
                for (var cause : entry.getValue()) {
                    if(!result.contains(cause.getMessage())) {
                        fail();
                    }
                }
            }
            assertTrue(true);
        }
    }

    @Test
    @DisplayName("Test for isSQLUniqueException that has Unique Exception")
    void isSQLUniqueExceptionPresent() {
        Throwable exception = new RuntimeException(new SQLException(SQL_UNIQ_EXCEPTION_MESSAGE));
        assertTrue(ExceptionUtils.isSQLUniqueException(exception));
    }

    @Test
    @DisplayName("Test for isSQLUniqueException that hasn't Unique Exception")
    void isSQLUniqueExceptionNoPresent() {
        Throwable exception = new RuntimeException(new SQLException("Ho-ho-ho"));
        assertFalse(ExceptionUtils.isSQLUniqueException(exception));
    }

    @Test
    @DisplayName("Test for stacktrace")
    void stacktrace() {
        try {
            throw new RuntimeException(MESSAGE_FOR_STACKTRACE);
        } catch (Throwable e) {
            PrintWriter pw = new PrintWriter(new StringWriter());
            e.printStackTrace(pw);
            String result = ExceptionUtils.stacktrace(e);
            assertEquals(pw.toString(), result);
        }
    }

    @Test
    @DisplayName("Test for wrapError")
    void wrapErrorWhenValidationException() {
        try {
            throw new ValidationException(MESSAGE_FOR_STACKTRACE);
        } catch (ValidationException e) {
            ExceptionUtils.wrapError(CONTEXT, e);
            assertTrue(
                    Objects.requireNonNull(CONTEXT.sessionAttribute(Keys.ERROR_KEY))
                            .toString().contains(MESSAGE_FOR_STACKTRACE)
            );
            assertNotEquals(
                    Objects.requireNonNull(CONTEXT.sessionAttribute(Keys.ERROR_KEY)).toString(),
                    MESSAGE_FOR_STACKTRACE
            );
        }
    }

    @Test
    @DisplayName("Test for wrapError")
    void wrapErrorWhenOtherException() {
        try {
            throw new RuntimeException(MESSAGE_FOR_STACKTRACE);
        } catch (RuntimeException e) {
            ExceptionUtils.wrapError(CONTEXT, e);
            assertEquals(
                    Objects.requireNonNull(CONTEXT.sessionAttribute(Keys.ERROR_KEY)).toString(),
                    MESSAGE_FOR_STACKTRACE
            );
        }
    }
}