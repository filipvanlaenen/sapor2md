package net.filipvanlaenen.sapor2md;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A class representing a probability mass function combination. Such a
 * combination consists of a number of parliamentary groups with each of them a
 * probability mass function associated to them. The probability mass function
 * may have a voting intention percentage share, a number of seats, or anything
 * else that is comparable as the discrete value.
 *
 * @param <T> The type of the keys for the probability mass functions.
 */
public abstract class ProbabilityMassFunctionCombination<T extends Comparable<T>> {
    /**
     * Magic number 0.95, or 95 percent.
     */
    protected static final double NINETY_FIVE_PERCENT = 0.95D;
    /**
     * A map holding the probability mass functions per group.
     */
    private final Map<String, ProbabilityMassFunction<T>> map = new HashMap<>();

    /**
     * Returns the map holding the probability mass functions per group.
     *
     * @return The map holding the probability mass functions per group.
     */
    Map<String, ProbabilityMassFunction<T>> getMap() {
        return map;
    }

    /**
     * Returns a set with all groups, sorted. The groups are sorted descending on
     * median first, upper bound of the 95 percent confidence interval second, and
     * lower bound of the 95 percent confidence interval third. If the median and
     * the confidence intervals are equal, the groups are sorted alphabetically on
     * their name.
     *
     * @return A set containing all the groups, sorted.
     */
    List<String> getGroups() {
        List<String> sortedGroups = new ArrayList<String>(map.keySet());
        sortedGroups.sort(new Comparator<String>() {
            @Override
            public int compare(final String group1, final String group2) {
                return compareGroups(group1, group2);
            }

        });
        return sortedGroups;
    }

    /**
     * Compares two groups for sorting. The groups are sorted descending on median
     * first, upper bound of the 95 percent confidence interval second, and lower
     * bound of the 95 percent confidence interval third. If the median and the
     * confidence intervals are equal, the groups are sorted alphabetically on their
     * name.
     *
     * @param group1 The name of the first group.
     * @param group2 The name of the second group.
     * @return The comparison result.
     */
    int compareGroups(final String group1, final String group2) {
        int compareMedian = getMedian(group2).compareTo(getMedian(group1));
        if (compareMedian == 0) {
            ConfidenceInterval<T> c1 = getConfidenceInterval(group1, NINETY_FIVE_PERCENT);
            ConfidenceInterval<T> c2 = getConfidenceInterval(group2, NINETY_FIVE_PERCENT);
            int compareUpperBound = c2.getUpperBound().compareTo(c1.getUpperBound());
            if (compareUpperBound == 0) {
                int compareLowerBound = c2.getLowerBound().compareTo(c1.getLowerBound());
                if (compareLowerBound == 0) {
                    return group1.compareToIgnoreCase(group2);
                } else {
                    return compareLowerBound;
                }
            } else {
                return compareUpperBound;
            }
        } else {
            return compareMedian;
        }
    }

    /**
     * Returns the probability of a parliamentary group obtaining a value.
     *
     * @param group The name of the parliamentary group.
     * @param value A value.
     * @return The probability of the parliamentary group to obtain the value.
     */
    double getProbability(final String group, final T value) {
        return map.get(group).getProbability(value);
    }

    /**
     * Returns the median of a parliamentary group.
     *
     * @param group The name of the parliamentary group.
     * @return The median for the parliamentary group.
     */
    T getMedian(final String group) {
        return map.get(group).getMedian();
    }

    /**
     * Returns the confidence interval for a group.
     *
     * @param group      The name of the parliamentary group.
     * @param confidence The level of confidence required.
     * @return The confidence interval for the given confidence for a group.
     */
    ConfidenceInterval<T> getConfidenceInterval(final String group, final double confidence) {
        return map.get(group).getConfidenceInterval(confidence);
    }

    /**
     * Calculates the medians.
     *
     * @return The medians.
     */
    protected Map<String, T> calculateMedians() {
        Map<String, T> medians = new HashMap<String, T>();
        for (String g : map.keySet()) {
            medians.put(g, map.get(g).getMedian());
        }
        return medians;
    }
}
