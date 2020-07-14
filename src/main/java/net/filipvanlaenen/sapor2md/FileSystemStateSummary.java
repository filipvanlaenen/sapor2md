package net.filipvanlaenen.sapor2md;

import java.io.File;
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
     * Constructor taking the directory and the base name for the state summary as
     * its parameters.
     *
     * @param directory
     *            The directory in which the state summary resides.
     * @param baseName
     *            The base name for the poll.
     */
    FileSystemStateSummary(final String directory, final String baseName) {
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
