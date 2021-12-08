package edu.sumdu.tss.elephant.helper.sql;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;

import static org.junit.jupiter.api.Assertions.*;

class ScriptReaderTest {

    private static final String PATH_FOR_SCRIPT = "path_for_script";
    private static final String PATH_FOR_EMPTY_SCRIPT = "path_for_script";

    @SneakyThrows
    @Test
    @DisplayName("Test for readStatement where file isn't empty")
    void readStatementNotEmpty() {
        ScriptReader sr = new ScriptReader(new BufferedReader(new FileReader(PATH_FOR_SCRIPT)));
        assertNotNull(sr.readStatement());
    }

    @SneakyThrows
    @Test
    @DisplayName("Test for readStatement where file is empty")
    void readStatementEmpty() {
        ScriptReader sr = new ScriptReader(new BufferedReader(new FileReader(PATH_FOR_EMPTY_SCRIPT)));
        assertNull(sr.readStatement());
    }

    //isInsideRemark just getter
    //isBlockRemark just getter
    //setSkipRemarks just setter
}