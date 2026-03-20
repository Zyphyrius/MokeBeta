package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.AnAverageHailey;
import model.BarcelonaBeefBogger;
import model.Character;
import model.EnemyAI;
import model.FridgeWagonMotor;
import model.HotMould;
import model.LordFishbowl;
import model.MWBerserker;
import model.MWCultist;
import model.MWNukeRain;
import model.MWTrooper;
import model.MokeGame;
import model.MopedMarauder;
import persistence.JsonReader;
import persistence.JsonWriter;

// Code referenced from CPSC 210 lecture labs
// https://github.students.cs.ubc.ca/CPSC210/C3-LectureLabStarter
// https://github.students.cs.ubc.ca/CPSC210/B02-SpaceInvadersBase

// The frame that holds all of moke's guis
public class MokeGUI extends JFrame {
    private static ArrayList<Character> allAllies = new ArrayList<>(List.of(new LordFishbowl(), new HotMould(),
        new FridgeWagonMotor(), new BarcelonaBeefBogger(), new MopedMarauder(), new AnAverageHailey()));
    private static ArrayList<Character> allEnemies = new ArrayList<>(List.of(new MWCultist(), new MWTrooper(), 
        new MWBerserker(), new MWNukeRain()));
    private static ArrayList<String> questions = new ArrayList<>(List.of(
            "<html>WELCOME TO MOKE!!!<br>Would you like to load a previous save file?<html>",
            "Would you like to face COM?"
    ));
    private int width;
    private int height;
    private CardLayout mokeLayout;
    private JPanel mainPanel;
    private CharacterViewGUI viewerPanel;
    private GameGUI gamePanel;
    private CharacterPickerGUI allyPickerPanel;
    private CharacterPickerGUI enemyPickerPanel;
    private YesNoSelectorGUI yesNoPanel;
    private JLabel textLabel;

    private MokeGame game;
    private Character current;
    private EnemyAI enemyAI;
    private boolean newTurn;
    private String currentQuestion;
    private ArrayList<Character> allies;
    private ArrayList<Character> enemies;

    private static final String JSON_DEST = "./data/mokeGameSavee.json";
    private boolean didSave;
    private JsonReader jsonReader = new JsonReader(JSON_DEST);
    private JsonWriter jsonWriter = new JsonWriter(JSON_DEST);

    // EFFECTS: creates a moke gui that holds a cardlayout of all panels
    //          then asks if want to load game
    public MokeGUI() {
        super("Moke");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Dimension scrn = Toolkit.getDefaultToolkit().getScreenSize();
        width = (int) (scrn.width * 0.8);
        height = (int) (scrn.height * 0.8);
        init();
        loadImages();
        this.setSize(width, height);
        centreOnScreen();
        mokeLayout.show(mainPanel, "YesNo");
        setVisible(true);
        askLoad();
    }

    // MODIFIES: this
    // EFFECTS: initializes all panels and adds them to mokeLayout
    private void init() {
        mokeLayout = new CardLayout();
        mainPanel = new JPanel(mokeLayout);
        yesNoPanel = new YesNoSelectorGUI(this);
        viewerPanel = new CharacterViewGUI(this);
        allyPickerPanel = new CharacterPickerGUI(this, allAllies);
        enemyPickerPanel = new CharacterPickerGUI(this, allEnemies);
        textLabel = new JLabel();

        mainPanel.add(yesNoPanel, "YesNo");
        mainPanel.add(viewerPanel, "View");
        mainPanel.add(allyPickerPanel, "AllyPicker");
        mainPanel.add(enemyPickerPanel, "EnemyPicker");
        mainPanel.add(textLabel, "Text");
        add(mainPanel);
    }

    // MODIFIES: this
    // EFFECTS: starts game and processes all inputs while game isnt over
    private void runMoke() {
        
    }

    // MODIFIES: this
    // EFFECTS: asks if want to load game
    private void askLoad() {
        currentQuestion = questions.get(0);
        yesNoPanel.askQuestion(currentQuestion);
        mokeLayout.show(mainPanel, "YesNo");
    }

    // MODIFIES: this
    // EFFECTS: asks if want to play against com
    private void askCom() {
        currentQuestion = questions.get(1);
        yesNoPanel.askQuestion(currentQuestion);
        mokeLayout.show(mainPanel, "YesNo");
    }

    

    // EFFECTS: handles yes no input from player
    //          with yes = 1, no = 0
    //          perform different next action depending on current question
    //          if load save: ask com if load success, input allies otherwise
    //          if ask com: set com to appropiate answer then start game
    public void handleYesNo(int input) {
        if (questions.indexOf(currentQuestion) == 0) {
            if (input == 1) {
                if (loadFile()) {
                    askCom();
                }
            }
            if (questions.indexOf(currentQuestion) == 0) {
                inputAllies();
            }
        } else if (questions.indexOf(currentQuestion) == 1) {
            boolean needed = false;
            if (input == 1) {
                needed = true;
            }
            enemyAI = new EnemyAI(game, needed);
            runMoke();
        }
    }

    // EFFECTS: asks player to input an arbitrary number of allies
    private void inputAllies() {
        mokeLayout.show(mainPanel, "AllyPicker");
    }

    // EFFECTS: asks player to input an arbitrary number of enemies
    private void inputEnemies() {
        mokeLayout.show(mainPanel, "EnemyPicker");
    }

    // MODIFIES: this
    // EFFECTS: sets allies to given list if its null then input enemies
    //          otherwise, sets enemies to given list and
    //          asks for com
    public void pickerDone(ArrayList<Character> characters) {
        if (allies == null) {
            allies = characters;
            inputEnemies();
        } else {
            enemies = characters;
            askCom();
        }
    }

    // EFFECTS: turns a list of characters into a list of strings with their names
    public ArrayList<String> charactersToNames(ArrayList<Character> characters) {
        ArrayList<String> characterNames = new ArrayList<String>();
        for (Character c : characters) {
            characterNames.add(c.getName());
        }
        return characterNames;
    }

    // EFFECTS: returns icon for given character
    public ImageIcon getCharacterIcon(Character c) {
        return null;
    }

    // Centres frame on desktop
	// modifies: this
	// effects:  location of frame is set so frame is centred on desktop
    private void centreOnScreen() {
        Dimension scrn = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((scrn.width - getWidth()) / 2, (scrn.height - getHeight()) / 2);
    }

    // EFFECTS: loads all images into image icons
    private void loadImages() {

    }

    public static ArrayList<Character> getAllAllies() {
        return allAllies;
    }

    public static ArrayList<Character> getAllEnemies() {
        return allEnemies;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    // Referenced from the JsonSerialization Demo
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

    // EFFECTS: saves game and closes
    private void saveGame() {
        try {
            jsonWriter.open();
            jsonWriter.write(game);
            jsonWriter.close();
            didSave = true;
        } catch (FileNotFoundException e1) {
            textLabel.setText("Failed to save!");
            mokeLayout.show(mainPanel, "Text");
        }
    }
    
    // EFFECTS: loads saved game and returns true if successful, false if not
    //          if failed, show text saying it failed
    private boolean loadFile() {
        try {
            game = jsonReader.read();
            return true;
        } catch (IOException e1) {
            textLabel.setText("Failed to load!");
            mokeLayout.show(mainPanel, "Text");
            return false;
        }
    }
}
