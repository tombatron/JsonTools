package com.tombatron.jsontools;

/**
 * JsonReader is used by the `json` method of the `Is` class to parse a potential
 * JSON string.
 */
public class JsonReader {

    private int length;
    private int position;
    private char[] text;

    /**
     * Sole constructor accessed via a static factory method.
     *
     * @param text A character array representing the candidate JSON string.
     */
    private JsonReader(char[] text) {
        this.length = text.length;
        this.position = -1;
        this.text = text;
    }

    /**
     * Decrement the current position of the reader by one.
     */
    public void back() {
        back(1);
    }

    /**
     * Decrement the current position of the reader by a specified amount.
     *
     * @param decrement Number of positions to decrement.
     */
    public void back(int decrement) {
        this.position -= decrement;
    }

    /**
     * Return the character in the current position.
     *
     * @return The character in the current reader position.
     */
    public char current() {
        return this.text[position];
    }

    /**
     * Increment the current position of the reader by one and return the character that
     * exists in that newly updated position.
     *
     * @return The next character from the reader.
     */
    public char next() {
        this.position++;

        return this.text[position];
    }

    /**
     * Return whether or not there is more data in the reader.
     *
     * @return True or false, does the reader have more to give?
     */
    public boolean hasNext() {
        return (this.position + 1) < this.length;
    }

    /**
     * Returns the current position of the JsonReader.
     *
     * @return The current position of the JsonReader.
     */
    public int getPosition() {
        return this.position;
    }

    /**
     * The static factory method used to create an instance of the JsonReader.
     *
     * @param text char[] that is reader by the JsonReader class.
     *
     * @return Instance of the JsonReader class.
     */
    public static JsonReader create(char[] text) {
        return new JsonReader(text);
    }

    @Override
    public String toString() {
        String nextCharacter;

        if (position + 1 < this.length) {
            nextCharacter = String.valueOf(this.text[position + 1]);
        } else {
            nextCharacter = "EOF";
        }

        String currentCharacter = String.valueOf(this.text[position]);

        return "Current Character='" + currentCharacter + "'; Next Character='" + nextCharacter + "'";
    }
}
