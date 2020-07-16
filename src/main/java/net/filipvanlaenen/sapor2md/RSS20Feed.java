package net.filipvanlaenen.sapor2md;

import java.io.File;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.Locale;

/**
 * Class producing an RSS 2.0 feed for a Sapor directory.
 */
public final class RSS20Feed {
    /**
     * Magic number 0.95, or 95 percent.
     */
    private static final double NINETY_FIVE_PERCENT = 0.95D;
    /**
     * Magic number 100.
     */
    private static final double ONE_HUNDRED = 100D;
    /**
     * Magic number one million (1,048,576), the threshold for when to include seat
     * projections in an RSS 2.0 feed.
     */
    private static final long ONE_MILLION = 1048576;

    /**
     * The Sapor directory for the RSS 2.0 feed.
     */
    private final SaporDirectory saporDirectory;
    /**
     * The mode for the RSS 2.0 feed.
     */
    private final RSS20FeedMode feedMode;
    /**
     * The file system directory for the RSS 2.0 feed.
     */
    private String directory;

    /**
     * Constructor taking the file system path for the Sapor directory as an
     * argument.
     *
     * @param directory The file system path to the Sapor directory.
     * @param feedMode  The mode of the feed.
     */
    RSS20Feed(final String directory, final RSS20FeedMode feedMode) {
        this(new FileSystemSaporDirectory(directory), feedMode);
        this.directory = directory;
    }

    /**
     * Constructor taking a <code>SaporDirectory</code> object as an argument.
     *
     * @param saporDirectory A Sapor directory.
     * @param feedMode       The mode of the feed.
     */
    RSS20Feed(final SaporDirectory saporDirectory, final RSS20FeedMode feedMode) {
        this.saporDirectory = saporDirectory;
        this.feedMode = feedMode;
    }

    /**
     * Exports the RSS 2.0 feed as a multiline string.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        sb.append("<rss version=\"2.0\" xmlns:dc=\"http://purl.org/dc/elements/1.1/\">\n");
        sb.append("  <channel>\n");
        sb.append("    <title>All Registered Polls for the ");
        sb.append(xmlEncode(saporDirectory.getCountryProperties().getParliamentName()));
        sb.append("</title>\n");
        sb.append("    <link>" + saporDirectory.getCountryProperties().getGitHubDirectoryURL() + "</link>\n");
        sb.append("    <description>All Registered Polls for the ");
        sb.append(xmlEncode(saporDirectory.getCountryProperties().getParliamentName()));
        sb.append("</description>\n");
        sb.append("    <pubDate>" + getPubDate().format(DateTimeFormatter.RFC_1123_DATE_TIME) + "</pubDate>\n");
        Iterator<Poll> pollIterator = saporDirectory.getSortedPolls();
        while (pollIterator.hasNext()) {
            Poll poll = pollIterator.next();
            if (poll.hasStateSummary() && poll.getStateSummary().getNumberOfSimulations() >= ONE_MILLION) {
                sb.append(createSeatProjectionsItem(poll));
            }
            if (poll.hasStateSummary() && poll.getStateSummary().getNumberOfSimulations() >= 1) {
                sb.append(createVotingIntentionsItem(poll));
            }
        }
        sb.append("  </channel>\n");
        sb.append("</rss>");
        return sb.toString();
    }

    /**
     * Encodes a text string such that in can be included in an XML document.
     *
     * @param text The text string to be encoded.
     * @return The result of the encoding.
     */
    static String xmlEncode(final String text) {
        return text.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;");
    }

    /**
     * Writes the feed to the file system, using the default name for the feed,
     * returning <code>true</code> if no problem occurred.
     *
     * @return True if no problem occurred, false otherwise.
     */
    boolean writeToFileSystem() {
        String filePath = directory + File.separator + feedMode.getFeedFileName();
        return FileSystemServices.writeStringToFile(toString(), filePath);
    }

