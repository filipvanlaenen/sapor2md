package net.filipvanlaenen.sapor2md;

import java.util.Map;

import net.filipvanlaenen.tsvgj.G;
import net.filipvanlaenen.tsvgj.StructuralElement;

/**
 * Class producing a voting intentions chart.
 */
public class VotingIntentionsChart extends HorizontalBartChart {
    private static final int MAX_CHOICE_WIDTH = 1000;
    private Integer numberOfGroups;
    private Map<String, ProbabilityMassFunction<ProbabilityRange>> votingIntentionsMap;

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

    @Override
    protected double calculateContentWidth() {
        return getWidestChoiceWidth() + 2 * SPACE_BETWEEN_ELEMENTS + MAX_CHOICE_WIDTH + getWidestLabelWidth();
    }

    private double getWidestChoiceWidth() {
        return 0; // TODO
    }

    private double getWidestLabelWidth() {
        return 0; // TODO
    }

    @Override
    int getNumberOfGroups() {
        if (numberOfGroups == null) {
            numberOfGroups = getVotingIntentionsMap().size();
        }
        return numberOfGroups;
    }

    private Map<String, ProbabilityMassFunction<ProbabilityRange>> getVotingIntentionsMap() {
        if (votingIntentionsMap == null) {
            votingIntentionsMap = getPoll().getVotingIntentions().getMap();
        }
        return votingIntentionsMap;
    }

    @Override
    protected StructuralElement createChartContent() {
        return new G(); // TODO
    }

    @Override
    protected String getTitleText() {
        return "Voting Intentions for the " + getSaporDirectory().getCountryProperties().getParliamentName();
    }

    @Override
    protected String getSubtitleText() {
        StringBuilder sb = new StringBuilder();
        sb.append("Based on an Opinion Poll by ");
        sb.append(getPoll().getPollingFirm());
        if (getPoll().getComissioners() != null) {
            sb.append(" for " + getPoll().getComissioners());
        }
        sb.append(", ");
        sb.append(TimeServices.formatPeriod(getPoll().getFieldworkStart(), getPoll().getFieldworkEnd()));
        return sb.toString();
    }
}
