package namedEntities;
import java.util.HashMap;
import java.util.Map;
import namedEntities.heuristics.*;

public class HeuristicMap {
    private static final Map<String, Heuristic> heuristicsMap = new HashMap<>();

    static {
        // here we can add more heuristics if we want
        heuristicsMap.put("CapitalizedWord", new CapitalizedWordHeuristic("CapitalizedWord"));
        heuristicsMap.put("SinArticulos", new SinArticulosHeuristic("SinArticulos"));
        heuristicsMap.put("RandomWord", new RandomWordHeuristic("RandomWord"));
    }

    public static Heuristic getHeuristic(String name){
        return heuristicsMap.get(name);
    }

    public static Boolean isHeuristic(String name){
        return heuristicsMap.containsKey(name);
    }
}
