package net.filipvanlaenen.sapor2md;

import java.time.LocalDate;

/**
 * An interface defining the behavior of a poll.
 */
public interface Poll {

    /**
     * Returns the base name of a poll.
     *
     * @return The base name of a poll.
     */
    String getBaseName();

    /**
     * Returns the state summary of a poll.
     *
     * @return The state summary of a poll.
     */
    StateSummary getStateSummary();

    /**
     * Returns whether the poll has a state summary.
     *
     * @return Returns whether the poll has a state summary.
     */
    default boolean hasStateSummary() {
        return getStateSummary() != null;
    }

    /**
     * Returns the commissioners for the poll.
     *
     * @return The commissioners for the poll.
     */
    String getComissioners();

    /**
     * Returns the end of the fieldwork period.
     *
     * @return The end of the fieldwork period.
     */
    LocalDate getFieldworkEnd();

    /**
     * Returns the start of the fieldwork period.
     *
     * @return The start of the fieldwork period.
     */
    LocalDate getFieldworkStart();

    /**
     * Returns the polling firm that conducted the poll.
     *
     * @return The polling firm that conducted the poll.
     */
    String getPollingFirm();

    /**
     * Returns the voting intentions for the poll.
     *
     * @return The voting intentions for the poll.
     */
    VotingIntentions getVotingIntentions();

    /**
     * Returns the size of the file with the voting intentions chart.
     *
     * @return The size of the file with the voting intentions chart.
     */
    long getVotingIntentionsFileSize();

}
