package net.filipvanlaenen.sapor2md;

import java.time.OffsetDateTime;

/**
 * Abstract class defining the behavior of the state summary for a poll and
 * implementing common functionality.
 */
public abstract class StateSummary {
    /**
     * Number of simulations run on the poll.
     */
    private long numberOfSimulations;
    /**
     * The timestamp for the state summary file.
     */
    private OffsetDateTime timestamp;

    /**
     * Returns the number of simulations run on the poll.
     *
     * @return The number of simulations run on the poll.
     */
    long getNumberOfSimulations() {
        return numberOfSimulations;
    }

    /**
     * Sets the number of simulations run on the poll.
     *
     * @param numberOfSimulations The number of simulations run on the poll.
     */
    void setNumberOfSimulations(final long numberOfSimulations) {
        this.numberOfSimulations = numberOfSimulations;
    }

    /**
     * Returns the timestamp of the state summary file.
     *
     * @return The timestamp of the state summary file.
     */
    OffsetDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the timestamp for the state summary file.
     *
     * @param timestamp The timestamp for the state summary file.
     */
    void setTimestamp(final OffsetDateTime timestamp) {
        this.timestamp = timestamp;
    }

}
