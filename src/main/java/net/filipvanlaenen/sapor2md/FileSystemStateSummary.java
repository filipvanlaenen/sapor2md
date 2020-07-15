package net.filipvanlaenen.sapor2md;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.OffsetDateTime;
import java.util.Map;

/**
 * Class implementing the <code>StateSummary</code> interface using the file
 * system.
 */
public final class FileSystemStateSummary implements StateSummary {
    /**
     * The key for the property containing the number of simulations run on the
     * poll.
     */
    private static final String NUMBER_OF_SIMULATIONS_KEY = "NumberOfSimulations";

    /**
     * The number of simulations run on the poll.
     */
    private final long numberOfSimulations;
    /**
     * The timestamp for the state summary file.
     */
    private final OffsetDateTime timestamp;

    /**
     * Constructor taking the file path for the state summary as its parameters.
     *
     * @param filePath
     *            The path to where the state summary is stored.
     */
    private FileSystemStateSummary(final String filePath) {
        Map<String, String> map = FileSystemServices.readFileIntoMap(filePath);
        this.numberOfSimulations = Long.parseLong(map.get(NUMBER_OF_SIMULATIONS_KEY));
        timestamp = FileSystemServices.getTimestamp(filePath);
    }

    /**
     * Reads a state summary from the file system, or returns <code>null</code> if
     * the file doesn't exist.
     *
     * @param directory
     *            The directory where the poll is residing.
     * @param baseName
     *            The base name for the poll.
     * @return An instance representing the state summary read in from the file
     *         system, or <code>null</code> if the state summary file doesn't exist.
     */
    static FileSystemStateSummary readFromFileSystem(final String directory, final String baseName) {
        String filePath = directory + File.separator + baseName + "_state_summary.txt";
        if (Files.exists(Paths.get(filePath))) {
            return new FileSystemStateSummary(filePath);
        } else {
            return null;
        }
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
