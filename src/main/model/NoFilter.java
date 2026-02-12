package model;

import java.util.ArrayList;

public class NoFilter implements CharacterFilter {
    // EFFECTS: filters out nothing, used for parameter inputs when no filtering is needed
    public ArrayList<Character> characterFilter(ArrayList<Character> characters) {
        return characters;
    }
}
