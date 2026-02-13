package model;

import java.util.ArrayList;

// A filter that filters out all non-ally characters
public class AllyFilter implements CharacterFilter {

    public AllyFilter() {
        
    }

    // EFFECTS: filters out non-ally characters from list of characters
    public ArrayList<Character> characterFilter(ArrayList<Character> characters) {
        ArrayList<Character> validCharacters = new ArrayList<Character>();
        for (Character c : characters) {
            if (MokeGame.getAllies().contains(c)) {
                validCharacters.add(c);
            }
        }
        return validCharacters;
    }
}
