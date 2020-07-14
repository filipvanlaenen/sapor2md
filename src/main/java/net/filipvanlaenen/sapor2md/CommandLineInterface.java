package net.filipvanlaenen.sapor2md;

import java.util.Arrays;
import java.util.List;

import net.filipvanlaenen.sapor2md.RSS20Feed.RSS20FeedMode;

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
        remainingArguments = Arrays.copyOfRange(args, 1, args.length);
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
            @Override
            String execute(final String... args) {
                String probabilityMassFunctionsString = FileSystemServices.readFileIntoString(args[0]);
                int parliamentSize = Integer.parseInt(args[1]);
                return SeatProjection.calculateAdjustedMedians(probabilityMassFunctionsString, parliamentSize);
            }
        },
        /**
         * Command to produce an RSS 2.0 feed for a directory suitable for IFTTT to be
         * posted to Twitter.
         */
        IftttRSS20Feed {

            /**
             * Produces an RSS 2.0 feed for a directory suitable for IFTTT to be posted to
             * Twitter.
             *
             * @params args The arguments for the command, i.e. the directory for which to
             *         produce the feed.
             * @return A multiline string containing the feed.
             */
            @Override
            String execute(final String... args) {
                String directory = args[0];
                RSS20Feed feed = new RSS20Feed(directory, RSS20FeedMode.IftttFeed);
                return feed.toString();
            }
        },
        /**
         * Command to list out the poll files from a set of directories.
         */
        PollFiles {
            /**
             * Lists all the poll files found in a set of directories, either all of them or
             * the ones that are common to all directories.
             *
             * @param args
             *            The arguments for the command, i.e. the mode (All or Common) and
             *            the list of directories.
             * @return A multiline string, with on each line the name of a poll file.
             */
            @Override
            String execute(final String... args) {
                PollFilesMode mode = PollFilesMode.valueOf(args[0]);
                String firstDirectory = args[1];
                List<String> pollFiles = FileSystemServices.getPollFilesList(firstDirectory);
                String[] directories = Arrays.copyOfRange(args, 2, args.length);
                for (String directory : directories) {
                    pollFiles = mode.mergePollFiles(pollFiles, FileSystemServices.getPollFilesList(directory));
                }
                return String.join("\n", pollFiles);
            }
        },
        /**
         * Command to produce the official RSS 2.0 feed for the GitHub website.
         */
        RSS20Feed {

            /**
             * Produces an RSS 2.0 feed for a directory.
             *
             * @params args The arguments for the command, i.e. the directory for which to
             *         produce the feed.
             * @return A multiline string containing the feed.
             */
            @Override
            String execute(final String... args) {
                String directory = args[0];
                RSS20Feed feed = new RSS20Feed(directory, RSS20FeedMode.GitHubFeed);
                return feed.toString();
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

    }
}
