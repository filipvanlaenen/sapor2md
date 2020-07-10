package net.filipvanlaenen.sapor2md;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class RSS20Feed {

    private final SaporDirectory saporDirectory;

    RSS20Feed(final String directory) {
        this(new FileSystemSaporDirectory(directory));
    }

    RSS20Feed(final SaporDirectory saporDirectory) {
        this.saporDirectory = saporDirectory;
    }

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

    private OffsetDateTime getPubDate() {
        return saporDirectory.getCountryProperties().getTimestamp();
    }
}
