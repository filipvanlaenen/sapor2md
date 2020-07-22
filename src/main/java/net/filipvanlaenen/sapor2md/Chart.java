package net.filipvanlaenen.sapor2md;

import java.io.File;

/**
 * Abstract class defining the behavior of a chart.
 */
public abstract class Chart {
    /**
     * The path to the Sapor directory.
     */
    private final String directoryPath;
    /**
     * The poll for the chart.
     */
    private final Poll poll;
    /**
     * The Sapor directory.
     */
    private final SaporDirectory directory;

    /**
     * Constructor taking the path to the Sapor directory and the name of the poll
     * file as its parameters.
     *
     * @param directoryPath The path to the Sapor directory in which the poll
     *                      resides.
     * @param pollFileName  The name of the poll file.
     */
    public Chart(final String directoryPath, final String pollFileName) {
        this(directoryPath, new FileSystemPoll(directoryPath, pollFileName));
    }

    /**
     * Constructor taking the path to the Sapor directory and the poll as its
     * parameters.
     *
     * @param directoryPath The path to the Sapor directory in which the poll
     *                      resides.
     * @param poll          The poll.
     */
    public Chart(final String directoryPath, final Poll poll) {
        this(directoryPath, new FileSystemSaporDirectory(directoryPath), poll);
    }

    /**
     * Constructor taking a Sapor directory and a poll as its parameters.
     *
     * @param directory The Sapor directory.
     * @param poll      The poll.
     */
    public Chart(final SaporDirectory directory, final Poll poll) {
        this(null, directory, poll);
    }

    /**
     * Private constructor taking a path to the Sapor directory, a Sapor directory
     * and a poll as its parameters.
     *
     * @param directoryPath The path to the Sapor directory in which the poll
     *                      resides.
     * @param directory     The Sapor directory.
     * @param poll          The poll.
     */
    private Chart(final String directoryPath, final SaporDirectory directory, final Poll poll) {
        this.directoryPath = directoryPath;
        this.directory = directory;
        this.poll = poll;
    }

    /**
     * Writes the chart as an SVG document to the file system, using the file name
     * for the chart, returning <code>true</code> if no problem occurred.
     *
     * @return True if no problem occurred, false otherwise.
     */
    boolean writeSvgToFileSystem() {
        String filePath = directoryPath + File.separator + getFileName();
        return FileSystemServices.writeStringToFile(toString(), filePath);
    }

    /**
     * Returns the file name for the chart.
     *
     * @return The file name for the chart.
     */
    String getFileName() {
        return poll.getBaseName() + getFileNameSuffix() + ".svg";
    }

    /**
     * Returns the suffix for the file name for the chart.
     *
     * @return The suffix for the file name for the chart.
     */
    abstract String getFileNameSuffix();
}
