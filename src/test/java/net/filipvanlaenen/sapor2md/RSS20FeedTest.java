package net.filipvanlaenen.sapor2md;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import org.junit.jupiter.api.Test;

/**
 * Unit tests on the <code>RSS20Feed</code> class.
 */
public class RSS20FeedTest {

    /**
     * Verifying that for a directory with no poll files, an empty feed is produced.
     */
    @Test
    void produceEmptyFeedForDirectoryWithNoPolls() {
        InMemoryCountryProperties countryProperties = new InMemoryCountryProperties();
        countryProperties.setParliamentName("Foo Parliament");
        countryProperties.setGitHubDirectoryURL("https://bar.github.io/foo_polls");
        LocalDateTime localDateTime = LocalDateTime.of(2020, Month.JANUARY, 1, 0, 0);
        ZoneOffset offset = ZoneOffset.of("+02:00");
        OffsetDateTime offsetDateTime = OffsetDateTime.of(localDateTime, offset);
        countryProperties.setTimestamp(offsetDateTime);
        SaporDirectory directory = new InMemorySaporDirectory(countryProperties);
        String actual = new RSS20Feed(directory).toString();
        StringBuilder sb = new StringBuilder();
        sb.append("<rss version=\"2.0\">\n");
        sb.append("  <channel>\n");
        sb.append("    <title>All Registered Polls for the Foo Parliament</title>\n");
        sb.append("    <link>https://bar.github.io/foo_polls</link>\n");
        sb.append("    <description>All Registered Polls for the Foo Parliament</description>\n");
        sb.append("    <pubDate>Wed, 1 Jan 2020 00:00:00 +0200</pubDate>\n");
        sb.append("  </channel>\n");
        sb.append("</rss>");
        String expected = sb.toString();
        assertEquals(expected, actual);
    }

}
