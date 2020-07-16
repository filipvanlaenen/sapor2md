package net.filipvanlaenen.sapor2md;

import java.io.File;
import java.time.OffsetDateTime;
import java.util.Map;

/**
 * Class implementing the abstract <code>CountryProperties</code> class against
 * a file system.
 */
public final class FileSystemCountryProperties extends CountryProperties {
    /**
     * The file name for the country properties.
     */
    private static final String COUNTRY_PROPERTIES_FILE_NAME = "country.properties";

    /**
     * Constructor taking the path to the Sapor directory as the argument.
     *
     * @param directory The path to the Sapor directory.
     */
    FileSystemCountryProperties(final String directory) {
        super(readCountryPropertiesFileIntoMap(directory), readTimestamp(directory));
    }

    /**
     * Reads the last modified timestamp for the file from the file system.
     *
     * @param directory The path to the Sapor directory.
     * @return The last modified timestamp for the file on the file system.
     */
    private static OffsetDateTime readTimestamp(final String directory) {
        return FileSystemServices.getTimestamp(createFilePath(directory));
    }

    /**
     * Reads the content of the country properties file from the file system and
     * returns it as a map.
     *
     * @param directory The path to the Sapor directory.
     * @return A map with the properties.
     */
    private static Map<String, String> readCountryPropertiesFileIntoMap(final String directory) {
        return FileSystemServices.readFileIntoMap(createFilePath(directory));
    }

    /**
     * Calculates the path to the country properties file in a Sapor directory.
     *
     * @param directory The path to the Sapor directory.
     * @return The path to the country properties file.
     */
    static String createFilePath(final String directory) {
        return directory + File.separator + COUNTRY_PROPERTIES_FILE_NAME;
    }
}
