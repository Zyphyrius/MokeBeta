package ui;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import model.Character;

import javax.swing.ImageIcon;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;

// Code referenced from CPSC 210 lecture lab
// https://github.students.cs.ubc.ca/CPSC210/C3-LectureLabStarter

// A hashmap that holds all character icons and their corresponding names
@ExcludeFromJacocoGeneratedReport
public class CharacterIconMap {
    Map<String, ImageIcon> characterMap;

    // EFFECTS: creates a hashmap with all character names and loads all their images
    public CharacterIconMap() {
        characterMap = new HashMap<>();
        loadImages();
    }

    // MODIFIES: this
    // EFFECTS: loads all images and puts them into the map
    //@SuppressWarnings("methodlength") will likely need to enable as game gets bigger
    private void loadImages() {
        String sep = File.separator;
        characterMap.put("Lord Fishbowl", new ImageIcon(System.getProperty("user.dir") + sep
                + "images" + sep + "lordfishbowl.png"));
        characterMap.put("Hot Mould", new ImageIcon(System.getProperty("user.dir") + sep
                + "images" + sep + "hotmould.png"));
        characterMap.put("Fridge Wagon Motor", new ImageIcon(System.getProperty("user.dir") + sep
                + "images" + sep + "fridgewagonmotor.png"));
        characterMap.put("Barcelona Beef Bogger", new ImageIcon(System.getProperty("user.dir") + sep
                + "images" + sep + "barcelonabeefbogger.png"));
        characterMap.put("Moped Marauder", new ImageIcon(System.getProperty("user.dir") + sep
                + "images" + sep + "mopedmarauder.png"));
        characterMap.put("An Average Hailey", new ImageIcon(System.getProperty("user.dir") + sep
                + "images" + sep + "anaveragehailey.png"));
        characterMap.put("Murky Water Cultist", new ImageIcon(System.getProperty("user.dir") + sep
                + "images" + sep + "mwcultist.png"));
        characterMap.put("Murky Water Trooper", new ImageIcon(System.getProperty("user.dir") + sep
                + "images" + sep + "mwtrooper.png"));
        characterMap.put("Murky Water Berserker", new ImageIcon(System.getProperty("user.dir") + sep
                + "images" + sep + "mwberserker.png"));
        characterMap.put("Murky Water Nuke Rain", new ImageIcon(System.getProperty("user.dir") + sep
                + "images" + sep + "mwnukerain.jpg"));
    }

    // EFFECTS: returns icon for given character
    public ImageIcon getCharacterImage(Character c) {
        return characterMap.get(c.getName());
    }
    
}
