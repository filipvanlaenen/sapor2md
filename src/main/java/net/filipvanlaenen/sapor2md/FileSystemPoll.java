package net.filipvanlaenen.sapor2md;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
     * The key for the property containing the name of the commissioners.
     */
    private static final String COMMISSIONERS_KEY = "Commissioners";
    /**
     * The key for the property containing the name of the polling firm that
     * conducted the poll.
     */
    private static final String POLLING_FIRM_KEY = "PollingFirm";
    /**
     * The key for the property containing the end date for the fieldwork period.
     */
    private static final String FIELDWORK_END_KEY = "FieldworkEnd";
    /**
     * The key for the property containing the start date for the fieldwork period.
     */
    private static final String FIELDWORK_START_KEY = "FieldworkStart";

    /**
     * The path to the poll file.
     */
    private final String filePath;

    /**
     * Constructor taking the directory in which the poll resides and the file name
     * of the poll as its parameters.
     *
     * @param directory    The directory in which the poll resides.
     * @param pollFileName The name of the poll file.
     */
    FileSystemPoll(final String directory, final String pollFileName) {
        super(extractBaseNameFromFileName(pollFileName));
        filePath = directory + File.separator + pollFileName;
        List<Map<String, String>> content = readFileIntoDoubleMap();
        Map<String, String> properties = content.get(0);
        setCommissioners(properties.get(COMMISSIONERS_KEY));
        setPollingFirm(properties.get(POLLING_FIRM_KEY));
        setFieldworkEnd(LocalDate.parse(properties.get(FIELDWORK_END_KEY), DateTimeFormatter.ISO_LOCAL_DATE));
        setFieldworkStart(LocalDate.parse(properties.get(FIELDWORK_START_KEY), DateTimeFormatter.ISO_LOCAL_DATE));
        setStateSummary(FileSystemStateSummary.readFromFileSystem(directory, getBaseName()));
        String votingIntentionsFilePath = directory + File.separator + getBaseName() + "-dichotomies-probabilities.psv";
        String votingIntentionsFileContent = FileSystemServices.readFileIntoString(votingIntentionsFilePath);
        setVotingIntentions(VotingIntentions.parseFromString(votingIntentionsFileContent));
        setVotingIntentionsChartFileSize(
                FileSystemServices.getFileSize(directory + File.separator + getBaseName() + ".png"));
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
     * @return A list containing two maps, representing the content of the poll
     *         file.
     */
    private List<Map<String, String>> readFileIntoDoubleMap() {
        String content = FileSystemServices.readFileIntoString(filePath);
        return parseDoubleMapFromString(content);
    }

    /**
     * Converts the content of a file to a double map.
     *
     * @param content A string with the content to be converted.
     * @return A list containing two maps, representing the content of the poll
     *         file.
     */
    static List<Map<String, String>> parseDoubleMapFromString(String content) {
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
