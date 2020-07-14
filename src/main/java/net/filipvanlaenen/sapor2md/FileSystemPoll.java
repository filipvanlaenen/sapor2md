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

public class FileSystemPoll implements Poll {

    private static final String COMMISSIONERS_KEY = "Commissioners";
    private static final String POLLING_FIRM_KEY = "PollingFirm";
    private static final String FIELDWORK_END_KEY = "FieldworkEnd";
    private static final String FIELDWORK_START_KEY = "FieldworkStart";

    /**
     * The path to the poll file.
     */
    private final String filePath;
    private final String directory;
    private final String baseName;
    private final String commissioners;
    private final LocalDate fieldworkEnd;
    private final LocalDate fieldworkStart;
    private final String pollingFirm;
    private final StateSummary stateSummary;
    private final VotingIntentions votingIntentions;

    FileSystemPoll(final String directory, final String pollFileName) {
        this.directory = directory;
        filePath = directory + File.separator + pollFileName;
        baseName = pollFileName.substring(0, pollFileName.length() - 5);
        List<Map<String, String>> content = readFileIntoDoubleMap(filePath);
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

    private List<Map<String, String>> readFileIntoDoubleMap(final String filePath) {
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
