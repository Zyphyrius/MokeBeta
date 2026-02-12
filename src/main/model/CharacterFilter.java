package model;

import java.util.ArrayList;

// An interface for filters when looking at list of characters
public interface CharacterFilter {
    // EFFECTS: filters through a list of characters to only ones that satisfy a condition
    ArrayList<Character> characterFilter(ArrayList<Character> characters);
}
