package net.filipvanlaenen.sapor2md;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class CommandLineInterface {

    public static void main(final String... args) {
        System.out.println(new CommandLineInterface().perform(readFileIntoString(args[0]), Integer.parseInt(args[1])));
    }

    private static String readFileIntoString(String filePath) {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(filePath), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s).append("\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contentBuilder.toString();
    }

    String perform(String probabilityMassFunctions, int parliamentSize) {
        Object[] seatProjectionArguments = parseProbabilityMassFunctionsString(probabilityMassFunctions);
        SeatProjection seatProjection = new SeatProjection(seatProjectionArguments);
        StringBuilder contentBuilder = new StringBuilder();
        contentBuilder.append("Choice | CI95LB | Median | Adjusted Median\n");
        for (String group : seatProjection.getGroups()) {
            contentBuilder.append(group).append(" | ").append(seatProjection.getLowerBoundOfConfidenceInterval(0.95D))
                    .append(" | ").append(seatProjection.getMedian(group)).append(" | ")
                    .append(seatProjection.getAdjustedMedian(group, parliamentSize)).append("\n");
        }
        return contentBuilder.toString();
    }

    private Object[] parseProbabilityMassFunctionsString(String probabilityMassFunctions) {
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
                arguments.add(new ProbabilityMassFunction(pmfArguments.toArray()));
            }
        }
        return arguments.toArray();
    }

}
