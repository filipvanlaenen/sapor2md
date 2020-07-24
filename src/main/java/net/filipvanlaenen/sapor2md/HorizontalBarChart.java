package net.filipvanlaenen.sapor2md;

public abstract class HorizontalBarChart extends Chart {
    protected static final int TICKS_HEIGHT = 10;
    protected static final int CHOICE_HEIGHT = 100;
    protected static final int CHOICE_LABEL_FONT_SIZE = 36;
    protected static final int SPACE_BETWEEN_CHOICES = 40;

    /**
     * Constructor taking the path to the Sapor directory and the name of the poll
     * file as its parameters.
     *
     * @param directoryPath The path to the Sapor directory in which the poll
     *                      resides.
     * @param pollFileName  The name of the poll file.
     */
    public HorizontalBarChart(final String directoryPath, final String pollFileName) {
        super(directoryPath, pollFileName);
    }

    /**
     * Constructor taking a Sapor directory and a poll as its parameters.
     *
     * @param directory The Sapor directory.
     * @param poll      The poll.
     */
    public HorizontalBarChart(final SaporDirectory directory, final Poll poll) {
        super(directory, poll);
    }

    @Override
    protected double calculateContentHeight() {
        return 2 * TICKS_HEIGHT + getNumberOfGroups() * CHOICE_HEIGHT
                + (getNumberOfGroups() - 1) * SPACE_BETWEEN_CHOICES;
    }

    abstract int getNumberOfGroups();
}
