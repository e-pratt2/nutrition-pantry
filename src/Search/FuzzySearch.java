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
        //Lowercase-ify both strings, split the query into tokens.
        String[] tokens = query.toLowerCase(Locale.US).split(" +");
        toTest = toTest.toLowerCase(Locale.US);

        //Keep track of the ngrams that match
        int totalPermutations = 0;
        int totalPassedPermutations = 0;

        //For each word in the query...
        for(String token : tokens) {
            //Permute the word with ngram length 2
            NGramPermuter sp = new NGramPermuter(token, 2);

            //Increment the total number of permutations according to the permuter
            totalPermutations += sp.getPermutationCount();

            //Permute!
            for(String substring : sp) {
                //If the ngram is contained in the test string, increase the score
                if(toTest.contains(substring))
                    totalPassedPermutations++;
            }
        }

        //Score is the ratio of found ngrams/(found ngrams + unfound ngrams)
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
        //Create a new results object, with the defined stringify function
        SearchResults<E> results = new SearchResults<>(stringGetter);

        //For each item in the collection,
        for(E item : items) {
            //Calculate the score
            float itemScore = scoreOf(query, stringGetter.apply(item));

            //If it matches at all, add it to the results.
            if(itemScore > 0.0)
                results.pushResult(item, itemScore);
        }

        //Return the results collection
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
        //Keep track of best score and object that achieved that score
        float bestScore = -1.0f;
        E bestItem = null;

        //For each item in the collection...
        for(E item : items) {
            //Calculate the score
            float itemScore = scoreOf(query, stringGetter.apply(item));

            //If it's better than the previous...
            if(itemScore > bestScore){
                if(itemScore == 1.0) //Perfect match! return it immediately
                    return item;

                //Update best to match
                bestScore = itemScore;
                bestItem = item;
            }
        }

        //Return the best.
        return bestItem;
    }
}