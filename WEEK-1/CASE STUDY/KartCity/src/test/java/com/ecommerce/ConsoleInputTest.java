package com.ecommerce;

import com.ecommerce.ui.ConsoleInput;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConsoleInputTest {
    @Test
    void blankAndNonnumericMenuEntriesAreRejectedBeforeAValidChoice() {
        try (Scanner scanner = new Scanner("   \nabc\n0\n")) {
            assertEquals("", ConsoleInput.readMenuChoice(scanner, "Choose: ", "0", "1"));
            assertEquals("", ConsoleInput.readMenuChoice(scanner, "Choose: ", "0", "1"));
            assertEquals("0", ConsoleInput.readMenuChoice(scanner, "Choose: ", "0", "1"));
        }
    }
}
