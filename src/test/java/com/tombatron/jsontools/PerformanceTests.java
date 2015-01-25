package com.tombatron.jsontools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class PerformanceTests {

    @Test
    public void validJsonParsePerformanceTest() {
        executeIsJsonPerformanceTest(
                JsonSamples.KNOWN_GOOD_LARGE_OBJECT_SAMPLE,
                "Valid JSON Parsing",
                "JsonTools library is not faster at parsing a valid JSON object than the reference code."
        );
    }

    @Test
    public void validJsonArrayParsePerformanceTest() {
        executeIsJsonPerformanceTest(
                JsonSamples.KNOWN_GOOD_LARGE_ARRAY_SAMPLE,
                "Valid JSON Array Parsing",
                "JsonTools library is not faster at parsing a valid JSON array than the reference code."
        );
    }

    @Test
    public void invalidJsonParsePerformanceTest() {
        executeIsJsonPerformanceTest(
                JsonSamples.KNOWN_BAD_LARGE_OBJECT_SAMPLE,
                "Invalid JSON Parsing",
                "JsonTools library is not faster at parsing an invalid JSON object than the reference code."
        );
    }

    @Test
    public void invalidJsonArrayParsePerformanceTest() {
        executeIsJsonPerformanceTest(
                JsonSamples.KNOWN_BAD_LARGE_ARRAY_SAMPLE,
                "Invalid JSON array Parsing",
                "JsonTools library is not faster at parsing an invalid JSON array than the reference code."
        );
    }

    /**
     * This is reference code used to test the performance of the `Is` class.
     * <p/>
     * http://stackoverflow.com/questions/10174898/how-to-check-whether-a-given-string-is-valid-json-in-java
     *
     * @param sampleJson JSON string to test.
     * @return Whether or not the given string is JSON.
     */
    public static boolean referenceCode(String sampleJson) {
        try {
            new JSONObject(sampleJson);
        } catch (JSONException ex) {
            // edited, to include @Arthur's comment
            // e.g. in case JSONArray is valid as well...
            try {
                new JSONArray(sampleJson);
            } catch (JSONException ex1) {
                return false;
            }
        }
        return true;
    }

    public static long printResults(String testName, long[] timings) {
        long sum = 0;

        for (long timing : timings) {
            sum += timing;
        }

        long average = sum / timings.length;

        long millisElapsed = average / 1000;

        System.out.println(testName + " - Completed " + timings.length + " iterations with each taking an average of " + millisElapsed + " milliseconds.");

        return millisElapsed;
    }

    public static void executeIsJsonPerformanceTest(String jsonSample, String testTitle, String failureMessage) {
        long start, end, duration;

        long[] timings = new long[10000];
        long[] referenceTimings = new long[10000];

        for (int i = 0; i < 10000; i++) {
            // Test the library code.
            start = System.nanoTime();

            Is.json(jsonSample);

            end = System.nanoTime();

            duration = (end - start);

            timings[i] = duration;

            // Test the reference code.
            start = System.nanoTime();

            referenceCode(jsonSample);

            end = System.nanoTime();

            duration = (end - start);

            referenceTimings[i] = duration;
        }

        long result = printResults(testTitle, timings);
        long referenceResult = printResults(testTitle + " (Reference)", referenceTimings);

        assertTrue(failureMessage, referenceResult > result);
    }
}
