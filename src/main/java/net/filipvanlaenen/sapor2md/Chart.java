package net.filipvanlaenen.sapor2md;

import java.io.File;

import net.filipvanlaenen.tsvgj.FontStyleValue;
import net.filipvanlaenen.tsvgj.FontWeightValue;
import net.filipvanlaenen.tsvgj.NoneValue;
import net.filipvanlaenen.tsvgj.Rect;
import net.filipvanlaenen.tsvgj.ShapeElement;
import net.filipvanlaenen.tsvgj.StructuralElement;
import net.filipvanlaenen.tsvgj.Svg;
import net.filipvanlaenen.tsvgj.Text;
import net.filipvanlaenen.tsvgj.TextAlignValue;
import net.filipvanlaenen.tsvgj.TextAnchorValue;
import net.filipvanlaenen.tsvgj.Transform;

/**
 * Abstract class defining the behavior of a chart.
 */
public abstract class Chart {
    private static final int COPYRIGHT_FONT_SIZE = 10;
    private static final String FONT_FAMILIY = "Lato";
    private static final int MARGIN = 20;
    protected static final int SPACE_BETWEEN_ELEMENTS = 20;
    private static final int SUBTITLE_FONT_SIZE = 28;
    private static final int TITLE_FONT_SIZE = 46;
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
    private Double height;
    private Double width;
    private Integer textColor;
    private Integer backgroundColor;

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

    /**
     * Exports the chart's SVG document as a string.
     */
    @Override
    public String toString() {
        Svg svg = new Svg().width(getWidth()).height(getHeight()).viewBox(0, 0, getWidth(), getHeight());
        svg.addElement(createBackgroundRectangle());
        svg.addElement(createTitle());
        svg.addElement(createSubtitle());
        svg.addElement(createCopyrightNotice());
        svg.addElement(createChartContent());
        return svg.asString();
    }

    private ShapeElement createBackgroundRectangle() {
        Rect rect = new Rect().x(0).y(0).width(getWidth()).height(getHeight());
        rect.fill(getBackgroundColor()).stroke(NoneValue.NONE);
        return rect;
    }

    /**
     * TODO: font-size should be in px
     * 
     * @return
     */
    private Text createTitle() {
        Text text = new Text(getTitleText());
        text.x(getWidth() / 2).y(MARGIN + TITLE_FONT_SIZE);
        text.fontFamily(FONT_FAMILIY).fontSize(TITLE_FONT_SIZE);
        text.fontWeight(FontWeightValue.BOLD).fontStyle(FontStyleValue.NORMAL);
        text.fill(getTextColor());
        text.textAnchor(TextAnchorValue.MIDDLE).textAlign(TextAlignValue.CENTER);
        return text;
    }

    protected abstract String getTitleText();

    /**
     * TODO: font-size should be in px
     * 
     * @return
     */
    private Text createSubtitle() {
        Text text = new Text(getSubtitleText());
        text.x(getWidth() / 2).y(MARGIN + TITLE_FONT_SIZE + SPACE_BETWEEN_ELEMENTS + SUBTITLE_FONT_SIZE);
        text.fontFamily(FONT_FAMILIY).fontSize(SUBTITLE_FONT_SIZE);
        text.fontWeight(FontWeightValue.BOLD).fontStyle(FontStyleValue.NORMAL);
        text.fill(getTextColor());
        text.textAnchor(TextAnchorValue.MIDDLE).textAlign(TextAlignValue.CENTER);
        return text;
    }

    private int getTextColor() {
        if (textColor == null) {
            textColor = getSaporDirectory().getCountryProperties().getTextColor();
        }
        return textColor;
    }

    private int getBackgroundColor() {
        if (backgroundColor == null) {
            backgroundColor = getSaporDirectory().getCountryProperties().getBackgroundColor();
        }
        return backgroundColor;
    }

    protected abstract String getSubtitleText();

    private Text createCopyrightNotice() {
        Text text = new Text(getCopyrightNoticeText());
        text.x(-4).y(getWidth() - 4);
        text.fontFamily(FONT_FAMILIY).fontStyle(FontStyleValue.NORMAL).fontWeight(FontWeightValue.NORMAL);
        // TODO: font-size should be in px
        text.fontSize(COPYRIGHT_FONT_SIZE);
        text.textAlign(TextAlignValue.CENTER).textAnchor(TextAnchorValue.END);
        text.fill(getTextColor());
        text.transform(Transform.rotate(270));
        return text;
    }

    String getCopyrightNoticeText() {
        String copyrightText = getSaporDirectory().getCountryProperties().getCopyrightText();
        if (copyrightText == null) {
            return "Chart produced using Sapor2MD";
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("© ");
            sb.append(TimeServices.getYear());
            sb.append(" ");
            sb.append(getSaporDirectory().getCountryProperties().getCopyrightText());
            sb.append(", chart produced using Sapor2MD");
            return sb.toString();
        }
    }

    protected abstract StructuralElement createChartContent();

    private Double getHeight() {
        if (height == null) {
            height = calculateHeight();
        }
        return height;
    }

    private double calculateHeight() {
        return 2 * MARGIN + TITLE_FONT_SIZE + 2 * SPACE_BETWEEN_ELEMENTS + SUBTITLE_FONT_SIZE
                + calculateContentHeight();
    }

    protected abstract double calculateContentHeight();

    private Double getWidth() {
        if (width == null) {
            width = calculateWidth();
        }
        return width;
    }

    private Double calculateWidth() {
        return 2 * MARGIN + calculateContentWidth();
    }

    protected abstract double calculateContentWidth();

    protected Poll getPoll() {
        return poll;
    }

    protected SaporDirectory getSaporDirectory() {
        return directory;
    }
}
