package net.filipvanlaenen.sapor2md;

import java.time.OffsetDateTime;

public class InMemoryCountryProperties implements CountryProperties {

    private String gitHubDirectory;
    private String parliamentName;
    private OffsetDateTime timestamp;

    void setGitHubDirectory(final String gitHubDirectory) {
        this.gitHubDirectory = gitHubDirectory;
    }

    @Override
    public String getGitHubDirectory() {
        return gitHubDirectory;
    }

    void setParliamentName(final String parliamentName) {
        this.parliamentName = parliamentName;
    }

    @Override
    public String getParliamentName() {
        return parliamentName;
    }

    void setTimestamp(final OffsetDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public OffsetDateTime getTimestamp() {
        return timestamp;
    }

}
