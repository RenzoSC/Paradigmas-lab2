package namedEntities.heuristics;

import java.text.Normalizer;
import java.util.List;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import namedEntities.Heuristic;

public class CapitalizedWordHeuristic extends Heuristic {

    public CapitalizedWordHeuristic(String label){
        super(label);
    }

    public List<String> extract(String text) {
        List<String> candidates = new ArrayList<>();

        // Remove special characters
        text = text.replaceAll("[-+.^:,\"]", "");
        // Normalize and separate accents from words
        text = Normalizer.normalize(text, Normalizer.Form.NFD);
        // Remove accents
        text = text.replaceAll("\\p{M}", "");

        // Creates a pattern to match capitalized words or capitalized words separates by a space
        Pattern pattern = Pattern.compile("[A-Z][a-z]+(?:\\s[A-Z][a-z]+)*");
        // Creates a matcher to find the pattern in the text
        Matcher matcher = pattern.matcher(text);

        // Find all matches and add them to the candidates list
        while (matcher.find()) {
            candidates.add(matcher.group());
        }
        return candidates;
    }
}
