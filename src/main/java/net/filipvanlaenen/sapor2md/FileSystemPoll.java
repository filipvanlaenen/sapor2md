package net.filipvanlaenen.sapor2md;

import java.io.File;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class implementing the <code>Poll</code> interface using the file system.
 */
public final class FileSystemPoll implements Poll {
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
     * The directory where the poll resides.
     */
    private final String directory;
    /**
     * The base name of the poll.
     */
    private final String baseName;
    /**
     * The name of the commissioners of the poll.
     */
    private final String commissioners;
    /**
     * The end date of the fieldwork period.
     */
    private final LocalDate fieldworkEnd;
    /**
     * The start date of the fieldwork period.
     */
    private final LocalDate fieldworkStart;
    /**
     * The name of the polling firm that conducted the poll.
     */
    private final String pollingFirm;
    /**
     * The state summary for the poll.
     */
    private final StateSummary stateSummary;
    /**
     * The voting intentions for the poll.
     */
    private final VotingIntentions votingIntentions;

    /**
     * Constructor taking the directory in which the poll resides and the file name
     * of the poll as its parameters.
     *
     * @param directory
     *            The directory in which the poll resides.
     * @param pollFileName
     *            The name of the poll file.
     */
    FileSystemPoll(final String directory, final String pollFileName) {
        this.directory = directory;
        filePath = directory + File.separator + pollFileName;
        baseName = pollFileName.substring(0, pollFileName.length() - ".poll".length());
        List<Map<String, String>> content = readFileIntoDoubleMap();
        Map<String, String> properties = content.get(0);
        this.commissioners = properties.get(COMMISSIONERS_KEY);
        this.pollingFirm = properties.get(POLLING_FIRM_KEY);
        this.fieldworkEnd = LocalDate.parse(properties.get(FIELDWORK_END_KEY), DateTimeFormatter.ISO_LOCAL_DATE);
        this.fieldworkStart = LocalDate.parse(properties.get(FIELDWORK_START_KEY), DateTimeFormatter.ISO_LOCAL_DATE);
        this.stateSummary = new FileSystemStateSummary(directory, baseName);
        String votingIntentionsFilePath = directory + File.separator + baseName + "-dichotomies-probabilities.psv";
        String votingIntentionsFileContent = FileSystemServices.readFileIntoString(votingIntentionsFilePath);
        this.votingIntentions = VotingIntentions.parseFromString(votingIntentionsFileContent);
    }

    /**
     * Reads the content of the poll file, and places the result in a double map.
     *
     * @return A list containing two maps, representing the content of the poll
     *         file.
     */
    private List<Map<String, String>> readFileIntoDoubleMap() {
        String content = FileSystemServices.readFileIntoString(filePath);
        String[] lines = content.split("\n");
        List<Map<String, String>> doubleMap = new ArrayList<Map<String, String>>();
        doubleMap.add(new HashMap<String, String>());
        doubleMap.add(new HashMap<String, String>());
        int i = 0;
        for (String line : lines) {
            if (line.endsWith("==")) {
                i = 1;
            } else {
                String[] elements = line.split("=");
                doubleMap.get(i).put(elements[0], elements[1]);
            }
        }
        return doubleMap;
    }

    @Override
    public String getBaseName() {
        return baseName;
    }

    @Override
    public StateSummary getStateSummary() {
        return stateSummary;
    }

    @Override
    public String getComissioners() {
        return commissioners;
    }

    @Override
    public LocalDate getFieldworkEnd() {
        return fieldworkEnd;
    }

    @Override
    public LocalDate getFieldworkStart() {
        return fieldworkStart;
    }

    @Override
    public String getPollingFirm() {
        return pollingFirm;
    }

    @Override
    public VotingIntentions getVotingIntentions() {
        return votingIntentions;
    }

    @Override
    public long getVotingIntentionsFileSize() {
        Path votingIntentionsChart = Paths.get(directory + File.separator + baseName + ".png");
        try {
            return FileChannel.open(votingIntentionsChart).size();
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
