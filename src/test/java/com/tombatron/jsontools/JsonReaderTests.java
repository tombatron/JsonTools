package com.tombatron.jsontools;

import org.junit.Test;

import static org.junit.Assert.*;
import static com.tombatron.jsontools.Constants.NULL;

public class JsonReaderTests {

    @Test
    public void canCreateJsonReaderUsingCharArray() {
        char[] testCharArray = "Hello World".toCharArray();

        JsonReader testReader = JsonReader.create(testCharArray);

        assertNotNull(testReader);
    }

    @Test
    public void canParseNextString() {
        char[] testCharArray = "\"Hello World\"".toCharArray();

        JsonReader testReader = JsonReader.create(testCharArray);

        assertEquals("Hello World", testReader.nextString());
    }

    @Test
    public void nullIfCannotParseNextString() {
        char[] testCharArray = "\"Hello World\"".toCharArray();

        JsonReader testReader = JsonReader.create(testCharArray);

        testReader.nextString();

        assertNull(testReader.nextString());
    }

    @Test
    public void canParseNextBoolean() {
        char[] true_ = "true".toCharArray();
        char[] false_ = "false".toCharArray();

        JsonReader reader;

        reader = JsonReader.create(true_);

        assertTrue(reader.nextBoolean());

        reader = JsonReader.create(false_);

        assertFalse(reader.nextBoolean());
    }

    @Test
    public void canParseNextNull() {
        char[] null_ = "null".toCharArray();

        JsonReader reader = JsonReader.create(null_);

        assertEquals(NULL, reader.nextNull());
    }

    @Test
    public void nullIfCannotParseNextBoolean() {
        char[] notABoolean = "notNull".toCharArray();

        JsonReader reader = JsonReader.create(notABoolean);

        assertNull(reader.nextBoolean());
    }

    @Test
    public void notNullIfCannotParseNextNull() {
        char[] notNull = "notNull".toCharArray();

        JsonReader reader = JsonReader.create(notNull);

        assertNotEquals(NULL, reader.nextNull());
    }
}
