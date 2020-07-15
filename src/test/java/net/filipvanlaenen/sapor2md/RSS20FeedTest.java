package net.filipvanlaenen.sapor2md;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import net.filipvanlaenen.sapor2md.RSS20Feed.RSS20FeedMode;

/**
 * Unit tests on the <code>RSS20Feed</code> class.
 */
public class RSS20FeedTest {
    /**
     * Magic number three.
     */
    private static final int THREE = 3;
    /**
     * Magic number four.
     */
    private static final int FOUR = 4;
    /**
     * Magic number five.
     */
    private static final int FIVE = 5;
    /**
     * Magic number 0.95, or 95 percent.
     */
    private static final double NINETY_FIVE_PERCENT = 0.95D;
    /**
     * Magic number 2000.
     */
    private static final double TWO_THOUSAND = 2000D;
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
    /**
     * Magic number 2020, used as a year number.
     */
    private static final int TWO_THOUSAND_AND_TWENTY = 2020;
    /**
     * A country properties object for testing purposes.
     */
    private InMemoryCountryProperties countryProperties;

    /**
     * Creates the in-memory country properties for the test.
     */
    @BeforeEach
    void createCountryProperties() {
        countryProperties = new InMemoryCountryProperties();
        countryProperties.setParliamentName("Foo Parliament");
        countryProperties.setGitHubDirectoryURL("https://bar.github.io/foo_polls");
        countryProperties.setTimestamp(createDateTime(TWO_THOUSAND_AND_TWENTY, Month.JANUARY, 1, 0, 0));
    }

    /**
     * Creates a date and time for a specified year, month, day, hour and minute
     * with +01:00 as the time zone.
     *
     * @param year
     *            A year.
     * @param month
     *            A month.
     * @param dayOfMonth
     *            A day of month.
     * @param hour
     *            An hour.
     * @param minute
     *            A minute.
     * @return A date and time as specified, with +01:00 as the time zone.
     */
    private OffsetDateTime createDateTime(final int year, final Month month, final int dayOfMonth, final int hour,
            final int minute) {
        LocalDateTime localDateTime = LocalDateTime.of(year, month, dayOfMonth, hour, minute);
        ZoneOffset offset = ZoneOffset.of("+01:00");
        return OffsetDateTime.of(localDateTime, offset);
    }

    /**
     * Verifying that for a directory with no poll files, an empty feed is produced.
     */
    @Test
    void produceEmptyFeedForDirectoryWithNoPolls() {
        SaporDirectory directory = new InMemorySaporDirectory(countryProperties);
        String actual = new RSS20Feed(directory, RSS20FeedMode.GitHubFeed).toString();
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        sb.append("<rss version=\"2.0\" xmlns:dc=\"http://purl.org/dc/elements/1.1/\">\n");
        sb.append("  <channel>\n");
        sb.append("    <title>All Registered Polls for the Foo Parliament</title>\n");
        sb.append("    <link>https://bar.github.io/foo_polls</link>\n");
        sb.append("    <description>All Registered Polls for the Foo Parliament</description>\n");
        sb.append("    <pubDate>Wed, 1 Jan 2020 00:00:00 +0100</pubDate>\n");
        sb.append("  </channel>\n");
        sb.append("</rss>");
        String expected = sb.toString();
        assertEquals(expected, actual);
    }

    /**
     * Verifying that for a directory with a poll that hasn't been calculated yet,
     * an empty feed is produced.
     */
    @Test
    void produceEmptyFeedForDirectoryWithPollThatHasNotBeenCalculated() {
        InMemorySaporDirectory directory = new InMemorySaporDirectory(countryProperties);
        directory.addPoll(new InMemoryPoll("2020-01-03-Baz"));
        String actual = new RSS20Feed(directory, RSS20FeedMode.GitHubFeed).toString();
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        sb.append("<rss version=\"2.0\" xmlns:dc=\"http://purl.org/dc/elements/1.1/\">\n");
        sb.append("  <channel>\n");
        sb.append("    <title>All Registered Polls for the Foo Parliament</title>\n");
        sb.append("    <link>https://bar.github.io/foo_polls</link>\n");
        sb.append("    <description>All Registered Polls for the Foo Parliament</description>\n");
        sb.append("    <pubDate>Wed, 1 Jan 2020 00:00:00 +0100</pubDate>\n");
        sb.append("  </channel>\n");
        sb.append("</rss>");
        String expected = sb.toString();
        assertEquals(expected, actual);
    }

