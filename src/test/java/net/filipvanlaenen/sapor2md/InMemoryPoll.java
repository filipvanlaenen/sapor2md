package net.filipvanlaenen.sapor2md;

import java.time.LocalDate;

public class InMemoryPoll implements Poll {

    final private String baseName;
    private StateSummary stateSummary;
    private String commissioners;
    private LocalDate fieldworkEnd;
    private LocalDate fieldworkStart;
    private String pollingFirm;
    private long votingIntentionsFileSize;

    InMemoryPoll(String baseName) {
        this.baseName = baseName;
    }

    @Override
    public String getBaseName() {
        return baseName;
    }

    void setStateSummary(StateSummary stateSummary) {
        this.stateSummary = stateSummary;
    }

    @Override
    public StateSummary getStateSummary() {
        return stateSummary;
    }

    @Override
    public String getComissioners() {
        return commissioners;
    }

    void setCommissioners(String commissioners) {
        this.commissioners = commissioners;

    }

    @Override
    public LocalDate getFieldworkEnd() {
        return fieldworkEnd;
    }

    void setFieldworkEnd(LocalDate fieldworkEnd) {
        this.fieldworkEnd = fieldworkEnd;
    }

    @Override
    public LocalDate getFieldworkStart() {
        return fieldworkStart;
    }

    void setFieldworkStart(LocalDate fieldworkStart) {
        this.fieldworkStart = fieldworkStart;
    }

    @Override
    public String getPollingFirm() {
        return pollingFirm;
    }

    void setPollingFirm(String pollingFirm) {
        this.pollingFirm = pollingFirm;
    }

    @Override
    public long getVotingIntentionsFileSize() {
        return votingIntentionsFileSize;
    }

    void setVotingIntentionsFileSize(long votingIntentionsFileSize) {
        this.votingIntentionsFileSize = votingIntentionsFileSize;
    }

}
