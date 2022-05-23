package Search;

import java.util.*;
import java.util.function.Function;

public class SearchResults<E> {
    private SortedSet<Result> results;
    private Function<E, String> stringifier;

    public SearchResults(Function<E, String> stringifier) {
        results = new TreeSet<>();
        this.stringifier = stringifier;
    }

    public class Result implements Comparable<Result> {
        private E object;
        private float score;
        private Function<E, String> stringifier;

        public Result(E object, float score, Function<E, String> stringifier) {
            this.object = object;
            this.score = score;
            this.stringifier = stringifier;
        }

        public E getObject() {
            return object;
        }

        public float getScore() {
            return score;
        }

        @Override
        public int compareTo(Result other) {
            return Float.compare(score, other.score);
        }

        private String percentageString() {
            return null;//int numStars = "*".repeat((int)(this.score * 5));
        }

        @Override
        public String toString() {
            return stringifier.apply(object) + ": " + score;
        }
    }

    public void pushResult(E item, float score) {
        results.add(new Result(item, score, stringifier));
    }
    public void pushResult(Result r) {
        results.add(r);
    }

    public List<Result> getBestResults(int limit) {
        ArrayList<Result> bestResults = new ArrayList<>();

        for(Result r : results)
            bestResults.add(r);

        int sublistStart = Math.max(bestResults.size() - limit, 0);

        List<Result> limitedResults = bestResults.subList(sublistStart, bestResults.size());

        Collections.reverse(limitedResults);

        return limitedResults;
    }

    public boolean isAmbiguous() {
        return false;
    }
}
