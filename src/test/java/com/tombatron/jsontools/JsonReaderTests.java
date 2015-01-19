package com.tombatron.jsontools;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class JsonReaderTests {

    @Test
    public void canCreateJsonReaderUsingCharArray() {
        char[] testCharArray = "Hello World".toCharArray();

        JsonReader testReader = JsonReader.create(testCharArray);

        assertNotNull(testReader);
    }
}
