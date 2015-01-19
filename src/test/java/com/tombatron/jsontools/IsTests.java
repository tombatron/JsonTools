package com.tombatron.jsontools;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class IsTests {

    @Test
    public void emptyJsonObjectIsValid() {
        assertTrue(Is.json("{}"));
    }

    @Test
    public void nonTerminatedEmptyJsonIsInvalid() {
        assertFalse(Is.json("{"));
    }

    @Test
    public void malformedKeyIsInvalid() {
        assertFalse(Is.json("{value:1}"));
    }

    @Test
    public void simpleStringValueIsValid() {
        assertTrue(Is.json("{\"Key\":\"Value\"}"));
    }

    @Test
    public void simpleNumberValueIsValid() {
        assertTrue(Is.json("{\"testing\":1000}"));
    }

    @Test
    public void multipleSimpleStringValuesAreValid() {
        assertTrue(Is.json("{\"nameone\":\"one\",\"nametwo\":\"two\"}"));
    }

    @Test
    public void multipleSimpleNumberValuesAreValid() {
        assertTrue(Is.json("{\"testing\":1000,\"testing1\":1001}"));
    }

    @Test
    public void simpleArrayValueIsValid() {
        assertTrue(Is.json("{\"array\":[1,2,3,4]}"));
    }

    @Test
    public void unterminatedArrayValueIsInvalid() {
        assertFalse(Is.json("{\"array\":[1,2,3,4}"));
    }

    @Test
    public void trueValueIsValid() {
        assertTrue(Is.json("{\"isTrue\":true}"));
    }

    @Test
    public void trueValueIsCaseSensitive() {
        assertFalse(Is.json("{\"isTrue\":True}"));
    }

    @Test
    public void falseValueIsValid() {
        assertTrue(Is.json("{\"isFalse\":false}"));
    }

    @Test
    public void falseValueIsCaseSensitive() {
        assertFalse(Is.json("{\"isFalse\":False}"));
    }

    @Test
    public void nullValueIsValid() {
        assertTrue(Is.json("{\"isNull\":null}"));
    }

    @Test
    public void nullValueIsCaseSensitive() {
        assertFalse(Is.json("{\"isNull\":Null}"));
    }

    @Test
    public void simpleObjectValueIsValid() {
        assertTrue(Is.json("{\"objectValue\":{\"nested\":\"object\"}}"));
    }

    @Test
    public void negativeNumbersAreValid() {
        assertTrue(Is.json("{\"negativeNumber\":-100}"));
    }

    @Test
    public void dashIsInvalidWhenNotAtFromOfNumber() {
        assertFalse(Is.json("{\"bogusNumber\":1-00}"));
    }

    @Test
    public void exponentialNotationIsAValidNumber() {
        assertTrue(Is.json("{\"exponentialNotation\":100e+1}"));
        assertTrue(Is.json("{\"exponentialNotation\":100E+1}"));
        assertTrue(Is.json("{\"exponentialNotation\":100e-1}"));
        assertTrue(Is.json("{\"exponentialNotation\":100E-1}"));
    }

    @Test
    public void trailingPlusOrDashAfterExponentIsInvalid() {
        assertFalse(Is.json("{\"exponentialNotation\":100e+}"));
        assertFalse(Is.json("{\"exponentialNotation\":100E+}"));
        assertFalse(Is.json("{\"exponentialNotation\":100e-}"));
        assertFalse(Is.json("{\"exponentialNotation\":100E-}"));
    }

    @Test
    public void decimalNumbersAreValid() {
        assertTrue(Is.json("{\"decimalNumber\":0.10}"));
    }

    @Test
    public void decimalNumbersRequireLeadingNumber() {
        assertFalse(Is.json("{\"decimalNumber\":.10}"));
    }
}