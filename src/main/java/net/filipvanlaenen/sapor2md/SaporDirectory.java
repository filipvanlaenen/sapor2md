package net.filipvanlaenen.sapor2md;

public interface SaporDirectory {

    CountryProperties getCountryProperties();

    default String getGitHubDirectory() {
        return getCountryProperties().getGitHubDirectory();
    }

    default String getParliamentName() {
        return getCountryProperties().getParliamentName();
    }

}
