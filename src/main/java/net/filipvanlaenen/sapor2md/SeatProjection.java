package net.filipvanlaenen.sapor2md;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A class representing a seat projection. A seat projection consists of a
 * number of parliamentary groups with each of them a probability mass function
 * associated to them.
 */
public class SeatProjection {

    /**
     * A map holding the probability mass functions per group.
     */
    private final Map<String, ProbabilityMassFunction<Integer>> map = new HashMap<>();
    /**
     * A map holding the adjusted medians per parliamentary group per parliament
     * size.
     */
    private final Map<Integer, Map<String, Integer>> adjustedMedians = new HashMap<>();

    /**
     * Constructs a seat projection from an array of objects. The array has to have
     * an even length, with each uneven element the name of a parliamentary groups,
     * and each even element a probability mass function.
     *
     * @param objects
     *            An array defining a seat projection.
     */
    SeatProjection(final Object... objects) {
        if (objects.length % 2 == 1) {
            throw new IllegalArgumentException(
                    "The number of arguments to construct a seat projection should be even.");
        }
        for (int i = 0; i < objects.length; i += 2) {
            Object key = objects[i];
            Object value = objects[i + 1];
            if (!(key instanceof String)) {
                throw new IllegalArgumentException(
                        "The uneven arguments to construct a seat projection should be strings.");
            } else if (!(value instanceof ProbabilityMassFunction)) {
                throw new IllegalArgumentException(
                        "The uneven arguments to construct a seat projection should be probability mass functions.");
            } else {
                map.put((String) key, (ProbabilityMassFunction<Integer>) value);
            }
        }
    }

    /**
     * Returns the probability of a parliamentary group obtaining exactly the
     * specified number of seats.
     *
     * @param group
     *            The name of the parliamentary group.
     * @param noOfSeats
     *            A number of seats.
     * @return The probability of the parliamentary group to obtain exactly the
     *         specified number of seats.
     */
    double getProbability(final String group, final Integer noOfSeats) {
        return map.get(group).getProbability(noOfSeats);
    }

    /**
     * Returns the median of a parliamentary group.
     *
     * @param group
     *            The name of the parliamentary group.
     * @return The median for the parliamentary group.
     */
    Integer getMedian(final String group) {
        return map.get(group).getMedian();
    }

    /**
     * Calculates the adjusted medians for a given size for a parliament.
     *
     * @param size
     *            The size of the parliament.
     * @return The adjusted medians for the given parliament size.
     */
    private Map<String, Integer> calculateAdjustedMedians(final int size) {
        int sumOfMedians = 0;
        for (String g : map.keySet()) {
            sumOfMedians += map.get(g).getMedian();
        }
        Map<String, Integer> medians = calculateMedians();
        if (sumOfMedians == size) {
            return medians;
        } else {
            double selectionFactor = 1D;
            Map<String, Integer> possibleResult = adjustMedians(medians, sumOfMedians, size, selectionFactor);
            while (possibleResult.isEmpty()) {
                selectionFactor /= 2D;
                possibleResult = adjustMedians(medians, sumOfMedians, size, selectionFactor);
            }
            return possibleResult;
        }
    }

    /**
     * Tries to find a solution based on the medians that fills up the parliament.
     * In many cases, a projection can be picked that would actually increase the
     * probability compared to the medians. If the selectionFactor is set to 1, only
     * keys having a larger probability than the median will be considered. If this
     * doesn't work, the factor should be gradually lowered, until a solution can be
     * found .
     *
     * @param medians
     *            The medians.
     * @param sumOfMedians
     *            The sum of the medians.
     * @param size
     *            The requested size of the parliament.
     * @param selectionFactor
     *            The factor used to compare a candidate number of seats'
     *            probability with the median's probability.
     * @return A seat projection that fills up the parliament, based on the medians.
     */
    private Map<String, Integer> adjustMedians(final Map<String, Integer> medians, final int sumOfMedians,
            final int size, final double selectionFactor) {
        Map<String, Integer> possibleResult = new HashMap<String, Integer>();
        Map<String, List<Integer>> candidates = new HashMap<String, List<Integer>>();
        String[] groups = new String[map.size()];
        int i = 0;
        for (String g : map.keySet()) {
            groups[i] = g;
            List<Integer> candidateSeats = new ArrayList<Integer>();
            Integer median = medians.get(g);
            ProbabilityMassFunction<Integer> pmf = map.get(g);
            double medianProbability = pmf.getProbability(median);
            for (Integer noOfSeats : pmf.keySet()) {
                if (((size > sumOfMedians) && (noOfSeats >= median) || (size < sumOfMedians) && (noOfSeats <= median))
                        && pmf.getProbability(noOfSeats) > medianProbability * selectionFactor) {
                    candidateSeats.add(noOfSeats);
                }
            }
            candidates.put(g, candidateSeats);
            i += 1;
        }
        double highestProbability = 0D;
        int[] counter = new int[candidates.size()];
        while (counter[counter.length - 1] < candidates.get(groups[groups.length - 1]).size()) {
            double p = 1D;
            int s = 0;
            for (int k = 0; k < counter.length; k++) {
                p *= map.get(groups[k]).getProbability(candidates.get(groups[k]).get(counter[k]));
                s += candidates.get(groups[k]).get(counter[k]);
            }
            if (s == size && p > highestProbability) {
                highestProbability = p;
                for (int k = 0; k < counter.length; k++) {
                    possibleResult.put(groups[k], candidates.get(groups[k]).get(counter[k]));
                }
            }
            counter[0] += 1;
            int j = 0;
            while (j < counter.length - 1 && counter[j] == candidates.get(groups[j]).size()) {
                counter[j] = 0;
                counter[j + 1] += 1;
                j += 1;
            }
        }
        return possibleResult;
    }

    /**
     * Calculates the medians.
     *
     * @return The medians.
     */
    private Map<String, Integer> calculateMedians() {
        Map<String, Integer> medians = new HashMap<String, Integer>();
        for (String g : map.keySet()) {
            medians.put(g, map.get(g).getMedian());
        }
        return medians;
    }

    /**
     * Returns the adjusted median of a parliamentary group for a given size for a
     * parliament.
     *
     * @param group
     *            The name of the parliamentary group.
     * @param size
     *            The size of the parliament.
     * @return The adjusted median for the parliamentary group.
     */
    Integer getAdjustedMedian(final String group, final int size) {
        if (!adjustedMedians.containsKey(size)) {
            adjustedMedians.put(size, calculateAdjustedMedians(size));
        }
        return adjustedMedians.get(size).get(group);
    }

    /**
     * Returns a set with all groups.
     *
     * @return A set containing all the groups.
     */
    Set<String> getGroups() {
        return map.keySet();
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
    ConfidenceInterval<Integer> getConfidenceInterval(final String group, final double confidence) {
        return map.get(group).getConfidenceInterval(confidence);
    }
}
