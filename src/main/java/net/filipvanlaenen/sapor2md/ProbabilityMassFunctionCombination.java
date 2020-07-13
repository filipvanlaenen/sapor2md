package net.filipvanlaenen.sapor2md;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * A class representing a probability masse function combination. Such a
 * combination consists of a number of parliamentary groups with each of them a
 * probability mass function associated to them. The probability mass function
 * may have a voting intention percentage share, a number of seats, or anything
 * else that is comparable as the discrete value.
 *
 * @param <T>
 *            The type of the keys for the probability mass functions.
 */
public abstract class ProbabilityMassFunctionCombination<T extends Comparable<T>> {
    /**
     * A map holding the probability mass functions per group.
     */
    protected final Map<String, ProbabilityMassFunction<T>> map = new HashMap<>();

    /**
     * Returns a set with all groups.
     *
     * @return A set containing all the groups.
     */
    Set<String> getGroups() {
        return map.keySet();
    }

    /**
     * Returns the probability of a parliamentary group obtaining a value.
     *
     * @param group
     *            The name of the parliamentary group.
     * @param value
     *            A value.
     * @return The probability of the parliamentary group to obtain the value.
     */
    double getProbability(final String group, final T value) {
        return map.get(group).getProbability(value);
    }

    /**
     * Returns the median of a parliamentary group.
     *
     * @param group
     *            The name of the parliamentary group.
     * @return The median for the parliamentary group.
     */
    T getMedian(final String group) {
        return map.get(group).getMedian();
    }

    /**
     * Returns the confidence interval for a group.
     *
     * @param group
     *            The name of the parliamentary group.
     * @param confidence
     *            The level of confidence required.
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
