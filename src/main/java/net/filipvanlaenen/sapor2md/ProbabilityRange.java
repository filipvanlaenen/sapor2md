package net.filipvanlaenen.sapor2md;

import java.util.Locale;

/**
 * Class implementing a probability range.
 */
public final class ProbabilityRange implements Comparable<ProbabilityRange> {
    /**
     * Magic number 100.
     */
    private static final double ONE_HUNDRED = 100D;

    /**
     * The lower bound of the probability range.
     */
    private final Double lowerBound;
    /**
     * The upper bound of the probability range.
     */
    private final Double upperBound;

    /**
     * Constructor taking the lower and the upper bound of the probability range as
     * its parameters.
     *
     * @param lowerBound The lower bound of the probability range.
     * @param upperBound The upper bound of the probability range.
     */
    ProbabilityRange(final Double lowerBound, final Double upperBound) {
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    @Override
    public int compareTo(final ProbabilityRange other) {
        if (other.getLowerBound() > lowerBound) {
            return -1;
        } else if (other.getLowerBound() < lowerBound) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * Returns the lower bound of the probability range.
     *
     * @return The lower bound of the probability range.
     */
    Double getLowerBound() {
        return lowerBound;
    }

    /**
     * Returns the upper bound of the probability range.
     *
     * @return The upper bound of the probability range.
     */
    Double getUpperBound() {
        return upperBound;
    }

    /**
     * Formats a double as a percentage number. The double is multiplied with 100
     * and formatted with one digit behind the decimal point.
     * 
     * @param percentage The double to be formatted.
     * @return A string with the double formatted as percentage number.
     */
    private static String formatPercentageNumber(final String format, final double percentage) {
        return String.format(format, percentage * ONE_HUNDRED, Locale.ENGLISH);
    }

    /**
     * Formats a confidence interval with probability ranges to a human readable
     * form. It takes the lower bound of the lower probability range and the upper
     * bound of the upper probability ranges as the lower an upper bounds.
     * 
     * @param ci The confidence interval.
     * @return A string with the confidence interval formatted in a human-readable
     *         form.
     */
    static String formatConfidenceInterval(final String format, final ConfidenceInterval<ProbabilityRange> ci) {
        return formatPercentageNumber(format, ci.getLowerBound().getLowerBound()) + "–"
                + formatPercentageNumber(format, ci.getUpperBound().getUpperBound()) + "%";
    }

}
