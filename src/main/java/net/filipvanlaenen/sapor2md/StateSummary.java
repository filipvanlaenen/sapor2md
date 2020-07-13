package net.filipvanlaenen.sapor2md;

import java.time.OffsetDateTime;

/**
 * Interface defining the behavior of the state summary for a poll.
 */
public interface StateSummary {

    /**
     * Returns the number of simulations run on the poll.
     *
     * @return The number of simulations run on the poll.
     */
    long getNumberOfSimulations();

    /**
     * Returns the timestamp of the state summary file.
     *
     * @return The timestamp of the state summary file.
     */
    OffsetDateTime getTimestamp();

}
