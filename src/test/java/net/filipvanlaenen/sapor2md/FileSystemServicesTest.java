package net.filipvanlaenen.sapor2md;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Unit tests on the <code>FileSystemServices</code> class.
 */
public class FileSystemServicesTest {
    /**
     * A sample map to run tests on.
     */
    private static Map<String, String> map;

    /**
     * Creates the double mpa to run tests on.
     */
    @BeforeAll
    static void createDoubleMapSample() {
        map = FileSystemServices.parseMapFromString("A=1");
    }

    /**
     * Verifies that the map contains only one property.
     */
    @Test
    void mapContainsOneProperty() {
        assertEquals(1, map.size());
    }

    /**
     * Verifies that the map contains the property.
     */
    @Test
    void mapContainsTheProperty() {
        assertEquals("1", map.get("A"));
    }
}
