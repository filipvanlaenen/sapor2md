package net.filipvanlaenen.sapor2md;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 * Unit tests on the <code>FileSystemCountryProperties</code> class.
 */
public class FileSystemCountryPropertiesTest {
    /**
     * Verifying that the file path to the country properties file is calculated
     * correctly.
     */
    @Test
    void filePathIsCalculatedCorrectly() {
        assertEquals("foo/country.properties", FileSystemCountryProperties.createFilePath("foo"));
    }
}
