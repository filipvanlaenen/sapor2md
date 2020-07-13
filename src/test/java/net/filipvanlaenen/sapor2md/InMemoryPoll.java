package net.filipvanlaenen.sapor2md;

import java.time.LocalDate;

/**
 * Class implementing the <code>Poll</code> interface, but in memory, such that
 * it can be used for testing purposes.
 */
public final class InMemoryPoll implements Poll {

    /**
     * The base name for the poll.
     */
    private final String baseName;
    /**
     * The state summary for the poll.
     */
    private StateSummary stateSummary;
    /**
     * The commissioners of the poll.
     */
    private String commissioners;
    /**
     * The end of the fieldwork period.
     */
    private LocalDate fieldworkEnd;
    /**
     * The start of the fieldwork period.
     */
    private LocalDate fieldworkStart;
    /**
     * The polling firm that conducted the poll.
     */
    private String pollingFirm;
    /**
     * The voting intentions for the poll.
     */
    private VotingIntentions votingIntentions;
    /**
     * The size of the file containing the voting intentions for the poll.
     */
    private long votingIntentionsFileSize;

    /**
     * Constructor taking the base name as the argument.
     *
     * @param baseName
     *            The base name of the poll.
     */
    InMemoryPoll(final String baseName) {
        this.baseName = baseName;
    }

    @Override
    public String getBaseName() {
        return baseName;
    }

    /**
     * Sets the state summary for the poll.
     *
     * @param stateSummary
     *            The state summary for the poll.
     */
    void setStateSummary(final StateSummary stateSummary) {
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

    /**
     * Sets the commissioners for the poll.
     *
     * @param commissioners
     *            The commissioners for the poll.
     */
    void setCommissioners(final String commissioners) {
        this.commissioners = commissioners;
    }

    @Override
    public LocalDate getFieldworkEnd() {
        return fieldworkEnd;
    }

    /**
     * Sets the end of the fieldwork period of the poll.
     *
     * @param fieldworkEnd
     *            The end of the fieldwork period of the poll.
     */
    void setFieldworkEnd(final LocalDate fieldworkEnd) {
        this.fieldworkEnd = fieldworkEnd;
    }

    @Override
    public LocalDate getFieldworkStart() {
        return fieldworkStart;
    }

    /**
     * Sets the start of the fieldwork period of the poll.
     *
     * @param fieldworkStart
     *            The start of the fieldwork period of the poll.
     */
    void setFieldworkStart(final LocalDate fieldworkStart) {
        this.fieldworkStart = fieldworkStart;
    }

    @Override
    public String getPollingFirm() {
        return pollingFirm;
    }

    /**
     * Sets the polling firm that conducted the poll.
     *
     * @param pollingFirm
     *            The polling firm that conducted the poll.
     */
    void setPollingFirm(final String pollingFirm) {
        this.pollingFirm = pollingFirm;
    }

    @Override
    public long getVotingIntentionsFileSize() {
        return votingIntentionsFileSize;
    }

    /**
     * Sets the size of the file containing the voting intentions.
     *
     * @param votingIntentionsFileSize
     *            The size of the file containing the voting intentions.
     */
    void setVotingIntentionsFileSize(final long votingIntentionsFileSize) {
        this.votingIntentionsFileSize = votingIntentionsFileSize;
    }

    /**
     * Sets the voting intentions for the poll.
     *
     * @param votingIntentions
     *            The voting intentions for the poll.
     */
    void setVotingIntentions(final VotingIntentions votingIntentions) {
        this.votingIntentions = votingIntentions;
    }

    @Override
    public VotingIntentions getVotingIntentions() {
        return votingIntentions;
    }

}
