package net.filipvanlaenen.sapor2md;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Abstract class defining the behavior for a Sapor directory and implementing
 * common functionality.
 */
public abstract class SaporDirectory {
    /**
     * The polls.
     */
    protected final Set<Poll> polls = new HashSet<Poll>();
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
            public int compare(final Poll poll1, final Poll poll2) {
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
