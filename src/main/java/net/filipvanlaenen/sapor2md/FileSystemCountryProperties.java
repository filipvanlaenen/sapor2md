package net.filipvanlaenen.sapor2md;

import java.io.File;
import java.time.OffsetDateTime;
import java.util.Map;

/**
 * Class implementing the <code>CountryProperties</code> interface against a
 * file system.
 */
public final class FileSystemCountryProperties implements CountryProperties {

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
     * The URL to the GitHub directory.
     */
    private final String gitHubDirectoryURL;
    /**
     * The name of the parliament.
     */
    private final String parliamentName;

    /**
     * Constructor taking the path to the Sapor directory as the argument.
     *
     * @param directory
     *            The path to the Sapor directory.
     */
    FileSystemCountryProperties(final String directory) {
        filePath = directory + File.separator + COUNTRY_PROPERTIES_FILE_NAME;
        Map<String, String> map = FileSystemServices.readFileIntoMap(filePath);
        this.gitHubDirectoryURL = map.get(GITHUB_DIRECTORY_URL_KEY);
        this.parliamentName = map.get(PARLIAMENT_NAME_KEY);
    }

    @Override
    public String getGitHubDirectoryURL() {
        return gitHubDirectoryURL;
    }

    @Override
    public String getParliamentName() {
        return parliamentName;
    }

    @Override
    public OffsetDateTime getTimestamp() {
        return FileSystemServices.getTimestamp(filePath);
    }

}
