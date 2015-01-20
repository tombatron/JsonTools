package com.tombatron.jsontools;

public class Is {

    public static boolean json(String text) {
        return json(text.toCharArray());
    }

    public static boolean json(char[] textCharArray) {
        return json(JsonReader.create(textCharArray));
    }

    public static boolean json(JsonReader reader) {
        char firstChar = reader.next();

        reader.back();

        if (firstChar == '{') {
            return parseObject(reader);
        }

        if (firstChar == '[') {
            return parseArray(reader);
        }

        return false;
    }

    private static boolean parseObject(JsonReader reader) {
        if (reader.getPosition() > 0) {
            reader.back();
        }

        if (reader.next() == '{') {
            while (reader.hasNext()) {
                switch (reader.next()) {
                    case '}':
                        return true;
                    case ',':
                        if (reader.next() == '}') {
                            return false;
                        }

                        if (!parseString(reader)) {
                            return false;
                        }

                        break;
                    default:
                        if (!parseString(reader)) {
                            return false;
                        }
                }

                if (reader.next() != ':') {
                    return false;
                }

                if (!parseValue(reader)) {
                    return false;
                }
            }

            reader.back();

            if (reader.next() == '}') {
                return true;
            }
        }

        return false;
    }

    private static boolean parseValue(JsonReader reader) {
        switch (reader.next()) {
            case '"':
                return parseString(reader);
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
            case '-':
                return parseNumber(reader);
            case '[':
                return parseArray(reader);
            case '{':
                return parseObject(reader);
            case 't':
            case 'f':
            case 'n':
                return parseConstant(reader);
            default:
                return false;
        }
    }

    private static boolean parseString(JsonReader reader) {
        reader.back();

        if (reader.next() == '"') {
            while (reader.hasNext()) {
                switch (reader.next()) {
                    case '"':
                        return true;
                    case '\\':
                        switch (reader.next()) {
                            case 'b':
                            case 'f':
                            case 'n':
                            case 'r':
                            case 't':
                            case '\\':
                            case '/':
                            case '"':
                                break;
                            case 'u':
                                if (!isHexDigit(reader.next())) {
                                    return false;
                                }

                                if (!isHexDigit(reader.next())) {
                                    return false;
                                }

                                if (!isHexDigit(reader.next())) {
                                    return false;
                                }

                                if (!isHexDigit(reader.next())) {
                                    return false;
                                }

                                break;
                            default:
                                return false;
                        }

                        break;
                    case '/':
                    case '\b':
                    case '\f':
                    case '\r':
                    case '\t':
                    case '\n':
                        return false;
                    default:
                        break;
                }
            }
        }

        return false;
    }

    private static boolean parseNumber(JsonReader reader) {
        char previousChar;
        boolean parsingExponent = false;

        reader.back();

        while (reader.hasNext()) {
            switch (reader.next()) {
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
                    break;
                case 'e':
                case 'E':
                    reader.back(2);

                    if (!isDigit(reader.next())) {
                        return false;
                    }

                    reader.next();

                    parsingExponent = true;

                    break;
                case '+':
                    reader.back(2);

                    previousChar = reader.next();

                    if (!(previousChar == 'e' || previousChar == 'E')) {
                        return false;
                    }

                    reader.next();

                    if (!isDigit(reader.next())) {
                        return false;
                    }

                    reader.back();

                    break;
                case '-':
                    reader.back(2);

                    previousChar = reader.next();

                    boolean isPreviousExponent = (previousChar == 'e' || previousChar == 'E');
                    boolean isPreviousDelimiter = previousChar == ':';

                    if (!(isPreviousExponent || isPreviousDelimiter)) {
                        return false;
                    }

                    reader.next();

                    if (!isDigit(reader.next())) {
                        return false;
                    }

                    reader.back();

                    break;
                case '.':
                    if (parsingExponent) {
                        return false;
                    }

                    reader.back(2);

                    if (!isDigit(reader.next())) {
                        return false;
                    }

                    reader.next();

                    if (!isDigit(reader.next())) {
                        return false;
                    }

                    reader.back();

                    break;
                case '}':
                case ']':
                case ',':
                    return true;
                default:
                    return false;
            }
        }

        return false;
    }

    private static boolean parseArray(JsonReader reader) {
        reader.back();

        if (reader.next() != '[') {
            return false;
        }

        while (reader.hasNext()) {
            switch (reader.next()) {
                case ']':
                    return true;
                case ',':
                    break;
                default:
                    reader.back();

                    if (!parseValue(reader)) {
                        return false;
                    }

                    break;
            }

            if (reader.current() == ']') {
                reader.back();
            }
        }

        return false;
    }

    private static boolean parseConstant(JsonReader reader) {
        reader.back();

        while (reader.hasNext()) {
            switch (reader.next()) {
                case 't':
                    if (reader.next() != 'r') {
                        return false;
                    }

                    if (reader.next() != 'u') {
                        return false;
                    }

                    if (reader.next() != 'e') {
                        return false;
                    }

                    break;
                case 'f':
                    if (reader.next() != 'a') {
                        return false;
                    }

                    if (reader.next() != 'l') {
                        return false;
                    }

                    if (reader.next() != 's') {
                        return false;
                    }

                    if (reader.next() != 'e') {
                        return false;
                    }

                    break;
                case 'n':
                    if (reader.next() != 'u') {
                        return false;
                    }

                    if (reader.next() != 'l') {
                        return false;
                    }

                    if (reader.next() != 'l') {
                        return false;
                    }

                    break;
                case '}':
                    return true;
                default:
                    return false;
            }
        }

        return false;
    }

    private static boolean isDigit(char c) {
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

    private static boolean isHexDigit(char c) {
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
