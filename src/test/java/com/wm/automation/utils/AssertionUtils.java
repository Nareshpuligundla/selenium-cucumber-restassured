package com.wm.automation.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.Assertions;

import java.util.List;

public class AssertionUtils {

    private static final Logger logger = LogManager.getLogger(AssertionUtils.class);

    private AssertionUtils() {}

    public static void assertEquals(Object actual, Object expected, String message) {
        logger.info("Asserting [{}] equals [{}] – {}", actual, expected, message);
        Assertions.assertThat(actual)
                .as(message)
                .isEqualTo(expected);
    }

    public static void assertNotNull(Object actual, String message) {
        logger.info("Asserting not-null – {}", message);
        Assertions.assertThat(actual).as(message).isNotNull();
    }

    public static void assertTrue(boolean condition, String message) {
        logger.info("Asserting true – {}", message);
        Assertions.assertThat(condition).as(message).isTrue();
    }

    public static void assertFalse(boolean condition, String message) {
        logger.info("Asserting false – {}", message);
        Assertions.assertThat(condition).as(message).isFalse();
    }

    public static void assertContains(String actual, String expected, String message) {
        logger.info("Asserting '{}' contains '{}' – {}", actual, expected, message);
        Assertions.assertThat(actual)
                .as(message)
                .contains(expected);
    }

    public static void assertContainsIgnoreCase(String actual, String expected, String message) {
        logger.info("Asserting '{}' contains-ignore-case '{}' – {}", actual, expected, message);
        Assertions.assertThat(actual.toLowerCase())
                .as(message)
                .contains(expected.toLowerCase());
    }

    public static void assertNotEmpty(String actual, String message) {
        logger.info("Asserting not-empty – {}", message);
        Assertions.assertThat(actual).as(message).isNotEmpty();
    }

    public static void assertStatusCode(int actual, int expected) {
        logger.info("Asserting HTTP status code {} == {}", actual, expected);
        Assertions.assertThat(actual)
                .as("HTTP status code")
                .isEqualTo(expected);
    }

    public static void assertListNotEmpty(List<?> list, String message) {
        logger.info("Asserting list is not empty – {}", message);
        Assertions.assertThat(list).as(message).isNotEmpty();
    }

    public static void assertGreaterThan(int actual, int threshold, String message) {
        logger.info("Asserting {} > {} – {}", actual, threshold, message);
        Assertions.assertThat(actual).as(message).isGreaterThan(threshold);
    }
}
