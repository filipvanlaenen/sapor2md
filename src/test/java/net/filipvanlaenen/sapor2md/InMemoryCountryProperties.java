package net.filipvanlaenen.sapor2md;

import java.time.OffsetDateTime;

/**
 * Class implementing the <code>CountryProperties</code> interface, to be used
 * for testing purposes.
 */
public final class InMemoryCountryProperties implements CountryProperties {

    /**
     * The URL to the GitHub directory.
     */
    private String gitHubDirectoryURL;
    /**
     * The name of the parliament.
     */
    private String parliamentName;
    /**
     * The timestamp for the country properties file.
     */
    private OffsetDateTime timestamp;

    /**
     * Sets the URL to the GitHub directory.
     *
     * @param gitHubDirectoryURL
     *            The URL to the GitHub directory.
     */
    void setGitHubDirectoryURL(final String gitHubDirectoryURL) {
        this.gitHubDirectoryURL = gitHubDirectoryURL;
    }

    @Override
    public String getGitHubDirectoryURL() {
        return gitHubDirectoryURL;
    }

    /**
     * Sets the name of the parliament.
     *
     * @param parliamentName
     *            The name of the parliament.
     */
    void setParliamentName(final String parliamentName) {
        this.parliamentName = parliamentName;
    }

    @Override
    public String getParliamentName() {
        return parliamentName;
    }

    /**
     * Sets the timestamp for the country property file.
     *
     * @param timestamp
     *            The timestamp for the country property file.
     */
    void setTimestamp(final OffsetDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public OffsetDateTime getTimestamp() {
        return timestamp;
    }

}
