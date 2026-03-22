package ui;

import java.util.HashMap;
import java.util.Map;
import model.Character;

import javax.swing.ImageIcon;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;

// Code referenced from CPSC 210 lecture lab
// https://github.students.cs.ubc.ca/CPSC210/C3-LectureLabStarter

// A hashmap that holds all character icons and their corresponding characters
@ExcludeFromJacocoGeneratedReport
public class CharacterIconMap {
    Map<Character, ImageIcon> characterMap;

    // EFFECTS: creates a hashmap with all characters and loads all their images
    public CharacterIconMap() {
        characterMap = new HashMap<>();
        loadImages();
    }

    // EFFECTS: loads all images and puts them into the map
    @SuppressWarnings("methodlength")
    private void loadImages() {

    }

    // EFFECTS: returns icon for given character
    public ImageIcon getCharacterImage(Character c) {
        return characterMap.get(c);
    }
    
}
