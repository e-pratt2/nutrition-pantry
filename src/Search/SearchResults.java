package Search;

import java.util.ArrayList;
import java.util.Collections;

public class SearchResults<E> {
    private ArrayList<Result> results;

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
    }

    public void pushResult(Result r) {
        int pos = Collections.binarySearch(results, r);
        results.add(pos, r);

    }

    public Result getBestResult() {

    }

    public boolean isAmbiguous() {

    }
}
