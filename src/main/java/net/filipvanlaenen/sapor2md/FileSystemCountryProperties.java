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

    private static OffsetDateTime readTimestamp(String directory) {
        return FileSystemServices.getTimestamp(createFilePath(directory));
    }

    private static Map<String, String> readCountryPropertiesFileIntoMap(final String directory) {
        return FileSystemServices.readFileIntoMap(createFilePath(directory));
    }

    static String createFilePath(final String directory) {
        return directory + File.separator + COUNTRY_PROPERTIES_FILE_NAME;
    }
}
