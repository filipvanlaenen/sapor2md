package net.filipvanlaenen.sapor2md;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Unit tests on the <code>FileSystemPoll</code> class.
 */
public class FileSystemPollTest {
    /**
     * A sample double map to run tests on.
     */
    private static List<Map<String, String>> doubleMap;

    /**
     * Creates the double mpa to run tests on.
     */
    @BeforeAll
    static void createDoubleMapSample() {
        doubleMap = FileSystemPoll.parseDoubleMapFromString("A=1\n==\nB=2");
    }

    /**
     * Verifies that the base name of a poll file is extracted correctly by removing
     * the <code>.poll</code> extension.
     */
    @Test
    void extractsTheBaseNameByRemovingTheExtension() {
        String fileName = "2020-01-01-Foo.poll";
        assertEquals("2020-01-01-Foo", FileSystemPoll.extractBaseNameFromFileName(fileName));
    }

    /**
     * Verifies that a string is correctly converted to a List with two Maps.
     */
    @Test
    void doubleMapContainsTwoItems() {
        assertEquals(2, doubleMap.size());
    }

    /**
     * Verifies that the first map in the double map list contains the first
     * property.
     */
    @Test
    void firstItemOfTheDoubleMapContainsTheFirstProperty() {
        assertEquals("1", doubleMap.get(0).get("A"));
    }

    /**
     * Verifies that the second map in the double map list contains the second
     * property.
     */
    @Test
    void secondItemOfTheDoubleMapContainsTheSecondProperty() {
        assertEquals("2", doubleMap.get(1).get("B"));
    }
}
