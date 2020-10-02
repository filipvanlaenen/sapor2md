package net.filipvanlaenen.sapor2md;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

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
     * Magic number six.
     */
    private static final int SIX = 6;
    /**
     * Magic number seven.
     */
    private static final int SEVEN = 7;
    /**
     * Magic number nine.
     */
    private static final int NINE = 9;
    /**
     * Magic number ten.
     */
    private static final int TEN = 10;
    /**
     * Magic number eleven.
     */
    private static final int ELEVEN = 11;
    /**
     * The magic number a half.
     */
    private static final double A_HALF = 0.5D;
    /**
     * Magic number 0.95, or 95 percent.
     */
    private static final double NINETY_FIVE_PERCENT = 0.95D;
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
     * Magic number 2019, used as a year number.
     */
    private static final int TWO_THOUSAND_AND_NINETEEN = 2019;
    /**
     * Magic number 2020, used as a year number.
     */
    private static final int TWO_THOUSAND_AND_TWENTY = 2020;
    /**
     * Magic number 2021, used as a year number.
     */
    private static final int TWO_THOUSAND_AND_TWENTY_ONE = 2021;
    /**
     * Magic number one million (1,048,576), the threshold for when to include seat
     * projections in an RSS 2.0 feed.
     */
    private static final long ONE_MILLION = 1048576;
    /**
     * A background color.
     */
    private static final String BACKGROUND_COLOR = "#DDEEFF";
    /**
     * A text color.
     */
    private static final String TEXT_COLOR = "#112233";

    /**
     * Creates the default in-memory country properties for the test.
     */
    CountryProperties createCountryProperties() {
        return createCountryProperties(false);
    }

    /**
     * Creates the default in-memory country properties for the test.
     */
    CountryProperties createCountryProperties(final boolean hasTwitterTags) {
        Map<String, String> map = new HashMap<String, String>();
        map.put(CountryProperties.NUMBER_OF_SEATS_KEY, Integer.toString(SIX));
        map.put(CountryProperties.GITHUB_DIRECTORY_URL_KEY, "https://bar.github.io/foo_polls");
        map.put(CountryProperties.PARLIAMENT_NAME_KEY, "Foo Parliament");
        map.put(CountryProperties.BACKGROUND_COLOR_KEY, BACKGROUND_COLOR);
        map.put(CountryProperties.TEXT_COLOR_KEY, TEXT_COLOR);
        if (hasTwitterTags) {
            map.put(CountryProperties.TWITTER_TAGS_KEY, "opinionpoll | foo");
        }
        OffsetDateTime timestamp = createDateTime(TWO_THOUSAND_AND_TWENTY, Month.JANUARY, 1, 0, 0);
        return new InMemoryCountryProperties(map, timestamp);
    }

    /**
     * Creates a date and time for a specified year, month, day, hour and minute
     * with +01:00 as the time zone.
     *
     * @param year       A year.
     * @param month      A month.
     * @param dayOfMonth A day of month.
     * @param hour       An hour.
     * @param minute     A minute.
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
        SaporDirectory directory = new InMemorySaporDirectory(createCountryProperties());
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
        InMemorySaporDirectory directory = new InMemorySaporDirectory(createCountryProperties());
        Map<String, String> properties = new HashMap<String, String>();
        properties.put(Poll.FIELDWORK_START_KEY, "2020-01-02");
        properties.put(Poll.FIELDWORK_END_KEY, "2020-01-03");
        directory.addPoll(new InMemoryPoll("2020-01-03-Baz", properties));
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
     * Creates a Sapor directory with one poll.
     *
     * @param numberOfSimulations The number of simulations run on the poll.
     * @param hasCommissioners    Whether the poll should have commissioners or not.
     * @param hasTwitterTags             Whether the directory should have tags or not.
     * @return A Sapor directory with one poll.
     */
    private SaporDirectory createDirectoryWithPoll(final long numberOfSimulations, final boolean hasCommissioners,
            final boolean hasTwitterTags) {
        InMemorySaporDirectory directory = new InMemorySaporDirectory(createCountryProperties(hasTwitterTags));
        Map<String, String> properties = new HashMap<String, String>();
        properties.put(Poll.POLLING_FIRM_KEY, "Baz");
        if (hasCommissioners) {
            properties.put(Poll.COMMISSIONERS_KEY, "Qux");
        }
        properties.put(Poll.FIELDWORK_START_KEY, "2020-01-02");
        properties.put(Poll.FIELDWORK_END_KEY, "2020-01-03");
        InMemoryPoll poll = new InMemoryPoll("2020-01-03-Baz", properties);
        poll.setVotingIntentionsChartFileSize(FIVE);
        poll.setSeatProjectionsChartFileSize(SIX);
        poll.setSeatingPlanProjectionChartFileSize(SEVEN);
        InMemoryStateSummary stateSummary = new InMemoryStateSummary();
        stateSummary.setNumberOfSimulations(numberOfSimulations);
        stateSummary.setTimestamp(createDateTime(TWO_THOUSAND_AND_TWENTY, Month.JANUARY, FOUR, 0, 0));
        poll.setStateSummary(stateSummary);
        VotingIntentions votingIntentions = new VotingIntentions("Red Party",
                VotingIntentionsTestServices.createProbabilityMassFunctionForConfidenceInterval(
                        RED_PARTY_CONFIDENCE_INTERVAL_LOWER_BOUND, RED_PARTY_CONFIDENCE_INTERVAL_UPPER_BOUND),
                "Green Party", VotingIntentionsTestServices.createProbabilityMassFunctionForConfidenceInterval(
                        GREEN_PARTY_CONFIDENCE_INTERVAL_LOWER_BOUND, GREEN_PARTY_CONFIDENCE_INTERVAL_UPPER_BOUND));
        poll.setVotingIntentions(votingIntentions);
        SeatProjection seatProjection = new SeatProjection("Red Party",
                new ProbabilityMassFunction<Integer>(0, 0D, 1, 0D, 2, 0D, THREE, 0D, FOUR, A_HALF, FIVE, A_HALF),
                "Green Party", new ProbabilityMassFunction<Integer>(0, 0D, 1, A_HALF, 2, A_HALF));
        poll.setSeatProjection(seatProjection);
        directory.addPoll(poll);
        return directory;
    }

    /**
     * Creates a Sapor directory with a number of polls.
     *
     * @param noOfPolls The number of polls requested.
     * @return A Sapor directory with the requests number of polls.
     */
    private SaporDirectory createDirectoryWithPolls(final int noOfPolls) {
        InMemorySaporDirectory directory = new InMemorySaporDirectory(createCountryProperties());
        for (int i = noOfPolls; i >= 1; i--) {
            directory.addPoll(createPoll(i, TWO_THOUSAND_AND_TWENTY));
        }
        return directory;
    }

    /**
     * Creates a poll to be included in a Sapor directory.
     *
     * @param i    The index of the poll, used as the day of month for the fieldwork
     *             start.
     * @param year The year of the poll.
     * @return A poll to be included in a Sapor directory.
     */
    private InMemoryPoll createPoll(final int i, final int year) {
        Map<String, String> properties = new HashMap<String, String>();
        properties.put(Poll.POLLING_FIRM_KEY, "Baz");
        properties.put(Poll.COMMISSIONERS_KEY, "Qux");
        properties.put(Poll.FIELDWORK_START_KEY, year + "-01-" + String.format("%02d", i));
        String endDate = year + "-01-" + String.format("%02d", i + 1);
        properties.put(Poll.FIELDWORK_END_KEY, endDate);
        InMemoryPoll poll = new InMemoryPoll(endDate + "-Baz", properties);
        poll.setVotingIntentionsChartFileSize(FIVE);
        poll.setSeatProjectionsChartFileSize(SIX);
        poll.setSeatingPlanProjectionChartFileSize(SEVEN);
        InMemoryStateSummary stateSummary = new InMemoryStateSummary();
        stateSummary.setNumberOfSimulations(1);
        stateSummary.setTimestamp(createDateTime(year, Month.JANUARY, i + 1, 0, 0));
        poll.setStateSummary(stateSummary);
        VotingIntentions votingIntentions = new VotingIntentions("Red Party",
                VotingIntentionsTestServices.createProbabilityMassFunctionForConfidenceInterval(
                        RED_PARTY_CONFIDENCE_INTERVAL_LOWER_BOUND, RED_PARTY_CONFIDENCE_INTERVAL_UPPER_BOUND),
                "Green Party", VotingIntentionsTestServices.createProbabilityMassFunctionForConfidenceInterval(
                        GREEN_PARTY_CONFIDENCE_INTERVAL_LOWER_BOUND, GREEN_PARTY_CONFIDENCE_INTERVAL_UPPER_BOUND));
        poll.setVotingIntentions(votingIntentions);
        SeatProjection seatProjection = new SeatProjection("Red Party",
                new ProbabilityMassFunction<Integer>(0, 0D, 1, 0D, 2, 0D, THREE, 0D, FOUR, A_HALF, FIVE, A_HALF),
                "Green Party", new ProbabilityMassFunction<Integer>(0, 0D, 1, A_HALF, 2, A_HALF));
        poll.setSeatProjection(seatProjection);
        return poll;
    }

    /**
     * Verifying that for a directory with a poll that has 1 simulation, a feed with
     * a voting intentions item is produced.
     */
    @Test
    void produceFeedWithVotingIntentionsItemForDirectoryWithPollWithOneSimulation() {
        SaporDirectory directory = createDirectoryWithPoll(1, true, false);
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
        SaporDirectory directory = createDirectoryWithPoll(1, false, false);
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
     * Verifying that for a directory with a poll that has one million simulations,
     * a feed with all items is produced.
     */
    @Test
    void produceFeedWithAllItemsForDirectoryWithPollWithOneMillionSimulations() {
        SaporDirectory directory = createDirectoryWithPoll(ONE_MILLION, true, false);
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
        sb.append("      <title>Opinion Poll by Baz for Qux, 2–3 January 2020 – Seating Plan Projection</title>\n");
        sb.append("      <link>https://bar.github.io/foo_polls/2020-01-03-Baz.html#seating-plan</link>\n");
        sb.append("      <description><ul>");
        sb.append("<li>Red Party: 5 seats</li>");
        sb.append("<li>Green Party: 1 seat</li>");
        sb.append("</ul></description>\n");
        sb.append("      <enclosure url=\"https://bar.github.io/foo_polls/2020-01-03-Baz-seating-plan.png\"");
        sb.append(" length=\"7\" type=\"image/png\"/>\n");
        sb.append("      <pubDate>Sat, 4 Jan 2020 00:00:00 +0100</pubDate>\n");
        sb.append("      <dc:date>2020-01-04T00:00:00+01:00</dc:date>\n");
        sb.append("    </item>\n");
        sb.append("    <item>\n");
        sb.append("      <title>Opinion Poll by Baz for Qux, 2–3 January 2020 – Seat Projections</title>\n");
        sb.append("      <link>https://bar.github.io/foo_polls/2020-01-03-Baz.html#seats</link>\n");
        sb.append("      <description><ul>");
        sb.append("<li>Red Party: 4–5 seats</li>");
        sb.append("<li>Green Party: 1–2 seats</li>");
        sb.append("</ul></description>\n");
        sb.append("      <enclosure url=\"https://bar.github.io/foo_polls/2020-01-03-Baz-seats.png\" length=\"6\"");
        sb.append(" type=\"image/png\"/>\n");
        sb.append("      <pubDate>Sat, 4 Jan 2020 00:00:00 +0100</pubDate>\n");
        sb.append("      <dc:date>2020-01-04T00:00:00+01:00</dc:date>\n");
        sb.append("    </item>\n");
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
     * Verifying that for a directory with a poll without commissioners that has one
     * million simulations, a feed with all items is produced.
     */
    @Test
    void produceFeedWithAllItemsForDirectoryWithPollWithoutCommissionnersWithOneMillionSimulations() {
        SaporDirectory directory = createDirectoryWithPoll(ONE_MILLION, false, false);
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
        sb.append("      <title>Opinion Poll by Baz, 2–3 January 2020 – Seating Plan Projection</title>\n");
        sb.append("      <link>https://bar.github.io/foo_polls/2020-01-03-Baz.html#seating-plan</link>\n");
        sb.append("      <description><ul>");
        sb.append("<li>Red Party: 5 seats</li>");
        sb.append("<li>Green Party: 1 seat</li>");
        sb.append("</ul></description>\n");
        sb.append("      <enclosure url=\"https://bar.github.io/foo_polls/2020-01-03-Baz-seating-plan.png\"");
        sb.append(" length=\"7\" type=\"image/png\"/>\n");
        sb.append("      <pubDate>Sat, 4 Jan 2020 00:00:00 +0100</pubDate>\n");
        sb.append("      <dc:date>2020-01-04T00:00:00+01:00</dc:date>\n");
        sb.append("    </item>\n");
        sb.append("    <item>\n");
        sb.append("      <title>Opinion Poll by Baz, 2–3 January 2020 – Seat Projections</title>\n");
        sb.append("      <link>https://bar.github.io/foo_polls/2020-01-03-Baz.html#seats</link>\n");
        sb.append("      <description><ul>");
        sb.append("<li>Red Party: 4–5 seats</li>");
        sb.append("<li>Green Party: 1–2 seats</li>");
        sb.append("</ul></description>\n");
        sb.append("      <enclosure url=\"https://bar.github.io/foo_polls/2020-01-03-Baz-seats.png\" length=\"6\"");
        sb.append(" type=\"image/png\"/>\n");
        sb.append("      <pubDate>Sat, 4 Jan 2020 00:00:00 +0100</pubDate>\n");
        sb.append("      <dc:date>2020-01-04T00:00:00+01:00</dc:date>\n");
        sb.append("    </item>\n");
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
        SaporDirectory directory = createDirectoryWithPoll(1, true, false);
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
        sb.append("Details on https://bar.github.io/foo_polls/2020-01-03-Baz.html<br/>");
        sb.append("<img src=\"https://bar.github.io/foo_polls/2020-01-03-Baz.png\"/>]]></description>\n");
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
     * Verifying that for a directory with eleven polls within thirty days from the
     * most recent poll, a feed with all eleven polls is produced for IFTTT.
     */
    @Test
    void iftttFeedIncludesAllPollsUpToThirtyDaysBackFromTheMostRecentPoll() {
        SaporDirectory directory = createDirectoryWithPolls(ELEVEN);
        String actual = new RSS20Feed(directory, RSS20FeedMode.IftttFeed).toString();
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        sb.append("<rss version=\"2.0\" xmlns:dc=\"http://purl.org/dc/elements/1.1/\">\n");
        sb.append("  <channel>\n");
        sb.append("    <title>All Registered Polls for the Foo Parliament</title>\n");
        sb.append("    <link>https://bar.github.io/foo_polls</link>\n");
        sb.append("    <description>All Registered Polls for the Foo Parliament</description>\n");
        sb.append("    <pubDate>Wed, 1 Jan 2020 00:00:00 +0100</pubDate>\n");
        for (int i = ELEVEN; i >= 1; i--) {
            String period = i + "–" + (i + 1);
            String dayOfMonth = String.format("%02d", i + 1);
            sb.append("    <item>\n");
            sb.append("      <title>Opinion Poll by Baz for Qux, ");
            sb.append(period);
            sb.append(" January 2020 – Voting Intentions</title>\n");
            sb.append("      <link>https://bar.github.io/foo_polls/2020-01-");
            sb.append(dayOfMonth);
            sb.append("-Baz.html</link>\n");
            sb.append("      <description><![CDATA[Voting intentions for the Foo Parliament<br/>");
            sb.append("Opinion poll by Baz for Qux, ");
            sb.append(period);
            sb.append(" January 2020<br/>");
            sb.append("Details on https://bar.github.io/foo_polls/2020-01-");
            sb.append(dayOfMonth);
            sb.append("-Baz.html<br/>");
            sb.append("<img src=\"https://bar.github.io/foo_polls/2020-01-");
            sb.append(dayOfMonth);
            sb.append("-Baz.png\"/>]]></description>\n");
            sb.append("      <enclosure url=\"https://bar.github.io/foo_polls/2020-01-");
            sb.append(dayOfMonth);
            sb.append("-Baz.png\" length=\"5\"");
            sb.append(" type=\"image/png\"/>\n");
            sb.append("      <pubDate>");
            OffsetDateTime timestamp = createDateTime(TWO_THOUSAND_AND_TWENTY, Month.JANUARY, i + 1, 0, 0);
            sb.append(timestamp.format(DateTimeFormatter.RFC_1123_DATE_TIME));
            sb.append("</pubDate>\n");
            sb.append("      <dc:date>2020-01-");
            sb.append(dayOfMonth);
            sb.append("T00:00:00+01:00</dc:date>\n");
            sb.append("    </item>\n");
        }
        sb.append("  </channel>\n");
        sb.append("</rss>");
        String expected = sb.toString();
        assertEquals(expected, actual);
    }

    /**
     * Verifying that for a directory with ten polls, one of which more than thirty
     * days older than the most recent poll, a feed with all ten polls is produced
     * for IFTTT.
     */
    @Test
    void iftttFeedDoesNotIncludeEleventhPollIfOlderThanThirtyDaysFromMostRecentPoll() {
        SaporDirectory directory = createDirectoryWithPolls(NINE);
        directory.addPoll(createPoll(1, TWO_THOUSAND_AND_TWENTY_ONE));
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
        sb.append("      <title>Opinion Poll by Baz for Qux, 1–2 January 2021 – Voting Intentions</title>\n");
        sb.append("      <link>https://bar.github.io/foo_polls/2021-01-02-Baz.html</link>\n");
        sb.append("      <description><![CDATA[Voting intentions for the Foo Parliament<br/>");
        sb.append("Opinion poll by Baz for Qux, 1–2 January 2021<br/>");
        sb.append("Details on https://bar.github.io/foo_polls/2021-01-02-Baz.html<br/>");
        sb.append("<img src=\"https://bar.github.io/foo_polls/2021-01-02-Baz.png\"/>]]></description>\n");
        sb.append("      <enclosure url=\"https://bar.github.io/foo_polls/2021-01-02-Baz.png\" length=\"5\"");
        sb.append(" type=\"image/png\"/>\n");
        sb.append("      <pubDate>Sat, 2 Jan 2021 00:00:00 +0100</pubDate>\n");
        sb.append("      <dc:date>2021-01-02T00:00:00+01:00</dc:date>\n");
        sb.append("    </item>\n");
        for (int i = NINE; i >= 1; i--) {
            String period = i + "–" + (i + 1);
            String dayOfMonth = String.format("%02d", i + 1);
            sb.append("    <item>\n");
            sb.append("      <title>Opinion Poll by Baz for Qux, ");
            sb.append(period);
            sb.append(" January 2020 – Voting Intentions</title>\n");
            sb.append("      <link>https://bar.github.io/foo_polls/2020-01-");
            sb.append(dayOfMonth);
            sb.append("-Baz.html</link>\n");
            sb.append("      <description><![CDATA[Voting intentions for the Foo Parliament<br/>");
            sb.append("Opinion poll by Baz for Qux, ");
            sb.append(period);
            sb.append(" January 2020<br/>");
            sb.append("Details on https://bar.github.io/foo_polls/2020-01-");
            sb.append(dayOfMonth);
            sb.append("-Baz.html<br/>");
            sb.append("<img src=\"https://bar.github.io/foo_polls/2020-01-");
            sb.append(dayOfMonth);
            sb.append("-Baz.png\"/>]]></description>\n");
            sb.append("      <enclosure url=\"https://bar.github.io/foo_polls/2020-01-");
            sb.append(dayOfMonth);
            sb.append("-Baz.png\" length=\"5\"");
            sb.append(" type=\"image/png\"/>\n");
            sb.append("      <pubDate>");
            OffsetDateTime timestamp = createDateTime(TWO_THOUSAND_AND_TWENTY, Month.JANUARY, i + 1, 0, 0);
            sb.append(timestamp.format(DateTimeFormatter.RFC_1123_DATE_TIME));
            sb.append("</pubDate>\n");
            sb.append("      <dc:date>2020-01-");
            sb.append(dayOfMonth);
            sb.append("T00:00:00+01:00</dc:date>\n");
            sb.append("    </item>\n");
        }
        sb.append("  </channel>\n");
        sb.append("</rss>");
        String expected = sb.toString();
        assertEquals(expected, actual);
    }

    /**
     * Verifying that for a directory with eleven polls, one of which more than
     * thirty days older than the most recent poll, a feed with only ten polls is
     * produced for IFTTT.
     */
    @Test
    void iftttFeedIncludesAtLeastTenPolls() {
        SaporDirectory directory = createDirectoryWithPolls(TEN);
        directory.addPoll(createPoll(1, TWO_THOUSAND_AND_NINETEEN));
        String actual = new RSS20Feed(directory, RSS20FeedMode.IftttFeed).toString();
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        sb.append("<rss version=\"2.0\" xmlns:dc=\"http://purl.org/dc/elements/1.1/\">\n");
        sb.append("  <channel>\n");
        sb.append("    <title>All Registered Polls for the Foo Parliament</title>\n");
        sb.append("    <link>https://bar.github.io/foo_polls</link>\n");
        sb.append("    <description>All Registered Polls for the Foo Parliament</description>\n");
        sb.append("    <pubDate>Wed, 1 Jan 2020 00:00:00 +0100</pubDate>\n");
        for (int i = TEN; i >= 1; i--) {
            String period = i + "–" + (i + 1);
            String dayOfMonth = String.format("%02d", i + 1);
            sb.append("    <item>\n");
            sb.append("      <title>Opinion Poll by Baz for Qux, ");
            sb.append(period);
            sb.append(" January 2020 – Voting Intentions</title>\n");
            sb.append("      <link>https://bar.github.io/foo_polls/2020-01-");
            sb.append(dayOfMonth);
            sb.append("-Baz.html</link>\n");
            sb.append("      <description><![CDATA[Voting intentions for the Foo Parliament<br/>");
            sb.append("Opinion poll by Baz for Qux, ");
            sb.append(period);
            sb.append(" January 2020<br/>");
            sb.append("Details on https://bar.github.io/foo_polls/2020-01-");
            sb.append(dayOfMonth);
            sb.append("-Baz.html<br/>");
            sb.append("<img src=\"https://bar.github.io/foo_polls/2020-01-");
            sb.append(dayOfMonth);
            sb.append("-Baz.png\"/>]]></description>\n");
            sb.append("      <enclosure url=\"https://bar.github.io/foo_polls/2020-01-");
            sb.append(dayOfMonth);
            sb.append("-Baz.png\" length=\"5\"");
            sb.append(" type=\"image/png\"/>\n");
            sb.append("      <pubDate>");
            OffsetDateTime timestamp = createDateTime(TWO_THOUSAND_AND_TWENTY, Month.JANUARY, i + 1, 0, 0);
            sb.append(timestamp.format(DateTimeFormatter.RFC_1123_DATE_TIME));
            sb.append("</pubDate>\n");
            sb.append("      <dc:date>2020-01-");
            sb.append(dayOfMonth);
            sb.append("T00:00:00+01:00</dc:date>\n");
            sb.append("    </item>\n");
        }
        sb.append("  </channel>\n");
        sb.append("</rss>");
        String expected = sb.toString();
        assertEquals(expected, actual);
    }

    /**
     * Verifying that for a directory with eleven polls, one of which more than
     * thirty days older than the most recent poll, a feed with all eleven polls is
     * produced for RSS 2.0.
     */
    @Test
    void rssFeedIncludesAllPolls() {
        SaporDirectory directory = createDirectoryWithPolls(TEN);
        directory.addPoll(createPoll(1, TWO_THOUSAND_AND_NINETEEN));
        String actual = new RSS20Feed(directory, RSS20FeedMode.GitHubFeed).toString();
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        sb.append("<rss version=\"2.0\" xmlns:dc=\"http://purl.org/dc/elements/1.1/\">\n");
        sb.append("  <channel>\n");
        sb.append("    <title>All Registered Polls for the Foo Parliament</title>\n");
        sb.append("    <link>https://bar.github.io/foo_polls</link>\n");
        sb.append("    <description>All Registered Polls for the Foo Parliament</description>\n");
        sb.append("    <pubDate>Wed, 1 Jan 2020 00:00:00 +0100</pubDate>\n");
        for (int i = TEN; i >= 1; i--) {
            String period = i + "–" + (i + 1);
            String dayOfMonth = String.format("%02d", i + 1);
            sb.append("    <item>\n");
            sb.append("      <title>Opinion Poll by Baz for Qux, ");
            sb.append(period);
            sb.append(" January 2020 – Voting Intentions</title>\n");
            sb.append("      <link>https://bar.github.io/foo_polls/2020-01-");
            sb.append(dayOfMonth);
            sb.append("-Baz.html</link>\n");
            sb.append("      <description><ul>");
            sb.append("<li>Red Party: 14.9–19.5%</li>");
            sb.append("<li>Green Party: 10.0–14.0%</li>");
            sb.append("</ul></description>\n");
            sb.append("      <enclosure url=\"https://bar.github.io/foo_polls/2020-01-");
            sb.append(dayOfMonth);
            sb.append("-Baz.png\" length=\"5\"");
            sb.append(" type=\"image/png\"/>\n");
            sb.append("      <pubDate>");
            OffsetDateTime timestamp = createDateTime(TWO_THOUSAND_AND_TWENTY, Month.JANUARY, i + 1, 0, 0);
            sb.append(timestamp.format(DateTimeFormatter.RFC_1123_DATE_TIME));
            sb.append("</pubDate>\n");
            sb.append("      <dc:date>2020-01-");
            sb.append(dayOfMonth);
            sb.append("T00:00:00+01:00</dc:date>\n");
            sb.append("    </item>\n");
        }
        sb.append("    <item>\n");
        sb.append("      <title>Opinion Poll by Baz for Qux, 1–2 January 2019 – Voting Intentions</title>\n");
        sb.append("      <link>https://bar.github.io/foo_polls/2019-01-02-Baz.html</link>\n");
        sb.append("      <description><ul>");
        sb.append("<li>Red Party: 14.9–19.5%</li>");
        sb.append("<li>Green Party: 10.0–14.0%</li>");
        sb.append("</ul></description>\n");
        sb.append("      <enclosure url=\"https://bar.github.io/foo_polls/2019-01-02-Baz.png\" length=\"5\"");
        sb.append(" type=\"image/png\"/>\n");
        sb.append("      <pubDate>Wed, 2 Jan 2019 00:00:00 +0100</pubDate>\n");
        sb.append("      <dc:date>2019-01-02T00:00:00+01:00</dc:date>\n");
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
        SaporDirectory directory = createDirectoryWithPoll(1, false, false);
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
        sb.append("Details on https://bar.github.io/foo_polls/2020-01-03-Baz.html<br/>");
        sb.append("<img src=\"https://bar.github.io/foo_polls/2020-01-03-Baz.png\"/>]]></description>\n");
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
     * Verifying that for a directory with a poll that has 1 million simulations, a
     * feed with all items is produced for IFTTT.
     */
    @Test
    void produceIftttFeedWithAllItemsForDirectoryWithPollWithAMillionSimulations() {
        SaporDirectory directory = createDirectoryWithPoll(ONE_MILLION, true, false);
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
        sb.append("      <title>Opinion Poll by Baz for Qux, 2–3 January 2020 – Seating Plan Projection</title>\n");
        sb.append("      <link>https://bar.github.io/foo_polls/2020-01-03-Baz.html#seating-plan</link>\n");
        sb.append("      <description><![CDATA[Seating plan projection for the Foo Parliament<br/>");
        sb.append("4 seats needed for a majority<br/>");
        sb.append("Opinion poll by Baz for Qux, 2–3 January 2020<br/>");
        sb.append("Details on https://bar.github.io/foo_polls/2020-01-03-Baz.html<br/>");
        sb.append("<img src=\"https://bar.github.io/foo_polls/2020-01-03-Baz-seating-plan.png\"/>]]></description>\n");
        sb.append("      <enclosure url=\"https://bar.github.io/foo_polls/2020-01-03-Baz-seating-plan.png\"");
        sb.append(" length=\"7\" type=\"image/png\"/>\n");
        sb.append("      <pubDate>Sat, 4 Jan 2020 00:00:00 +0100</pubDate>\n");
        sb.append("      <dc:date>2020-01-04T00:00:00+01:00</dc:date>\n");
        sb.append("    </item>\n");
        sb.append("    <item>\n");
        sb.append("      <title>Opinion Poll by Baz for Qux, 2–3 January 2020 – Seat Projections</title>\n");
        sb.append("      <link>https://bar.github.io/foo_polls/2020-01-03-Baz.html#seats</link>\n");
        sb.append("      <description><![CDATA[Seat projections for the Foo Parliament<br/>");
        sb.append("4 seats needed for a majority<br/>");
        sb.append("Opinion poll by Baz for Qux, 2–3 January 2020<br/>");
        sb.append("Details on https://bar.github.io/foo_polls/2020-01-03-Baz.html<br/>");
        sb.append("<img src=\"https://bar.github.io/foo_polls/2020-01-03-Baz-seats.png\"/>]]></description>\n");
        sb.append("      <enclosure url=\"https://bar.github.io/foo_polls/2020-01-03-Baz-seats.png\" length=\"6\"");
        sb.append(" type=\"image/png\"/>\n");
        sb.append("      <pubDate>Sat, 4 Jan 2020 00:00:00 +0100</pubDate>\n");
        sb.append("      <dc:date>2020-01-04T00:00:00+01:00</dc:date>\n");
        sb.append("    </item>\n");
        sb.append("    <item>\n");
        sb.append("      <title>Opinion Poll by Baz for Qux, 2–3 January 2020 – Voting Intentions</title>\n");
        sb.append("      <link>https://bar.github.io/foo_polls/2020-01-03-Baz.html</link>\n");
        sb.append("      <description><![CDATA[Voting intentions for the Foo Parliament<br/>");
        sb.append("Opinion poll by Baz for Qux, 2–3 January 2020<br/>");
        sb.append("Details on https://bar.github.io/foo_polls/2020-01-03-Baz.html<br/>");
        sb.append("<img src=\"https://bar.github.io/foo_polls/2020-01-03-Baz.png\"/>]]></description>\n");
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
     * Verifying that for a directory with a poll that has 1 million simulations, a
     * feed with all items is produced for IFTTT.
     */
    @Test
    void produceIftttFeedWithAllItemsForDirectoryWithPollWithAMillionSimulationsAndTwitterTags() {
        SaporDirectory directory = createDirectoryWithPoll(ONE_MILLION, true, true);
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
        sb.append("      <title>Opinion Poll by Baz for Qux, 2–3 January 2020 – Seating Plan Projection</title>\n");
        sb.append("      <link>https://bar.github.io/foo_polls/2020-01-03-Baz.html#seating-plan</link>\n");
        sb.append("      <description><![CDATA[Seating plan projection for the Foo Parliament<br/>");
        sb.append("4 seats needed for a majority<br/>");
        sb.append("Opinion poll by Baz for Qux, 2–3 January 2020<br/>");
        sb.append("Details on https://bar.github.io/foo_polls/2020-01-03-Baz.html<br/>");
        sb.append("#opinionpoll #foo<br/>");
        sb.append("<img src=\"https://bar.github.io/foo_polls/2020-01-03-Baz-seating-plan.png\"/>]]></description>\n");
        sb.append("      <enclosure url=\"https://bar.github.io/foo_polls/2020-01-03-Baz-seating-plan.png\"");
        sb.append(" length=\"7\" type=\"image/png\"/>\n");
        sb.append("      <pubDate>Sat, 4 Jan 2020 00:00:00 +0100</pubDate>\n");
        sb.append("      <dc:date>2020-01-04T00:00:00+01:00</dc:date>\n");
        sb.append("    </item>\n");
        sb.append("    <item>\n");
        sb.append("      <title>Opinion Poll by Baz for Qux, 2–3 January 2020 – Seat Projections</title>\n");
        sb.append("      <link>https://bar.github.io/foo_polls/2020-01-03-Baz.html#seats</link>\n");
        sb.append("      <description><![CDATA[Seat projections for the Foo Parliament<br/>");
        sb.append("4 seats needed for a majority<br/>");
        sb.append("Opinion poll by Baz for Qux, 2–3 January 2020<br/>");
        sb.append("Details on https://bar.github.io/foo_polls/2020-01-03-Baz.html<br/>");
        sb.append("#opinionpoll #foo<br/>");
        sb.append("<img src=\"https://bar.github.io/foo_polls/2020-01-03-Baz-seats.png\"/>]]></description>\n");
        sb.append("      <enclosure url=\"https://bar.github.io/foo_polls/2020-01-03-Baz-seats.png\" length=\"6\"");
        sb.append(" type=\"image/png\"/>\n");
        sb.append("      <pubDate>Sat, 4 Jan 2020 00:00:00 +0100</pubDate>\n");
        sb.append("      <dc:date>2020-01-04T00:00:00+01:00</dc:date>\n");
        sb.append("    </item>\n");
        sb.append("    <item>\n");
        sb.append("      <title>Opinion Poll by Baz for Qux, 2–3 January 2020 – Voting Intentions</title>\n");
        sb.append("      <link>https://bar.github.io/foo_polls/2020-01-03-Baz.html</link>\n");
        sb.append("      <description><![CDATA[Voting intentions for the Foo Parliament<br/>");
        sb.append("Opinion poll by Baz for Qux, 2–3 January 2020<br/>");
        sb.append("Details on https://bar.github.io/foo_polls/2020-01-03-Baz.html<br/>");
        sb.append("#opinionpoll #foo<br/>");
        sb.append("<img src=\"https://bar.github.io/foo_polls/2020-01-03-Baz.png\"/>]]></description>\n");
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
     * million simulations, a feed with all items is produced for IFTTT.
     */
    @Test
    void produceIftttFeedWithAllItemsForDirectoryWithPollWithoutCommissionersWithAMillionSimulations() {
        SaporDirectory directory = createDirectoryWithPoll(ONE_MILLION, false, false);
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
        sb.append("      <title>Opinion Poll by Baz, 2–3 January 2020 – Seating Plan Projection</title>\n");
        sb.append("      <link>https://bar.github.io/foo_polls/2020-01-03-Baz.html#seating-plan</link>\n");
        sb.append("      <description><![CDATA[Seating plan projection for the Foo Parliament<br/>");
        sb.append("4 seats needed for a majority<br/>");
        sb.append("Opinion poll by Baz, 2–3 January 2020<br/>");
        sb.append("Details on https://bar.github.io/foo_polls/2020-01-03-Baz.html<br/>");
        sb.append("<img src=\"https://bar.github.io/foo_polls/2020-01-03-Baz-seating-plan.png\"/>]]></description>\n");
        sb.append("      <enclosure url=\"https://bar.github.io/foo_polls/2020-01-03-Baz-seating-plan.png\"");
        sb.append(" length=\"7\" type=\"image/png\"/>\n");
        sb.append("      <pubDate>Sat, 4 Jan 2020 00:00:00 +0100</pubDate>\n");
        sb.append("      <dc:date>2020-01-04T00:00:00+01:00</dc:date>\n");
        sb.append("    </item>\n");
        sb.append("    <item>\n");
        sb.append("      <title>Opinion Poll by Baz, 2–3 January 2020 – Seat Projections</title>\n");
        sb.append("      <link>https://bar.github.io/foo_polls/2020-01-03-Baz.html#seats</link>\n");
        sb.append("      <description><![CDATA[Seat projections for the Foo Parliament<br/>");
        sb.append("4 seats needed for a majority<br/>");
        sb.append("Opinion poll by Baz, 2–3 January 2020<br/>");
        sb.append("Details on https://bar.github.io/foo_polls/2020-01-03-Baz.html<br/>");
        sb.append("<img src=\"https://bar.github.io/foo_polls/2020-01-03-Baz-seats.png\"/>]]></description>\n");
        sb.append("      <enclosure url=\"https://bar.github.io/foo_polls/2020-01-03-Baz-seats.png\" length=\"6\"");
        sb.append(" type=\"image/png\"/>\n");
        sb.append("      <pubDate>Sat, 4 Jan 2020 00:00:00 +0100</pubDate>\n");
        sb.append("      <dc:date>2020-01-04T00:00:00+01:00</dc:date>\n");
        sb.append("    </item>\n");
        sb.append("    <item>\n");
        sb.append("      <title>Opinion Poll by Baz, 2–3 January 2020 – Voting Intentions</title>\n");
        sb.append("      <link>https://bar.github.io/foo_polls/2020-01-03-Baz.html</link>\n");
        sb.append("      <description><![CDATA[Voting intentions for the Foo Parliament<br/>");
        sb.append("Opinion poll by Baz, 2–3 January 2020<br/>");
        sb.append("Details on https://bar.github.io/foo_polls/2020-01-03-Baz.html<br/>");
        sb.append("<img src=\"https://bar.github.io/foo_polls/2020-01-03-Baz.png\"/>]]></description>\n");
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
     * Verifying that the XML encoding converts a string correctly.
     */
    @Test
    void xmlEncodeConvertsCorrectly() {
        assertEquals("&lt;&amp;&gt;&lt;&amp;&gt;", RSS20Feed.xmlEncode("<&><&>"));
    }

    /**
     * Verifying the correct formatting of a confidence interval with 0 seats.
     */
    @Test
    void confidenceIntervalWithZeroSeatsFormattedCorrectly() {
        ProbabilityMassFunction<Integer> pmf = new ProbabilityMassFunction<Integer>(0, 1D);
        ConfidenceInterval<Integer> ci = pmf.getConfidenceInterval(NINETY_FIVE_PERCENT);
        assertEquals("0 seats", RSS20Feed.formatSeatsConfidenceInterval(ci));
    }

    /**
     * Verifying the correct formatting of a confidence interval with 0–1 seats.
     */
    @Test
    void confidenceIntervalWithZeroToOneSeatsFormattedCorrectly() {
        ProbabilityMassFunction<Integer> pmf = new ProbabilityMassFunction<Integer>(0, A_HALF, 1, A_HALF);
        ConfidenceInterval<Integer> ci = pmf.getConfidenceInterval(NINETY_FIVE_PERCENT);
        assertEquals("0–1 seats", RSS20Feed.formatSeatsConfidenceInterval(ci));
    }

    /**
     * Verifying the correct formatting of a confidence interval with 1 seat.
     */
    @Test
    void confidenceIntervalWithOneSeatFormattedCorrectly() {
        ProbabilityMassFunction<Integer> pmf = new ProbabilityMassFunction<Integer>(0, 0D, 1, 1D);
        ConfidenceInterval<Integer> ci = pmf.getConfidenceInterval(NINETY_FIVE_PERCENT);
        assertEquals("1 seat", RSS20Feed.formatSeatsConfidenceInterval(ci));
    }

    /**
     * Verifying the correct formatting of a confidence interval with 2 seats.
     */
    @Test
    void confidenceIntervalWithTwoSeatsFormattedCorrectly() {
        ProbabilityMassFunction<Integer> pmf = new ProbabilityMassFunction<Integer>(0, 0D, 1, 0D, 2, 1D);
        ConfidenceInterval<Integer> ci = pmf.getConfidenceInterval(NINETY_FIVE_PERCENT);
        assertEquals("2 seats", RSS20Feed.formatSeatsConfidenceInterval(ci));
    }
}
