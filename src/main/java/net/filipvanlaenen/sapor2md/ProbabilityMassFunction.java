package net.filipvanlaenen.sapor2md;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ProbabilityMassFunction<T extends Comparable> {

    private final Map<T, Double> map = new HashMap<T, Double>();

    ProbabilityMassFunction(Object... objects) {
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

    double getProbability(Object key) {
        return map.get(key);
    }

    T getMedian() {
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

}
