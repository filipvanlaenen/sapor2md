package net.filipvanlaenen.sapor2md;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * The command-line interface for this library.
 */
public class CommandLineInterface {
    /**
     * Main entry point for the command-line interface.
     *
     * @param args
     *            The arguments from the command-line.
     */
    public static void main(final String... args) {
        System.out.println(new CommandLineInterface().perform(readFileIntoString(args[0]), Integer.parseInt(args[1])));
    }

    /**
     * Reads a file from the file system and returns the result as a single string.
     *
     * @param filePath
     *            The path to the file to be read.
     * @return The content of the file as a single string.
     */
    private static String readFileIntoString(String filePath) {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(filePath), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s).append("\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contentBuilder.toString();
    }

    /**
     * Performs the action requested from the command-line.
     *
     * @param probabilityMassFunctionsString
     *            The probability mass functions encoded in a single string.
     * @param parliamentSize
     *            The size of the parliament.
     * @return Whatever was requested by the user from the command-line.
     */
    String perform(String probabilityMassFunctionsString, int parliamentSize) {
        SeatProjection seatProjection = parseProbabilityMassFunctionsString(probabilityMassFunctionsString);
        StringBuilder contentBuilder = new StringBuilder();
        contentBuilder.append("Choice | CI95LB | Median | Adjusted Median\n");
        for (String group : seatProjection.getGroups()) {
            contentBuilder.append(group).append(" | ")
                    .append(seatProjection.getLowerBoundOfConfidenceInterval(group, 0.95D)).append(" | ")
                    .append(seatProjection.getMedian(group)).append(" | ")
                    .append(seatProjection.getAdjustedMedian(group, parliamentSize)).append("\n");
        }
        return contentBuilder.toString();
    }

    private SeatProjection parseProbabilityMassFunctionsString(String probabilityMassFunctions) {
        List<Object> arguments = new ArrayList<Object>();
        String[] lines = probabilityMassFunctions.split("\\R");
        for (String line : lines) {
            String[] lineComponents = line.split("\\|");
            String label = lineComponents[0].trim();
            if (!label.equals("Choice")) {
                arguments.add(label);
                List<Object> pmfArguments = new ArrayList<Object>();
                for (int i = 1; i < lineComponents.length; i++) {
                    pmfArguments.add(i - 1);
                    pmfArguments.add(Double.parseDouble(lineComponents[i]));
                }
                arguments.add(new ProbabilityMassFunction<Integer>(pmfArguments.toArray()));
            }
        }
        return new SeatProjection(arguments.toArray());
    }

}
