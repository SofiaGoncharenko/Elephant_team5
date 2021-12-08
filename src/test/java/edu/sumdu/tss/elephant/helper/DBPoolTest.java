package edu.sumdu.tss.elephant.helper;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.sql2o.Sql2o;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class DBPoolTest {
    private static final String TEMPLATE_URL = "postgresql://%s:%s@%s:%s/%s";
    private static final String DATABASE_NAME = "DB_NAME";

    @BeforeAll
    static void setUp() {
        Keys.loadParams(new File("config.properties"));
    }

    @Test
    @DisplayName("Test for getConnection method at DBPool")
    void getConnection() {
        assertNotNull(DBPool.getConnection());
        assertNotNull(DBPool.getConnection(DATABASE_NAME));
    }


    @Test
    @DisplayName("Test for dbUtilUrl method at DBPool")
    void dbUtilUrl() {
        assertEquals(
                DBPool.dbUtilUrl(DATABASE_NAME),
                String.format(TEMPLATE_URL, Keys.get("DB.USERNAME"), Keys.get("DB.PASSWORD"), Keys.get("DB.URL"), Keys.get("DB.PORT"), DATABASE_NAME)
        );
    }
}