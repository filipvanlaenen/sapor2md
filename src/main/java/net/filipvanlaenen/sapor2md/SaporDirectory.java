package net.filipvanlaenen.sapor2md;

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
     * Returns the name of the parliament.
     *
     * @return The name of the parliament.
     */
    default String getParliamentName() {
        return getCountryProperties().getParliamentName();
    }

}
