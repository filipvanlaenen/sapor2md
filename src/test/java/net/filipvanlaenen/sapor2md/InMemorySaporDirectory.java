package net.filipvanlaenen.sapor2md;

public class InMemorySaporDirectory implements SaporDirectory {

    private final InMemoryCountryProperties countryProperties;

    InMemorySaporDirectory(final InMemoryCountryProperties countryProperties) {
        this.countryProperties = countryProperties;
    }

    @Override
    public CountryProperties getCountryProperties() {
        return countryProperties;
    }

}