    /**
     * Creates an item for the voting intentions for a poll.
     *
     * @param poll The poll for which an item should be created with the voting
     *             intentions.
     * @return A string representing the item to be included in the feed.
     */
    private String createVotingIntentionsItem(final Poll poll) {
        StringBuilder sb = new StringBuilder();
        sb.append("    <item>\n");
        sb.append("      <title>Opinion Poll by ");
        sb.append(xmlEncode(poll.getPollingFirm()));
        if (poll.getComissioners() != null) {
            sb.append(" for " + xmlEncode(poll.getComissioners()));
        }
        sb.append(", ");
        sb.append(formatPeriod(poll.getFieldworkStart(), poll.getFieldworkEnd()));
        sb.append(" – Voting Intentions</title>\n");
        sb.append("      <link>");
        sb.append(saporDirectory.getCountryProperties().getGitHubDirectoryURL());
        sb.append("/");
        sb.append(poll.getBaseName());
        sb.append(".html</link>\n");
        sb.append("      <description>");
        sb.append(feedMode.createVotingIntentionsItemDescription(poll, saporDirectory));
        sb.append("</description>\n");
        sb.append("      <enclosure url=\"");
        sb.append(saporDirectory.getCountryProperties().getGitHubDirectoryURL());
        sb.append("/");
        sb.append(poll.getBaseName());
        sb.append(".png\" length=\"");
        sb.append(poll.getVotingIntentionsChartFileSize());
        sb.append("\" type=\"image/png\"/>\n");
        OffsetDateTime timestamp = poll.getStateSummary().getTimestamp();
        sb.append("      <pubDate>" + timestamp.format(DateTimeFormatter.RFC_1123_DATE_TIME) + "</pubDate>\n");
        sb.append("      <dc:date>" + timestamp.format(DateTimeFormatter.ISO_DATE_TIME) + "</dc:date>\n");
        sb.append("    </item>\n");
        return sb.toString();
    }

    /**
     * Creates an item for the seat projections per party for a poll.
     *
     * @param poll The poll for which an item should be created with the seat
     *             projections per party.
     * @return A string representing the item to be included in the feed.
     */
    private String createSeatProjectionsItem(final Poll poll) {
        StringBuilder sb = new StringBuilder();
        sb.append("    <item>\n");
        sb.append("      <title>Opinion Poll by ");
        sb.append(xmlEncode(poll.getPollingFirm()));
        if (poll.getComissioners() != null) {
            sb.append(" for " + xmlEncode(poll.getComissioners()));
        }
        sb.append(", ");
        sb.append(formatPeriod(poll.getFieldworkStart(), poll.getFieldworkEnd()));
        sb.append(" – Seat Projections</title>\n");
        sb.append("      <link>");
        sb.append(saporDirectory.getCountryProperties().getGitHubDirectoryURL());
        sb.append("/");
        sb.append(poll.getBaseName());
        sb.append(".html#seats</link>\n");
        sb.append("      <description>");
        sb.append(feedMode.createSeatProjectionsItemDescription(poll, saporDirectory));
        sb.append("</description>\n");
        sb.append("      <enclosure url=\"");
        sb.append(saporDirectory.getCountryProperties().getGitHubDirectoryURL());
        sb.append("/");
        sb.append(poll.getBaseName());
        sb.append("-seats.png\" length=\"");
        sb.append(poll.getSeatProjectionsChartFileSize());
        sb.append("\" type=\"image/png\"/>\n");
        OffsetDateTime timestamp = poll.getStateSummary().getTimestamp();
        sb.append("      <pubDate>" + timestamp.format(DateTimeFormatter.RFC_1123_DATE_TIME) + "</pubDate>\n");
        sb.append("      <dc:date>" + timestamp.format(DateTimeFormatter.ISO_DATE_TIME) + "</dc:date>\n");
        sb.append("    </item>\n");
        return sb.toString();
    }

