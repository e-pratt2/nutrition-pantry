package Search;

import java.util.*;

public class SearchResults<E> {
    private SortedSet<Result> results;

    public SearchResults() {
        results = new TreeSet<>();
    }

    public class Result implements Comparable<Result> {
        private E object;
        private float score;

        public Result(E object, float score) {
            this.object = object;
            this.score = score;
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

        @Override
        public String toString() {
            return object.toString() + ": " + score;
        }
    }

    public void pushResult(E item, float score) {
        results.add(new Result(item, score));
    }
    public void pushResult(Result r) {
        results.add(r);
    }

    public List<Result> getBestResults() {
        ArrayList<Result> bestResults = new ArrayList<>();
        return Arrays.asList((Result[])results.toArray());
    }

    public boolean isAmbiguous() {
        return false;
    }
}
