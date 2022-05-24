package Search;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class NGramPermuterTest {
    @Test
    void nGramPermuterTest() {
        NGramPermuter ng = new NGramPermuter("Hello, world!", 2);

        ArrayList<String> nGrams = new ArrayList<>();

        ng.forEachRemaining(nGrams::add);

        String[] expected = {
                "He",
                "el",
                "ll",
                "lo",
                "o,",
                ", ",
                " w",
                "wo",
                "or",
                "rl",
                "ld",
                "d!"
        };

        assertArrayEquals(expected, nGrams.toArray());
    }
}