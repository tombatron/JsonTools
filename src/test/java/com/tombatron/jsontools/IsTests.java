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

    @Test
    public void trailingDecimalPointIsInvalid() {
        assertFalse(Is.json("{\"decimalNumber\":10.}"));
    }

    @Test
    public void decimalPointInsideExpoNotationIsInvalid() {
        assertFalse(Is.json("{\"exponentialNotation\":100e-10.1}"));
        assertFalse(Is.json("{\"exponentialNotation\":100E-10.2}"));
        assertFalse(Is.json("{\"exponentialNotation\":100e-10.3}"));
        assertFalse(Is.json("{\"exponentialNotation\":100E-10.4}"));
    }

    @Test
    public void unescapedQuoteIsInvalid() {
        assertFalse(Is.json("{\"quote\":\"\"\"}"));
    }

    @Test
    public void escapedQuoteIsValid() {
        assertTrue(Is.json("{\"quote\":\"\\\"\"}"));
    }

    @Test
    public void unescapedReverseSolidusIsInvalid() {
        assertFalse(Is.json("{\"reverseSolidus\":\"\\\"}"));
    }

    @Test
    public void escapedReverseSolidusIsValid() {
        assertTrue(Is.json("{\"reverseSolidus\":\"\\\\\"}"));
    }

    @Test
    public void escapedSolidusIsValid() {
        assertTrue(Is.json("{\"solidus\":\"\\/\"}"));
    }

    @Test
    public void unescapedBackspaceIsInvalid() {
        assertFalse(Is.json("{\"backspace\":\"\b\"}"));
    }

    @Test
    public void escapedBackspaceIsValid() {
        assertTrue(Is.json("{\"backspace\":\"\\b\"}"));
    }

    @Test
    public void unescapedFormFeedIsInvalid() {
        assertFalse(Is.json("{\"formfeed\":\"\f\"}"));
    }

    @Test
    public void escapedFormFeedIsValid() {
        assertTrue(Is.json("{\"formfeed\":\"\\\\f\"}"));
    }

    @Test
    public void unescapedNewLineIsInvalid() {
        assertFalse(Is.json("{\"newLine\":\"\n\"}"));
    }

    @Test
    public void escapedNewLineIsValid() {
        assertTrue(Is.json("{\"newLine\":\"\\\\n\"}"));
    }

    @Test
    public void unescapedCarriageReturnIsInvalid() {
        assertFalse(Is.json("{\"carriageReturn\":\"\r\"}"));
    }

    @Test
    public void escapedCarriageReturnIsValid() {
        assertTrue(Is.json("{\"carriageReturn\":\"\\\\r\"}"));
    }

    @Test
    public void unescapedHorizontalTabIsInvalid() {
        assertFalse(Is.json("{\"horizontalTab\":\"\t\"}"));
    }

    @Test
    public void escapedHorizontalTabIsValid() {
        assertTrue(Is.json("{\"horizontalTab\":\"\\\\t\"}"));
    }

    @Test
    public void escapedUnicodeWithFourFollowingHexDigitsIsValid() {
        assertTrue(Is.json("{\"escapedUnicode\":\"\\uAaBb\"}"));
        assertTrue(Is.json("{\"escapedUnicode\":\"\\uCcDd\"}"));
        assertTrue(Is.json("{\"escapedUnicode\":\"\\uEeFf\"}"));
        assertTrue(Is.json("{\"escapedUnicode\":\"\\u1234\"}"));
        assertTrue(Is.json("{\"escapedUnicode\":\"\\u5678\"}"));
        assertTrue(Is.json("{\"escapedUnicode\":\"\\u90AB\"}"));
    }

    @Test
    public void escapedUnicodeWithoutFourFollowingHexDigitsIsInvalid() {
        assertFalse(Is.json("{\"escapedUnicode\":\"\\uAaAG\"}"));
        assertFalse(Is.json("{\"escapedUnicode\":\"\\uAaA A\"}"));
        assertFalse(Is.json("{\"escapedUnicode\":\"\\u A a A A\"}"));
        assertFalse(Is.json("{\"escapedUnicode\":\"\\uAaAP\"}"));
    }

    @Test
    public void emptyArrayIsValid() {
        assertTrue(Is.json("[]"));
    }

    @Test
    public void emptyArrayWithValueSeparatorIsInvalid() {
        assertFalse(Is.json("[,]"));
    }

    @Test
    public void arrayWithTrailingValueSeparatorIsInvalid() {
        assertFalse(Is.json("[1,]"));
    }

    @Test
    public void arrayWithLeadingValueSeperatorIsInvalid() {
        assertFalse(Is.json("[,1]"));
    }

    @Test
    public void canHaveArrayOfConstants() {
        assertTrue(Is.json("[true,false,null]"));
    }

    @Test
    public void whitespaceCharactersAreParseable() {
        assertTrue(Is.json("    \t\n\r{       \r\n\"hello\"      \n: \t3\n\t\r}"));
    }

    @Test
    public void testCase1IsValid() {
        assertTrue(Is.json("{\n" +
                                "    \"name\": \"Jack (\\\"Bee\\\") Nimble\", \n" +
                                "    \"format\": {\n" +
                                "        \"type\":       \"rect\", \n" +
                                "        \"width\":      1920, \n" +
                                "        \"height\":     1080, \n" +
                                "        \"interlace\":  false, \n" +
                                "        \"frame rate\": 24\n" +
                                "    }\n" +
                                "}"
                )
        );
    }

    @Test
    public void testCase2IsValid() {
        assertTrue(Is.json("[\"Sunday\", \"Monday\", \"Tuesday\", \"Wednesday\", \"Thursday\", \"Friday\", \"Saturday\"]"));
    }

    @Test
    public void testCase3IsValid() {
        assertTrue(Is.json("[\n" +
                "    [0, -1, 0],\n" +
                "    [1, 0, 0],\n" +
                "    [0, 0, 1]\n" +
                "]"));
    }

    @Test
    public void testCase4IsValid() {
        assertTrue(Is.json("{\n" +
                "    \"glossary\": {\n" +
                "        \"title\": \"example glossary\",\n" +
                "\t\t\"GlossDiv\": {\n" +
                "            \"title\": \"S\",\n" +
                "\t\t\t\"GlossList\": {\n" +
                "                \"GlossEntry\": {\n" +
                "                    \"ID\": \"SGML\",\n" +
                "\t\t\t\t\t\"SortAs\": \"SGML\",\n" +
                "\t\t\t\t\t\"GlossTerm\": \"Standard Generalized Markup Language\",\n" +
                "\t\t\t\t\t\"Acronym\": \"SGML\",\n" +
                "\t\t\t\t\t\"Abbrev\": \"ISO 8879:1986\",\n" +
                "\t\t\t\t\t\"GlossDef\": {\n" +
                "                        \"para\": \"A meta-markup language, used to create markup languages such as DocBook.\",\n" +
                "\t\t\t\t\t\t\"GlossSeeAlso\": [\"GML\", \"XML\"]\n" +
                "                    },\n" +
                "\t\t\t\t\t\"GlossSee\": \"markup\"\n" +
                "                }\n" +
                "            }\n" +
                "        }\n" +
                "    }\n" +
                "}"));
    }

    @Test
    public void testCase5IsValid() {
        assertTrue(Is.json("{\"menu\": {\n" +
                "  \"id\": \"file\",\n" +
                "  \"value\": \"File\",\n" +
                "  \"popup\": {\n" +
                "    \"menuitem\": [\n" +
                "      {\"value\": \"New\", \"onclick\": \"CreateNewDoc()\"},\n" +
                "      {\"value\": \"Open\", \"onclick\": \"OpenDoc()\"},\n" +
                "      {\"value\": \"Close\", \"onclick\": \"CloseDoc()\"}\n" +
                "    ]\n" +
                "  }\n" +
                "}}"));
    }

    @Test
    public void testCase6IsValid() {
        assertTrue(Is.json("{\"widget\": {\n" +
                "    \"debug\": \"on\",\n" +
                "    \"window\": {\n" +
                "        \"title\": \"Sample Konfabulator Widget\",\n" +
                "        \"name\": \"main_window\",\n" +
                "        \"width\": 500,\n" +
                "        \"height\": 500\n" +
                "    },\n" +
                "    \"image\": { \n" +
                "        \"src\": \"Images/Sun.png\",\n" +
                "        \"name\": \"sun1\",\n" +
                "        \"hOffset\": 250,\n" +
                "        \"vOffset\": 250,\n" +
                "        \"alignment\": \"center\"\n" +
                "    },\n" +
                "    \"text\": {\n" +
                "        \"data\": \"Click Here\",\n" +
                "        \"size\": 36,\n" +
                "        \"style\": \"bold\",\n" +
                "        \"name\": \"text1\",\n" +
                "        \"hOffset\": 250,\n" +
                "        \"vOffset\": 100,\n" +
                "        \"alignment\": \"center\",\n" +
                "        \"onMouseUp\": \"sun1.opacity = (sun1.opacity / 100) * 90;\"\n" +
                "    }\n" +
                "}}"));
    }

    @Test
    public void testCase7IsValid() {
        assertTrue(Is.json("{\"web-app\": {\n" +
                "  \"servlet\": [   \n" +
                "    {\n" +
                "      \"servlet-name\": \"cofaxCDS\",\n" +
                "      \"servlet-class\": \"org.cofax.cds.CDSServlet\",\n" +
                "      \"init-param\": {\n" +
                "        \"configGlossary:installationAt\": \"Philadelphia, PA\",\n" +
                "        \"configGlossary:adminEmail\": \"ksm@pobox.com\",\n" +
                "        \"configGlossary:poweredBy\": \"Cofax\",\n" +
                "        \"configGlossary:poweredByIcon\": \"/images/cofax.gif\",\n" +
                "        \"configGlossary:staticPath\": \"/content/static\",\n" +
                "        \"templateProcessorClass\": \"org.cofax.WysiwygTemplate\",\n" +
                "        \"templateLoaderClass\": \"org.cofax.FilesTemplateLoader\",\n" +
                "        \"templatePath\": \"templates\",\n" +
                "        \"templateOverridePath\": \"\",\n" +
                "        \"defaultListTemplate\": \"listTemplate.htm\",\n" +
                "        \"defaultFileTemplate\": \"articleTemplate.htm\",\n" +
                "        \"useJSP\": false,\n" +
                "        \"jspListTemplate\": \"listTemplate.jsp\",\n" +
                "        \"jspFileTemplate\": \"articleTemplate.jsp\",\n" +
                "        \"cachePackageTagsTrack\": 200,\n" +
                "        \"cachePackageTagsStore\": 200,\n" +
                "        \"cachePackageTagsRefresh\": 60,\n" +
                "        \"cacheTemplatesTrack\": 100,\n" +
                "        \"cacheTemplatesStore\": 50,\n" +
                "        \"cacheTemplatesRefresh\": 15,\n" +
                "        \"cachePagesTrack\": 200,\n" +
                "        \"cachePagesStore\": 100,\n" +
                "        \"cachePagesRefresh\": 10,\n" +
                "        \"cachePagesDirtyRead\": 10,\n" +
                "        \"searchEngineListTemplate\": \"forSearchEnginesList.htm\",\n" +
                "        \"searchEngineFileTemplate\": \"forSearchEngines.htm\",\n" +
                "        \"searchEngineRobotsDb\": \"WEB-INF/robots.db\",\n" +
                "        \"useDataStore\": true,\n" +
                "        \"dataStoreClass\": \"org.cofax.SqlDataStore\",\n" +
                "        \"redirectionClass\": \"org.cofax.SqlRedirection\",\n" +
                "        \"dataStoreName\": \"cofax\",\n" +
                "        \"dataStoreDriver\": \"com.microsoft.jdbc.sqlserver.SQLServerDriver\",\n" +
                "        \"dataStoreUrl\": \"jdbc:microsoft:sqlserver://LOCALHOST:1433;DatabaseName=goon\",\n" +
                "        \"dataStoreUser\": \"sa\",\n" +
                "        \"dataStorePassword\": \"dataStoreTestQuery\",\n" +
                "        \"dataStoreTestQuery\": \"SET NOCOUNT ON;select test='test';\",\n" +
                "        \"dataStoreLogFile\": \"/usr/local/tomcat/logs/datastore.log\",\n" +
                "        \"dataStoreInitConns\": 10,\n" +
                "        \"dataStoreMaxConns\": 100,\n" +
                "        \"dataStoreConnUsageLimit\": 100,\n" +
                "        \"dataStoreLogLevel\": \"debug\",\n" +
                "        \"maxUrlLength\": 500}},\n" +
                "    {\n" +
                "      \"servlet-name\": \"cofaxEmail\",\n" +
                "      \"servlet-class\": \"org.cofax.cds.EmailServlet\",\n" +
                "      \"init-param\": {\n" +
                "      \"mailHost\": \"mail1\",\n" +
                "      \"mailHostOverride\": \"mail2\"}},\n" +
                "    {\n" +
                "      \"servlet-name\": \"cofaxAdmin\",\n" +
                "      \"servlet-class\": \"org.cofax.cds.AdminServlet\"},\n" +
                " \n" +
                "    {\n" +
                "      \"servlet-name\": \"fileServlet\",\n" +
                "      \"servlet-class\": \"org.cofax.cds.FileServlet\"},\n" +
                "    {\n" +
                "      \"servlet-name\": \"cofaxTools\",\n" +
                "      \"servlet-class\": \"org.cofax.cms.CofaxToolsServlet\",\n" +
                "      \"init-param\": {\n" +
                "        \"templatePath\": \"toolstemplates/\",\n" +
                "        \"log\": 1,\n" +
                "        \"logLocation\": \"/usr/local/tomcat/logs/CofaxTools.log\",\n" +
                "        \"logMaxSize\": \"\",\n" +
                "        \"dataLog\": 1,\n" +
                "        \"dataLogLocation\": \"/usr/local/tomcat/logs/dataLog.log\",\n" +
                "        \"dataLogMaxSize\": \"\",\n" +
                "        \"removePageCache\": \"/content/admin/remove?cache=pages&id=\",\n" +
                "        \"removeTemplateCache\": \"/content/admin/remove?cache=templates&id=\",\n" +
                "        \"fileTransferFolder\": \"/usr/local/tomcat/webapps/content/fileTransferFolder\",\n" +
                "        \"lookInContext\": 1,\n" +
                "        \"adminGroupID\": 4,\n" +
                "        \"betaServer\": true}}],\n" +
                "  \"servlet-mapping\": {\n" +
                "    \"cofaxCDS\": \"/\",\n" +
                "    \"cofaxEmail\": \"/cofaxutil/aemail/*\",\n" +
                "    \"cofaxAdmin\": \"/admin/*\",\n" +
                "    \"fileServlet\": \"/static/*\",\n" +
                "    \"cofaxTools\": \"/tools/*\"},\n" +
                " \n" +
                "  \"taglib\": {\n" +
                "    \"taglib-uri\": \"cofax.tld\",\n" +
                "    \"taglib-location\": \"/WEB-INF/tlds/cofax.tld\"}}}"));
    }

    @Test
    public void testCase8IsValid() {
        assertTrue(Is.json("{\"menu\": {\n" +
                "    \"header\": \"SVG Viewer\",\n" +
                "    \"items\": [\n" +
                "        {\"id\": \"Open\"},\n" +
                "        {\"id\": \"OpenNew\", \"label\": \"Open New\"},\n" +
                "        null,\n" +
                "        {\"id\": \"ZoomIn\", \"label\": \"Zoom In\"},\n" +
                "        {\"id\": \"ZoomOut\", \"label\": \"Zoom Out\"},\n" +
                "        {\"id\": \"OriginalView\", \"label\": \"Original View\"},\n" +
                "        null,\n" +
                "        {\"id\": \"Quality\"},\n" +
                "        {\"id\": \"Pause\"},\n" +
                "        {\"id\": \"Mute\"},\n" +
                "        null,\n" +
                "        {\"id\": \"Find\", \"label\": \"Find...\"},\n" +
                "        {\"id\": \"FindAgain\", \"label\": \"Find Again\"},\n" +
                "        {\"id\": \"Copy\"},\n" +
                "        {\"id\": \"CopyAgain\", \"label\": \"Copy Again\"},\n" +
                "        {\"id\": \"CopySVG\", \"label\": \"Copy SVG\"},\n" +
                "        {\"id\": \"ViewSVG\", \"label\": \"View SVG\"},\n" +
                "        {\"id\": \"ViewSource\", \"label\": \"View Source\"},\n" +
                "        {\"id\": \"SaveAs\", \"label\": \"Save As\"},\n" +
                "        null,\n" +
                "        {\"id\": \"Help\"},\n" +
                "        {\"id\": \"About\", \"label\": \"About Adobe CVG Viewer...\"}\n" +
                "    ]\n" +
                "}}"));
    }
}