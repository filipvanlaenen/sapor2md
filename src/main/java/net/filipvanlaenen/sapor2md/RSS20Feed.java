package net.filipvanlaenen.sapor2md;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;

/**
 * Class producing an RSS 2.0 feed for a Sapor directory.
 */
public final class RSS20Feed {

    private static final int MAX_NO_OF_ITEMS = 50;
    /**
     * The Sapor directory for the RSS 2.0 feed.
     */
    private final SaporDirectory saporDirectory;

    /**
     * Constructor taking the file system path for the Sapor directory as an
     * argument.
     *
     * @param directory
     *            The file system path to the Sapor directory.
     */
    RSS20Feed(final String directory) {
        this(new FileSystemSaporDirectory(directory));
    }

    /**
     * Constructor taking a <code>SaporDirectory</code> object as an argument.
     *
     * @param saporDirectory
     *            A Sapor directory.
     */
    RSS20Feed(final SaporDirectory saporDirectory) {
        this.saporDirectory = saporDirectory;
    }

    /**
     * Exports the RSS 2.0 feed as a multiline string.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("<rss version=\"2.0\">\n");
        sb.append("  <channel>\n");
        sb.append("    <title>All Registered Polls for the " + saporDirectory.getParliamentName() + "</title>\n");
        sb.append("    <link>" + saporDirectory.getGitHubDirectoryURL() + "</link>\n");
        sb.append("    <description>All Registered Polls for the ");
        sb.append(saporDirectory.getParliamentName());
        sb.append("</description>\n");
        sb.append("    <pubDate>" + getPubDate().format(DateTimeFormatter.RFC_1123_DATE_TIME) + "</pubDate>\n");
        int noOfItems = 0;
        Iterator<Poll> pollIterator = saporDirectory.getPolls();
        while (noOfItems < MAX_NO_OF_ITEMS && pollIterator.hasNext()) {
            Poll poll = pollIterator.next();
            if (poll.hasStateSummary() && poll.getStateSummary().getNumberOfSimulations() >= 1) {
                OffsetDateTime timestamp = poll.getStateSummary().getTimestamp();
                sb.append("    <item>\n");
                sb.append("      <title>Opinion Poll by ");
                sb.append(poll.getPollingFirm());
                sb.append(" for ");
                sb.append(poll.getComissioners());
                sb.append(", ");
                sb.append(formatPeriod(poll.getFieldworkStart(), poll.getFieldworkEnd()));
                sb.append(" – Voting Intentions</title>\n");
                sb.append("      <link>");
                sb.append(saporDirectory.getGitHubDirectoryURL());
                sb.append("/");
                sb.append(poll.getBaseName());
                sb.append(".html</link>\n");
                sb.append("      <description><ul>");
                sb.append("</ul></description>\n");
                sb.append("      <enclosure url=\"");
                sb.append(saporDirectory.getGitHubDirectoryURL());
                sb.append("/");
                sb.append(poll.getBaseName());
                sb.append(".png\" length=\"");
                sb.append(poll.getVotingIntentionsFileSize());
                sb.append("\" type=\"image/png\"/>\n");
                sb.append("      <pubDate>" + timestamp.format(DateTimeFormatter.RFC_1123_DATE_TIME) + "</pubDate>\n");
                sb.append("      <dc:date>" + timestamp.format(DateTimeFormatter.ISO_DATE_TIME) + "</dc:date>\n");
                sb.append("    </item>\n");
            }
        }
        sb.append("  </channel>\n");
        sb.append("</rss>");
        return sb.toString();
    }

    private String formatPeriod(LocalDate start, LocalDate end) {
        DateTimeFormatter dayMonthYear = DateTimeFormatter.ofPattern("d MMMM yyyy");
        DateTimeFormatter dayMonth = DateTimeFormatter.ofPattern("d MMMM");
        DateTimeFormatter day = DateTimeFormatter.ofPattern("d");
        if (start.getYear() != end.getYear()) {
            return start.format(dayMonthYear) + "–" + end.format(dayMonthYear);
        } else if (start.getMonth() != end.getMonth()) {
            return start.format(dayMonth) + "–" + end.format(dayMonthYear);
        } else if (start.getDayOfMonth() != end.getDayOfMonth()) {
            return start.format(day) + "–" + end.format(dayMonthYear);
        } else {
            return start.format(dayMonthYear);
        }
    }

    /**
     * Returns the date to be used as the publication date for the entire feed.
     *
     * @return The publication date for the feed.
     */
    private OffsetDateTime getPubDate() {
        return saporDirectory.getCountryProperties().getTimestamp();
    }
}
