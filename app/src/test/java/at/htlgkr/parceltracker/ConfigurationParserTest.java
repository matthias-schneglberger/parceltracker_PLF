package at.htlgkr.parceltracker;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class ConfigurationParserTest {
    public static final String TEST_FILE = "{\n" +
            "    \"category1\":     \"test1\", \"category2\": \"test2\",\n" +
            "    \"category3\": \"test3 test3\",    \"category4\": \"test5\",\n" +
            "    \"categoryCount\": \"4\"\n" +
            "}";

    public static final String TEST_FILE_TO_MUCH = "{\n" +
            "    \"category1\":     \"test1\", \"category2\": \"test2\",\n" +
            "    \"category3\": \"test3 test3\",    \"category4\": \"test5\", \"category5\": \"test6\", \"category6\": \"test6\",\n" +
            "    \"categoryCount\": \"4\"\n" +
            "}";

    public static final String TEST_FILE_SIMPLE = "{\n" +
            "    \"category1\": \"test1\",\n"+
            "    \"categoryCount\": \"1\"\n" +
            "}";

    public static final String TEST_FILE_SINGLE_LINE= "{    \"category1\":     \"test1\", \"category2\": \"test2\",    \"category3\": \"test3 test3\",    \"category4\": \"test5\",    \"categoryCount\": \"4\"}";


    public static final String TEST_FILE_LOT_LINES = "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n{\n\n\n\n" +
            "  \n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n  \"category1\":  \n\n\n\n\n\n\n\n\n\n\n\n\n   \"test1\"\n\n\n\n\n\n\n\n\n\n\n\n, \"category2\": \n\n\n\n\n\n\n\n\n\n\"test2\",\n" +
            "   \n\n\n\n\n\n\n\n\n\n\n\n\n \"category3\": \n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\"test3 test3\"\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n,\n\n\n\n\n\n\n\n\n\n\n\n\n    \"category4\": \"test5\",\n" +
            "   \n\n\n\n\n\n\n\n\n\n\n\n\n\n\n \"categoryCount\": \n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\"4\"\n" +
            "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n}\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n";

    @Test
    public void configurationParser_createsSomething_simple() throws IOException {
        String initialString = TEST_FILE_SIMPLE;
        InputStream targetStream = new ByteArrayInputStream(initialString.getBytes());
        List<Category> categories = ConfigurationParser.getCategories(targetStream);

        assertTrue(categories.size() > 0);
    }

    @Test
    public void configurationParser_correctSize_simple() throws IOException {
        String initialString = TEST_FILE_SIMPLE;
        InputStream targetStream = new ByteArrayInputStream(initialString.getBytes());
        List<Category> categories = ConfigurationParser.getCategories(targetStream);

        assertEquals(1, categories.size());
    }

    @Test
    public void configurationParser_correctData_simple() throws IOException {
        String initialString = TEST_FILE_SIMPLE;
        InputStream targetStream = new ByteArrayInputStream(initialString.getBytes());
        List<Category> actual = ConfigurationParser.getCategories(targetStream);

        TestUtils.ListTester<Category> lt = new TestUtils.ListTester<>();

        assertTrue(lt.listsEqual(getCategories().subList(0, 1), actual));
    }

    @Test
    public void configurationParser_createsSomething() throws IOException {
        String initialString = TEST_FILE;
        InputStream targetStream = new ByteArrayInputStream(initialString.getBytes());
        List<Category> categories = ConfigurationParser.getCategories(targetStream);

        assertTrue(categories.size() > 0);
    }

    @Test
    public void configurationParser_correctSize() throws IOException {
        String initialString = TEST_FILE;
        InputStream targetStream = new ByteArrayInputStream(initialString.getBytes());
        List<Category> categories = ConfigurationParser.getCategories(targetStream);

        assertEquals(4, categories.size());
    }

    @Test
    public void configurationParser_correctData() throws IOException {
        String initialString = TEST_FILE;
        InputStream targetStream = new ByteArrayInputStream(initialString.getBytes());
        List<Category> actual = ConfigurationParser.getCategories(targetStream);

        TestUtils.ListTester<Category> lt = new TestUtils.ListTester<>();

        assertTrue(lt.listsEqual(getCategories(), actual));
    }

    @Test
    public void configurationParser_createsSomething_singleLine() throws IOException {
        String initialString = TEST_FILE_SINGLE_LINE;
        InputStream targetStream = new ByteArrayInputStream(initialString.getBytes());
        List<Category> categories = ConfigurationParser.getCategories(targetStream);

        assertTrue(categories.size() > 0);
    }

    @Test
    public void configurationParser_correctSize_singleLine() throws IOException {
        String initialString = TEST_FILE_SINGLE_LINE;
        InputStream targetStream = new ByteArrayInputStream(initialString.getBytes());
        List<Category> categories = ConfigurationParser.getCategories(targetStream);

        assertEquals(4, categories.size());
    }

    @Test
    public void configurationParser_correctData_singleLine() throws IOException {
        String initialString = TEST_FILE_SINGLE_LINE;
        InputStream targetStream = new ByteArrayInputStream(initialString.getBytes());
        List<Category> actual = ConfigurationParser.getCategories(targetStream);

        TestUtils.ListTester<Category> lt = new TestUtils.ListTester<>();

        assertTrue(lt.listsEqual(getCategories(), actual));
    }

    @Test
    public void configurationParser_createsSomething_lotLines() throws IOException {
        String initialString = TEST_FILE_LOT_LINES;
        InputStream targetStream = new ByteArrayInputStream(initialString.getBytes());
        List<Category> categories = ConfigurationParser.getCategories(targetStream);

        assertTrue(categories.size() > 0);
    }

    @Test
    public void configurationParser_correctSize_lotLines() throws IOException {
        String initialString = TEST_FILE_LOT_LINES;
        InputStream targetStream = new ByteArrayInputStream(initialString.getBytes());
        List<Category> categories = ConfigurationParser.getCategories(targetStream);

        assertEquals(4, categories.size());
    }

    @Test
    public void configurationParser_correctData_lotLines() throws IOException {
        String initialString = TEST_FILE_LOT_LINES;
        InputStream targetStream = new ByteArrayInputStream(initialString.getBytes());
        List<Category> actual = ConfigurationParser.getCategories(targetStream);

        TestUtils.ListTester<Category> lt = new TestUtils.ListTester<>();

        assertTrue(lt.listsEqual(getCategories(), actual));
    }

    @Test
    public void configurationParser_createsSomething_toMuch() throws IOException {
        String initialString = TEST_FILE_TO_MUCH;
        InputStream targetStream = new ByteArrayInputStream(initialString.getBytes());
        List<Category> categories = ConfigurationParser.getCategories(targetStream);

        assertTrue(categories.size() > 0);
    }

    @Test
    public void configurationParser_correctSize_toMuch() throws IOException {
        String initialString = TEST_FILE_TO_MUCH;
        InputStream targetStream = new ByteArrayInputStream(initialString.getBytes());
        List<Category> categories = ConfigurationParser.getCategories(targetStream);

        assertEquals(4, categories.size());
    }

    @Test
    public void configurationParser_correctData_toMuch() throws IOException {
        String initialString = TEST_FILE_TO_MUCH;
        InputStream targetStream = new ByteArrayInputStream(initialString.getBytes());
        List<Category> actual = ConfigurationParser.getCategories(targetStream);

        TestUtils.ListTester<Category> lt = new TestUtils.ListTester<>();

        assertTrue(lt.listsEqual(getCategories(), actual));
    }

    private List<Category> getCategories() {
        List<Category> result = new ArrayList<>();

        result.add(new Category(1, "test1"));
        result.add(new Category(2, "test2"));
        result.add(new Category(3, "test3 test3"));
        result.add(new Category(4, "test5"));

        return result;
    }
}