    /**
     * Formats a period, consisting of two dates, to a human-readable form. Common
     * elements in the dates are joined, such that the result takes one of the
     * following forms: 1–2 January 2020, 1 January–2 February 2020 or 1 January
     * 2020–31 December 2021.
     *
     * @param start The start of the period.
     * @param end   The end of the period.
     * @return A string with the period formatted in a human-readable form.
     */
    static String formatPeriod(final LocalDate start, final LocalDate end) {
        DateTimeFormatter dayMonthYear = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale.ENGLISH);
        DateTimeFormatter dayMonth = DateTimeFormatter.ofPattern("d MMMM", Locale.ENGLISH);
        DateTimeFormatter day = DateTimeFormatter.ofPattern("d", Locale.ENGLISH);
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
     * Formats a double as a percentage number. The double is multiplied with 100
     * and formatted with one digit behind the decimal point.
     *
     * @param percentage The double to be formatted.
     * @return A string with the double formatted as percentage number.
     */
    private static String formatPercentageNumber(final double percentage) {
        return String.format("%.1f", percentage * ONE_HUNDRED, Locale.ENGLISH);
    }

    /**
     * Formats a confidence interval with probability ranges to a human readable
     * form. It takes the lower bound of the lower probability range and the upper
     * bound of the upper probability ranges as the lower an upper bounds.
     *
     * @param ci The confidence interval.
     * @return A string with the confidence interval formatted in a human-readable
     *         form.
     */
    private static String formatProbabilityRangeConfidenceInterval(final ConfidenceInterval<ProbabilityRange> ci) {
        return formatPercentageNumber(ci.getLowerBound().getLowerBound()) + "–"
                + formatPercentageNumber(ci.getUpperBound().getUpperBound()) + "%";
    }

