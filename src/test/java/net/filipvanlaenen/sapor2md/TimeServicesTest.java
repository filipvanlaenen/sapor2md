package net.filipvanlaenen.sapor2md;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.Month;

import org.junit.jupiter.api.Test;

public class TimeServicesTest {
    /**
     * Magic number 2020, used as a year number.
     */
    private static final int TWO_THOUSAND_AND_TWENTY = 2020;
    /**
     * Magic number 2021, used as a year number.
     */
    private static final int TWO_THOUSAND_AND_TWENTY_ONE = 2021;
    /**
     * Local date representing 1 January 2020.
     */
    private static final LocalDate FIRST_OF_JANUARY_2020 = LocalDate.of(TWO_THOUSAND_AND_TWENTY, Month.JANUARY, 1);

    /**
     * Verifying that when a period has the same start and end date, it is formatted
     * as a simple date.
     */
    @Test
    void formattedPeriodWithSameStartAndEndDateIsFormattedAsADate() {
        assertEquals("1 January 2020", TimeServices.formatPeriod(FIRST_OF_JANUARY_2020, FIRST_OF_JANUARY_2020));
    }

    /**
     * Verifying that when a period falls within the same month, it is formatted
     * separating the day of month only.
     */
    @Test
    void formattedPeriodWithinSameMonthSeparatesDayOfMonthOnly() {
        assertEquals("1–2 January 2020", TimeServices.formatPeriod(FIRST_OF_JANUARY_2020,
                LocalDate.of(TWO_THOUSAND_AND_TWENTY, Month.JANUARY, 2)));
    }

    /**
     * Verifying that when a period falls within the same year but not the same
     * month, it is formatted separating the day of month and month only.
     */
    @Test
    void formattedPeriodWithinSameYearSeparatesDayOfMonthAndMonth() {
        assertEquals("1 January–1 February 2020", TimeServices.formatPeriod(FIRST_OF_JANUARY_2020,
                LocalDate.of(TWO_THOUSAND_AND_TWENTY, Month.FEBRUARY, 1)));
    }

    /**
     * Verifying that when a period spans across years, it is formatted as two dates
     * separated.
     */
    @Test
    void formattedPeriodAcrossYearsSeparatesDates() {
        assertEquals("1 January 2020–1 January 2021", TimeServices.formatPeriod(FIRST_OF_JANUARY_2020,
                LocalDate.of(TWO_THOUSAND_AND_TWENTY_ONE, Month.JANUARY, 1)));
    }
}
