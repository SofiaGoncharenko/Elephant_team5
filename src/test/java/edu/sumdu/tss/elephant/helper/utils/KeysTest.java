package edu.sumdu.tss.elephant.helper.utils;

import edu.sumdu.tss.elephant.helper.Keys;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

public class KeysTest {
    static File file;

    @BeforeAll
    static void file() {
        file = new File("config.properties");
    }

    @Test
    @DisplayName("Test for loadParams")
    void loadParamsWithEmptyFile() {
        assertThrows(
                RuntimeException.class,
                () -> Keys.loadParams(new File(""))
        );
    }

    @Test
    @DisplayName("Test for loadParams")
    void loadParams1() {
        try {
            Properties properties = new Properties();
            properties.load(new FileReader(file));
            properties.remove("DB.NAME");
            File file1 = new File("negativeLoadParam.properties");
            FileOutputStream fr = new FileOutputStream(file1);
            properties.store(fr, "Properties");
            fr.close();
            assertThrows(IllegalArgumentException.class, () -> Keys.loadParams(file1));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Test for loadParams")
    void loadParams2() {
        try {
            Properties properties = new Properties();
            properties.load(new FileReader(file));
            properties.remove("DB.PASSWORD");
            File file1 = new File("negativeLoadSecureParam.properties");
            FileOutputStream fr = new FileOutputStream(file1);
            properties.store(fr, "Properties");
            fr.close();
            assertThrows(IllegalArgumentException.class, () -> Keys.loadParams(file1));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Test for get")
    void get() {
        Keys.loadParams(file);
        assertThrows(RuntimeException.class, () -> Keys.get(StringUtils.randomAlphaString(5)));
        String db = Keys.get("DB.NAME");
        assertNotNull(db);
        assertFalse(db.isEmpty());
    }

    @Test
    @DisplayName("Test for isProduction")
    void isProduction() {
        try {
            Properties properties = new Properties();
            properties.load(new FileReader(file));
            assertEquals(properties.getProperty("ENV").equalsIgnoreCase("production"), Keys.isProduction());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}