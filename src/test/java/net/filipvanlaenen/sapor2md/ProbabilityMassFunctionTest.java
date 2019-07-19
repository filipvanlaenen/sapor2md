package net.filipvanlaenen.sapor2md;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Unit tests on <code>ProbabilityMassFunction</code>.
 */
public class ProbabilityMassFunctionTest {
    /**
     * Test verifying that the constructor throws an
     * <code>IllegalArgumentException</code> if the number of arguments is odd.
     */
    @Test
    void constructorShouldThrowIllegalArgumentExceptionIfNoOfArgumentsIsOdd() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ProbabilityMassFunction(0);
        });
    }

    /**
     * Test verifying that the constructor throws an
     * <code>IllegalArgumentException</code> if one of the even arguments isn't a
     * number.
     */
    @Test
    void constructorShouldThrowIllegalArgumentExceptionIfOneOfTheEvenArgumentsIsNotANumber() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ProbabilityMassFunction(0, "foo");
        });
    }

    /**
     * Test verifying that the constructor puts the arguments correctly in the map,
     * and the getter can fetch one of the probabilities correctly.
     */
    @Test
    void constructorMapsArgumentsCorrectlyToItsInternalMap() {
        ProbabilityMassFunction pmf = new ProbabilityMassFunction(0, 0.3, 1, 0.4, 2, 0.3);
        assertEquals(0.3, pmf.getProbability(0));
    }
}
