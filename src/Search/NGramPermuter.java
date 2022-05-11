package Search;

import java.util.Iterator;

public class NGramPermuter implements Iterable<String>, Iterator<String> {
    private String toPermute;
    private int currentIndex, nGramLength;

    public NGramPermuter(String toPermute, int nGramLength) {
        this.toPermute = toPermute;
        this.currentIndex = 0;
        this.nGramLength = nGramLength;
    }
    @Override
    public Iterator<String> iterator() {
        return this;
    }

    @Override
    public boolean hasNext() {
        return currentIndex < (toPermute.length() - (nGramLength-1));
    }

    public int getPermutationCount() {
        return Math.max(toPermute.length() - (nGramLength-1), 0);
    }

    @Override
    public String next() {
        String toReturn = toPermute.substring(currentIndex, currentIndex + nGramLength);
        currentIndex++;
        return toReturn;
    }
}