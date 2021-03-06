package net.filipvanlaenen.sapor2md;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A utility class providing file system services.
 */
public final class FileSystemServices {

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private FileSystemServices() {
    }

    /**
     * Reads a file from the file system and returns the result as a single string.
     *
     * @param filePath The path to the file to be read.
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

    /**
     * Reads a file from the file system and returns the result as a map, using
     * <code>=</code> as the separator between the keys and the values.
     *
     * @param filePath The path to the file to be read.
     * @return The content of the file as a map.
     */
    static Map<String, String> readFileIntoMap(final String filePath) {
        String content = FileSystemServices.readFileIntoString(filePath);
        return parseMapFromString(content);
    }

    /**
     * Converts the content of a string to a map, using <code>=</code> as the
     * separator between the keys and the values.
     *
     * @param content A string with the content to be converted.
     * @return A map.
     */
    static Map<String, String> parseMapFromString(final String content) {
        String[] lines = content.split("\n");
        Map<String, String> map = new HashMap<String, String>();
        for (String line : lines) {
            String[] elements = line.split("=");
            map.put(elements[0], elements[1]);
        }
        return map;
    }

    /**
     * Writes a string to a file on the file system. Returns <code>false</code> if
     * an <code>IOException</code> was thrown, and <code>true</code> otherwise.
     *
     * @param content  The content to be written to the file.
     * @param filePath The path to the file.
     * @return True if the string was written to the file, or false if an
     *         IOException was thrown in the process.
     */
    static boolean writeStringToFile(final String content, final String filePath) {
        try {
            Files.writeString(Paths.get(filePath), content, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Returns the last modified timestamp of a file as an
     * <code>OffsetDataTime</code>.
     *
     * @param filePath The path to the file.
     * @return The last modified timestamp.
     */
    static OffsetDateTime getTimestamp(final String filePath) {
        FileTime timestamp;
        try {
            timestamp = Files.getLastModifiedTime(Paths.get(filePath));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return OffsetDateTime.ofInstant(timestamp.toInstant(), ZoneOffset.systemDefault());
    }

    /**
     * Returns a list with the names of the poll files in a directory.
     *
     * @param directory The path to a directory.
     * @return A list with the names of the poll files in the directory.
     */
    static List<String> getPollFilesList(final String directory) {
        try (Stream<Path> walk = Files.walk(Paths.get(directory))) {
            return walk.filter(Files::isRegularFile).map(x -> x.getFileName().toString())
                    .filter(f -> f.endsWith(".poll")).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Returns the size of a file.
     *
     * @param filePath The path to the file.
     * @return The size of the file, or 0 if it doesn't exist.
     */
    static long getFileSize(final String filePath) {
        try {
            return FileChannel.open(Paths.get(filePath)).size();
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
