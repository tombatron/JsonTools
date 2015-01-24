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

    @Test
    public void canParseNextNumber() {
        JsonReader reader;

        char[] number_ex_1 = "-100}".toCharArray();

        reader = JsonReader.create(number_ex_1);

        assertEquals("-100", reader.nextNumber());

        char[] number_ex_2 = "100}".toCharArray();

        reader = JsonReader.create(number_ex_2);

        assertEquals("100", reader.nextNumber());

        char[] number_ex_3 = "0.10}".toCharArray();

        reader = JsonReader.create(number_ex_3);

        assertEquals("0.10", reader.nextNumber());

        char[] number_ex_4 = "10e-100}".toCharArray();

        reader = JsonReader.create(number_ex_4);

        assertEquals("10e-100", reader.nextNumber());

        char[] number_ex_5 = "100e+100}".toCharArray();

        reader = JsonReader.create(number_ex_5);

        assertEquals("100e+100", reader.nextNumber());
    }

    @Test
    public void willReturnNullIfCantParseNextNumber() {
        char[] bad_number_ex = ".10".toCharArray();

        JsonReader reader = JsonReader.create(bad_number_ex);

        assertNull(reader.nextNumber());
    }
}
