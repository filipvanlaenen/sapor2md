package net.filipvanlaenen.sapor2md;

import java.io.File;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;

public class FileSystemCountryProperties implements CountryProperties {

    final static private String COUNTRY_PROPERTIES_FILE_NAME = "country.properties";
    final static private String GITHUB_DIRECTORY_KEY = "GitHubDirectory";
    final static private String PARLIAMENT_NAME_KEY = "ParliamentName";

    final private String filePath;
    final private String gitHubDirectory;
    final private String parliamentName;

    FileSystemCountryProperties(String directory) {
        filePath = directory + File.pathSeparator + COUNTRY_PROPERTIES_FILE_NAME;
        String content = FileSystemServices.readFileIntoString(filePath);
        String[] lines = content.split("\n");
        Map<String, String> map = new HashMap<String, String>();
        for (String line : lines) {
            String[] elements = line.split("=");
            map.put(elements[0], elements[1]);
        }
        this.gitHubDirectory = map.get(GITHUB_DIRECTORY_KEY);
        this.parliamentName = map.get(PARLIAMENT_NAME_KEY);
    }

    @Override
    public String getGitHubDirectory() {
        return gitHubDirectory;
    }

    @Override
    public String getParliamentName() {
        return parliamentName;
    }

    @Override
    public OffsetDateTime getTimestamp() {
        return FileSystemServices.getTimestamp(filePath);
    }

}
