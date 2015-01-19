package com.tombatron.jsontools;

public class Is {

    public static boolean json(String text) {
        return json(text.toCharArray());
    }

    public static boolean json(char[] textCharArray) {
        return json(JsonReader.create(textCharArray));
    }

    public static boolean json(JsonReader reader) {
        return parseObject(reader);
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
                    default:
                        break;
                }
            }
        }

        return false;
    }

    private static boolean parseNumber(JsonReader reader) {
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
                case '-':
                    reader.back(2);

                    if (reader.next() != ':') {
                        return false;
                    }

                    reader.next();

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

        while(reader.hasNext()) {
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

}
