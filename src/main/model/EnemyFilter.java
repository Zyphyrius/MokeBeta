package model;

import java.util.ArrayList;

// A filter that filters out all non-enemy characters
public class EnemyFilter implements CharacterFilter {

    public EnemyFilter() {
        
    }

    // EFFECTS: filters out non-enemy characters from list of characters
    public ArrayList<Character> characterFilter(ArrayList<Character> characters) {
        ArrayList<Character> validCharacters = new ArrayList<Character>();
        for (Character c : characters) {
            if (MokeGame.getEnemies().contains(c)) {
                validCharacters.add(c);
            }
        }
        return validCharacters;
    }
}
