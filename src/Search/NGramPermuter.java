package Search;

import java.util.Iterator;

/**
 * An iterator that enumerates all possible n-grams of a given string with the specified length n.
 * An n-gram is a substring of length `n`.
 * Helper class for FuzzySearch.java.
 */
public class NGramPermuter implements Iterable<String>, Iterator<String> {
    private String toPermute;
    private int currentIndex, nGramLength;

    /**
     * Build a new permuter.
     * @param toPermute the string who's n-grams to permute
     * @param nGramLength the length of the n-grams.
     */
    public NGramPermuter(String toPermute, int nGramLength) {
        this.toPermute = toPermute;
        this.currentIndex = 0;
        this.nGramLength = nGramLength;
    }

    /**
     * Create the iterator for this permutation. Inherited from Iterable.
     * @return an iterator that will step through all n-grams.
     */
    @Override
    public Iterator<String> iterator() {
        return this;
    }

    /**
     * Check whether there is more data to enumerate. Inherited from Iterator.
     * @return true if there is more data - ie., next() will return a valid string.
     */
    @Override
    public boolean hasNext() {
        return currentIndex < (toPermute.length() - (nGramLength-1));
    }

    /**
     * Calculate the number of permutations there are in the string.
     * @return the number of permutations, ie. the number of times next() may be called.
     */
    public int getPermutationCount() {
        return Math.max(toPermute.length() - (nGramLength-1), 0);
    }

    /**
     * Get the next n-gram permutation from the string and advance the iterator.
     * @return the next n-gram.
     */
    @Override
    public String next() {
        String toReturn = toPermute.substring(currentIndex, currentIndex + nGramLength);
        currentIndex++;
        return toReturn;
    }
}