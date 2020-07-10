package net.filipvanlaenen.sapor2md;

/**
 * Class implementing the <code>SaporDirectory</code> interface in memory, to be
 * used for testing purposes.
 */
public final class InMemorySaporDirectory implements SaporDirectory {

    /**
     * The in-memory country properties.
     */
    private final InMemoryCountryProperties countryProperties;

    /**
     * Constructor taking an instance of <code>InMemoryCountryProperties</code> as
     * the argument.
     *
     * @param countryProperties
     *            The country properties for the Sapor directory.
     */
    InMemorySaporDirectory(final InMemoryCountryProperties countryProperties) {
        this.countryProperties = countryProperties;
    }

    @Override
    public CountryProperties getCountryProperties() {
        return countryProperties;
    }

}
