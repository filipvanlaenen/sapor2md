package net.filipvanlaenen.sapor2md;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Unit tests on the <code>CommandLineInterface</code> class.
 */
public class CommandLineInterfaceTest {
    /**
     * Test verifying that given a set of probability mass functions and a size for
     * the parliament, the command-line interface produces for each group the number
     * of certain seats (lower bound of the 95 percent confidence interval), the
     * median, and the adjusted median.
     */
    @Test
    void cliProducesCertainSeatsMediansAndAdjustedMediansForProbabilityMassFunctionsSet() {
        CommandLineInterface cli = new CommandLineInterface();
        String probabilityMassFunctions = "Choice | 0 | 1 | 2\n" + "Red Party | 0.4 | 0.35 | 0.25\n"
                + "Blue Party | 0.65 | 0.35\n" + "Green Party | 0.75 | 0.25\n";
        String actual = cli.perform(probabilityMassFunctions, 2);
        String expected = "Choice | CI95LB | Median | Adjusted Median\n" + "Red Party | 0 | 1 | 2\n"
                + "Blue Party | 0 | 0 | 0\n" + "Green Party | 0 | 0 | 0\n";
        assertEquals(expected, actual);
    }
}
