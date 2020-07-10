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
     * Returns the name of the GitHub directory.
     *
     * @return The name of the GitHub directory.
     */
    default String getGitHubDirectory() {
        return getCountryProperties().getGitHubDirectory();
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
