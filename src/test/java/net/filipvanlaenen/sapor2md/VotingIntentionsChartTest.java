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
 * Unit tests on the <code>VotingIntentionsChart</code> class.
 */
public class VotingIntentionsChartTest {
    private static final String GREEN_PARTY = "Green Party";
    /**
     * Magic number six.
     */
    private static final int SIX = 6;
    /**
     * Magic number 2020, used as a year number.
     */
    private static final int TWO_THOUSAND_AND_TWENTY = 2020;
    /**
     * A parliament name.
     */
    private static final String PARLIAMENT_NAME = "Foo Parliament";
    /**
     * A background color.
     */
    private static final String BACKGROUND_COLOR = "#DDEEFF";
    /**
     * A text color.
     */
    private static final String TEXT_COLOR = "#112233";
    /**
     * Lower bound for the 95 percent confidence interval for the red party.
     */
    private static final double RED_PARTY_CONFIDENCE_INTERVAL_LOWER_BOUND = 0.149D;
    /**
     * Upper bound for the 95 percent confidence interval for the red party.
     */
    private static final double RED_PARTY_CONFIDENCE_INTERVAL_UPPER_BOUND = 0.195D;
    /**
     * Lower bound for the 95 percent confidence interval for the green party.
     */
    private static final double GREEN_PARTY_CONFIDENCE_INTERVAL_LOWER_BOUND = 0.100D;
    /**
     * Upper bound for the 95 percent confidence interval for the green party.
     */
    private static final double GREEN_PARTY_CONFIDENCE_INTERVAL_UPPER_BOUND = 0.140D;
    private static final double DELTA = 0.001D;

    /**
     * The chart to run the tests on.
     */
    private VotingIntentionsChart chart;

    /**
     * Creates a voting intentions chart to run the tests on.
     */
    @BeforeEach
    void createChart() {
        Map<String, String> map = new HashMap<String, String>();
        map.put(CountryProperties.NUMBER_OF_SEATS_KEY, Integer.toString(SIX));
        map.put(CountryProperties.PARLIAMENT_NAME_KEY, PARLIAMENT_NAME);
        map.put(CountryProperties.BACKGROUND_COLOR_KEY, BACKGROUND_COLOR);
        map.put(CountryProperties.TEXT_COLOR_KEY, TEXT_COLOR);
        LocalDateTime localDateTime = LocalDateTime.of(TWO_THOUSAND_AND_TWENTY, Month.JANUARY, 1, 0, 0);
        ZoneOffset offset = ZoneOffset.of("+01:00");
        OffsetDateTime timestamp = OffsetDateTime.of(localDateTime, offset);
        CountryProperties countryProperties = new InMemoryCountryProperties(map, timestamp);
        SaporDirectory directory = new InMemorySaporDirectory(countryProperties);
        Map<String, String> properties = new HashMap<String, String>();
        properties.put(Poll.POLLING_FIRM_KEY, "Baz");
        properties.put(Poll.FIELDWORK_START_KEY, "2020-01-02");
        properties.put(Poll.FIELDWORK_END_KEY, "2020-01-03");
        Poll poll = new InMemoryPoll("2020-01-03-Baz", properties);
        VotingIntentions votingIntentions = new VotingIntentions("Red Party",
                VotingIntentionsTestServices.createProbabilityMassFunctionForConfidenceInterval(
                        RED_PARTY_CONFIDENCE_INTERVAL_LOWER_BOUND, RED_PARTY_CONFIDENCE_INTERVAL_UPPER_BOUND),
                GREEN_PARTY, VotingIntentionsTestServices.createProbabilityMassFunctionForConfidenceInterval(
                        GREEN_PARTY_CONFIDENCE_INTERVAL_LOWER_BOUND, GREEN_PARTY_CONFIDENCE_INTERVAL_UPPER_BOUND));
        poll.setVotingIntentions(votingIntentions);
        chart = new VotingIntentionsChart(directory, poll);
    }

    /**
     * Test verifying that the file name is calculated correctly.
     */
    @Test
    void fileNameShouldHaveNoSuffix() {
        assertEquals("2020-01-03-Baz.svg", chart.getFileName());
    }

