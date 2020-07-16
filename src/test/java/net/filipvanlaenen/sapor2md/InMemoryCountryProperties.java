package net.filipvanlaenen.sapor2md;

import java.time.OffsetDateTime;
import java.util.Map;

/**
 * Class implementing the abstract <code>CountryProperties</code> class, but in
 * memory, such that it can be used for testing purposes.
 */
public class InMemoryCountryProperties extends CountryProperties {
    /**
     * Constructor taking a map with the properties and a timestamp as its
     * parameters.
     *
     * @param map       The map containing the country properties.
     * @param timestamp The last modified timestamp of the file.
     */
    InMemoryCountryProperties(final Map<String, String> map, final OffsetDateTime timestamp) {
        super(map, timestamp);
    }
}
