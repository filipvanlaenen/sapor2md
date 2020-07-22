package net.filipvanlaenen.sapor2md;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

/**
 * Unit tests on the <code>VotingIntentionsChart</code> class.
 */
public class VotingIntentionsChartTest {
    /**
     * Magic number six.
     */
    private static final int SIX = 6;
    /**
     * Magic number 2020, used as a year number.
     */
    private static final int TWO_THOUSAND_AND_TWENTY = 2020;

    /**
     * Test verifying that the file name is calculated correctly.
     */
    @Test
    void fileNameShouldHaveNoSuffix() {
        Map<String, String> map = new HashMap<String, String>();
        map.put(CountryProperties.NUMBER_OF_SEATS_KEY, Integer.toString(SIX));
        LocalDateTime localDateTime = LocalDateTime.of(TWO_THOUSAND_AND_TWENTY, Month.JANUARY, 1, 0, 0);
        ZoneOffset offset = ZoneOffset.of("+01:00");
        OffsetDateTime timestamp = OffsetDateTime.of(localDateTime, offset);
        CountryProperties countryProperties = new InMemoryCountryProperties(map, timestamp);
        SaporDirectory directory = new InMemorySaporDirectory(countryProperties);
        Map<String, String> properties = new HashMap<String, String>();
        properties.put(Poll.FIELDWORK_START_KEY, "2020-01-02");
        properties.put(Poll.FIELDWORK_END_KEY, "2020-01-03");
        Poll poll = new InMemoryPoll("2020-01-03-Baz", properties);
        VotingIntentionsChart chart = new VotingIntentionsChart(directory, poll);
        assertEquals("2020-01-03-Baz.svg", chart.getFileName());
    }
}