    /**
     * Creates a probability mass function for the voting intentions such that the
     * 95 percent confidence interval is as specified.
     *
     * @param lowerBound
     *            The lower bound of the 95 percent confidence interval.
     * @param upperBound
     *            The lower bound of the 95 percent confidence interval.
     * @return A probability mass function having the 95 percent confidence interval
     *         as specified.
     */
    private ProbabilityMassFunction<ProbabilityRange> createProbabilityMassFunctionForConfidenceInterval(
            final double lowerBound, final double upperBound) {
        int noBelow = (int) (lowerBound * TWO_THOUSAND);
        List<Object> args = new ArrayList<Object>();
        for (int i = 0; i < noBelow; i++) {
            args.add(new ProbabilityRange(((double) i) / TWO_THOUSAND, ((double) (i + 1)) / TWO_THOUSAND));
            args.add(0D);
        }
        int noBetween = (int) ((upperBound - lowerBound) * TWO_THOUSAND);
        double p = NINETY_FIVE_PERCENT / noBetween;
        for (int i = 0; i < noBetween; i++) {
            args.add(new ProbabilityRange(((double) (noBelow + i)) / TWO_THOUSAND,
                    ((double) (noBelow + i + 1)) / TWO_THOUSAND));
            if (i == 0 || i == noBetween - 1) {
                args.add(p + (1D - NINETY_FIVE_PERCENT) / 2D);
            } else {
                args.add(p);
            }
        }
        return new ProbabilityMassFunction<ProbabilityRange>(args.toArray());
    }

    /**
     * Creates a Sapor directory with one poll for which only one simulation has
     * been calculated.
     *
     * @param hasCommissioners
     *            Whether the poll shoud have commissioners or not.
     * @return A Sapor directory with one poll for which only one simulation has
     *         been calculated.
     */
    private SaporDirectory createDirectoryWithPollWithOneSimulation(final boolean hasCommissioners) {
        InMemorySaporDirectory directory = new InMemorySaporDirectory(countryProperties);
        InMemoryPoll poll = new InMemoryPoll("2020-01-03-Baz");
        poll.setPollingFirm("Baz");
        if (hasCommissioners) {
            poll.setCommissioners("Qux");
        }
        poll.setFieldworkStart(LocalDate.of(TWO_THOUSAND_AND_TWENTY, Month.JANUARY, 2));
        poll.setFieldworkEnd(LocalDate.of(TWO_THOUSAND_AND_TWENTY, Month.JANUARY, THREE));
        poll.setVotingIntentionsChartFileSize(FIVE);
        InMemoryStateSummary stateSummary = new InMemoryStateSummary();
        stateSummary.setNumberOfSimulations(1);
        stateSummary.setTimestamp(createDateTime(TWO_THOUSAND_AND_TWENTY, Month.JANUARY, FOUR, 0, 0));
        poll.setStateSummary(stateSummary);
        VotingIntentions votingIntentions = new VotingIntentions("Red Party",
                createProbabilityMassFunctionForConfidenceInterval(RED_PARTY_CONFIDENCE_INTERVAL_LOWER_BOUND,
                        RED_PARTY_CONFIDENCE_INTERVAL_UPPER_BOUND),
                "Green Party", createProbabilityMassFunctionForConfidenceInterval(
                        GREEN_PARTY_CONFIDENCE_INTERVAL_LOWER_BOUND, GREEN_PARTY_CONFIDENCE_INTERVAL_UPPER_BOUND));
        poll.setVotingIntentions(votingIntentions);
        directory.addPoll(poll);
        return directory;
    }

