package net.filipvanlaenen.sapor2md;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class TimeServices {
    /**
     * Formats a period, consisting of two dates, to a human-readable form. Common
     * elements in the dates are joined, such that the result takes one of the
     * following forms: 1–2 January 2020, 1 January–2 February 2020 or 1 January
     * 2020–31 December 2021.
     *
     * @param start The start of the period.
     * @param end   The end of the period.
     * @return A string with the period formatted in a human-readable form.
     */
    static String formatPeriod(final LocalDate start, final LocalDate end) {
        DateTimeFormatter dayMonthYear = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale.ENGLISH);
        DateTimeFormatter dayMonth = DateTimeFormatter.ofPattern("d MMMM", Locale.ENGLISH);
        DateTimeFormatter day = DateTimeFormatter.ofPattern("d", Locale.ENGLISH);
        if (start.getYear() != end.getYear()) {
            return start.format(dayMonthYear) + "–" + end.format(dayMonthYear);
        } else if (start.getMonth() != end.getMonth()) {
            return start.format(dayMonth) + "–" + end.format(dayMonthYear);
        } else if (start.getDayOfMonth() != end.getDayOfMonth()) {
            return start.format(day) + "–" + end.format(dayMonthYear);
        } else {
            return start.format(dayMonthYear);
        }
    }
}
