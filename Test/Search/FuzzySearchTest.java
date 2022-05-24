package Search;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import java.util.stream.Collectors;

class FuzzySearchTest {
    @Test
    void scoreOf() {
        String testString = "The quick brown fox jumps over the lazy dog.";

        //No matches should return 0.0.
        assertEquals(0.0, FuzzySearch.scoreOf("Soup time", testString));

        //Partial match on "The", should have a small score
        assertTrue(FuzzySearch.scoreOf("The soup", testString) > 0.1);

        //Full match, 1.0
        assertEquals(1.0, FuzzySearch.scoreOf(testString, testString));

        //Should be case agnostic
        assertEquals(1.0, FuzzySearch.scoreOf(testString.toUpperCase(Locale.ROOT), testString));
    }

    @Test
    void search() {
        List<String> testStrings = Arrays.asList(
                "Test string 12",
                "Test string 25",
                "Other words",
                "Unrelated"
        );

        //Contains mostly "Test string 25", some "Test string 12", a little "Unrelated", and no "Other words"
        SearchResults<String> results = FuzzySearch.search("Tebtst stringnbunrel 252", testStrings, (String s) -> s);

        //Extract best results of the search as strings
        List<String> best = results.getBestResults(4)
                .stream()
                .map(
                    (res) -> res.getObject()
                ).collect(Collectors.toList());

        String[] expected = {
                "Test string 25",
                "Test string 12",
                "Unrelated"
        };

        assertArrayEquals(expected, best.toArray());
    }

    @Test
    void findBestMatch() {
        List<String> testStrings = Arrays.asList(
                "Test string 12",
                "Test string 25",
                "Other words",
                "Unrelated"
        );

        Function<String, String> passLambda = (String s) -> s;

        assertEquals("Test string 12", FuzzySearch.findBestMatch("Test 12", testStrings, passLambda));
        assertEquals("Test string 25", FuzzySearch.findBestMatch("TeST 25", testStrings, passLambda));
        assertEquals("Test string 25", FuzzySearch.findBestMatch("stRIng 25", testStrings, passLambda));

        assertEquals("Unrelated", FuzzySearch.findBestMatch("unrel 25", testStrings, passLambda));
        assertEquals("Other words", FuzzySearch.findBestMatch("wor", testStrings, passLambda));
    }
}