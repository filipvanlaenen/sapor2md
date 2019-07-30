package net.filipvanlaenen.sapor2md;

public class ConfidenceInterval<T> {

    final private T lowerBound;

    ConfidenceInterval(final T lowerBound, final T upperBound) {
        this.lowerBound = lowerBound;
    }

    T getLowerBound() {
        return lowerBound;
    }

}
