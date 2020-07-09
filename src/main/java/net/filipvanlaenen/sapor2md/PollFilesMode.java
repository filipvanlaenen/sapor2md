package net.filipvanlaenen.sapor2md;

import java.util.ArrayList;
import java.util.List;

/**
 * The modes in which poll files from a set of directories can be merged
 * together.
 */
enum PollFilesMode {
    /**
     * Mode to merge poll files from directories retaining all of them.
     */
    All {
        /**
         * Merges two lists of poll files together, keeping all poll files from both
         * lists.
         */
        @Override
        List<String> mergePollFiles(final List<String> pollFiles, final List<String> localPollFiles) {
            List<String> newPollFiles = new ArrayList<>(localPollFiles);
            newPollFiles.removeAll(pollFiles);
            List<String> allPollFiles = new ArrayList<>(pollFiles);
            allPollFiles.addAll(newPollFiles);
            return allPollFiles;
        }
    },
    /**
     * Mode to merge poll files from directories retaining the common ones only.
     */
    Common {
        /**
         * Merges two lists of poll files together, keeping only the poll files that are
         * common to both lists.
         */
        @Override
        List<String> mergePollFiles(final List<String> pollFiles, final List<String> localPollFiles) {
            List<String> missingPollFiles = new ArrayList<>(pollFiles);
            missingPollFiles.removeAll(localPollFiles);
            List<String> commonPollFiles = new ArrayList<>(pollFiles);
            commonPollFiles.removeAll(missingPollFiles);
            return commonPollFiles;
        }
    };

    /**
     * Merges two lists of poll files together.
     *
     * @param pollFiles
     *            The list of poll files collected so far.
     * @param localPollFiles
     *            The list of poll files from a new directory.
     * @return The result of merging the two lists of poll files.
     */
    abstract List<String> mergePollFiles(List<String> pollFiles, List<String> localPollFiles);
}
