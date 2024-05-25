package namedEntities.heuristics;

import java.text.Normalizer;
import java.util.List;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//For Random Numbers
import java.util.Random;

import namedEntities.Heuristic;

public class RandomWordHeuristic extends Heuristic {

    public RandomWordHeuristic(String label){
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

        // Crea un Patron para Matchear Palabras que comienzan con mayuscula
        Pattern pattern = Pattern.compile("[A-Z][a-z]+(?:\\s[A-Z][a-z]+)*");
        // Creates a matcher to find the pattern in the text
        Matcher matcher = pattern.matcher(text);

        Random rand = new Random();
        // Find all matches and add them to the candidates list
        while (matcher.find()) {
            if(rand.nextInt(3) != 0) //Crea un Random entre 0-2 Habiendo %66 de ser Agregado
            {candidates.add(matcher.group());}
        }
        return candidates;
    }
}
