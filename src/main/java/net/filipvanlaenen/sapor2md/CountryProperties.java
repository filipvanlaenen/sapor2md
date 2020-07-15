package net.filipvanlaenen.sapor2md;

import java.time.OffsetDateTime;

/**
 * Abstract class defining the behavior of the country properties and
 * implementing common functionality.
 */
public abstract class CountryProperties {
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
     * Returns the URL of the GitHub directory.
     *
     * @return The URL of the GitHub directory.
     */
    String getGitHubDirectoryURL() {
        return gitHubDirectoryURL;
    }

    /**
     * Sets the URL to the GitHub directory.
     *
     * @param gitHubDirectoryURL The URL to the GitHub directory.
     */
    protected void setGitHubDirectoryURL(final String gitHubDirectoryURL) {
        this.gitHubDirectoryURL = gitHubDirectoryURL;
    }

    /**
     * Returns the name of the parliament.
     *
     * @return The name of the parliament.
     */
    String getParliamentName() {
        return parliamentName;
    }

    /**
     * Sets the name of the parliament.
     *
     * @param parliamentName The name of the parliament.
     */
    protected void setParliamentName(final String parliamentName) {
        this.parliamentName = parliamentName;
    }

    /**
     * Returns the last modified timestamp of the file.
     *
     * @return The last modified timestamp.
     */
    OffsetDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the timestamp for the country property file.
     *
     * @param timestamp The timestamp for the country property file.
     */
    protected void setTimestamp(final OffsetDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
