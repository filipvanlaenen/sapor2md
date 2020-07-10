package net.filipvanlaenen.sapor2md;

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
     * Constructor using the path to the Sapor directory as the parameter.
     *
     * @param directory
     *            The path to the Sapor directory.
     */
    FileSystemSaporDirectory(final String directory) {
        this.countryProperties = new FileSystemCountryProperties(directory);
    }

    @Override
    public CountryProperties getCountryProperties() {
        return countryProperties;
    }

}
