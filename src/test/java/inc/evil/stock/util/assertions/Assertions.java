package inc.evil.stock.util.assertions;


import org.opentest4j.AssertionFailedError;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.JsonNode;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

public class Assertions {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> void assertEquals(T expected, T actual, Comparator<T> comparator) {
        int compareResult = comparator.compare(expected, actual);
        if (compareResult != 0) {
            throw new AssertionFailedError("Not equal!", expected, actual);
        }
    }

    public static <T> void assertEquals(List<T> expected, List<T> actual, Comparator<T> comparator) {
        String message = "array lengths differ , expected: <" + expected + "> but was: <" + actual + ">";
        org.junit.jupiter.api.Assertions.assertEquals(expected.size(), actual.size(), message);
        for (int i = 0; i < expected.size(); ++i) {
            T expectedItem = expected.get(i);
            T actualItem = actual.get(i);
            assertEquals(expectedItem, actualItem, comparator);
        }
    }

    public static void assertJsonEquals(String expected, String actual) {
        try {
            JsonNode expectedNode = objectMapper.readTree(expected);
            JsonNode actualNode = objectMapper.readTree(actual);
            org.junit.jupiter.api.Assertions.assertEquals(expectedNode, actualNode);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
