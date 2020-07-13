package net.filipvanlaenen.sapor2md;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A class representing a seat projection. A seat projection consists of a
 * number of parliamentary groups with each of them a probability mass function
 * in terms of numbers of seats associated to them.
 */
public class SeatProjection extends ProbabilityMassFunctionCombination<Integer> {

    /**
     * A map holding the adjusted medians per parliamentary group per parliament
     * size.
     */
    private final Map<Integer, Map<String, Integer>> adjustedMedians = new HashMap<>();

    /**
     * Parses a string into a seat projection object.
     *
     * @param probabilityMassFunctions
     *            A string representation of a seat projection.
     * @return A seat projection object.
     */
    private static SeatProjection parseFromString(final String probabilityMassFunctions) {
        List<Object> arguments = new ArrayList<Object>();
        String[] lines = probabilityMassFunctions.split("\\R");
        for (String line : lines) {
            String[] lineComponents = line.split("\\|");
            String label = lineComponents[0].trim();
            if (!label.equals("Choice")) {
                arguments.add(label);
                List<Object> pmfArguments = new ArrayList<Object>();
                for (int i = 1; i < lineComponents.length; i++) {
                    pmfArguments.add(i - 1);
                    pmfArguments.add(Double.parseDouble(lineComponents[i]));
                }
                arguments.add(new ProbabilityMassFunction<Integer>(pmfArguments.toArray()));
            }
        }
        return new SeatProjection(arguments.toArray());
    }

    /**
     * Calculates the adjusted lower bound of the 95 percent confidence interval,
     * the median and the adjusted median for a seat projection and a parliament
     * size. The seat projection is given as a string that is parsed into a seat
     * projection object.
     *
     * @param probabilityMassFunctionsString
     *            A string that can be parsed into a seat projection object.
     * @param parliamentSize
     *            The size of the parliament.
     * @return A string with the lower bound of the 95 percent confidence interval,
     *         the median and the adjusted median for each parliamentary group.
     */
    static String calculateAdjustedMedians(final String probabilityMassFunctionsString, final int parliamentSize) {
        SeatProjection seatProjection = SeatProjection.parseFromString(probabilityMassFunctionsString);
        StringBuilder contentBuilder = new StringBuilder();
        contentBuilder.append("Choice | CI95LB | Median | Adjusted Median\n");
        for (String group : seatProjection.getGroups()) {
            contentBuilder.append(group).append(" | ")
                    .append(seatProjection.getConfidenceInterval(group, 0.95D).getLowerBound()).append(" | ")
                    .append(seatProjection.getMedian(group)).append(" | ")
                    .append(seatProjection.getAdjustedMedian(group, parliamentSize)).append("\n");
        }
        return contentBuilder.toString();
    }

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
        Map<String, List<Integer>> candidateNosOfSeats = selectCandidateNosOfSeatsToBeAdjustedMedians(medians,
                sumOfMedians, size, selectionFactor);
        int noOfGroups = map.size();
        String[] groups = new ArrayList<String>(map.keySet()).toArray(new String[noOfGroups]);
        double highestProbability = 0D;
        int[] counter = new int[noOfGroups];
        while (counter[noOfGroups - 1] < candidateNosOfSeats.get(groups[noOfGroups - 1]).size()) {
            double p = 1D;
            int s = 0;
            for (int k = 0; k < noOfGroups; k++) {
                p *= map.get(groups[k]).getProbability(candidateNosOfSeats.get(groups[k]).get(counter[k]));
                s += candidateNosOfSeats.get(groups[k]).get(counter[k]);
            }
            if (s == size && p > highestProbability) {
                highestProbability = p;
                for (int k = 0; k < noOfGroups; k++) {
                    possibleResult.put(groups[k], candidateNosOfSeats.get(groups[k]).get(counter[k]));
                }
            }
            counter[0] += 1;
            int j = 0;
            while (j < noOfGroups - 1 && counter[j] == candidateNosOfSeats.get(groups[j]).size()) {
                counter[j] = 0;
                counter[j + 1] += 1;
                j += 1;
            }
        }
        return possibleResult;
    }

    /**
     * Selects the numbers of seats for all parties that are candidates to be
     * adjusted medians.
     *
     * @param medians
     *            The medians per group.
     * @param sumOfMedians
     *            The sum of the medians
     * @param size
     *            The size of the parliament.
     * @param selectionFactor
     *            The factor used to compare a candidate number of seats'
     *            probability with the median's probability.
     * @return The numbers of seats that are candidates to be adjusted medians, per
     *         group.
     */
    private Map<String, List<Integer>> selectCandidateNosOfSeatsToBeAdjustedMedians(final Map<String, Integer> medians,
            final int sumOfMedians, final int size, final double selectionFactor) {
        Map<String, List<Integer>> candidateNosOfSeats = new HashMap<String, List<Integer>>();
        for (String g : map.keySet()) {
            List<Integer> candidateNoOfSeats = new ArrayList<Integer>();
            Integer median = medians.get(g);
            ProbabilityMassFunction<Integer> pmf = map.get(g);
            double medianProbability = pmf.getProbability(median);
            for (Integer noOfSeats : pmf.keySet()) {
                if (((size > sumOfMedians) && (noOfSeats >= median) || (size < sumOfMedians) && (noOfSeats <= median))
                        && pmf.getProbability(noOfSeats) >= medianProbability * selectionFactor) {
                    candidateNoOfSeats.add(noOfSeats);
                }
            }
            candidateNosOfSeats.put(g, candidateNoOfSeats);
        }
        return candidateNosOfSeats;
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

}
