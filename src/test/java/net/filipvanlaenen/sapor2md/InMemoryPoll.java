package net.filipvanlaenen.sapor2md;

import java.util.Map;

/**
 * Class implementing the abstract <code>Poll</code> class, but in memory, such
 * that it can be used for testing purposes.
 */
public final class InMemoryPoll extends Poll {
    /**
     * Constructor taking the base name and a map with the properties as the
     * argument.
     *
     * @param baseName   The base name of the poll.
     * @param properties The map containing the country properties.
     */
    InMemoryPoll(final String baseName, final Map<String, String> properties) {
        super(baseName, properties);
    }
}
