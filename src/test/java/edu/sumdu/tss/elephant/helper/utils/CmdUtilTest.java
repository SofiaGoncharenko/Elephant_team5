package edu.sumdu.tss.elephant.helper.utils;

import edu.sumdu.tss.elephant.helper.exception.BackupException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CmdUtilTest {
    private static final String VALID_COMMAND = "notepad.exe";
    private static final String INVALID_COMMAND = "asdasd";

    @Test
    @DisplayName("Test for exec method at CmdUtil with valid command")
    void execValidCommand() {
        try {
            CmdUtil.exec(VALID_COMMAND);
            assertTrue(true);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    @DisplayName("Test for exec method at CmdUtil with invalid command")
    void execInvalidCommand() {
        assertThrows(BackupException.class, () -> {CmdUtil.exec(INVALID_COMMAND);});
    }
}