package net.filipvanlaenen.sapor2md;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Unit tests on the <code>PollFilesMode</code> class.
 */
public class PollFilesModeTest {
    /**
     * A list with two poll files: <code>a.poll</code> and <code>b.poll</code>.
     */
    private static final List<String> AB_LIST = Arrays.asList("a.poll", "b.poll");
    /**
     * A list with two poll files: <code>a.poll</code> and <code>c.poll</code>.
     */
    private static final List<String> AC_LIST = Arrays.asList("a.poll", "c.poll");

    /**
     * Test verifying that in the merge common mode, only the common files are
     * retained after merging.
     */
    @Test
    void commonModeShouldRetainCommonFilesOnly() {
        List<String> result = PollFilesMode.Common.mergePollFiles(AB_LIST, AC_LIST);
        List<String> expected = Arrays.asList("a.poll");
        assertThat(result, is(expected));
    }

    /**
     * Test verifying that in the merge all mode, all files are retained after
     * merging.
     */
    @Test
    void allModeShouldRetainAllFiles() {
        List<String> result = PollFilesMode.All.mergePollFiles(AB_LIST, AC_LIST);
        List<String> expected = Arrays.asList("a.poll", "b.poll", "c.poll");
        assertThat(result, is(expected));
    }
}
