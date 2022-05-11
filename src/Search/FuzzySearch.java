package Search;

import java.util.Locale;
import java.util.function.Function;


public class FuzzySearch {
    public static float scoreOf(String query, String toTest) {
        String[] tokens = query.toLowerCase(Locale.US).split(" ");
        toTest = toTest.toLowerCase(Locale.US);

        int totalPermutations = 0;
        int totalPassedPermutations = 0;

        for(String token : tokens) {
            NGramPermuter sp = new NGramPermuter(token, 2);

            totalPermutations += sp.getPermutationCount();
            for(String substring : sp) {
                if(toTest.indexOf(substring) != -1)
                    totalPassedPermutations++;
            }
        }

        return totalPassedPermutations/(float)totalPermutations;
    }
    public <E> E findBestMatch(String query, Iterable<E> items, Function<E, String> stringGetter) {
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