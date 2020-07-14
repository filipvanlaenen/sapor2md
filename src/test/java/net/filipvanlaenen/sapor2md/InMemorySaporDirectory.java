package net.filipvanlaenen.sapor2md;

/**
 * Class implementing the <code>SaporDirectory</code> interface in memory, to be
 * used for testing purposes.
 */
public final class InMemorySaporDirectory extends SaporDirectory {
    /**
     * Constructor taking an instance of <code>InMemoryCountryProperties</code> as
     * the argument.
     *
     * @param countryProperties
     *            The country properties for the Sapor directory.
     */
    InMemorySaporDirectory(final InMemoryCountryProperties countryProperties) {
        this.countryProperties = countryProperties;
    }

    /**
     * Adds a poll.
     *
     * @param poll
     *            A poll.
     */
    void addPoll(final Poll poll) {
        polls.add(poll);
    }
}
