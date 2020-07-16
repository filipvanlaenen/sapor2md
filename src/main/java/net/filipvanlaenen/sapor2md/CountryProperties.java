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
     * The number of seats in the parliament.
     */
    private int numberOfSeats;
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
    void setGitHubDirectoryURL(final String gitHubDirectoryURL) {
        this.gitHubDirectoryURL = gitHubDirectoryURL;
    }

    /**
     * Returns the number of seats.
     *
     * @return The number of seats.
     */
    int getNumberOfSeats() {
        return numberOfSeats;
    }

    /**
     * Sets the number of seats in the parliament.
     *
     * @param numberOfSeats The number of seats in the parliament.
     */
    void setNumberOfSeats(final int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    /**
     * Returns the number of seats needed for a majority.
     *
     * @return The number of seats needed for a majority.
     */
    int getNumberOfSeatsForMajority() {
        return numberOfSeats / 2 + 1;
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
    void setParliamentName(final String parliamentName) {
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
    void setTimestamp(final OffsetDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
