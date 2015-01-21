package com.tombatron.jsontools;

import org.junit.Test;

import static org.junit.Assert.*;

public class JsonReaderTests {

    @Test
    public void canCreateJsonReaderUsingCharArray() {
        char[] testCharArray = "Hello World".toCharArray();

        JsonReader testReader = JsonReader.create(testCharArray);

        assertNotNull(testReader);
    }

    @Test
    public void canParseNextString() {
        char[] testCharArray = "{\"Hello World\":123}".toCharArray();

        JsonReader testReader = JsonReader.create(testCharArray);

        // Have to fast forward past the first brace because the string parser will see it
        // as a document error.
        testReader.next();

        assertEquals("Hello World", testReader.nextString());
    }

    @Test
    public void nullIfCannotParseNextString() {
        char[] testCharArray = "{\"Hello World\":123}".toCharArray();

        JsonReader testReader = JsonReader.create(testCharArray);

        testReader.next();
        testReader.nextString();

        assertNull(testReader.nextString());
    }
}
