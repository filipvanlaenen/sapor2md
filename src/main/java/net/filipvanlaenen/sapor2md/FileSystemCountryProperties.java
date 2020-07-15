package net.filipvanlaenen.sapor2md;

import java.io.File;
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
     * The key for the property containing the URL to the GitHub directory.
     */
    private static final String GITHUB_DIRECTORY_URL_KEY = "GitHubDirectoryURL";
    /**
     * The key for the parliament name.
     */
    private static final String PARLIAMENT_NAME_KEY = "ParliamentName";

    /**
     * The path to the country properties file.
     */
    private final String filePath;

    /**
     * Constructor taking the path to the Sapor directory as the argument.
     *
     * @param directory The path to the Sapor directory.
     */
    FileSystemCountryProperties(final String directory) {
        filePath = directory + File.separator + COUNTRY_PROPERTIES_FILE_NAME;
        Map<String, String> map = FileSystemServices.readFileIntoMap(filePath);
        setGitHubDirectoryURL(map.get(GITHUB_DIRECTORY_URL_KEY));
        setParliamentName(map.get(PARLIAMENT_NAME_KEY));
        setTimestamp(FileSystemServices.getTimestamp(filePath));
    }
}
