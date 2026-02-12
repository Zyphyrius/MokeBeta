package model;

import java.util.ArrayList;

public class AllyFilter implements CharacterFilter {
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
