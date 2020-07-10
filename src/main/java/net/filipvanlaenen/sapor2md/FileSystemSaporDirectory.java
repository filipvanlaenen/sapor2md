package net.filipvanlaenen.sapor2md;

public class FileSystemSaporDirectory implements SaporDirectory {

    private final FileSystemCountryProperties countryProperties;

    FileSystemSaporDirectory(String directory) {
        this.countryProperties = new FileSystemCountryProperties(directory);
    }

    @Override
    public CountryProperties getCountryProperties() {
        return countryProperties;
    }

}
