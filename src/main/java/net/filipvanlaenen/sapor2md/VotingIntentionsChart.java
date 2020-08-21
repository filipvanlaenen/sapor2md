package net.filipvanlaenen.sapor2md;

import java.util.List;
import java.util.Map;

import net.filipvanlaenen.tsvgj.FontStyleValue;
import net.filipvanlaenen.tsvgj.FontWeightValue;
import net.filipvanlaenen.tsvgj.G;
import net.filipvanlaenen.tsvgj.Line;
import net.filipvanlaenen.tsvgj.Pattern;
import net.filipvanlaenen.tsvgj.PatternUnitsValue;
import net.filipvanlaenen.tsvgj.Rect;
import net.filipvanlaenen.tsvgj.StructuralElement;
import net.filipvanlaenen.tsvgj.Text;
import net.filipvanlaenen.tsvgj.TextAlignValue;
import net.filipvanlaenen.tsvgj.TextAnchorValue;
import net.filipvanlaenen.tsvgj.Transform;

/**
 * Class producing a voting intentions chart.
 */
public class VotingIntentionsChart extends HorizontalBarChart {
    static final int MAX_CHOICE_WIDTH = 1000;
    private Integer numberOfGroups;
    private Double widestChoiceWidth;
    private Double widestLabelWidth;
    private Double largestValue;
    private Map<String, ProbabilityMassFunction<ProbabilityRange>> votingIntentionsMap;
    private List<String> sortedGroups;

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
        // TODO: Could be slightly smarter: it's possible that the widest label doesn't
        // occur together with the widest bar (e.g. 20 is wider than 21)
        return getWidestChoiceWidth() + 2 * SPACE_BETWEEN_ELEMENTS + MAX_CHOICE_WIDTH + getWidestLabelWidth();
    }

    private double getWidestChoiceWidth() {
        if (widestChoiceWidth == null) {
            widestChoiceWidth = 0d;
            for (String name : getVotingIntentionsMap().keySet()) {
                double width = getLabelWidth(name, CHOICE_LABEL_FONT_SIZE);
                if (width > widestChoiceWidth) {
                    widestChoiceWidth = width;
                }
            }
        }
        return widestChoiceWidth;
    }

    private double getWidestLabelWidth() {
        if (widestLabelWidth == null) {
            widestLabelWidth = 0d;
            for (ProbabilityMassFunction<ProbabilityRange> pmf : getVotingIntentionsMap().values()) {
                ConfidenceInterval<ProbabilityRange> ci = pmf.getConfidenceInterval(0.95D);
                String label = ProbabilityRange.formatConfidenceInterval("%.0f", ci);
                double width = getLabelWidth(label, CHOICE_LABEL_FONT_SIZE);
                if (width > widestLabelWidth) {
                    widestLabelWidth = width;
                }
            }
        }
        return widestLabelWidth;
    }

    private double getLargestValue() {
        if (largestValue == null) {
            largestValue = 0d;
            for (ProbabilityMassFunction<ProbabilityRange> pmf : getVotingIntentionsMap().values()) {
                ConfidenceInterval<ProbabilityRange> ci = pmf.getConfidenceInterval(0.95D);
                double value = ci.getUpperBound().getUpperBound();
                if (value > largestValue) {
                    largestValue = value;
                }
            }
            // TODO: Also go through the last results
        }
        return largestValue;
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
    protected StructuralElement createChartContent(List<Pattern> chartContentPatterns) {
        G g = new G();
        g.addElement(createLegend());
        g.addElement(createGridLines());
        g.addElement(createDataElements(chartContentPatterns));
        if (needsMajorityLine()) {
            g.addElement(createMajorityLine());
        }
        if (hasThreshold()) {
            g.addElement(createThresholdLine());
        }
        return g;
    }

    private boolean hasThreshold() {
        return false; // TODO
    }

    private boolean needsMajorityLine() {
        return false; // TODO
    }

    private StructuralElement createLegend() {
        return new G();// TODO
    }

    private StructuralElement createGridLines() {
        return new G();// TODO
    }

    private StructuralElement createDataElements(List<Pattern> chartContentPatterns) {
        G g = new G();
        int i = 0;
        for (String group : getSortedGroups()) {
            g.addElement(createGroupDataElements(group, i, chartContentPatterns));
            i += 1;
        }
        return g;
    }

    private StructuralElement createGroupDataElements(final String group, final int i,
            List<Pattern> chartContentPatterns) {
        G g = new G();
        g.addElement(createGroupLabel(group, i));
        g.addElement(createLastResultRectangle(group, i));
        g.addElement(createLastResultLabel(group, i));
        g.addElement(createRectangleToUpperBound(group, i, chartContentPatterns));
        g.addElement(createRectangleToMedian(group, i, chartContentPatterns));
        g.addElement(createRectangleToLowerBound(group, i));
        g.addElement(createResultLabel(group, i));
        return g;
    }

    private double calculateBarXProperty() {
        return MARGIN + getWidestChoiceWidth() + SPACE_BETWEEN_ELEMENTS;
    }

    private double calculateBarYProperty(final int i) {
        return MARGIN + TITLE_FONT_SIZE + SPACE_BETWEEN_ELEMENTS + SUBTITLE_FONT_SIZE + SPACE_BETWEEN_ELEMENTS
                + TICKS_HEIGHT + i * (CHOICE_HEIGHT + SPACE_BETWEEN_CHOICES);
    }

    private Rect createLastResultRectangle(String group, final int i) {
        return new Rect(); // TODO
    }

    private Pattern createHatchingPattern(int color, int angle) {
        Pattern pattern = new Pattern().height(10).width(10);
        pattern.patternUnits(PatternUnitsValue.USER_SPACE_ON_USE);
        pattern.patternTransform(Transform.rotate(angle, 0, 0));
        Line line = new Line().x1(0).y1(0).x2(0).y2(10).stroke(color).strokeWidth(20D / 3);
        pattern.addElement(line);
        return pattern;
    }

    private Rect createRectangleToUpperBound(String group, final int i, List<Pattern> chartContentPatterns) {
        Rect rect = new Rect();
        rect.x(calculateBarXProperty());
        rect.y(calculateBarYProperty(i));
        rect.height(POLL_RESULT_HEIGHT);
        double value = votingIntentionsMap.get(group).getConfidenceInterval(0.95D).getUpperBound().getUpperBound();
        rect.width(MAX_CHOICE_WIDTH * value / getLargestValue());
        Pattern pattern = createHatchingPattern(0, 45);// TODO: choice_color[choice]
        chartContentPatterns.add(pattern);
        rect.fill(pattern);
        rect.stroke(0).strokeWidth(BAR_STROKE); // TODO: choice_color[choice]
        return rect;
    }

    private Rect createRectangleToMedian(String group, final int i, List<Pattern> chartContentPatterns) {
        Rect rect = new Rect();
        rect.x(calculateBarXProperty());
        rect.y(calculateBarYProperty(i));
        rect.height(POLL_RESULT_HEIGHT);
        double value = votingIntentionsMap.get(group).getMedian().getUpperBound();
        rect.width(MAX_CHOICE_WIDTH * value / getLargestValue());
        Pattern pattern = createHatchingPattern(0, 135);// TODO: choice_color[choice]
        chartContentPatterns.add(pattern);
        rect.fill(pattern);
        rect.stroke(0).strokeWidth(BAR_STROKE); // TODO: choice_color[choice]
        return rect;
    }

    private Rect createRectangleToLowerBound(String group, final int i) {
        Rect rect = new Rect();
        rect.x(calculateBarXProperty());
        rect.y(calculateBarYProperty(i));
        rect.height(POLL_RESULT_HEIGHT);
        double value = votingIntentionsMap.get(group).getConfidenceInterval(0.95D).getLowerBound().getUpperBound();
        rect.width(MAX_CHOICE_WIDTH * value / getLargestValue());
        rect.fill(0); // TODO: choice_color[choice]
        return rect;
    }

    private Text createGroupLabel(String group, final int i) {
        Text text = new Text(group);
        text.x(MARGIN + getWidestChoiceWidth());
        double y = MARGIN + TITLE_FONT_SIZE + SPACE_BETWEEN_ELEMENTS + SUBTITLE_FONT_SIZE + SPACE_BETWEEN_ELEMENTS
                + TICKS_HEIGHT + i * (CHOICE_HEIGHT + SPACE_BETWEEN_CHOICES) + CHOICE_HEIGHT * 2D / 3D;
        text.y(y);
        text.fontFamily(FONT_FAMILIY).fontStyle(FontStyleValue.NORMAL).fontWeight(FontWeightValue.BOLD);
        text.fontSize(CHOICE_LABEL_FONT_SIZE); // TODO Should be in px
        text.textAlign(TextAlignValue.CENTER).textAnchor(TextAnchorValue.END);
        text.fill(getTextColor());
        return text;
    }

    private Text createLastResultLabel(String group, final int i) {
        return new Text(""); // TODO
    }

    private Text createResultLabel(String group, final int i) {
        return new Text(""); // TODO
    }

    private List<String> getSortedGroups() {
        if (sortedGroups == null) {
            sortedGroups = getPoll().getVotingIntentions().getSortedGroups();
        }
        return sortedGroups;
    }

    private StructuralElement createMajorityLine() {
        return new G();// TODO
    }

    private StructuralElement createThresholdLine() {
        return new G();// TODO
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
