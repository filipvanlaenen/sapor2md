package net.filipvanlaenen.sapor2md;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests on the <code>SaporDirectory</code> class.
 */
public class SaporDirectoryTest {
    /**
     * Magic number 2020, used as a year number.
     */
    private static final int TWO_THOUSAND_AND_TWENTY = 2020;
    /**
     * Local date representing 1 January 2020.
     */
    private static final LocalDate FIRST_OF_JANUARY_2020 = LocalDate.of(TWO_THOUSAND_AND_TWENTY, Month.JANUARY, 1);
    /**
     * Local date representing 2 January 2020.
     */
    private static final LocalDate SECOND_OF_JANUARY_2020 = LocalDate.of(TWO_THOUSAND_AND_TWENTY, Month.JANUARY, 2);
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
        countryProperties = new InMemoryCountryProperties(map, null);
    }

    /**
     * Verifying that the polls are returned and sorted in chronologically
     * descending order, sorted by the end date of the fieldwork period.
     */
    @Test
    void getPollsSortsPolls() {
        InMemorySaporDirectory directory = new InMemorySaporDirectory(countryProperties);
        InMemoryPoll poll1 = new InMemoryPoll("2020-01-01-Baz");
        poll1.setFieldworkEnd(FIRST_OF_JANUARY_2020);
        InMemoryPoll poll2 = new InMemoryPoll("2020-01-02-Baz");
        poll2.setFieldworkEnd(SECOND_OF_JANUARY_2020);
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
        InMemoryPoll poll1 = new InMemoryPoll("2020-01-01-Baz");
        poll1.setFieldworkEnd(FIRST_OF_JANUARY_2020);
        InMemoryPoll poll2 = new InMemoryPoll("2020-01-02-Baz");
        poll2.setFieldworkEnd(SECOND_OF_JANUARY_2020);
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
        InMemoryPoll poll1 = new InMemoryPoll("2020-01-02-Baz");
        poll1.setFieldworkStart(FIRST_OF_JANUARY_2020);
        poll1.setFieldworkEnd(SECOND_OF_JANUARY_2020);
        InMemoryPoll poll2 = new InMemoryPoll("2020-01-02-Qux");
        poll2.setFieldworkStart(SECOND_OF_JANUARY_2020);
        poll2.setFieldworkEnd(SECOND_OF_JANUARY_2020);
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
        InMemoryPoll poll1 = new InMemoryPoll("2020-01-02-Qux");
        poll1.setFieldworkStart(FIRST_OF_JANUARY_2020);
        poll1.setFieldworkEnd(FIRST_OF_JANUARY_2020);
        poll1.setPollingFirm("Qux");
        InMemoryPoll poll2 = new InMemoryPoll("2020-01-02-Baz");
        poll2.setFieldworkStart(FIRST_OF_JANUARY_2020);
        poll2.setFieldworkEnd(FIRST_OF_JANUARY_2020);
        poll2.setPollingFirm("Baz");
        directory.addPoll(poll1);
        directory.addPoll(poll2);
        assertTrue(directory.comparePolls(poll1, poll2) > 0);
    }
}
