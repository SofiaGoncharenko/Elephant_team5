package edu.sumdu.tss.elephant.model;

import edu.sumdu.tss.elephant.helper.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    private static final String INIT_LOGIN = "django";
    private static final String INIT_TOKEN = "aqdq1hghffgse65";
    private static final String INIT_PASSWORD = "ala34234q";
    private static final String INIT_PASSWORD2 = "ala342342";
    private static final long INIT_USER_ROLE = 3;
    private static User USER;

    @BeforeEach
    void setUp() {
        USER = new User();
        USER.setLogin(INIT_LOGIN);
        USER.setPassword(INIT_PASSWORD);
        USER.setRole(INIT_USER_ROLE);
        USER.setToken(INIT_TOKEN);
    }

    @Test
    @DisplayName("Test for setPassword")
    void setPassword() {
        assertEquals(USER.getPassword(), USER.crypt(INIT_PASSWORD));
    }

    @Test
    @DisplayName("Test for role")
    void role() {
        assertEquals(USER.role(), UserRole.byValue(INIT_USER_ROLE));
    }

    //mb initial method has wrong logic
    @Test
    @DisplayName("[must fail] Test for crypt")
    void crypt() {
        assertEquals(
                USER.crypt(INIT_PASSWORD),
                USER.crypt(INIT_PASSWORD)
        );
        assertNotEquals(
                USER.crypt(INIT_PASSWORD),
                USER.crypt(INIT_PASSWORD2)
        );
    }

    @Test
    @DisplayName("Test for resetToken")
    void resetToken() {
        USER.resetToken();
        assertNotEquals(USER.getToken(), INIT_TOKEN);
        assertEquals(USER.getToken().length(), User.API_KEY_SIZE);
    }
}