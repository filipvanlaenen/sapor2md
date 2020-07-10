package net.filipvanlaenen.sapor2md;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.stream.Stream;

public class FileSystemServices {
    /**
     * Reads a file from the file system and returns the result as a single string.
     *
     * @param filePath
     *            The path to the file to be read.
     * @return The content of the file as a single multiline string.
     */
    static String readFileIntoString(final String filePath) {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(filePath), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s).append("\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contentBuilder.toString();
    }

    static OffsetDateTime getTimestamp(String filePath) {
        FileTime timestamp;
        try {
            timestamp = Files.getLastModifiedTime(Paths.get(filePath));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return OffsetDateTime.ofInstant(timestamp.toInstant(), ZoneOffset.systemDefault());
    }
}
