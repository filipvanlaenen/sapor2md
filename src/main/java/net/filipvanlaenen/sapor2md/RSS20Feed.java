package net.filipvanlaenen.sapor2md;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Class producing an RSS 2.0 feed for a Sapor directory.
 */
public final class RSS20Feed {

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
        sb.append("    <link>" + saporDirectory.getGitHubDirectory() + "</link>\n");
        sb.append("    <description>All Registered Polls for the " + saporDirectory.getParliamentName()
                + "</description>\n");
        sb.append("    <pubDate>" + getPubDate().format(DateTimeFormatter.RFC_1123_DATE_TIME) + "</pubDate>\n");
        sb.append("  </channel>\n");
        sb.append("</rss>");
        return sb.toString();
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
