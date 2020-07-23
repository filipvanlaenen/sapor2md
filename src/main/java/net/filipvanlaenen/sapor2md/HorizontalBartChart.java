package net.filipvanlaenen.sapor2md;

public abstract class HorizontalBartChart extends Chart {
    private static final int TICKS_HEIGHT = 10;
    private static final int CHOICE_HEIGHT = 100;
    private static final int SPACE_BETWEEN_CHOICES = 40;

    /**
     * Constructor taking the path to the Sapor directory and the name of the poll
     * file as its parameters.
     *
     * @param directoryPath The path to the Sapor directory in which the poll
     *                      resides.
     * @param pollFileName  The name of the poll file.
     */
    public HorizontalBartChart(final String directoryPath, final String pollFileName) {
        super(directoryPath, pollFileName);
    }

    /**
     * Constructor taking a Sapor directory and a poll as its parameters.
     *
     * @param directory The Sapor directory.
     * @param poll      The poll.
     */
    public HorizontalBartChart(final SaporDirectory directory, final Poll poll) {
        super(directory, poll);
    }

    @Override
    protected double calculateContentHeight() {
        return 2 * TICKS_HEIGHT + getNumberOfGroups() * CHOICE_HEIGHT
                + (getNumberOfGroups() - 1) * SPACE_BETWEEN_CHOICES;
    }

    abstract int getNumberOfGroups();
}
