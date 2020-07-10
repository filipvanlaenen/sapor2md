package net.filipvanlaenen.sapor2md;

import java.time.OffsetDateTime;

/**
 * Class implementing the <code>CountryProperties</code> interface, to be used
 * for testing purposes.
 */
public final class InMemoryCountryProperties implements CountryProperties {

    /**
     * The name of the GitHub directory.
     */
    private String gitHubDirectory;
    /**
     * The name of the parliament.
     */
    private String parliamentName;
    /**
     * The timestamp for the country properties file.
     */
    private OffsetDateTime timestamp;

    /**
     * Sets the name of the GitHub directory.
     *
     * @param gitHubDirectory
     *            The name of the GitHub directory.
     */
    void setGitHubDirectory(final String gitHubDirectory) {
        this.gitHubDirectory = gitHubDirectory;
    }

    @Override
    public String getGitHubDirectory() {
        return gitHubDirectory;
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