    /**
     * Formats a confidence interval with seats to a human readable form.
     *
     * @param ci The confidence interval.
     * @return A string with the confidence interval formatted in a human-readable
     *         form.
     */
    static String formatSeatsConfidenceInterval(final ConfidenceInterval<Integer> ci) {
        int lowerBound = ci.getLowerBound();
        int upperBound = ci.getUpperBound();
        if (upperBound == 0) {
            return "0 seats";
        } else if (lowerBound == 1 && upperBound == 1) {
            return "1 seat";
        } else if (lowerBound == upperBound) {
            return lowerBound + " seats";
        } else {
            return lowerBound + "–" + upperBound + " seats";
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

    /**
     * Enumeration with the modes that can be applied to an RSS 2.0 feed.
     */
    enum RSS20FeedMode {
        /**
         * Mode for the RSS 2.0 feed to be used as the official feed on the GitHub
         * website.
         */
        GitHubFeed {
            @Override
            String getFeedFileName() {
                return "rss.xml";
            }

            @Override
            String createVotingIntentionsItemDescription(final Poll poll, final SaporDirectory saporDir) {
                StringBuilder sb = new StringBuilder();
                sb.append("<ul>");
                VotingIntentions votingIntentions = poll.getVotingIntentions();
                ConfidenceInterval<ProbabilityRange> ci;
                for (String group : votingIntentions.getGroups()) {
                    sb.append("<li>");
                    sb.append(xmlEncode(group));
                    sb.append(": ");
                    ci = votingIntentions.getConfidenceInterval(group, NINETY_FIVE_PERCENT);
                    sb.append(formatProbabilityRangeConfidenceInterval(ci));
                    sb.append("</li>");
                }
                sb.append("</ul>");
                return sb.toString();
            }

            @Override
            String createSeatProjectionsItemDescription(final Poll poll, final SaporDirectory saporDir) {
                StringBuilder sb = new StringBuilder();
                sb.append("<ul>");
                SeatProjection seatProjection = poll.getSeatProjection();
                ConfidenceInterval<Integer> ci;
                for (String group : seatProjection.getGroups()) {
                    sb.append("<li>");
                    sb.append(xmlEncode(group));
                    sb.append(": ");
                    ci = seatProjection.getConfidenceInterval(group, NINETY_FIVE_PERCENT);
                    sb.append(formatSeatsConfidenceInterval(ci));
                    sb.append("</li>");
                }
                sb.append("</ul>");
                return sb.toString();
            }
        },
        /**
         * Mode for the RSS 2.0 feed to be consumed by IFTTT to produce Twitter
         * messages.
         */
        IftttFeed {
            @Override
            String getFeedFileName() {
                return "rss-ifttt.xml";
            }

            @Override
            String createVotingIntentionsItemDescription(final Poll poll, final SaporDirectory saporDir) {
                StringBuilder sb = new StringBuilder();
                sb.append("<![CDATA[");
                sb.append("Voting intentions for the ");
                sb.append(xmlEncode(saporDir.getCountryProperties().getParliamentName()));
                sb.append("<br/>");
                sb.append("Opinion poll by ");
                sb.append(poll.getPollingFirm());
                if (poll.getComissioners() != null) {
                    sb.append(" for " + xmlEncode(poll.getComissioners()));
                }
                sb.append(", ");
                sb.append(formatPeriod(poll.getFieldworkStart(), poll.getFieldworkEnd()));
                sb.append("<br/>");
                sb.append("<img src=\"");
                sb.append(saporDir.getCountryProperties().getGitHubDirectoryURL());
                sb.append("/");
                sb.append(poll.getBaseName());
                sb.append(".png\"/>");
                sb.append("<br/>");
                sb.append("Details on ");
                sb.append(saporDir.getCountryProperties().getGitHubDirectoryURL());
                sb.append("/");
                sb.append(poll.getBaseName());
                sb.append(".html");
                sb.append("]]>");
                return sb.toString();
            }

            @Override
            String createSeatProjectionsItemDescription(final Poll poll, final SaporDirectory saporDir) {
                StringBuilder sb = new StringBuilder();
                sb.append("<![CDATA[");
                sb.append("Seat projections for the ");
                sb.append(xmlEncode(saporDir.getCountryProperties().getParliamentName()));
                sb.append("<br/>");
                sb.append(saporDir.getCountryProperties().getNumberOfSeatsForMajority());
                sb.append(" seats needed for a majority<br/>");
                sb.append("Opinion poll by ");
                sb.append(poll.getPollingFirm());
                if (poll.getComissioners() != null) {
                    sb.append(" for " + xmlEncode(poll.getComissioners()));
                }
                sb.append(", ");
                sb.append(formatPeriod(poll.getFieldworkStart(), poll.getFieldworkEnd()));
                sb.append("<br/>");
                sb.append("<img src=\"");
                sb.append(saporDir.getCountryProperties().getGitHubDirectoryURL());
                sb.append("/");
                sb.append(poll.getBaseName());
                sb.append("-seats.png\"/>");
                sb.append("<br/>");
                sb.append("Details on ");
                sb.append(saporDir.getCountryProperties().getGitHubDirectoryURL());
                sb.append("/");
                sb.append(poll.getBaseName());
                sb.append(".html");
                sb.append("]]>");
                return sb.toString();
            }
        };

        /**
         * Creates the description field for an item about the voting intentions for a
         * poll.
         *
         * @param poll     The poll.
         * @param saporDir The Sapor directory.
         * @return A string with the content of the description field for an item about
         *         the voting intentions for a poll.
         */
        abstract String createVotingIntentionsItemDescription(Poll poll, SaporDirectory saporDir);

        /**
         * Creates the description field for an item about the seat projections per
         * party for a poll.
         *
         * @param poll     The poll.
         * @param saporDir The Sapor directory.
         * @return A string with the content of the description field for an item about
         *         the seat projections per party for a poll.
         */
        abstract String createSeatProjectionsItemDescription(Poll poll, SaporDirectory saporDir);

        /**
         * Returns the file name for the feed.
         *
         * @return The file name for the feed.
         */
        abstract String getFeedFileName();
    }
}
