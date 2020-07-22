package net.filipvanlaenen.sapor2md;

/**
 * Class producing a voting intentions chart.
 */
public class VotingIntentionsChart extends Chart {
    /**
     * Constructor taking the path to the Sapor directory and the name of the poll
     * file as its parameters.
     *
     * @param directoryPath The path to the Sapor directory in which the poll
     *                      resides.
     * @param pollFileName  The name of the poll file.
     */
    public VotingIntentionsChart(final String directoryPath, final String pollFileName) {
        super(directoryPath, pollFileName);
    }

    /**
     * Constructor taking a Sapor directory and a poll as its parameters.
     *
     * @param directory The Sapor directory.
     * @param poll      The poll.
     */
    public VotingIntentionsChart(final SaporDirectory directory, final Poll poll) {
        super(directory, poll);
    }

    /**
     * Returns the suffix for the file name for the chart.
     *
     * @return The suffix for the file name for the chart.
     */
    @Override
    public String getFileNameSuffix() {
        return "";
    }
}
