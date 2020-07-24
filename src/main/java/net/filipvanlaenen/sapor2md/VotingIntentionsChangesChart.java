package net.filipvanlaenen.sapor2md;

import java.util.Map;

import net.filipvanlaenen.tsvgj.G;
import net.filipvanlaenen.tsvgj.StructuralElement;

/**
 * Class producing a voting intentions changes chart.
 */
public class VotingIntentionsChangesChart extends HorizontalBarChart {
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
    public VotingIntentionsChangesChart(final String directoryPath, final String pollFileName) {
        super(directoryPath, pollFileName);
    }

    /**
     * Constructor taking a Sapor directory and a poll as its parameters.
     *
     * @param directory The Sapor directory.
     * @param poll      The poll.
     */
    public VotingIntentionsChangesChart(final SaporDirectory directory, final Poll poll) {
        super(directory, poll);
    }

    /**
     * Returns the suffix for the file name for the chart.
     *
     * @return The suffix for the file name for the chart.
     */
    @Override
    public String getFileNameSuffix() {
        return "-changes";
    }

    @Override
    protected double calculateContentWidth() {
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
        return "Voting Intentions Changes for the " + getSaporDirectory().getCountryProperties().getParliamentName();
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
