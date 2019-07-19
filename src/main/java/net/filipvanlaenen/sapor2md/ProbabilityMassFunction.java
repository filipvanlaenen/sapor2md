package net.filipvanlaenen.sapor2md;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A class representing a probability mass function.
 *
 * @param <T>
 */
public class ProbabilityMassFunction<T extends Comparable> {

    /**
     * A map holding the probabilities per key.
     */
    private final Map<T, Double> map = new HashMap<T, Double>();
    /**
     * The median for the probability mass function.
     */
    private T median;

    /**
     * Constructs a probability mass function from an array of objects. The array
     * has to have an even length, with each uneven element an instance of the key
     * class, and each even element a probability.
     *
     * @param objects
     *            An array defining a probability mass function.
     */
    ProbabilityMassFunction(final Object... objects) {
        if (objects.length % 2 == 1) {
            throw new IllegalArgumentException(
                    "The number of arguments to construct a probability mass function should be even.");
        }
        for (int i = 0; i < objects.length; i += 2) {
            Object key = objects[i];
            Object value = objects[i + 1];
            if (value instanceof Number) {
                map.put((T) key, (Double) value);
            } else {
                throw new IllegalArgumentException(
                        "The even arguments to construct a probability mass function should be numbers.");
            }
        }
    }

    /**
     * Returns the probability for a key.
     *
     * @param key
     *            The key.
     * @return The probability for the key.
     */
    double getProbability(final Object key) {
        return map.get(key);
    }

    /**
     * Calculates the median for the probability mass function.
     *
     * @return The median for the probability mass function.
     */
    private T calculateMedian() {
        Set<T> set = map.keySet();
        List<T> keys = new ArrayList<T>(set);
        Collections.sort(keys);
        double accumulatedProbability = 0D;
        for (T key : keys) {
            accumulatedProbability += map.get(key);
            if (accumulatedProbability >= 0.5D) {
                return key;
            }
        }
        return null;
    }

    /**
     * Returns the median for the probability mass function.
     *
     * @return The median for the probability mass function.
     */
    T getMedian() {
        if (median == null) {
            median = calculateMedian();
        }
        return median;
    }

}
