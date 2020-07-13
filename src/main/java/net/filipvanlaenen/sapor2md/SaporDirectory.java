package net.filipvanlaenen.sapor2md;

import java.util.Iterator;

/**
 * Interface defining the behavior for a Sapor directory.
 */
public interface SaporDirectory {

    /**
     * Returns the country properties.
     *
     * @return The country properties.
     */
    CountryProperties getCountryProperties();

    /**
     * Returns the URL to the GitHub directory.
     *
     * @return The URL to the GitHub directory.
     */
    default String getGitHubDirectoryURL() {
        return getCountryProperties().getGitHubDirectoryURL();
    }

    /**
     * Returns an iterator with the polls in the directory.
     *
     * @return An iterator with the polls.
     */
    Iterator<Poll> getPolls();

}
