package net.filipvanlaenen.sapor2md;

import java.util.ArrayList;
import java.util.List;

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

    /**
     * Parses a string into a voting intentions object.
     *
     * @param probabilityMassFunctions
     *            A string representation of voting intentions.
     * @return A voting intentions object.
     */
    static VotingIntentions parseFromString(final String probabilityMassFunctions) {
        List<Object> arguments = new ArrayList<Object>();
        String[] lines = probabilityMassFunctions.split("\\R");
        for (String line : lines) {
            String[] lineComponents = line.split("\\|");
            String label = lineComponents[0].trim();
            if (!label.equals("Choice")) {
                arguments.add(label);
                List<Object> pmfArguments = new ArrayList<Object>();
                for (int i = 1; i < lineComponents.length; i++) {
                    pmfArguments.add(new ProbabilityRange(((double) (i - 1)) / 2000D, ((double) i) / 2000D));
                    pmfArguments.add(Double.parseDouble(lineComponents[i]));
                }
                arguments.add(new ProbabilityMassFunction<ProbabilityRange>(pmfArguments.toArray()));
            }
        }
        return new VotingIntentions(arguments.toArray());
    }
}
