package model;

import java.util.ArrayList;

// A filter type that doesn't filter out anything, used for parameter inputs when no filtering is needed
public class NoFilter implements CharacterFilter {

    public NoFilter() {
        
    }

    // EFFECTS: filters out nothing
    public ArrayList<Character> characterFilter(ArrayList<Character> characters) {
        return characters;
    }
}
