package Search;

import java.util.Locale;
import java.util.function.Function;


/**
 * A class providing interfaces for an implementation of a fuzzy-search algorithm.
 */
public class FuzzySearch {
    /**
     * Get a "closeness" score between a query string and another string.
     * Ranges from 0 (no match at all) to 1 (strongly matches).
     * Parameters are commutative (scoreOf(A,B) == scoreOf(B,A))
     * @param query the string to test to
     * @param toTest the string to test against
     * @return the strength of the match, from 0.0 to 1.0
     */
    public static float scoreOf(String query, String toTest) {
        String[] tokens = query.toLowerCase(Locale.US).split(" ");
        toTest = toTest.toLowerCase(Locale.US);

        int totalPermutations = 0;
        int totalPassedPermutations = 0;

        for(String token : tokens) {
            NGramPermuter sp = new NGramPermuter(token, 2);

            totalPermutations += sp.getPermutationCount();
            for(String substring : sp) {
                if(toTest.contains(substring))
                    totalPassedPermutations++;
            }
        }

        return totalPassedPermutations/(float)totalPermutations;
    }

    /**
     * Search an iterable collection, comparing to the user's query and collecting the best matches.
     * @param query the string to search for.
     * @param items the collection of items to search for matches within.
     * @param stringGetter a function mapping objects to their searchable string form. E::toString works here.
     * @param <E> the generic type of the objects within the collection.
     * @return a SearchResults object containing the best matches and their scores.
     */
    public static <E> SearchResults<E> search(String query, Iterable<E> items, Function<E, String> stringGetter) {
        SearchResults<E> results = new SearchResults<>(stringGetter);
        for(E item : items) {
            float itemScore = scoreOf(query, stringGetter.apply(item));

            if(itemScore > 0.0)
                results.pushResult(item, itemScore);
        }

        return results;
    }

    /**
     * Search an iterable collection, returning only the best matching object. Will exit early if a 100% (score of 1) match is found
     * @param query the string to search for.
     * @param items the collection of objects to search for matches within.
     * @param stringGetter a function to map objects to their searchable form. E::toString works here.
     * @param <E> the generic type of the objects within the collection.
     * @return the best match. If there are multiple objects with the same score, returns the earliest instance. Null for empty collection.
     */
    public static <E> E findBestMatch(String query, Iterable<E> items, Function<E, String> stringGetter) {
        float bestScore = -1.0f;
        E bestItem = null;
        for(E item : items) {
            float itemScore = scoreOf(query, stringGetter.apply(item));

            if(itemScore > bestScore){
                if(itemScore == 1.0) //Perfect match!
                    return item;
                bestScore = itemScore;
                bestItem = item;
            }
        }

        return bestItem;
    }
}