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

    @Test
    public void testCase9IsValid() {
        assertTrue(Is.json("[\n" +
                "  {\n" +
                "    \"_id\": \"54bee3bd7c3040bb22bb4261\",\n" +
                "    \"index\": 0,\n" +
                "    \"guid\": \"b64e47d0-0dea-4493-90e6-ae7bd702bd09\",\n" +
                "    \"isActive\": true,\n" +
                "    \"balance\": \"$1,137.13\",\n" +
                "    \"picture\": \"http://placehold.it/32x32\",\n" +
                "    \"age\": 27,\n" +
                "    \"eyeColor\": \"brown\",\n" +
                "    \"name\": \"Henson Cantu\",\n" +
                "    \"gender\": \"male\",\n" +
                "    \"company\": \"NEUROCELL\",\n" +
                "    \"email\": \"hensoncantu@neurocell.com\",\n" +
                "    \"phone\": \"+1 (933) 405-3656\",\n" +
                "    \"address\": \"575 Laurel Avenue, Jackpot, Delaware, 9869\",\n" +
                "    \"about\": \"Est culpa cillum ea aliqua eiusmod aliquip ut esse cupidatat aliqua in. Commodo ipsum non occaecat eu minim aliquip aliqua. Deserunt excepteur non amet sunt nisi eiusmod ea. Eiusmod esse Lorem nulla labore excepteur quis nisi nulla officia labore nostrud do dolor labore. Lorem ut incididunt elit magna adipisicing. Voluptate veniam enim sit non cupidatat aute laborum velit.\\r\\n\",\n" +
                "    \"registered\": \"2014-01-10T14:12:25 +05:00\",\n" +
                "    \"latitude\": 15.291407,\n" +
                "    \"longitude\": 78.959359,\n" +
                "    \"tags\": [\n" +
                "      \"fugiat\",\n" +
                "      \"adipisicing\",\n" +
                "      \"ad\",\n" +
                "      \"ad\",\n" +
                "      \"et\",\n" +
                "      \"excepteur\",\n" +
                "      \"ea\"\n" +
                "    ],\n" +
                "    \"friends\": [\n" +
                "      {\n" +
                "        \"id\": 0,\n" +
                "        \"name\": \"Mcguire Brooks\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 1,\n" +
                "        \"name\": \"Flores Williamson\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 2,\n" +
                "        \"name\": \"Hurley Travis\"\n" +
                "      }\n" +
                "    ],\n" +
                "    \"greeting\": \"Hello, Henson Cantu! You have 1 unread messages.\",\n" +
                "    \"favoriteFruit\": \"apple\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"_id\": \"54bee3bd2fe3853a41f3e7bf\",\n" +
                "    \"index\": 1,\n" +
                "    \"guid\": \"9c57678e-7fc4-4f09-9abc-0208c7ebbed5\",\n" +
                "    \"isActive\": false,\n" +
                "    \"balance\": \"$1,663.72\",\n" +
                "    \"picture\": \"http://placehold.it/32x32\",\n" +
                "    \"age\": 36,\n" +
                "    \"eyeColor\": \"brown\",\n" +
                "    \"name\": \"Allison Holcomb\",\n" +
                "    \"gender\": \"male\",\n" +
                "    \"company\": \"SENMAO\",\n" +
                "    \"email\": \"allisonholcomb@senmao.com\",\n" +
                "    \"phone\": \"+1 (978) 504-3518\",\n" +
                "    \"address\": \"400 Tillary Street, Elwood, American Samoa, 1647\",\n" +
                "    \"about\": \"Enim do qui exercitation officia ullamco. Aliqua nisi non velit do dolore cupidatat id adipisicing ipsum nisi consequat ex. Adipisicing laborum ipsum labore et aliqua.\\r\\n\",\n" +
                "    \"registered\": \"2014-10-10T02:33:51 +04:00\",\n" +
                "    \"latitude\": 7.860403,\n" +
                "    \"longitude\": -28.998814,\n" +
                "    \"tags\": [\n" +
                "      \"exercitation\",\n" +
                "      \"minim\",\n" +
                "      \"Lorem\",\n" +
                "      \"aute\",\n" +
                "      \"sint\",\n" +
                "      \"pariatur\",\n" +
                "      \"nostrud\"\n" +
                "    ],\n" +
                "    \"friends\": [\n" +
                "      {\n" +
                "        \"id\": 0,\n" +
                "        \"name\": \"Joyce Jackson\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 1,\n" +
                "        \"name\": \"Chandler Mayer\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 2,\n" +
                "        \"name\": \"Noel Lott\"\n" +
                "      }\n" +
                "    ],\n" +
                "    \"greeting\": \"Hello, Allison Holcomb! You have 3 unread messages.\",\n" +
                "    \"favoriteFruit\": \"apple\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"_id\": \"54bee3bdf11f6c37f30ee709\",\n" +
                "    \"index\": 2,\n" +
                "    \"guid\": \"f23d950a-7605-4aa0-82ba-926fb9ef983c\",\n" +
                "    \"isActive\": true,\n" +
                "    \"balance\": \"$3,175.09\",\n" +
                "    \"picture\": \"http://placehold.it/32x32\",\n" +
                "    \"age\": 30,\n" +
                "    \"eyeColor\": \"green\",\n" +
                "    \"name\": \"Elsie Powers\",\n" +
                "    \"gender\": \"female\",\n" +
                "    \"company\": \"AUTOMON\",\n" +
                "    \"email\": \"elsiepowers@automon.com\",\n" +
                "    \"phone\": \"+1 (997) 428-2134\",\n" +
                "    \"address\": \"834 Bartlett Street, Weedville, Kansas, 7224\",\n" +
                "    \"about\": \"Quis voluptate tempor nulla adipisicing qui enim. Nulla consectetur nisi qui labore minim exercitation eiusmod non. Officia aliqua consequat in culpa cupidatat fugiat amet ipsum. Et elit quis esse labore do anim velit. Deserunt incididunt in adipisicing fugiat ullamco cillum laboris consequat voluptate cupidatat commodo eu minim quis. Commodo nostrud est eu ullamco minim cillum cupidatat aliquip.\\r\\n\",\n" +
                "    \"registered\": \"2014-03-06T16:10:19 +05:00\",\n" +
                "    \"latitude\": -42.893993,\n" +
                "    \"longitude\": 135.33561,\n" +
                "    \"tags\": [\n" +
                "      \"velit\",\n" +
                "      \"adipisicing\",\n" +
                "      \"enim\",\n" +
                "      \"sunt\",\n" +
                "      \"ullamco\",\n" +
                "      \"nisi\",\n" +
                "      \"id\"\n" +
                "    ],\n" +
                "    \"friends\": [\n" +
                "      {\n" +
                "        \"id\": 0,\n" +
                "        \"name\": \"Hatfield Roy\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 1,\n" +
                "        \"name\": \"Ruiz Lane\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 2,\n" +
                "        \"name\": \"Bridget Foster\"\n" +
                "      }\n" +
                "    ],\n" +
                "    \"greeting\": \"Hello, Elsie Powers! You have 7 unread messages.\",\n" +
                "    \"favoriteFruit\": \"strawberry\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"_id\": \"54bee3bd2d0c8b741bce7e2e\",\n" +
                "    \"index\": 3,\n" +
                "    \"guid\": \"6a1587fe-6ad2-4d7f-9472-d51328e47790\",\n" +
                "    \"isActive\": true,\n" +
                "    \"balance\": \"$1,597.94\",\n" +
                "    \"picture\": \"http://placehold.it/32x32\",\n" +
                "    \"age\": 22,\n" +
                "    \"eyeColor\": \"green\",\n" +
                "    \"name\": \"Vinson Cooke\",\n" +
                "    \"gender\": \"male\",\n" +
                "    \"company\": \"FUTURITY\",\n" +
                "    \"email\": \"vinsoncooke@futurity.com\",\n" +
                "    \"phone\": \"+1 (955) 500-2414\",\n" +
                "    \"address\": \"550 Vanderbilt Street, Ballico, Marshall Islands, 7427\",\n" +
                "    \"about\": \"Commodo minim duis Lorem laborum. Nulla sint cupidatat ullamco anim duis adipisicing tempor ad ullamco ea voluptate ut veniam. Cillum aute ad qui pariatur est duis proident in voluptate non. Culpa exercitation irure tempor aliquip. Mollit sunt elit quis consectetur voluptate. Ipsum voluptate nulla magna veniam incididunt pariatur voluptate aliqua consectetur sint dolore.\\r\\n\",\n" +
                "    \"registered\": \"2014-06-12T03:14:58 +04:00\",\n" +
                "    \"latitude\": 61.962715,\n" +
                "    \"longitude\": 37.707542,\n" +
                "    \"tags\": [\n" +
                "      \"laboris\",\n" +
                "      \"magna\",\n" +
                "      \"labore\",\n" +
                "      \"exercitation\",\n" +
                "      \"duis\",\n" +
                "      \"dolore\",\n" +
                "      \"anim\"\n" +
                "    ],\n" +
                "    \"friends\": [\n" +
                "      {\n" +
                "        \"id\": 0,\n" +
                "        \"name\": \"Noelle Vargas\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 1,\n" +
                "        \"name\": \"Eaton Haney\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 2,\n" +
                "        \"name\": \"Beulah Richardson\"\n" +
                "      }\n" +
                "    ],\n" +
                "    \"greeting\": \"Hello, Vinson Cooke! You have 8 unread messages.\",\n" +
                "    \"favoriteFruit\": \"apple\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"_id\": \"54bee3bdcb9ecd81ea0651b7\",\n" +
                "    \"index\": 4,\n" +
                "    \"guid\": \"15290c08-ec74-4297-9e16-185b3eb4e668\",\n" +
                "    \"isActive\": true,\n" +
                "    \"balance\": \"$2,576.11\",\n" +
                "    \"picture\": \"http://placehold.it/32x32\",\n" +
                "    \"age\": 22,\n" +
                "    \"eyeColor\": \"blue\",\n" +
                "    \"name\": \"Cecelia Glover\",\n" +
                "    \"gender\": \"female\",\n" +
                "    \"company\": \"JAMNATION\",\n" +
                "    \"email\": \"ceceliaglover@jamnation.com\",\n" +
                "    \"phone\": \"+1 (812) 595-3750\",\n" +
                "    \"address\": \"482 Dictum Court, Edneyville, Indiana, 1696\",\n" +
                "    \"about\": \"Sint est incididunt deserunt amet. Sunt culpa aute Lorem ex occaecat amet incididunt deserunt. Fugiat occaecat adipisicing esse consectetur id nostrud mollit. Sit commodo eiusmod elit in incididunt dolor ex. Aliquip tempor officia aute est nulla velit esse consectetur labore exercitation. Tempor est fugiat occaecat sit consequat aliqua cupidatat. Eu nostrud non est velit labore aute eu veniam labore.\\r\\n\",\n" +
                "    \"registered\": \"2014-12-03T17:45:44 +05:00\",\n" +
                "    \"latitude\": -71.963632,\n" +
                "    \"longitude\": -179.483509,\n" +
                "    \"tags\": [\n" +
                "      \"do\",\n" +
                "      \"mollit\",\n" +
                "      \"esse\",\n" +
                "      \"veniam\",\n" +
                "      \"proident\",\n" +
                "      \"reprehenderit\",\n" +
                "      \"magna\"\n" +
                "    ],\n" +
                "    \"friends\": [\n" +
                "      {\n" +
                "        \"id\": 0,\n" +
                "        \"name\": \"Gordon Leon\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 1,\n" +
                "        \"name\": \"Nichole Jenkins\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 2,\n" +
                "        \"name\": \"Powell Hodge\"\n" +
                "      }\n" +
                "    ],\n" +
                "    \"greeting\": \"Hello, Cecelia Glover! You have 1 unread messages.\",\n" +
                "    \"favoriteFruit\": \"banana\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"_id\": \"54bee3bd81ea0244e17faa85\",\n" +
                "    \"index\": 5,\n" +
                "    \"guid\": \"5abf665c-4f20-40bf-838f-4756b8f46410\",\n" +
                "    \"isActive\": false,\n" +
                "    \"balance\": \"$2,448.89\",\n" +
                "    \"picture\": \"http://placehold.it/32x32\",\n" +
                "    \"age\": 23,\n" +
                "    \"eyeColor\": \"brown\",\n" +
                "    \"name\": \"Pearson Roberts\",\n" +
                "    \"gender\": \"male\",\n" +
                "    \"company\": \"MAGNAFONE\",\n" +
                "    \"email\": \"pearsonroberts@magnafone.com\",\n" +
                "    \"phone\": \"+1 (899) 487-3253\",\n" +
                "    \"address\": \"873 Dekoven Court, Whipholt, Texas, 4910\",\n" +
                "    \"about\": \"Consectetur aliqua nostrud culpa mollit et anim aute quis eu. Dolor duis ullamco quis nisi excepteur excepteur elit nulla velit. Cillum proident quis id sunt amet nostrud culpa esse. Qui voluptate proident sit deserunt do do dolor elit nisi officia ex fugiat ipsum.\\r\\n\",\n" +
                "    \"registered\": \"2015-01-03T22:48:18 +05:00\",\n" +
                "    \"latitude\": -32.339181,\n" +
                "    \"longitude\": 41.677629,\n" +
                "    \"tags\": [\n" +
                "      \"adipisicing\",\n" +
                "      \"aliquip\",\n" +
                "      \"labore\",\n" +
                "      \"proident\",\n" +
                "      \"non\",\n" +
                "      \"minim\",\n" +
                "      \"labore\"\n" +
                "    ],\n" +
                "    \"friends\": [\n" +
                "      {\n" +
                "        \"id\": 0,\n" +
                "        \"name\": \"Keller Cross\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 1,\n" +
                "        \"name\": \"Juliette Baker\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": 2,\n" +
                "        \"name\": \"Dawson Hansen\"\n" +
                "      }\n" +
                "    ],\n" +
                "    \"greeting\": \"Hello, Pearson Roberts! You have 5 unread messages.\",\n" +
                "    \"favoriteFruit\": \"strawberry\"\n" +
                "  }\n" +
                "]"));
    }
}