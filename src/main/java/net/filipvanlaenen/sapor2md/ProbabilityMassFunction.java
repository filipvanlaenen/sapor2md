package net.filipvanlaenen.sapor2md;

import java.util.HashMap;
import java.util.Map;

public class ProbabilityMassFunction {

    private final Map<Object, Double> map = new HashMap<Object, Double>();

    ProbabilityMassFunction(Object... objects) {
        if (objects.length % 2 == 1) {
            throw new IllegalArgumentException(
                    "The number of arguments to construct a probability mass function should be even.");
        }
        for (int i = 0; i < objects.length; i += 2) {
            Object key = objects[i];
            Object value = objects[i + 1];
            if (value instanceof Number) {
                map.put(key, (Double) value);
            } else {
                throw new IllegalArgumentException(
                        "The even arguments to construct a probability mass function should be numbers.");
            }
        }
    }

    double getProbability(Object key) {
        return map.get(key);
    }

}
