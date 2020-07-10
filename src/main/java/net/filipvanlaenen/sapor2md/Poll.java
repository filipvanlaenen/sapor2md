package net.filipvanlaenen.sapor2md;

import java.time.LocalDate;

public interface Poll {

    String getBaseName();

    StateSummary getStateSummary();

    default boolean hasStateSummary() {
        return getStateSummary() != null;
    }

    String getComissioners();

    LocalDate getFieldworkEnd();

    LocalDate getFieldworkStart();

    String getPollingFirm();

    long getVotingIntentionsFileSize();

}
