package net.filipvanlaenen.sapor2md;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;

/**
 * The command-line interface for this library.
 */
public final class CommandLineInterface {
    /**
     * Main entry point for the command-line interface.
     *
     * @param args
     *            The arguments from the command-line.
     */
    public static void main(final String... args) {
        CommandLineInterface cli = new CommandLineInterface(args);
        String output = cli.execute();
        System.out.println(output);
    }

    /**
     * The name of the command, as a string.
     */
    private final String command;
    /**
     * The remaining arguments for the command.
     */
    private final String[] remainingArguments;

    /**
     * Creates a command-line interface object that can run the requested command
     * with the remaining arguments.
     *
     * @param args
     *            The arguments from the command-line, of which the first should be
     *            the name of the command to be executed, and the rest according to
     *            what arguments the command needs.
     */
    private CommandLineInterface(final String... args) {
        command = args[0];
        remainingArguments = Arrays.copyOfRange(args, 1, args.length - 1);
    }

    /**
     * Executes the command with the remaining arguments.
     *
     * @return The output of the command.
     */
    private String execute() {
        return Command.valueOf(command).execute(remainingArguments);
    }

    /**
     * Enumeration with the commands that can be invoked from the command line.
     */
    enum Command {
        /**
         * Command to calculate the adjusted medians.
         */
        AdjustedMedians {
            /**
             * Calculates the adjusted medians based on the seat projection in a file and a
             * parliament size.
             *
             * @param args
             *            The arguments for the command, i.e. the file name for the seat
             *            projection and a parliament size.
             * @return A string with the lower bound of the 95 percent confidence interval,
             *         the median and the adjusted median for each parliamentary group.
             */
            String execute(final String... args) {
                String probabilityMassFunctionsString = readFileIntoString(args[0]);
                int parliamentSize = Integer.parseInt(args[1]);
                return SeatProjection.calculateAdjustedMedians(probabilityMassFunctionsString, parliamentSize);
            }
        };

        /**
         * Executes the command.
         *
         * @param args
         *            The arguments for the command.
         * @return The string output resulting from executing the command.
         */
        abstract String execute(String... args);

        /**
         * Reads a file from the file system and returns the result as a single string.
         *
         * @param filePath
         *            The path to the file to be read.
         * @return The content of the file as a single string.
         */
        private static String readFileIntoString(final String filePath) {
            StringBuilder contentBuilder = new StringBuilder();
            try (Stream<String> stream = Files.lines(Paths.get(filePath), StandardCharsets.UTF_8)) {
                stream.forEach(s -> contentBuilder.append(s).append("\n"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return contentBuilder.toString();
        }
    }
}