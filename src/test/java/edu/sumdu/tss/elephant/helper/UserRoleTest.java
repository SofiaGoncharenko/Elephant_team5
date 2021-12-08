package edu.sumdu.tss.elephant.helper;

import io.javalin.core.security.RouteRole;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserRoleTest {

    @Test
    @DisplayName("Test for anyone")
    void anyoneUserRole() {
        assertEquals(UserRole.ANYONE.maxConnections(), 0);
        assertEquals(UserRole.ANYONE.maxDB(), 0);
        assertEquals(UserRole.ANYONE.maxStorage(), 0);
        assertEquals(UserRole.ANYONE.maxBackupsPerDB(), 0);
        assertEquals(UserRole.ANYONE.maxScriptsPerDB(), 0);
    }

    @Test
    @DisplayName("Test for uncheked")
    void unchekedUserRole() {
        assertEquals(UserRole.UNCHEKED.maxConnections(), 0);
        assertEquals(UserRole.UNCHEKED.maxDB(), 0);
        assertEquals(UserRole.UNCHEKED.maxStorage(), 0);
        assertEquals(UserRole.UNCHEKED.maxBackupsPerDB(), 0);
        assertEquals(UserRole.UNCHEKED.maxScriptsPerDB(), 0);
    }

    @Test
    @DisplayName("Test for basic")
    void basicUserRole() {
        assertEquals(UserRole.BASIC_USER.maxConnections(), 5);
        assertEquals(UserRole.BASIC_USER.maxDB(), 2);
        assertEquals(UserRole.BASIC_USER.maxStorage(), 20 * FileUtils.ONE_MB);
        assertEquals(UserRole.BASIC_USER.maxBackupsPerDB(), 1);
        assertEquals(UserRole.BASIC_USER.maxScriptsPerDB(), 2);
    }

    @Test
    @DisplayName("Test for promoted")
    void promotedUserRole() {
        assertEquals(UserRole.PROMOTED_USER.maxConnections(), 5);
        assertEquals(UserRole.PROMOTED_USER.maxDB(), 3);
        assertEquals(UserRole.PROMOTED_USER.maxStorage(), 50 * FileUtils.ONE_MB);
        assertEquals(UserRole.PROMOTED_USER.maxBackupsPerDB(), 5);
        assertEquals(UserRole.PROMOTED_USER.maxScriptsPerDB(), 5);
    }

    @Test
    @DisplayName("Test for admin")
    void adminUserRole() {
        assertEquals(UserRole.ANYONE.maxConnections(), 5);
        assertEquals(UserRole.ANYONE.maxDB(), 100);
        assertEquals(UserRole.ANYONE.maxStorage(), 50 * FileUtils.ONE_MB);
        assertEquals(UserRole.ANYONE.maxBackupsPerDB(), 10);
        assertEquals(UserRole.ANYONE.maxScriptsPerDB(), 10);
    }
}