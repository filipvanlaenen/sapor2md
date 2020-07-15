package net.filipvanlaenen.sapor2md;

import java.util.List;

/**
 * Class implementing the <code>SaporDirectory</code> interface using the file
 * system.
 */
public final class FileSystemSaporDirectory extends SaporDirectory {
    /**
     * Constructor using the path to the Sapor directory as the parameter.
     *
     * @param directory The path to the Sapor directory.
     */
    FileSystemSaporDirectory(final String directory) {
        super(new FileSystemCountryProperties(directory));
        List<String> pollFileNames = FileSystemServices.getPollFilesList(directory);
        for (String pollFileName : pollFileNames) {
            addPoll(new FileSystemPoll(directory, pollFileName));
        }
    }
}
