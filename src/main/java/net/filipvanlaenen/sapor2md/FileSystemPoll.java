package net.filipvanlaenen.sapor2md;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class implementing the abstract <code>Poll</code> class using the file
 * system.
 */
public final class FileSystemPoll extends Poll {
    /**
     * Constructor taking the directory in which the poll resides and the file name
     * of the poll as its parameters.
     *
     * @param directory    The directory in which the poll resides.
     * @param pollFileName The name of the poll file.
     */
    FileSystemPoll(final String directory, final String pollFileName) {
        super(extractBaseNameFromFileName(pollFileName), readFileIntoDoubleMap(directory, pollFileName).get(0));
        setStateSummary(FileSystemStateSummary.readFromFileSystem(directory, getBaseName()));
        String votingIntentionsFilePath = directory + File.separator + getBaseName() + "-dichotomies-probabilities.psv";
        String votingIntentionsFileContent = FileSystemServices.readFileIntoString(votingIntentionsFilePath);
        setVotingIntentions(VotingIntentions.parseFromString(votingIntentionsFileContent));
        setVotingIntentionsChartFileSize(
                FileSystemServices.getFileSize(directory + File.separator + getBaseName() + ".png"));
        String seatProjectionFilePath = directory + File.separator + getBaseName()
                + "-polychotomy-seats-probabilities.psv";
        String seatProjectionFileContent = FileSystemServices.readFileIntoString(seatProjectionFilePath);
        setSeatProjection(SeatProjection.parseFromString(seatProjectionFileContent));
        setSeatingPlanProjectionChartFileSize(
                FileSystemServices.getFileSize(directory + File.separator + getBaseName() + "-seating-plan.png"));
        setSeatProjectionsChartFileSize(
                FileSystemServices.getFileSize(directory + File.separator + getBaseName() + "-seats.png"));
    }

    /**
     * Extracts the base name from the name of the poll file.
     *
     * @param pollFileName Name of the poll file.
     * @return The base name.
     */
    static String extractBaseNameFromFileName(final String pollFileName) {
        return pollFileName.substring(0, pollFileName.length() - ".poll".length());
    }

    /**
     * Reads the content of the poll file, and places the result in a double map.
     *
     * @param directory    The directory in which the poll resides.
     * @param pollFileName The name of the poll file.
     * @return A list containing two maps, representing the content of the poll
     *         file.
     */
    private static List<Map<String, String>> readFileIntoDoubleMap(final String directory, final String pollFileName) {
        String filePath = directory + File.separator + pollFileName;
        String content = FileSystemServices.readFileIntoString(filePath);
        return parseDoubleMapFromString(content);
    }

    /**
     * Converts the content of a string to a double map.
     *
     * @param content A string with the content to be converted.
     * @return A list containing two maps, representing the content of the poll
     *         file.
     */
    static List<Map<String, String>> parseDoubleMapFromString(final String content) {
        String[] lines = content.split("\n");
        List<Map<String, String>> doubleMap = new ArrayList<Map<String, String>>();
        doubleMap.add(new HashMap<String, String>());
        doubleMap.add(new HashMap<String, String>());
        int i = 0;
        for (String line : lines) {
            if (line.equals("==")) {
                i = 1;
            } else {
                String[] elements = line.split("=");
                doubleMap.get(i).put(elements[0], elements[1]);
            }
        }
        return doubleMap;
    }
}
