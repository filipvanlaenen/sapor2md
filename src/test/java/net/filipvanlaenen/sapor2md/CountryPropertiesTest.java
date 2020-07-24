package net.filipvanlaenen.sapor2md;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests on the <code>CountryProperties</code> class.
 */
public class CountryPropertiesTest {
    /**
     * Magic number four.
     */
    private static final int FOUR = 4;
    /**
     * Magic number six.
     */
    private static final int SIX = 6;
    /**
     * Magic number seven.
     */
    private static final int SEVEN = 7;
    /**
     * Magic number 2020, used as a year number.
     */
    private static final int TWO_THOUSAND_AND_TWENTY = 2020;
    /**
     * A GitHub directory URL.
     */
    private static final String GITHUB_DIRECTORY_URL = "https://bar.github.io/foo_polls";
    /**
     * A copyright text.
     */
    private static final String COPYRIGHT_TEXT = "John Doe";
    /**
     * A parliament name.
     */
    private static final String PARLIAMENT_NAME = "Foo Parliament";
    /**
     * A background color.
     */
    private static final String BACKGROUND_COLOR = "#DDEEFF";
    /**
     * The background color as integer.
     */
    private static final int BACKGROUND_COLOR_AS_INT = 0xDDEEFF;
    /**
     * A text color.
     */
    private static final String TEXT_COLOR = "#112233";
    /**
     * The text color as integer.
     */
    private static final int TEXT_COLOR_AS_INT = 0x112233;

    /**
     * A country properties object for testing purposes.
     */
    private CountryProperties countryProperties;

    /**
     * Creates the in-memory country properties for the test.
     */
    @BeforeEach
    void createCountryProperties() {
        Map<String, String> map = new HashMap<String, String>();
        map.put(CountryProperties.COPYRIGHT_TEXT_KEY, COPYRIGHT_TEXT);
        map.put(CountryProperties.GITHUB_DIRECTORY_URL_KEY, GITHUB_DIRECTORY_URL);
        map.put(CountryProperties.NUMBER_OF_SEATS_KEY, Integer.toString(SIX));
        map.put(CountryProperties.PARLIAMENT_NAME_KEY, PARLIAMENT_NAME);
        map.put(CountryProperties.BACKGROUND_COLOR_KEY, BACKGROUND_COLOR);
        map.put(CountryProperties.TEXT_COLOR_KEY, TEXT_COLOR);
        LocalDateTime localDateTime = LocalDateTime.of(TWO_THOUSAND_AND_TWENTY, Month.JANUARY, 1, 0, 0);
        ZoneOffset offset = ZoneOffset.of("+01:00");
        OffsetDateTime timestamp = OffsetDateTime.of(localDateTime, offset);
        countryProperties = new InMemoryCountryProperties(map, timestamp);
    }

    /**
     * Verifying that the GitHub directory URL is wired correctly from the map.
     */
    @Test
    void constructorWiresGitHubDirectoryUrlCorrectly() {
        assertEquals(GITHUB_DIRECTORY_URL, countryProperties.getGitHubDirectoryURL());
    }

    /**
     * Verifying that the number of seats is wired correctly from the map.
     */
    @Test
    void constructorWiresNumberOfSeatsCorrectly() {
        assertEquals(SIX, countryProperties.getNumberOfSeats());
    }

    /**
     * Verifying that the number of seats for majority is calculated correctly for
     * an even number of seats.
     */
    @Test
    void numberOfSeatsForMajorityIsCalculatedCorrectlyForEvenNumberOfSeats() {
        assertEquals(FOUR, countryProperties.getNumberOfSeatsForMajority());
    }

    /**
     * Verifying that the number of seats for majority is calculated correctly for
     * an odd number of seats.
     */
    @Test
    void numberOfSeatsForMajorityIsCalculatedCorrectlyForOddNumberOfSeats() {
        Map<String, String> map = new HashMap<String, String>();
        map.put(CountryProperties.NUMBER_OF_SEATS_KEY, Integer.toString(SEVEN));
        map.put(CountryProperties.BACKGROUND_COLOR_KEY, BACKGROUND_COLOR);
        map.put(CountryProperties.TEXT_COLOR_KEY, TEXT_COLOR);
        CountryProperties otherCountryProperties = new InMemoryCountryProperties(map, null);
        assertEquals(FOUR, otherCountryProperties.getNumberOfSeatsForMajority());
    }

    /**
     * Verifying that the copyright text is wired correctly from the map.
     */
    @Test
    void constructorWiresCopyrightTextCorrectly() {
        assertEquals(COPYRIGHT_TEXT, countryProperties.getCopyrightText());
    }

    /**
     * Verifying that the parliament name is wired correctly from the map.
     */
    @Test
    void constructorWiresParliamentNameCorrectly() {
        assertEquals(PARLIAMENT_NAME, countryProperties.getParliamentName());
    }

    /**
     * Verifying that the text color is wired correctly from the map.
     */
    @Test
    void constructorWiresTextColorCorrectly() {
        assertEquals(TEXT_COLOR_AS_INT, countryProperties.getTextColor());
    }

    /**
     * Verifying that the background color is wired correctly from the map.
     */
    @Test
    void constructorWiresBackgroundColorCorrectly() {
        assertEquals(BACKGROUND_COLOR_AS_INT, countryProperties.getBackgroundColor());
    }
}
