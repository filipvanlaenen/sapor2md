package net.filipvanlaenen.sapor2md;

import java.time.OffsetDateTime;

/**
 * Class implementing the <code>StateSummary</code> interface, but in-memory,
 * such that it can be used for testing purposes.
 */
public final class InMemoryStateSummary implements StateSummary {

    /**
     * Number of simulations run on the poll.
     */
    private long numberOfSimulations;
    /**
     * The timestamp for the state summary file.
     */
    private OffsetDateTime timestamp;

    /**
     * Sets the number of simulations run on the poll.
     *
     * @param numberOfSimulations
     *            The number of simulations run on the poll.
     */
    void setNumberOfSimulations(final long numberOfSimulations) {
        this.numberOfSimulations = numberOfSimulations;
    }

    @Override
    public long getNumberOfSimulations() {
        return numberOfSimulations;
    }

    /**
     * Sets the timestamp for the state summary file.
     *
     * @param timestamp
     *            The timestamp for the state summary file.
     */
    void setTimestamp(final OffsetDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public OffsetDateTime getTimestamp() {
        return timestamp;
    }

}
