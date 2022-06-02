package Search;

import java.util.*;
import java.util.function.Function;

public class SearchResults<E> {
    private final SortedSet<Result> results;
    private final Function<E, String> stringifier;

    public SearchResults(Function<E, String> stringifier) {
        results = new TreeSet<>();
        this.stringifier = stringifier;
    }

    public class Result implements Comparable<Result> {
        private final E object;
        private final float score;
        private final Function<E, String> stringifier;

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
            int numStars = Math.round(this.score * 5);
            return "[" + "*".repeat(numStars) + " ".repeat(5-numStars) + "]";
        }

        @Override
        public String toString() {
            return percentageString() + " " + stringifier.apply(object);
        }
    }

    public void pushResult(E item, float score) {
        results.add(new Result(item, score, stringifier));
    }

    public List<Result> getBestResults(int limit) {
        ArrayList<Result> bestResults = new ArrayList<>(results);

        int sublistStart = Math.max(bestResults.size() - limit, 0);

        List<Result> limitedResults = bestResults.subList(sublistStart, bestResults.size());

        Collections.reverse(limitedResults);

        return limitedResults;
    }

    public boolean isAmbiguous() {
        return false;
    }
}
