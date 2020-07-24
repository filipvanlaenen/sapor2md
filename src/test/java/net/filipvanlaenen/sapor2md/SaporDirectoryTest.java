package net.filipvanlaenen.sapor2md;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests on the <code>SaporDirectory</code> class.
 */
public class SaporDirectoryTest {
    /**
     * A background color.
     */
    private static final String BACKGROUND_COLOR = "#DDEEFF";
    /**
     * A text color.
     */
    private static final String TEXT_COLOR = "#112233";
    /**
     * The country properties to be used by the tests.
     */
    private CountryProperties countryProperties;

    /**
     * Creates country properties to be used by the tests.
     */
    @BeforeEach
    void createCountryProperties() {
        Map<String, String> map = new HashMap<String, String>();
        map.put(CountryProperties.NUMBER_OF_SEATS_KEY, Integer.toString(1));
        map.put(CountryProperties.BACKGROUND_COLOR_KEY, BACKGROUND_COLOR);
        map.put(CountryProperties.TEXT_COLOR_KEY, TEXT_COLOR);
        countryProperties = new InMemoryCountryProperties(map, null);
    }

    /**
     * Verifying that the polls are returned and sorted in chronologically
     * descending order, sorted by the end date of the fieldwork period.
     */
    @Test
    void getPollsSortsPolls() {
        InMemorySaporDirectory directory = new InMemorySaporDirectory(countryProperties);
        Map<String, String> properties1 = new HashMap<String, String>();
        properties1.put(Poll.FIELDWORK_START_KEY, "2020-01-01");
        properties1.put(Poll.FIELDWORK_END_KEY, "2020-01-01");
        InMemoryPoll poll1 = new InMemoryPoll("2020-01-01-Baz", properties1);
        Map<String, String> properties2 = new HashMap<String, String>();
        properties2.put(Poll.FIELDWORK_START_KEY, "2020-01-02");
        properties2.put(Poll.FIELDWORK_END_KEY, "2020-01-02");
        InMemoryPoll poll2 = new InMemoryPoll("2020-01-02-Baz", properties2);
        directory.addPoll(poll1);
        directory.addPoll(poll2);
        assertEquals(poll2, directory.getSortedPolls().next());
    }

    /**
     * Verifying that the comparator sorts the polls in chronologically descending
     * order, sorted by the end date of the fieldwork period.
     */
    @Test
    void comparePollsSortsPollsChronologicallyReversedByFieldworkEndDate() {
        InMemorySaporDirectory directory = new InMemorySaporDirectory(countryProperties);
        Map<String, String> properties1 = new HashMap<String, String>();
        properties1.put(Poll.FIELDWORK_START_KEY, "2020-01-01");
        properties1.put(Poll.FIELDWORK_END_KEY, "2020-01-01");
        InMemoryPoll poll1 = new InMemoryPoll("2020-01-01-Baz", properties1);
        Map<String, String> properties2 = new HashMap<String, String>();
        properties2.put(Poll.FIELDWORK_START_KEY, "2020-01-02");
        properties2.put(Poll.FIELDWORK_END_KEY, "2020-01-02");
        InMemoryPoll poll2 = new InMemoryPoll("2020-01-02-Baz", properties2);
        directory.addPoll(poll1);
        directory.addPoll(poll2);
        assertTrue(directory.comparePolls(poll1, poll2) > 0);
    }

    /**
     * Verifying that the comparator sorts the polls in chronologically descending
     * order, sorted by the start date of the fieldwork period if the end dates are
     * equal.
     */
    @Test
    void comparePollsSortsPollsChronologicallyReversedByFieldworkEndAndStartDate() {
        InMemorySaporDirectory directory = new InMemorySaporDirectory(countryProperties);
        Map<String, String> properties1 = new HashMap<String, String>();
        properties1.put(Poll.FIELDWORK_START_KEY, "2020-01-01");
        properties1.put(Poll.FIELDWORK_END_KEY, "2020-01-02");
        InMemoryPoll poll1 = new InMemoryPoll("2020-01-02-Baz", properties1);
        Map<String, String> properties2 = new HashMap<String, String>();
        properties2.put(Poll.FIELDWORK_START_KEY, "2020-01-02");
        properties2.put(Poll.FIELDWORK_END_KEY, "2020-01-02");
        InMemoryPoll poll2 = new InMemoryPoll("2020-01-02-Qux", properties2);
        directory.addPoll(poll1);
        directory.addPoll(poll2);
        assertTrue(directory.comparePolls(poll1, poll2) > 0);
    }

    /**
     * Verifying that the comparator sorts the polls in chronologically descending
     * order, sorted by the polling firm name if the fieldwork period start and end
     * dates are equal.
     */
    @Test
    void comparePollsSortsPollsAlphabeticallyIfFieldworkPeriodsAreEqual() {
        InMemorySaporDirectory directory = new InMemorySaporDirectory(countryProperties);
        Map<String, String> properties1 = new HashMap<String, String>();
        properties1.put(Poll.FIELDWORK_START_KEY, "2020-01-01");
        properties1.put(Poll.FIELDWORK_END_KEY, "2020-01-01");
        properties1.put(Poll.POLLING_FIRM_KEY, "Qux");
        InMemoryPoll poll1 = new InMemoryPoll("2020-01-02-Qux", properties1);
        Map<String, String> properties2 = new HashMap<String, String>();
        properties2.put(Poll.FIELDWORK_START_KEY, "2020-01-01");
        properties2.put(Poll.FIELDWORK_END_KEY, "2020-01-01");
        properties2.put(Poll.POLLING_FIRM_KEY, "Baz");
        InMemoryPoll poll2 = new InMemoryPoll("2020-01-02-Baz", properties2);
        directory.addPoll(poll1);
        directory.addPoll(poll2);
        assertTrue(directory.comparePolls(poll1, poll2) > 0);
    }
}
