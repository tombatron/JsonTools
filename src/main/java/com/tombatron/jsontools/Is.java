package com.tombatron.jsontools;

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

                        if (reader.nextString() == null) {
                            return false;
                        }

                        break;

                    case STRING_DELIMITER:
                        reader.back();

                        if (reader.nextString() == null) {
                            return false;
                        }

                        break;

                    default:

                        return false;
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
                reader.back();

                return reader.nextString() != null;
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
                reader.back();

                return reader.nextNumber() != null;
            case ARRAY_BEGIN:
                return parseArray(reader);
            case OBJECT_BEGIN:
                return parseObject(reader);
            case 't':
            case 'f':
                reader.back();

                return reader.nextBoolean() != null;
            case 'n':
                reader.back();

                return reader.nextNull() == NULL;
            default:
                return false;
        }
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
}