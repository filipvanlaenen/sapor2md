package net.filipvanlaenen.sapor2md;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests on the <code>Poll</code> class.
 */
public class PollTest {
    /**
     * A GitHub directory URL.
     */
    private static final String POLLING_FIRM = "Baz";
    /**
     * A GitHub directory URL.
     */
    private static final String COMMISSIONERS = "Qux";
    /**
     * Magic number 2020, used as a year number.
     */
    private static final int TWO_THOUSAND_AND_TWENTY = 2020;
    /**
     * Local date representing 2 January 2020.
     */
    private static final LocalDate SECOND_OF_JANUARY_2020 = LocalDate.of(TWO_THOUSAND_AND_TWENTY, Month.JANUARY, 2);
    /**
     * Local date representing 3 January 2020.
     */
    private static final LocalDate THIRD_OF_JANUARY_2020 = LocalDate.of(TWO_THOUSAND_AND_TWENTY, Month.JANUARY, 3);

    /**
     * A poll object for testing purposes.
     */
    private Poll poll;

    /**
     * Creates the in-memory poll for the test.
     */
    @BeforeEach
    void createPollProperties() {
        Map<String, String> properties = new HashMap<String, String>();
        properties.put(Poll.POLLING_FIRM_KEY, POLLING_FIRM);
        properties.put(Poll.COMMISSIONERS_KEY, COMMISSIONERS);
        properties.put(Poll.FIELDWORK_START_KEY, "2020-01-02");
        properties.put(Poll.FIELDWORK_END_KEY, "2020-01-03");
        poll = new InMemoryPoll("2020-01-03-Baz", properties);
    }

    /**
     * Verifying that the polling firm is wired correctly from the map.
     */
    @Test
    void constructorWiresPollingFirmCorrectly() {
        assertEquals(POLLING_FIRM, poll.getPollingFirm());
    }

    /**
     * Verifying that the commissioners are wired correctly from the map.
     */
    @Test
    void constructorWiresCommissionersCorrectly() {
        assertEquals(COMMISSIONERS, poll.getComissioners());
    }

    /**
     * Verifying that the fieldwork start is wired correctly from the map.
     */
    @Test
    void constructorWiresFieldworkStartCorrectly() {
        assertEquals(SECOND_OF_JANUARY_2020, poll.getFieldworkStart());
    }

    /**
     * Verifying that the fieldwork end is wired correctly from the map.
     */
    @Test
    void constructorWiresFieldworkEndCorrectly() {
        assertEquals(THIRD_OF_JANUARY_2020, poll.getFieldworkEnd());
    }
}
