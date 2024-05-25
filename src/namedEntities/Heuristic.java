package namedEntities;

import java.util.List;

public abstract class Heuristic {
    String label = null;
    public Heuristic(String label){
        this.label = label;
    }

    public abstract List<String> extract(String text);
}
