package net.filipvanlaenen.sapor2md;

import java.time.OffsetDateTime;

public interface StateSummary {

    long getNumberOfSimulations();

    OffsetDateTime getTimestamp();

}