    /**
     * Verifying that for a directory with a poll that has 1 simulation, a feed with
     * a voting intentions item is produced.
     */
    @Test
    void produceFeedWithVotingIntentionsItemForDirectoryWithPollWithOneSimulation() {
        SaporDirectory directory = createDirectoryWithPollWithOneSimulation(true);
        String actual = new RSS20Feed(directory, RSS20FeedMode.GitHubFeed).toString();
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        sb.append("<rss version=\"2.0\" xmlns:dc=\"http://purl.org/dc/elements/1.1/\">\n");
        sb.append("  <channel>\n");
        sb.append("    <title>All Registered Polls for the Foo Parliament</title>\n");
        sb.append("    <link>https://bar.github.io/foo_polls</link>\n");
        sb.append("    <description>All Registered Polls for the Foo Parliament</description>\n");
        sb.append("    <pubDate>Wed, 1 Jan 2020 00:00:00 +0100</pubDate>\n");
        sb.append("    <item>\n");
        sb.append("      <title>Opinion Poll by Baz for Qux, 2–3 January 2020 – Voting Intentions</title>\n");
        sb.append("      <link>https://bar.github.io/foo_polls/2020-01-03-Baz.html</link>\n");
        sb.append("      <description><ul>");
        sb.append("<li>Red Party: 14.9–19.5%</li>");
        sb.append("<li>Green Party: 10.0–14.0%</li>");
        sb.append("</ul></description>\n");
        sb.append("      <enclosure url=\"https://bar.github.io/foo_polls/2020-01-03-Baz.png\" length=\"5\"");
        sb.append(" type=\"image/png\"/>\n");
        sb.append("      <pubDate>Sat, 4 Jan 2020 00:00:00 +0100</pubDate>\n");
        sb.append("      <dc:date>2020-01-04T00:00:00+01:00</dc:date>\n");
        sb.append("    </item>\n");
        sb.append("  </channel>\n");
        sb.append("</rss>");
        String expected = sb.toString();
        assertEquals(expected, actual);
    }

    /**
     * Verifying that for a directory with a poll without commissioners that has 1
     * simulation, a feed with a voting intentions item is produced.
     */
    @Test
    void produceFeedWithVotingIntentionsItemForDirectoryWithPollWithoutCommissionnersWithOneSimulation() {
        SaporDirectory directory = createDirectoryWithPollWithOneSimulation(false);
        String actual = new RSS20Feed(directory, RSS20FeedMode.GitHubFeed).toString();
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        sb.append("<rss version=\"2.0\" xmlns:dc=\"http://purl.org/dc/elements/1.1/\">\n");
        sb.append("  <channel>\n");
        sb.append("    <title>All Registered Polls for the Foo Parliament</title>\n");
        sb.append("    <link>https://bar.github.io/foo_polls</link>\n");
        sb.append("    <description>All Registered Polls for the Foo Parliament</description>\n");
        sb.append("    <pubDate>Wed, 1 Jan 2020 00:00:00 +0100</pubDate>\n");
        sb.append("    <item>\n");
        sb.append("      <title>Opinion Poll by Baz, 2–3 January 2020 – Voting Intentions</title>\n");
        sb.append("      <link>https://bar.github.io/foo_polls/2020-01-03-Baz.html</link>\n");
        sb.append("      <description><ul>");
        sb.append("<li>Red Party: 14.9–19.5%</li>");
        sb.append("<li>Green Party: 10.0–14.0%</li>");
        sb.append("</ul></description>\n");
        sb.append("      <enclosure url=\"https://bar.github.io/foo_polls/2020-01-03-Baz.png\" length=\"5\"");
        sb.append(" type=\"image/png\"/>\n");
        sb.append("      <pubDate>Sat, 4 Jan 2020 00:00:00 +0100</pubDate>\n");
        sb.append("      <dc:date>2020-01-04T00:00:00+01:00</dc:date>\n");
        sb.append("    </item>\n");
        sb.append("  </channel>\n");
        sb.append("</rss>");
        String expected = sb.toString();
        assertEquals(expected, actual);
    }

