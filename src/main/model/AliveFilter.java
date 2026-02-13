package model;

import java.util.ArrayList;

// A filter that filters out all dead characters
public class AliveFilter implements CharacterFilter {

    public AliveFilter() {
        
    }
    
    // EFFECTS: filters out dead characters from list of characters
    public ArrayList<Character> characterFilter(ArrayList<Character> characters) {
        ArrayList<Character> validCharacters = new ArrayList<Character>();
        for (Character c : characters) {
            if (!c.isDead()) {
                validCharacters.add(c);
            }
        }
        return validCharacters;
    }
}
