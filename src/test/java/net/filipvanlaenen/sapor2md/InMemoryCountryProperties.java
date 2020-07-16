package net.filipvanlaenen.sapor2md;

import java.time.OffsetDateTime;
import java.util.Map;

public class InMemoryCountryProperties extends CountryProperties {
    InMemoryCountryProperties(Map<String, String> map, OffsetDateTime timestamp) {
        super(map, timestamp);
    }
}
