package com.tombatron.jsontools;

import java.util.Arrays;

import static com.tombatron.jsontools.JsonReader.isDigit;
import static com.tombatron.jsontools.JsonReader.isHexDigit;
import static com.tombatron.jsontools.Constants.*;

public class Is {

    public static boolean json(String text) {
        return json(text.toCharArray());
    }

    public static boolean json(char[] textCharArray) {
        return json(JsonReader.create(textCharArray));
    }

    public static boolean json(JsonReader reader) {
        char firstDelimiter = reader.nextDelimiter();
        boolean result = false;

        reader.back();

        if (firstDelimiter == OBJECT_BEGIN) {
            result = parseObject(reader);
        }

        if (firstDelimiter == ARRAY_BEGIN) {
            result = parseArray(reader);
        }

        return result;
    }

    private static boolean parseObject(JsonReader reader) {
        reader.back();

        if (reader.nextDelimiter() == OBJECT_BEGIN) {
            while (reader.hasNext()) {
                switch (reader.nextDelimiter()) {
                    case OBJECT_END:
                        return true;
                    case ',':
                        if (reader.hasNoValueAhead()) {
                            return false;
                        }

                        reader.next();

                        if (!parseString(reader)) {
                            return false;
                        }

                        break;
                    default:
                        if (!parseString(reader)) {
                            return false;
                        }
                }

                if (reader.nextKeyValueDelimiter() != ':') {
                    return false;
                }

                if (!parseValue(reader)) {
                    return false;
                }
            }

            reader.back();

            if (reader.next() == OBJECT_END) {
                return true;
            }
        }

        return false;
    }

    private static boolean parseValue(JsonReader reader) {
        switch (reader.nextValueDelimiter()) {
            case STRING_DELIMITER:
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
            case ARRAY_BEGIN:
                return parseArray(reader);
            case ARRAY_END:
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

        if (reader.nextStringDelimiter() == STRING_DELIMITER) {
            while (reader.hasNext()) {
                switch (reader.next()) {
                    case STRING_DELIMITER:
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
                            case STRING_DELIMITER:
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
        boolean foundDigit = false;

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
                    foundDigit = true;

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
                    previousChar = reader.back();

                    boolean isPreviousExponent = (previousChar == 'e' || previousChar == 'E');
                    boolean isPreviousDelimiter;

                    switch(previousChar) {
                        case ':':
                        case ' ':
                        case ARRAY_BEGIN:
                        case ',':
                        case '\n':
                        case '\t':
                        case '\r':
                            isPreviousDelimiter = true;
                            break;
                        default:
                            isPreviousDelimiter = false;
                            break;
                    }

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
                case OBJECT_END:
                case ARRAY_END:
                case ',':
                    reader.back();

                    return true;
                case ' ':
                case '\n':
                case '\t':
                case '\r':
                    if (foundDigit) {
                        reader.back();

                        return true;
                    }

                    break;
                default:
                    return false;
            }
        }

        return false;
    }

    private static boolean parseArray(JsonReader reader) {
        reader.back();

        if (reader.next() != ARRAY_BEGIN) {
            return false;
        }

        while (reader.hasNext()) {
            switch (reader.next()) {
                case ARRAY_END:
                    return true;
                case ',':
                    if (reader.hasNoValueAhead() || reader.hasNoValueBehind()) {
                        return false;
                    }

                    break;
                default:
                    if (!reader.isCurrentWhitespace()) {
                        reader.back();

                        if (!parseValue(reader)) {
                            return false;
                        }
                    }

                    break;
            }
        }

        return false;
    }

    private static boolean parseConstant(JsonReader reader) {
        reader.back();

        while (reader.hasNext()) {
            switch (reader.next()) {
                case 't':
                    if(!Arrays.equals(reader.next(3), EXPECTED_NEXT_TRUE_CHARACTERS)) {
                        return false;
                    }

                    break;
                case 'f':
                    if(!Arrays.equals(reader.next(4), EXPECTED_NEXT_FALSE_CHARACTERS)) {
                        return false;
                    }

                    break;
                case 'n':
                    if(!Arrays.equals(reader.next(3), EXPECTED_NEXT_NULL_CHARACTERS)) {
                        return false;
                    }

                    break;
                case OBJECT_END:
                case ARRAY_END:
                case ',':
                    reader.back();

                    return true;
                default:
                    if (reader.isCurrentWhitespace()) {
                        break;
                    }

                    return false;
            }
        }

        return false;
    }
}