package net.filipvanlaenen.sapor2md;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import net.filipvanlaenen.sapor2md.RSS20Feed.RSS20FeedMode;

/**
 * Unit tests on the <code>RSS20Feed</code> class.
 */
public class RSS20FeedTest {
    private InMemoryCountryProperties countryProperties;

    /**
     * Creates the in-memory country properties for the test.
     */
    @BeforeEach
    void createCountryProperties() {
        countryProperties = new InMemoryCountryProperties();
        countryProperties.setParliamentName("Foo Parliament");
        countryProperties.setGitHubDirectoryURL("https://bar.github.io/foo_polls");
        countryProperties.setTimestamp(createDateTime(2020, Month.JANUARY, 1, 0, 0));
    }

    private OffsetDateTime createDateTime(int year, Month month, int dayOfMonth, int hour, int minute) {
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
        sb.append("<rss version=\"2.0\">\n");
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
        sb.append("<rss version=\"2.0\">\n");
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
     * Verifying that for a directory with a poll that has 1 simulation, a feed with
     * a voting intentions item is produced.
     */
    @Test
    void produceFeedWithVotingIntentionsItemForDirectoryWithPollWithOneSimulations() {
        InMemorySaporDirectory directory = new InMemorySaporDirectory(countryProperties);
        InMemoryPoll poll = new InMemoryPoll("2020-01-03-Baz");
        poll.setPollingFirm("Baz");
        poll.setCommissioners("Qux");
        poll.setFieldworkStart(LocalDate.of(2020, Month.JANUARY, 2));
        poll.setFieldworkEnd(LocalDate.of(2020, Month.JANUARY, 3));
        poll.setVotingIntentionsFileSize(387423);
        InMemoryStateSummary stateSummary = new InMemoryStateSummary();
        stateSummary.setNumberOfSimulations(1);
        stateSummary.setTimestamp(createDateTime(2020, Month.JANUARY, 4, 0, 0));
        poll.setStateSummary(stateSummary);
        VotingIntentions votingIntentions = new VotingIntentions();
        poll.setVotingIntentions(votingIntentions);
        directory.addPoll(poll);
        String actual = new RSS20Feed(directory, RSS20FeedMode.GitHubFeed).toString();
        StringBuilder sb = new StringBuilder();
        sb.append("<rss version=\"2.0\">\n");
        sb.append("  <channel>\n");
        sb.append("    <title>All Registered Polls for the Foo Parliament</title>\n");
        sb.append("    <link>https://bar.github.io/foo_polls</link>\n");
        sb.append("    <description>All Registered Polls for the Foo Parliament</description>\n");
        sb.append("    <pubDate>Wed, 1 Jan 2020 00:00:00 +0100</pubDate>\n");
        sb.append("    <item>\n");
        sb.append("      <title>Opinion Poll by Baz for Qux, 2–3 January 2020 – Voting Intentions</title>\n");
        sb.append("      <link>https://bar.github.io/foo_polls/2020-01-03-Baz.html</link>\n");
        sb.append(
                "      <description><ul><li>Red Party: 14.9–19.5%</li><li>Green Party: 10.0–14.0%</li></ul></description>\n");
        sb.append(
                "      <enclosure url=\"https://bar.github.io/foo_polls/2020-01-03-Baz.png\" length=\"387423\" type=\"image/png\"/>\n");
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
    void produceIftttFeedWithVotingIntentionsItemForDirectoryWithPollWithOneSimulations() {
        InMemorySaporDirectory directory = new InMemorySaporDirectory(countryProperties);
        InMemoryPoll poll = new InMemoryPoll("2020-01-03-Baz");
        poll.setPollingFirm("Baz");
        poll.setCommissioners("Qux");
        poll.setFieldworkStart(LocalDate.of(2020, Month.JANUARY, 2));
        poll.setFieldworkEnd(LocalDate.of(2020, Month.JANUARY, 3));
        poll.setVotingIntentionsFileSize(387423);
        InMemoryStateSummary stateSummary = new InMemoryStateSummary();
        stateSummary.setNumberOfSimulations(1);
        stateSummary.setTimestamp(createDateTime(2020, Month.JANUARY, 4, 0, 0));
        poll.setStateSummary(stateSummary);
        VotingIntentions votingIntentions = new VotingIntentions();
        poll.setVotingIntentions(votingIntentions);
        directory.addPoll(poll);
        String actual = new RSS20Feed(directory, RSS20FeedMode.IftttFeed).toString();
        StringBuilder sb = new StringBuilder();
        sb.append("<rss version=\"2.0\">\n");
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
        sb.append(
                "      <enclosure url=\"https://bar.github.io/foo_polls/2020-01-03-Baz.png\" length=\"387423\" type=\"image/png\"/>\n");
        sb.append("      <pubDate>Sat, 4 Jan 2020 00:00:00 +0100</pubDate>\n");
        sb.append("      <dc:date>2020-01-04T00:00:00+01:00</dc:date>\n");
        sb.append("    </item>\n");
        sb.append("  </channel>\n");
        sb.append("</rss>");
        String expected = sb.toString();
        assertEquals(expected, actual);
    }
}
