package net.filipvanlaenen.sapor2md;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;

/**
 * Class producing an RSS 2.0 feed for a Sapor directory.
 */
public final class RSS20Feed {

    /**
     * The Sapor directory for the RSS 2.0 feed.
     */
    private final SaporDirectory saporDirectory;
    private final RSS20FeedMode feedMode;

    /**
     * Constructor taking the file system path for the Sapor directory as an
     * argument.
     *
     * @param directory
     *            The file system path to the Sapor directory.
     */
    RSS20Feed(final String directory, RSS20FeedMode feedMode) {
        this(new FileSystemSaporDirectory(directory), feedMode);
    }

    /**
     * Constructor taking a <code>SaporDirectory</code> object as an argument.
     *
     * @param saporDirectory
     *            A Sapor directory.
     */
    RSS20Feed(final SaporDirectory saporDirectory, RSS20FeedMode feedMode) {
        this.saporDirectory = saporDirectory;
        this.feedMode = feedMode;
    }

    /**
     * Exports the RSS 2.0 feed as a multiline string.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("<rss version=\"2.0\">\n");
        sb.append("  <channel>\n");
        sb.append("    <title>All Registered Polls for the " + saporDirectory.getCountryProperties().getParliamentName()
                + "</title>\n");
        sb.append("    <link>" + saporDirectory.getGitHubDirectoryURL() + "</link>\n");
        sb.append("    <description>All Registered Polls for the ");
        sb.append(saporDirectory.getCountryProperties().getParliamentName());
        sb.append("</description>\n");
        sb.append("    <pubDate>" + getPubDate().format(DateTimeFormatter.RFC_1123_DATE_TIME) + "</pubDate>\n");
        Iterator<Poll> pollIterator = saporDirectory.getPolls();
        while (pollIterator.hasNext()) {
            Poll poll = pollIterator.next();
            if (poll.hasStateSummary() && poll.getStateSummary().getNumberOfSimulations() >= 1) {
                sb.append(createVotingIntentionsItem(poll));
            }
        }
        sb.append("  </channel>\n");
        sb.append("</rss>");
        return sb.toString();
    }

    private String createVotingIntentionsItem(Poll poll) {
        StringBuilder sb = new StringBuilder();
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
        sb.append("      <description>" + feedMode.createVotingIntentionsItemDescription(poll, saporDirectory)
                + "</description>\n");
        sb.append("      <enclosure url=\"");
        sb.append(saporDirectory.getGitHubDirectoryURL());
        sb.append("/");
        sb.append(poll.getBaseName());
        sb.append(".png\" length=\"");
        sb.append(poll.getVotingIntentionsFileSize());
        sb.append("\" type=\"image/png\"/>\n");
        OffsetDateTime timestamp = poll.getStateSummary().getTimestamp();
        sb.append("      <pubDate>" + timestamp.format(DateTimeFormatter.RFC_1123_DATE_TIME) + "</pubDate>\n");
        sb.append("      <dc:date>" + timestamp.format(DateTimeFormatter.ISO_DATE_TIME) + "</dc:date>\n");
        sb.append("    </item>\n");
        return sb.toString();
    }

    private static String formatPeriod(LocalDate start, LocalDate end) {
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

    private static String formatProbabilityRangeConfidenceInterval(ConfidenceInterval<ProbabilityRange> ci) {
        return String.format("%.1f", ci.getLowerBound().getLowerBound() * 100D) + "–"
                + String.format("%.1f", ci.getUpperBound().getUpperBound()* 100D) + "%";
    }

    /**
     * Returns the date to be used as the publication date for the entire feed.
     *
     * @return The publication date for the feed.
     */
    private OffsetDateTime getPubDate() {
        return saporDirectory.getCountryProperties().getTimestamp();
    }

    enum RSS20FeedMode {
        GitHubFeed {
            @Override
            String createVotingIntentionsItemDescription(Poll poll, SaporDirectory saporDirectory) {
                StringBuilder sb = new StringBuilder();
                sb.append("<ul>");
                VotingIntentions votingIntentions = poll.getVotingIntentions();
                for (String group : votingIntentions.getGroups()) {
                    sb.append("<li>");
                    sb.append(group);
                    sb.append(": ");
                    sb.append(formatProbabilityRangeConfidenceInterval(
                            votingIntentions.getConfidenceInterval(group, 0.95)));
                    sb.append("</li>");
                }
                sb.append("</ul>");
                return sb.toString();
            }
        },
        IftttFeed {
            @Override
            String createVotingIntentionsItemDescription(Poll poll, SaporDirectory saporDirectory) {
                StringBuilder sb = new StringBuilder();
                sb.append("<![CDATA[");
                sb.append("Voting intentions for the " + saporDirectory.getCountryProperties().getParliamentName());
                sb.append("<br/>");
                sb.append("Opinion poll by ");
                sb.append(poll.getPollingFirm());
                sb.append(" for ");
                sb.append(poll.getComissioners());
                sb.append(", ");
                sb.append(formatPeriod(poll.getFieldworkStart(), poll.getFieldworkEnd()));
                sb.append("<br/>");
                sb.append("<img src=\"");
                sb.append(saporDirectory.getGitHubDirectoryURL());
                sb.append("/");
                sb.append(poll.getBaseName());
                sb.append(".png\"/>");
                sb.append("<br/>");
                sb.append("Details on ");
                sb.append(saporDirectory.getGitHubDirectoryURL());
                sb.append("/");
                sb.append(poll.getBaseName());
                sb.append(".html");
                sb.append("]]>");
                return sb.toString();
            }
        };

        abstract String createVotingIntentionsItemDescription(Poll poll, SaporDirectory saporDirectory);
    }
}