    /**
     * Test verifying that the number of groups is calculated correctly.
     */
    @Test
    void numberOfGroupsShouldBeCorrect() {
        assertEquals(2, chart.getNumberOfGroups());
    }

    /**
     * Test verifying that the title text is correct.
     */
    @Test
    void titleTextShouldBeCorrect() {
        assertEquals("Voting Intentions for the Foo Parliament", chart.getTitleText());
    }

    /**
     * Test verifying the correct calculation of the content width.
     */
    @Test
    void contentWidthShouldBeCorrect() {
        double expected = 2d * Chart.SPACE_BETWEEN_ELEMENTS + VotingIntentionsChart.MAX_CHOICE_WIDTH
                + (GREEN_PARTY.length() + +"15–20%".length()) * HorizontalBarChart.CHOICE_LABEL_FONT_SIZE
                        * Chart.M_WIDTH;
        assertEquals(expected, chart.calculateContentWidth(), DELTA);
    }

    /**
     * Test verifying the SVG content of the chart.
     */
    @Test
    void svgContentShouldBeCorrect() {
        StringBuilder sb = new StringBuilder();
        sb.append("<svg height=\"414\" viewBox=\"0 0 1500.444 414\" width=\"1500.444\"");
        sb.append(" xmlns=\"http://www.w3.org/2000/svg\">\n");
        sb.append("  <rect fill=\"#DDEEFF\" height=\"414\" stroke=\"none\" width=\"1500.444\" x=\"0\" y=\"0\"/>\n");
        sb.append("  <text fill=\"#112233\" font-family=\"Lato\" font-size=\"46\" font-style=\"normal\"");
        sb.append(" font-weight=\"bold\" text-align=\"center\" text-anchor=\"middle\" x=\"750.222\" y=\"66\">Voting");
        sb.append(" Intentions for the Foo Parliament</text>\n");
        sb.append("  <text fill=\"#112233\" font-family=\"Lato\" font-size=\"28\" font-style=\"normal\"");
        sb.append(" font-weight=\"bold\" text-align=\"center\" text-anchor=\"middle\" x=\"750.222\" y=\"114\">Based");
        sb.append(" on an Opinion Poll by Baz, 2–3 January 2020</text>\n");
        sb.append("  <text fill=\"#112233\" font-family=\"Lato\" font-size=\"10\" font-style=\"normal\"");
        sb.append(" font-weight=\"normal\" text-align=\"center\" text-anchor=\"end\" transform=\"rotate(270)\"");
        sb.append(" x=\"-4\" y=\"1496.444\">Chart produced using Sapor2MD</text>\n");
        sb.append("  <defs>\n");
        sb.append("    <pattern id=\"pattern-1\" height=\"10\" patternTransform=\"rotate(45 0,0)\"");
        sb.append(" patternUnits=\"userSpaceOnUse\" width=\"10\">\n");
        sb.append("      <line stroke=\"#000000\" stroke-width=\"6.666667\" x1=\"0\" x2=\"0\" y1=\"0\" y2=\"10\"/>\n");
        sb.append("    </pattern>\n");
        sb.append("    <pattern id=\"pattern-2\" height=\"10\" patternTransform=\"rotate(135 0,0)\"");
        sb.append(" patternUnits=\"userSpaceOnUse\" width=\"10\">\n");
        sb.append("      <line stroke=\"#000000\" stroke-width=\"6.666667\" x1=\"0\" x2=\"0\" y1=\"0\" y2=\"10\"/>\n");
        sb.append("    </pattern>\n");
        sb.append("    <pattern id=\"pattern-3\" height=\"10\" patternTransform=\"rotate(45 0,0)\"");
        sb.append(" patternUnits=\"userSpaceOnUse\" width=\"10\">\n");
        sb.append("      <line stroke=\"#000000\" stroke-width=\"6.666667\" x1=\"0\" x2=\"0\" y1=\"0\" y2=\"10\"/>\n");
        sb.append("    </pattern>\n");
        sb.append("    <pattern id=\"pattern-4\" height=\"10\" patternTransform=\"rotate(135 0,0)\"");
        sb.append(" patternUnits=\"userSpaceOnUse\" width=\"10\">\n");
        sb.append("      <line stroke=\"#000000\" stroke-width=\"6.666667\" x1=\"0\" x2=\"0\" y1=\"0\" y2=\"10\"/>\n");
        sb.append("    </pattern>\n");
        sb.append("  </defs>\n");
        sb.append("  <g>\n");
        sb.append("    <g>\n");
        sb.append("      <rect/>\n");
        sb.append("      <rect fill=\"#112233\" height=\"40\" width=\"120\" x=\"1240.444\" y=\"296.666667\"/>\n");
        sb.append("      <rect/>\n");
        sb.append("      <rect/>\n");
        sb.append("      <text></text>\n");
        sb.append("      <text></text>\n");
        sb.append("    </g>\n");
        sb.append("    <g/>\n");
        sb.append("    <g>\n");
        sb.append("      <g>\n");
        sb.append("        <text fill=\"#112233\" font-family=\"Lato\" font-size=\"36\" font-style=\"normal\"");
        sb.append(" font-weight=\"bold\" text-align=\"center\" text-anchor=\"end\" x=\"292.052\"");
        sb.append(" y=\"210.666667\">Red Party</text>\n");
        sb.append("        <rect/>\n");
        sb.append("        <text></text>\n");
        sb.append("        <rect fill=\"url(#pattern-1)\" height=\"80\" stroke=\"#000000\" stroke-width=\"5\"");
        sb.append(" width=\"1000\" x=\"312.052\" y=\"144\"/>\n");
        sb.append("        <rect fill=\"url(#pattern-2)\" height=\"80\" stroke=\"#000000\" stroke-width=\"5\"");
        sb.append(" width=\"882.051282\" x=\"312.052\" y=\"144\"/>\n");
        sb.append("        <rect fill=\"#000000\" height=\"80\" width=\"766.666667\" x=\"312.052\" y=\"144\"/>\n");
        sb.append("        <text fill=\"#112233\" font-family=\"Lato\" font-size=\"36\" font-style=\"normal\"");
        sb.append(" font-weight=\"bold\" text-align=\"center\" text-anchor=\"start\" x=\"1332.052\"");
        sb.append(" y=\"197.333333\">15–20%</text>\n");
        sb.append("      </g>\n");
        sb.append("      <g>\n");
        sb.append("        <text fill=\"#112233\" font-family=\"Lato\" font-size=\"36\" font-style=\"normal\"");
        sb.append(" font-weight=\"bold\" text-align=\"center\" text-anchor=\"end\" x=\"292.052\"");
        sb.append(" y=\"350.666667\">Green Party</text>\n");
        sb.append("        <rect/>\n");
        sb.append("        <text></text>\n");
        sb.append("        <rect fill=\"url(#pattern-3)\" height=\"80\" stroke=\"#000000\" stroke-width=\"5\"");
        sb.append(" width=\"717.948718\" x=\"312.052\" y=\"284\"/>\n");
        sb.append("        <rect fill=\"url(#pattern-4)\" height=\"80\" stroke=\"#000000\" stroke-width=\"5\"");
        sb.append(" width=\"615.384615\" x=\"312.052\" y=\"284\"/>\n");
        sb.append("        <rect fill=\"#000000\" height=\"80\" width=\"515.384615\" x=\"312.052\" y=\"284\"/>\n");
        sb.append("        <text fill=\"#112233\" font-family=\"Lato\" font-size=\"36\" font-style=\"normal\"");
        sb.append(" font-weight=\"bold\" text-align=\"center\" text-anchor=\"start\" x=\"1050.000718\"");
        sb.append(" y=\"337.333333\">10–14%</text>\n");
        sb.append("      </g>\n");
        sb.append("    </g>\n");
        sb.append("  </g>\n");
        sb.append("</svg>");
        assertEquals(sb.toString(), chart.toString());
    }
}
