package net.filipvanlaenen.sapor2md;

/**
 * A class representing voting intentions. Voting intentions consist of a number
 * of parliamentary groups with each of them a probability mass function in
 * terms of precentage ranges associated to them.
 */
public class VotingIntentions extends ProbabilityMassFunctionCombination<ProbabilityRange> {
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
