package net.filipvanlaenen.sapor2md;

import java.io.File;
import java.time.OffsetDateTime;
import java.util.Map;

public class FileSystemStateSummary implements StateSummary {
    private static final String NUMBER_OF_SIMULATIONS_KEY = "NumberOfSimulations";

    private final long numberOfSimulations;
    private final OffsetDateTime timestamp;

    FileSystemStateSummary(String directory, String baseName) {
        String filePath = directory + File.separator + baseName + "_state_summary.txt";
        Map<String, String> map = FileSystemServices.readFileIntoMap(filePath);
        this.numberOfSimulations = Long.parseLong(map.get(NUMBER_OF_SIMULATIONS_KEY));
        timestamp = FileSystemServices.getTimestamp(filePath);
    }

    @Override
    public long getNumberOfSimulations() {
        return numberOfSimulations;
    }

    @Override
    public OffsetDateTime getTimestamp() {
        return timestamp;
    }
}
