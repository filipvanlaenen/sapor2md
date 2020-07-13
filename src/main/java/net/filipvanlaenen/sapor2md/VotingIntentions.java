package net.filipvanlaenen.sapor2md;

/**
 * A class representing voting intentions. Voting intentions consist of a number
 * of parliamentary groups with each of them a probability mass function in
 * terms of percentage ranges associated to them.
 */
public class VotingIntentions extends ProbabilityMassFunctionCombination<ProbabilityRange> {
    /**
     * Constructs voting intentions from an array of objects. The array has to have
     * an even length, with each uneven element the name of a parliamentary group,
     * and each even element a probability mass function.
     *
     * @param objects
     *            An array defining voting intentions.
     */
    VotingIntentions(final Object... objects) {
        if (objects.length % 2 == 1) {
            throw new IllegalArgumentException(
                    "The number of arguments to construct voting intentions should be even.");
        }
        for (int i = 0; i < objects.length; i += 2) {
            Object key = objects[i];
            Object value = objects[i + 1];
            if (!(key instanceof String)) {
                throw new IllegalArgumentException(
                        "The uneven arguments to construct voting intentions should be strings.");
            } else if (!(value instanceof ProbabilityMassFunction)) {
                throw new IllegalArgumentException(
                        "The uneven arguments to construct voting intentions should be probability mass functions.");
            } else {
                map.put((String) key, (ProbabilityMassFunction<ProbabilityRange>) value);
            }
        }
    }
}
