package com.tombatron.jsontools;

import org.junit.Test;

public class PerformanceTests {

    @Test
    public void validJsonParsePerformanceTest() {
        long[] timings = new long[10000];

        for (int i = 0; i < 10000; i++) {
            long start = System.nanoTime();

            Is.json(JsonSamples.KNOWN_GOOD_LARGE_OBJECT_SAMPLE);

            long end = System.nanoTime();

            long duration = (end - start);

            timings[i] = duration;
        }

        printResults("Valid JSON Parsing", timings);
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
