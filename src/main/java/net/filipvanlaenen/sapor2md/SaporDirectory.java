package net.filipvanlaenen.sapor2md;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * Abstract class defining the behavior for a Sapor directory and implementing
 * common functionality.
 */
public abstract class SaporDirectory {
    /**
     * The polls.
     */
    protected final List<Poll> polls = new ArrayList<Poll>();
    /**
     * The country properties.
     */
    protected CountryProperties countryProperties;

    /**
     * Returns the country properties.
     *
     * @return The country properties.
     */
    CountryProperties getCountryProperties() {
        return countryProperties;
    }

    /**
     * Returns an iterator with the polls in the directory.
     *
     * @return An iterator with the polls.
     */
    Iterator<Poll> getPolls() {
        List<Poll> sortedPolls = new ArrayList<Poll>(polls);
        sortedPolls.sort(new Comparator<Poll>() {
            @Override
            public int compare(Poll poll1, Poll poll2) {
                int compareEndDate = poll2.getFieldworkEnd().compareTo(poll1.getFieldworkEnd());
                if (compareEndDate == 0) {
                    int compareStartDate = poll2.getFieldworkStart().compareTo(poll1.getFieldworkStart());
                    if (compareStartDate == 0) {
                        return poll1.getPollingFirm().compareTo(poll2.getPollingFirm());
                    } else {
                        return compareStartDate;
                    }
                } else {
                    return compareEndDate;
                }
            }
        });
        return sortedPolls.iterator();
    }
}
