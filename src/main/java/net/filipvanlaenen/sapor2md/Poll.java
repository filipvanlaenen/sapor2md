package net.filipvanlaenen.sapor2md;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 * An abstract class defining the behavior of a poll and implementing common
 * functionality.
 */
public abstract class Poll {
    /**
     * The key for the property containing the name of the commissioners.
     */
    static final String COMMISSIONERS_KEY = "Commissioners";
    /**
     * The key for the property containing the name of the polling firm that
     * conducted the poll.
     */
    static final String POLLING_FIRM_KEY = "PollingFirm";
    /**
     * The key for the property containing the end date for the fieldwork period.
     */
    static final String FIELDWORK_END_KEY = "FieldworkEnd";
    /**
     * The key for the property containing the start date for the fieldwork period.
     */
    static final String FIELDWORK_START_KEY = "FieldworkStart";

    /**
     * The base name for the poll.
     */
    private final String baseName;
    /**
     * The commissioners of the poll.
     */
    private final String commissioners;
    /**
     * The end of the fieldwork period.
     */
    private final LocalDate fieldworkEnd;
    /**
     * The start of the fieldwork period.
     */
    private final LocalDate fieldworkStart;
    /**
     * The polling firm that conducted the poll.
     */
    private final String pollingFirm;
    /**
     * The size of the file containing the chart with the seating plan projection
     * for the poll.
     */
    private long seatingPlanProjectionChartFileSize;
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
     * Constructor taking the base name and a map with the properties as the
     * argument.
     *
     * @param baseName   The base name of the poll.
     * @param properties The map containing the country properties.
     */
    Poll(final String baseName, final Map<String, String> properties) {
        this.baseName = baseName;
        commissioners = properties.get(COMMISSIONERS_KEY);
        pollingFirm = properties.get(POLLING_FIRM_KEY);
        fieldworkEnd = LocalDate.parse(properties.get(FIELDWORK_END_KEY), DateTimeFormatter.ISO_LOCAL_DATE);
        fieldworkStart = LocalDate.parse(properties.get(FIELDWORK_START_KEY), DateTimeFormatter.ISO_LOCAL_DATE);
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
     * Returns the end of the fieldwork period.
     *
     * @return The end of the fieldwork period.
     */
    LocalDate getFieldworkEnd() {
        return fieldworkEnd;
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
     * Returns the polling firm that conducted the poll.
     *
     * @return The polling firm that conducted the poll.
     */
    String getPollingFirm() {
        return pollingFirm;
    }

    /**
     * Returns the size of the file with the seating plan projection chart.
     *
     * @return The size of the file with the seating plan projection chart.
     */
    long getSeatingPlanProjectionChartFileSize() {
        return seatingPlanProjectionChartFileSize;
    }

    /**
     * Sets the size of the file containing the seating plan projection chart.
     *
     * @param seatingPlanProjectionChartFileSize The size of the file containing the
     *                                           seating plan projection chart.
     */
    void setSeatingPlanProjectionChartFileSize(final long seatingPlanProjectionChartFileSize) {
        this.seatingPlanProjectionChartFileSize = seatingPlanProjectionChartFileSize;
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
     * @param seatProjectionsChartFileSize The size of the file containing the seat
     *                                     projections per party chart.
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
