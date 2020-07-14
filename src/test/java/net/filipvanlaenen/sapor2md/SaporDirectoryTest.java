package net.filipvanlaenen.sapor2md;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.Month;

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
     * Verifying that the polls are returned in chronologically descending order,
     * sorted by the end date of the fieldwork period.
     */
    @Test
    void getPollsSortsPollsChronologicallyReversedByFieldworkEndDate() {
        InMemorySaporDirectory directory = new InMemorySaporDirectory(new InMemoryCountryProperties());
        InMemoryPoll poll1 = new InMemoryPoll("2020-01-01-Baz");
        poll1.setFieldworkEnd(LocalDate.of(TWO_THOUSAND_AND_TWENTY, Month.JANUARY, 1));
        InMemoryPoll poll2 = new InMemoryPoll("2020-01-02-Baz");
        poll2.setFieldworkEnd(LocalDate.of(TWO_THOUSAND_AND_TWENTY, Month.JANUARY, 2));
        directory.addPoll(poll1);
        directory.addPoll(poll2);
        assertEquals(poll2, directory.getPolls().next());
    }

    /**
     * Verifying that the polls are returned in chronologically descending order,
     * sorted by the start date of the fieldwork period if the end dates are equal.
     */
    @Test
    void getPollsSortsPollsChronologicallyReversedByFieldworkEndAndStartDate() {
        InMemorySaporDirectory directory = new InMemorySaporDirectory(new InMemoryCountryProperties());
        InMemoryPoll poll1 = new InMemoryPoll("2020-01-02-Baz");
        poll1.setFieldworkStart(LocalDate.of(TWO_THOUSAND_AND_TWENTY, Month.JANUARY, 1));
        poll1.setFieldworkEnd(LocalDate.of(TWO_THOUSAND_AND_TWENTY, Month.JANUARY, 2));
        InMemoryPoll poll2 = new InMemoryPoll("2020-01-02-Qux");
        poll2.setFieldworkStart(LocalDate.of(TWO_THOUSAND_AND_TWENTY, Month.JANUARY, 2));
        poll2.setFieldworkEnd(LocalDate.of(TWO_THOUSAND_AND_TWENTY, Month.JANUARY, 2));
        directory.addPoll(poll1);
        directory.addPoll(poll2);
        assertEquals(poll2, directory.getPolls().next());
    }

    /**
     * Verifying that the polls are returned in chronologically descending order,
     * sorted by the polling firm name if the fieldwork period start and end dates
     * are equal.
     */
    @Test
    void getPollsSortsPollsAlphabeticallyIfFieldworkPeriodsAreEqual() {
        InMemorySaporDirectory directory = new InMemorySaporDirectory(new InMemoryCountryProperties());
        InMemoryPoll poll1 = new InMemoryPoll("2020-01-02-Qux");
        poll1.setFieldworkStart(LocalDate.of(TWO_THOUSAND_AND_TWENTY, Month.JANUARY, 1));
        poll1.setFieldworkEnd(LocalDate.of(TWO_THOUSAND_AND_TWENTY, Month.JANUARY, 1));
        poll1.setPollingFirm("Qux");
        InMemoryPoll poll2 = new InMemoryPoll("2020-01-02-Baz");
        poll2.setFieldworkStart(LocalDate.of(TWO_THOUSAND_AND_TWENTY, Month.JANUARY, 1));
        poll2.setFieldworkEnd(LocalDate.of(TWO_THOUSAND_AND_TWENTY, Month.JANUARY, 1));
        poll2.setPollingFirm("Baz");
        directory.addPoll(poll1);
        directory.addPoll(poll2);
        assertEquals(poll2, directory.getPolls().next());
    }

}
