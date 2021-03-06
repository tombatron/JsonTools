package com.tombatron.jsontools;

import static com.tombatron.jsontools.Constants.NULL;

/**
 * JsonReader is used by the `json` method of the `Is` class to parse a potential
 * JSON string.
 */
public final class JsonReader {

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
    public char back() {
        return back(1);
    }

    /**
     * Decrement the current position of the reader by a specified amount.
     *
     * @param decrement Number of positions to decrement.
     */
    public char back(int decrement) {
        if (this.position - decrement >= -1) {
            this.position -= decrement;
        }

        return (this.position >= 0) ? this.text[this.position] : NULL;
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
        return next(1)[0];
    }

    /**
     * Get the next `n` number of characters and return them as a char[].
     *
     * @param n Number of characters to advance.
     * @return A char[] containing the next `n` characters.
     */
    public char[] next(int n) {
        char[] result = new char[n];

        for (int i = 0; i < n; i++) {
            this.position++;

            if (this.position >= this.length) {
                result[i] = NULL;
            } else {
                result[i] = this.text[position];
            }
        }

        return result;
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
     * @return Instance of the JsonReader class.
     */
    public static JsonReader create(char[] text) {
        return new JsonReader(text);
    }

    /**
     * Go to the next delimiter in the reader. If no more delimiters can be found a null
     * character will be returned.
     * <p/>
     * If a character other than a "whitespace" character or object/array delimiter is encountered a null
     * character will be returned.
     *
     * @return The next delimiter character.
     */
    public char nextDelimiter() {
        while (hasNext()) {
            switch (next()) {
                case ' ':
                case '\t':
                case '\n':
                case '\r':
                    break;
                case '{':
                case '}':
                case '[':
                case ']':
                case ',':
                case '"':
                    back();

                    return next();
                default:
                    return NULL;
            }
        }

        return NULL;
    }

    /**
     * Go to the next string delimiter in the reader. If no more string delimiters can be found
     * a null character will be returned.
     * <p/>
     * If a character other than a "whitespace" character or string delimiter is encountered a null
     * character will be returned.
     *
     * @return The next string delimiter.
     */
    public char nextStringDelimiter() {
        while (hasNext()) {
            switch (next()) {
                case '"':
                    back();

                    return next();
                case '\r':
                case '\n':
                case '\t':
                case ' ':
                    break;
                default:
                    return NULL;
            }
        }

        return NULL;
    }

    /**
     * Go to the next key/value delimiter (:) in the reader. If no key/value delimiters can be found
     * a null character will be returned.
     * <p/>
     * If a character other than a "whitespace" character or key/value delimiter is encountered a null
     * character will be returned.
     *
     * @return The next key/value delimiter.
     */
    public char nextKeyValueDelimiter() {
        while (hasNext()) {
            switch (next()) {
                case ':':
                    back();

                    return next();
                case '\r':
                case '\n':
                case '\t':
                case ' ':
                    break;
                default:
                    return NULL;
            }
        }

        return NULL;
    }

    /**
     * Go to the next value delimiter in the reader.
     * <p/>
     * If a character other than a "whitespace" character or value delimiter is encountered a null character
     * will be returned.
     *
     * @return The next start to a value.
     */
    public char nextValueDelimiter() {
        while (hasNext()) {
            char nextChar = next();

            if (isDigit(nextChar)) {
                back();

                return next();
            }

            switch (nextChar) {
                case ' ':
                case '\r':
                case '\n':
                case '\t':
                    break;
                case 't':
                case 'f':
                case 'n':
                case '{':
                case '[':
                case '}':
                case ']':
                case '"':
                case '-':
                    back();

                    return next();
                default:
                    return NULL;
            }
        }

        return NULL;
    }

    /**
     * Looks behind to see if there were any value delimiters.
     *
     * @return `true` if a value delimiter has been found, else `false`.
     */
    public boolean hasValueBehind() {
        int currentPosition = this.getPosition();
        boolean foundValue = false;

        for (; ; ) {
            char previousChar = back();

            if (isHexDigit(previousChar)) {
                break;
            }

            switch (previousChar) {
                case ' ':
                    break;
                case ']':
                case '}':
                case '"':
                case 'e':
                case 'l':
                    foundValue = true;
                    break;
                case '{':
                case '[':
                    return false;
            }

            if (foundValue) {
                break;
            }
        }

        this.position = currentPosition;

        return true;
    }

    /**
     * A negation of the `hasValueBehind` method put here to increase readability.
     *
     * @return `false` if a value delimiter has been found, else `true`.
     */
    public boolean hasNoValueBehind() {
        return !hasValueBehind();
    }

    /**
     * Looks ahead to see if there are any value delimiters.
     *
     * @return `true` if a value delimiter has been found, else `false`.
     */
    public boolean hasValueAhead() {
        int currentPosition = this.getPosition();
        boolean foundValue = false;

        for (; ; ) {
            char nextChar = next();

            if (isDigit(nextChar)) {
                break;
            }

            switch (nextChar) {
                case ' ':
                    break;
                case '"':
                case '{':
                case '[':
                case 't':
                case 'f':
                case 'n':
                case '-':
                    foundValue = true;
                    break;
                case ']':
                case '}':
                    return false;
            }

            if (foundValue) {
                break;
            }
        }

        this.position = currentPosition;

        return true;
    }

    /**
     * A negation of the `hasValueAhead` method put here to increase readability.
     *
     * @return `false` if a value delimiter has been found, else `true`.
     */
    public boolean hasNoValueAhead() {
        return !hasValueAhead();
    }

    /**
     * This method is used to determine if the character in the current position is whitespace.
     *
     * @return `true` if the current character is whitespace, else `false`.
     */
    public boolean isCurrentWhitespace() {
        switch (current()) {
            case ' ':
            case '\n':
            case '\r':
            case '\t':
                return true;
            default:
                return false;
        }
    }

    /**
     * Override toString() method to ease in debugging.
     *
     * @return "Current Character='{current character}'; Next Character='{next character}'"
     */
    @Override
    public String toString() {
        String nextCharacter = position + 1 < this.length ? String.valueOf(this.text[position + 1]) : "EOF";
        String currentCharacter = position < 0 ? "BOF" : String.valueOf(this.text[position]);
        String currentPosition = String.valueOf(position);

        return "Current Position=" + currentPosition + "; Current Character='" + currentCharacter + "'; Next Character='" + nextCharacter + "'";
    }

    /**
     * Test whether or not a given char represents a base 10 numeral.
     *
     * @param c char to test.
     * @return `true` if the given char represents a numeral, else `false`.
     */
    public static boolean isDigit(char c) {
        switch (c) {
            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
                return true;
            default:
                return false;
        }
    }

    /**
     * Test whether or not a given char represents a base 16 numeral.
     *
     * @param c char to test.
     * @return `true` if the given char represents a base 16 numeral, else `false`.
     */
    public static boolean isHexDigit(char c) {
        boolean isDecimalDigit = isDigit(c);
        boolean isUpperHexDigit = false;

        if (!isDecimalDigit) {
            switch (c) {
                case 'a':
                case 'A':
                case 'b':
                case 'B':
                case 'c':
                case 'C':
                case 'd':
                case 'D':
                case 'e':
                case 'E':
                case 'f':
                case 'F':
                    isUpperHexDigit = true;
                    break;
                default:
                    isUpperHexDigit = false;
                    break;
            }
        }

        return isDecimalDigit || isUpperHexDigit;
    }
}