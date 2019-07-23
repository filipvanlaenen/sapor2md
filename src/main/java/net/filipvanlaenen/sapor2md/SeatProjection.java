package net.filipvanlaenen.sapor2md;

import java.util.HashMap;
import java.util.Map;

/**
 * A class representing a seat projection. A seat projection consists of a
 * number of parliamentary groups with each of them a probability mass function
 * associated to them.
 */
public class SeatProjection {

    /**
     * A map holding the probability mass functions per group.
     */
    private final Map<String, ProbabilityMassFunction<Integer>> map = new HashMap<String, ProbabilityMassFunction<Integer>>();

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

}
