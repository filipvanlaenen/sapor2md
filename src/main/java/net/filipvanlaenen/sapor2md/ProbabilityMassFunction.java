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
 *            The type of the keys for the probability mass function.
 */
public class ProbabilityMassFunction<T extends Comparable<T>> {
    /**
     * The magic number one half.
     */
    private static final double ONE_HALF = 0.5D;
    /**
     * A map holding the probabilities per key.
     */
    private final Map<T, Double> map = new HashMap<T, Double>();
    /**
     * The median for the probability mass function.
     */
    private T median;

    /**
     * A map holding the confidence intervals.
     */
    private final Map<Double, ConfidenceInterval<T>> confidenceIntervals = new HashMap<Double, ConfidenceInterval<T>>();

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
            if (accumulatedProbability >= ONE_HALF) {
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

    /**
     * Returns the keys of the probability mass function.
     *
     * @return The keys of the probability mass function.
     */
    Set<T> keySet() {
        return map.keySet();
    }

    /**
     * Returns a confidence interval on the probability mass function.
     *
     * @param confidence
     *            The level of confidence for the interval.
     * @return The confidence interval.
     */
    ConfidenceInterval<T> getConfidenceInterval(final double confidence) {
        if (!confidenceIntervals.containsKey(confidence)) {
            confidenceIntervals.put(confidence, calculateConfidenceInterval(confidence));
        }
        return confidenceIntervals.get(confidence);
    }

    /**
     * Calculates a confidence interval.
     *
     * @param confidence
     *            The level of confidence for the interval.
     * @return The confidence interval as a pair of Ts.
     */
    private ConfidenceInterval<T> calculateConfidenceInterval(final double confidence) {
        Set<T> set = map.keySet();
        List<T> keys = new ArrayList<T>(set);
        Collections.sort(keys);
        double lowerProbabilityBound = (1D - confidence) / 2D;
        double upperProbabilityBound = 1D - lowerProbabilityBound;
        T lowerBound = null;
        T upperBound = keys.get(0);
        double accumulatedProbability = 0D;
        for (T key : keys) {
            accumulatedProbability += map.get(key);
            if (lowerBound == null && accumulatedProbability > lowerProbabilityBound) {
                lowerBound = key;
            }
            if (accumulatedProbability <= upperProbabilityBound) {
                upperBound = key;
            }
        }
        return new ConfidenceInterval<T>(lowerBound, upperBound);
    }

}
