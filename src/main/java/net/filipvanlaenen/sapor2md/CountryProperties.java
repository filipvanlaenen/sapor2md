package net.filipvanlaenen.sapor2md;

import java.time.OffsetDateTime;
import java.util.Map;

/**
 * Abstract class defining the behavior of the country properties and
 * implementing common functionality.
 */
public abstract class CountryProperties {
    /**
     * The key for the background color.
     */
    static final String BACKGROUND_COLOR_KEY = "BackgroundColor";
    /**
     * The key for the copyright text.
     */
    static final String COPYRIGHT_TEXT_KEY = "CopyrightText";
    /**
     * The key for the property containing the URL to the GitHub directory.
     */
    static final String GITHUB_DIRECTORY_URL_KEY = "GitHubDirectoryURL";
    /**
     * The key for the number of seats.
     */
    static final String NUMBER_OF_SEATS_KEY = "NumberOfSeats";
    /**
     * The key for the parliament name.
     */
    static final String PARLIAMENT_NAME_KEY = "ParliamentName";
    /**
     * The key for the text color.
     */
    static final String TEXT_COLOR_KEY = "TextColor";

    /**
     * The copyirght text.
     */
    private final String copyrightText;
    /**
     * The background color.
     */
    private final int backgroundColor;
    /**
     * The URL to the GitHub directory.
     */
    private final String gitHubDirectoryURL;
    /**
     * The number of seats in the parliament.
     */
    private final int numberOfSeats;
    /**
     * The name of the parliament.
     */
    private final String parliamentName;
    /**
     * The text color.
     */
    private final int textColor;
    /**
     * The timestamp for the country properties file.
     */
    private final OffsetDateTime timestamp;

    /**
     * Constructor taking a map with the properties and a timestamp as its
     * parameters.
     *
     * @param map       The map containing the country properties.
     * @param timestamp The last modified timestamp of the file.
     */
    CountryProperties(final Map<String, String> map, final OffsetDateTime timestamp) {
        backgroundColor = parseHexadecimalColorCode(map.get(BACKGROUND_COLOR_KEY));
        copyrightText = map.get(COPYRIGHT_TEXT_KEY);
        gitHubDirectoryURL = map.get(GITHUB_DIRECTORY_URL_KEY);
        numberOfSeats = Integer.parseInt(map.get(NUMBER_OF_SEATS_KEY));
        parliamentName = map.get(PARLIAMENT_NAME_KEY);
        textColor = parseHexadecimalColorCode(map.get(TEXT_COLOR_KEY));
        this.timestamp = timestamp;
    }

    /**
     * Parses a hexadecimal color code into an integer.
     *
     * @param hex The hexadecimal color code.
     * @return The integer value.
     */
    private int parseHexadecimalColorCode(final String hex) {
        return Integer.parseInt(hex.substring(1, 7), 16);
    }

    /**
     * Returns the background color.
     *
     * @return The background color.
     */
    int getBackgroundColor() {
        return backgroundColor;
    }

    /**
     * Returns the copyright text.
     *
     * @return The copyright text.
     */
    String getCopyrightText() {
        return copyrightText;
    }

    /**
     * Returns the URL of the GitHub directory.
     *
     * @return The URL of the GitHub directory.
     */
    String getGitHubDirectoryURL() {
        return gitHubDirectoryURL;
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
     * Returns the text color.
     *
     * @return The text color.
     */
    int getTextColor() {
        return textColor;
    }

    /**
     * Returns the last modified timestamp of the file.
     *
     * @return The last modified timestamp.
     */
    OffsetDateTime getTimestamp() {
        return timestamp;
    }

}
