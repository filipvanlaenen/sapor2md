package net.filipvanlaenen.sapor2md;

import java.time.OffsetDateTime;

public class InMemoryStateSummary implements StateSummary {

    private long numberOfSimulations;
    /**
     * The timestamp for the state summary file.
     */
    private OffsetDateTime timestamp;

    void setNumberOfSimulations(long numberOfSimulations) {
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
