package net.filipvanlaenen.sapor2md;

import java.time.OffsetDateTime;

/**
 * Interface defining the behavior of the country properties.
 */
public interface CountryProperties {

    /**
     * Returns the URL of the GitHub directory.
     *
     * @return The URL of the GitHub directory.
     */
    String getGitHubDirectoryURL();

    /**
     * Returns the name of the parliament.
     *
     * @return The name of the parliament.
     */
    String getParliamentName();

    /**
     * Returns the last modified timestamp of the file.
     *
     * @return The last modified timestamp.
     */
    OffsetDateTime getTimestamp();

}
