package net.filipvanlaenen.sapor2md;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import net.filipvanlaenen.tsvgj.StructuralElement;

/**
 * Unit tests on the <code>Chart</code> class.
 */
public class ChartTest {
    /**
     * A background color.
     */
    private static final String BACKGROUND_COLOR = "#DDEEFF";
    /**
     * A text color.
     */
    private static final String TEXT_COLOR = "#112233";

    /**
     * Test verifying the content of the copyright notice text when the user hasn't
     * provided a name.
     */
    @Test
    void defaultCopyrightNoticeTextIsCorrect() {
        TimeServices.setClock(LocalDate.of(2019, Month.JANUARY, 1));
        Chart chart = createChart("John Doe");
        assertEquals("© 2019 John Doe, chart produced using Sapor2MD", chart.getCopyrightNoticeText());
    }

    /**
     * Test verifying that the content of the copyright notice text when the user
     * has provided a name.
     */
    @Test
    void customCopyrightNoticeTextIsCorrect() {
        Chart chart = createChart(null);
        assertEquals("Chart produced using Sapor2MD", chart.getCopyrightNoticeText());
    }

    private Chart createChart(final String copyrightText) {
        Map<String, String> map = new HashMap<String, String>();
        map.put(CountryProperties.NUMBER_OF_SEATS_KEY, "2");
        map.put(CountryProperties.BACKGROUND_COLOR_KEY, BACKGROUND_COLOR);
        map.put(CountryProperties.TEXT_COLOR_KEY, TEXT_COLOR);
        if (copyrightText != null) {
            map.put(CountryProperties.COPYRIGHT_TEXT_KEY, copyrightText);
        }
        CountryProperties countryProperties = new InMemoryCountryProperties(map, null);
        SaporDirectory directory = new InMemorySaporDirectory(countryProperties);
        Map<String, String> properties = new HashMap<String, String>();
        properties.put(Poll.FIELDWORK_START_KEY, "2020-01-02");
        properties.put(Poll.FIELDWORK_END_KEY, "2020-01-03");
        Poll poll = new InMemoryPoll("2020-01-03-Baz", properties);
        Chart chart = new Chart(directory, poll) {

            @Override
            String getFileNameSuffix() {
                return null;
            }

            @Override
            protected String getTitleText() {
                return null;
            }

            @Override
            protected String getSubtitleText() {
                return null;
            }

            @Override
            protected StructuralElement createChartContent() {
                return null;
            }

            @Override
            protected double calculateContentHeight() {
                return 0;
            }

            @Override
            protected double calculateContentWidth() {
                return 0;
            }
        };
        return chart;
    }
}
