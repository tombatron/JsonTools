package com.tombatron.jsontools;

public class Constants {

    private Constants() {
    }

    public static final char NULL = '\u0000';
    public static final char STRING_DELIMITER = '"';

    public static final char[] EXPECTED_NEXT_TRUE_CHARACTERS = new char[]{'r', 'u', 'e'};
    public static final char[] EXPECTED_NEXT_FALSE_CHARACTERS = new char[]{'a', 'l', 's', 'e'};
    public static final char[] EXPECTED_NEXT_NULL_CHARACTERS = new char[]{'u', 'l', 'l'};
}
