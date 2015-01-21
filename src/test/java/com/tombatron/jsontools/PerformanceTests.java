package com.tombatron.jsontools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

public class PerformanceTests {

    @Test
    public void validJsonParsePerformanceTest() {
        long start, end, duration;

        long[] timings = new long[10000];
        long[] referenceTimings = new long[10000];

        for (int i = 0; i < 10000; i++) {
            // Test the library code.
            start = System.nanoTime();

            Is.json(JsonSamples.KNOWN_GOOD_LARGE_OBJECT_SAMPLE);

            end = System.nanoTime();

            duration = (end - start);

            timings[i] = duration;

            // Test the reference code.
            start = System.nanoTime();

            referenceCode(JsonSamples.KNOWN_GOOD_LARGE_OBJECT_SAMPLE);

            end = System.nanoTime();

            duration = (end - start);

            referenceTimings[i] = duration;
        }

        printResults("Valid JSON Parsing", timings);
        printResults("Valid JSON Parsing (Reference)", referenceTimings);
    }

    /**
     * This is reference code used to test the performance of the `Is` class.
     *
     * http://stackoverflow.com/questions/10174898/how-to-check-whether-a-given-string-is-valid-json-in-java
     *
     * @param sampleJson JSON string to test.
     *
     * @return Whether or not the given string is JSON.
     */
    public boolean referenceCode(String sampleJson) {
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

    public static void printResults(String testName, long[] timings) {
        long sum = 0;

        for (long timing : timings) {
            sum += timing;
        }

        long average = sum / timings.length;

        long millisElapsed = average / 1000;

        System.out.println(testName + " - Completed " + timings.length + " iterations with each taking an average of " + millisElapsed + " milliseconds.");
    }
}
