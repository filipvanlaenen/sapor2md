package net.filipvanlaenen.sapor2md;

import java.time.LocalDate;

/**
 * An abstract class defining the behavior of a poll and implementing common
 * functionality.
 */
public abstract class Poll {
    /**
     * The base name for the poll.
     */
    private final String baseName;
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
     * The seat projection for the poll.
     */
    private SeatProjection seatProjection;
    /**
     * The size of the file containing the chart with the seat projections per party
     * for the poll.
     */
    private long seatProjectionsChartFileSize;
    /**
     * The state summary for the poll.
     */
    private StateSummary stateSummary;
    /**
     * The voting intentions for the poll.
     */
    private VotingIntentions votingIntentions;
    /**
     * The size of the file containing the chart with the voting intentions for the
     * poll.
     */
    private long votingIntentionsChartFileSize;

    /**
     * Constructor taking the base name as the argument.
     *
     * @param baseName The base name of the poll.
     */
    Poll(final String baseName) {
        this.baseName = baseName;
    }

    /**
     * Returns the base name of a poll.
     *
     * @return The base name of a poll.
     */
    String getBaseName() {
        return baseName;
    }

    /**
     * Returns the commissioners for the poll.
     *
     * @return The commissioners for the poll.
     */
    String getComissioners() {
        return commissioners;
    }

    /**
     * Sets the commissioners for the poll.
     *
     * @param commissioners The commissioners for the poll.
     */
    void setCommissioners(final String commissioners) {
        this.commissioners = commissioners;
    }

    /**
     * Returns the end of the fieldwork period.
     *
     * @return The end of the fieldwork period.
     */
    LocalDate getFieldworkEnd() {
        return fieldworkEnd;
    }

    /**
     * Sets the end of the fieldwork period of the poll.
     *
     * @param fieldworkEnd The end of the fieldwork period of the poll.
     */
    void setFieldworkEnd(final LocalDate fieldworkEnd) {
        this.fieldworkEnd = fieldworkEnd;
    }

    /**
     * Returns the start of the fieldwork period.
     *
     * @return The start of the fieldwork period.
     */
    LocalDate getFieldworkStart() {
        return fieldworkStart;
    }

    /**
     * Sets the start of the fieldwork period of the poll.
     *
     * @param fieldworkStart The start of the fieldwork period of the poll.
     */
    void setFieldworkStart(final LocalDate fieldworkStart) {
        this.fieldworkStart = fieldworkStart;
    }

    /**
     * Returns the polling firm that conducted the poll.
     *
     * @return The polling firm that conducted the poll.
     */
    String getPollingFirm() {
        return pollingFirm;
    }

    /**
     * Sets the polling firm that conducted the poll.
     *
     * @param pollingFirm The polling firm that conducted the poll.
     */
    void setPollingFirm(final String pollingFirm) {
        this.pollingFirm = pollingFirm;
    }
    
    /**
     * Returns the seat projection for the poll.
     *
     * @return The seat projection for the poll.
     */
    SeatProjection getSeatProjection() {
        return seatProjection;
    }

    /**
     * Sets the seat projection for the poll.
     *
     * @param seatProjection The seat projection for the poll.
     */
    void setSeatProjection(final SeatProjection seatProjection) {
        this.seatProjection = seatProjection;
    }

    /**
     * Returns the size of the file with the seat projections per party chart.
     *
     * @return The size of the file with the seat projections per party chart.
     */
    long getSeatProjectionsChartFileSize() {
        return seatProjectionsChartFileSize;
    }

    /**
     * Sets the size of the file containing the seat projections per party chart.
     *
     * @param votingIntentionsChartFileSize The size of the file containing the seat
     *                                      projections per party chart.
     */
    void setSeatProjectionsChartFileSize(final long seatProjectionsChartFileSize) {
        this.seatProjectionsChartFileSize = seatProjectionsChartFileSize;
    }

    /**
     * Returns the state summary of a poll.
     *
     * @return The state summary of a poll.
     */
    StateSummary getStateSummary() {
        return stateSummary;
    }

    /**
     * Returns whether the poll has a state summary.
     *
     * @return Returns whether the poll has a state summary.
     */
    boolean hasStateSummary() {
        return stateSummary != null;
    }

    /**
     * Sets the state summary for the poll.
     *
     * @param stateSummary The state summary for the poll.
     */
    void setStateSummary(final StateSummary stateSummary) {
        this.stateSummary = stateSummary;
    }

    /**
     * Returns the voting intentions for the poll.
     *
     * @return The voting intentions for the poll.
     */
    VotingIntentions getVotingIntentions() {
        return votingIntentions;
    }

    /**
     * Sets the voting intentions for the poll.
     *
     * @param votingIntentions The voting intentions for the poll.
     */
    void setVotingIntentions(final VotingIntentions votingIntentions) {
        this.votingIntentions = votingIntentions;
    }

    /**
     * Returns the size of the file with the voting intentions chart.
     *
     * @return The size of the file with the voting intentions chart.
     */
    long getVotingIntentionsChartFileSize() {
        return votingIntentionsChartFileSize;
    }

    /**
     * Sets the size of the file containing the voting intentions chart.
     *
     * @param votingIntentionsChartFileSize The size of the file containing the
     *                                      voting intentions chart.
     */
    void setVotingIntentionsChartFileSize(final long votingIntentionsChartFileSize) {
        this.votingIntentionsChartFileSize = votingIntentionsChartFileSize;
    }
}
