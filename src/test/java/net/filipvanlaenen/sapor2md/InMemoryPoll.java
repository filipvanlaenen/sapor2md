package net.filipvanlaenen.sapor2md;

/**
 * Class implementing the abstract <code>Poll</code> class, but in memory, such
 * that it can be used for testing purposes.
 */
public final class InMemoryPoll extends Poll {
    /**
     * Constructor taking the base name as the argument.
     *
     * @param baseName The base name of the poll.
     */
    InMemoryPoll(final String baseName) {
        super(baseName);
    }
}
