package net.filipvanlaenen.sapor2md;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Class implementing the <code>SaporDirectory</code> interface using the file
 * system.
 */
public final class FileSystemSaporDirectory implements SaporDirectory {

    /**
     * The country properties.
     */
    private final FileSystemCountryProperties countryProperties;
    /**
     * The polls.
     */
    private final List<Poll> polls;

    /**
     * Constructor using the path to the Sapor directory as the parameter.
     *
     * @param directory
     *            The path to the Sapor directory.
     */
    FileSystemSaporDirectory(final String directory) {
        this.countryProperties = new FileSystemCountryProperties(directory);
        List<String> pollFileNames = FileSystemServices.getPollFilesList(directory);
        polls = new ArrayList<Poll>();
        for (String pollFileName : pollFileNames) {
            polls.add(new FileSystemPoll(directory, pollFileName));
        }
    }

    @Override
    public CountryProperties getCountryProperties() {
        return countryProperties;
    }

    @Override
    public Iterator<Poll> getPolls() {
        return polls.iterator();
    }

}