    /**
     * Verifying that for a directory with a poll that has 1 simulation, a feed with
     * a voting intentions item is produced for IFTTT.
     */
    @Test
    void produceIftttFeedWithVotingIntentionsItemForDirectoryWithPollWithOneSimulation() {
        SaporDirectory directory = createDirectoryWithPollWithOneSimulation(true);
        String actual = new RSS20Feed(directory, RSS20FeedMode.IftttFeed).toString();
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        sb.append("<rss version=\"2.0\" xmlns:dc=\"http://purl.org/dc/elements/1.1/\">\n");
        sb.append("  <channel>\n");
        sb.append("    <title>All Registered Polls for the Foo Parliament</title>\n");
        sb.append("    <link>https://bar.github.io/foo_polls</link>\n");
        sb.append("    <description>All Registered Polls for the Foo Parliament</description>\n");
        sb.append("    <pubDate>Wed, 1 Jan 2020 00:00:00 +0100</pubDate>\n");
        sb.append("    <item>\n");
        sb.append("      <title>Opinion Poll by Baz for Qux, 2–3 January 2020 – Voting Intentions</title>\n");
        sb.append("      <link>https://bar.github.io/foo_polls/2020-01-03-Baz.html</link>\n");
        sb.append("      <description><![CDATA[Voting intentions for the Foo Parliament<br/>");
        sb.append("Opinion poll by Baz for Qux, 2–3 January 2020<br/>");
        sb.append("<img src=\"https://bar.github.io/foo_polls/2020-01-03-Baz.png\"/><br/>");
        sb.append("Details on https://bar.github.io/foo_polls/2020-01-03-Baz.html]]></description>\n");
        sb.append("      <enclosure url=\"https://bar.github.io/foo_polls/2020-01-03-Baz.png\" length=\"5\"");
        sb.append(" type=\"image/png\"/>\n");
        sb.append("      <pubDate>Sat, 4 Jan 2020 00:00:00 +0100</pubDate>\n");
        sb.append("      <dc:date>2020-01-04T00:00:00+01:00</dc:date>\n");
        sb.append("    </item>\n");
        sb.append("  </channel>\n");
        sb.append("</rss>");
        String expected = sb.toString();
        assertEquals(expected, actual);
    }

    /**
     * Verifying that for a directory with a poll without commissioners that has 1
     * simulation, a feed with a voting intentions item is produced for IFTTT.
     */
    @Test
    void produceIftttFeedWithVotingIntentionsItemForDirectoryWithPollWithoutCommissionersWithOneSimulation() {
        SaporDirectory directory = createDirectoryWithPollWithOneSimulation(false);
        String actual = new RSS20Feed(directory, RSS20FeedMode.IftttFeed).toString();
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        sb.append("<rss version=\"2.0\" xmlns:dc=\"http://purl.org/dc/elements/1.1/\">\n");
        sb.append("  <channel>\n");
        sb.append("    <title>All Registered Polls for the Foo Parliament</title>\n");
        sb.append("    <link>https://bar.github.io/foo_polls</link>\n");
        sb.append("    <description>All Registered Polls for the Foo Parliament</description>\n");
        sb.append("    <pubDate>Wed, 1 Jan 2020 00:00:00 +0100</pubDate>\n");
        sb.append("    <item>\n");
        sb.append("      <title>Opinion Poll by Baz, 2–3 January 2020 – Voting Intentions</title>\n");
        sb.append("      <link>https://bar.github.io/foo_polls/2020-01-03-Baz.html</link>\n");
        sb.append("      <description><![CDATA[Voting intentions for the Foo Parliament<br/>");
        sb.append("Opinion poll by Baz, 2–3 January 2020<br/>");
        sb.append("<img src=\"https://bar.github.io/foo_polls/2020-01-03-Baz.png\"/><br/>");
        sb.append("Details on https://bar.github.io/foo_polls/2020-01-03-Baz.html]]></description>\n");
        sb.append("      <enclosure url=\"https://bar.github.io/foo_polls/2020-01-03-Baz.png\" length=\"5\"");
        sb.append(" type=\"image/png\"/>\n");
        sb.append("      <pubDate>Sat, 4 Jan 2020 00:00:00 +0100</pubDate>\n");
        sb.append("      <dc:date>2020-01-04T00:00:00+01:00</dc:date>\n");
        sb.append("    </item>\n");
        sb.append("  </channel>\n");
        sb.append("</rss>");
        String expected = sb.toString();
        assertEquals(expected, actual);
    }
}
