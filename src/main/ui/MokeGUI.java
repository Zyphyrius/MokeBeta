package ui;

import javax.swing.*;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;

import java.awt.*;
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
import model.Tile;
import persistence.JsonReader;
import persistence.JsonWriter;

// Code referenced from CPSC 210 lecture labs
// https://github.students.cs.ubc.ca/CPSC210/C3-LectureLabStarter
// https://github.students.cs.ubc.ca/CPSC210/B02-SpaceInvadersBase

// The frame that holds all of moke's guis
@ExcludeFromJacocoGeneratedReport
public class MokeGUI extends JFrame {
    private static ArrayList<Character> allAllies = new ArrayList<>(List.of(new LordFishbowl(), new HotMould(),
        new FridgeWagonMotor(), new BarcelonaBeefBogger(), new MopedMarauder(), new AnAverageHailey()));
    private static ArrayList<Character> allEnemies = new ArrayList<>(List.of(new MWCultist(), new MWTrooper(), 
        new MWBerserker(), new MWNukeRain()));
    private static ArrayList<String> questions = new ArrayList<>(List.of(
            "<html>WELCOME TO MOKE!!!<br>Would you like to load a previous save file?</html>",
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
    private JLabel gameOverLabel;
    private CharacterIconMap charMap;

    private MokeGame game;
    private Character current;
    private EnemyAI enemyAI;
    private String currentQuestion;
    private ArrayList<Character> allies;
    private ArrayList<Character> enemies;
    private boolean inAttackView;

    private static final String JSON_DEST = "./data/mokeGameSave.json";
    private boolean didSave;
    private JsonReader jsonReader = new JsonReader(JSON_DEST);
    private JsonWriter jsonWriter = new JsonWriter(JSON_DEST);

    // EFFECTS: creates a moke gui that holds a cardlayout of all panels
    //          then asks if want to load game
    public MokeGUI() {
        super("Moke");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Dimension scrn = Toolkit.getDefaultToolkit().getScreenSize();
        width = (int) (scrn.width * 0.9);
        height = (int) (scrn.height * 0.9);
        if (width > height) {
            width = height;
        } else {
            height = width;
        }
        init();
        this.setSize(width, height);
        centreOnScreen();
        mokeLayout.show(mainPanel, "YesNo");
        setVisible(true);
        askLoad();
    }

    // MODIFIES: this
    // EFFECTS: initializes all panels and adds them to mokeLayout,
    //          besides gameGUI
    private void init() {
        charMap = new CharacterIconMap();
        mokeLayout = new CardLayout();
        mainPanel = new JPanel(mokeLayout);
        yesNoPanel = new YesNoSelectorGUI(this);
        viewerPanel = new CharacterViewGUI(this);
        allyPickerPanel = new CharacterPickerGUI(this, allAllies);
        enemyPickerPanel = new CharacterPickerGUI(this, allEnemies);
        gameOverLabel = new JLabel();

        mainPanel.add(yesNoPanel, "YesNo");
        mainPanel.add(viewerPanel, "View");
        mainPanel.add(allyPickerPanel, "AllyPicker");
        mainPanel.add(enemyPickerPanel, "EnemyPicker");
        mainPanel.add(gameOverLabel, "GameOver");
        add(mainPanel);
    }

    // MODIFIES: this
    // EFFECTS: starts game and sets up all variables
    private void runMoke() {
        inAttackView = false;
        didSave = false;
        current = game.getCurrentCharacter();
        gamePanel = new GameGUI(this, Math.max(MokeGame.getAllies().size(), MokeGame.getEnemies().size()) + 3);
        mainPanel.add(gamePanel, "Game");
        mokeLayout.show(mainPanel, "Game");
        newTurn();
    }

    // EFFECTS: determines if the next turn is a player controlled turn or not
    //          then starts correct turn
    private void newTurn() {
        if (MokeGame.getEnemies().contains(current) & enemyAI.getNeededForGame()) {
            startAiTurn();
            System.out.println("bruh");
        } else {
            updateAll();
        }
    }

    // EFFECTS: controls and displays the enemy ai during their turn
    private void startAiTurn() {
        gamePanel.showEmpty();
        enemyAI.setAttacked(false);
        enemyAI.setMoved(false);
        Timer timer = new Timer(800, e -> {
            {
                updateAll();
                int action = enemyAI.determineAction(current);
                if (action == 2) {
                    aiMovement();
                } else if (action == 1) {
                    aiAttack();
                } else if (action == 4) {
                    ((Timer) e.getSource()).stop();
                    endTurn();
                    gamePanel.showMainMenu();
                }
            }
        });
        timer.start();
    }

    // EFFECTS: performs enemy ai's movement
    private void aiMovement() {
        int action = enemyAI.determineMove(current);
        switch (action) {
            case 1:
                movementPressed("up");
                break;
            case 2:
                movementPressed("down");
                break;
            case 3:
                movementPressed("left");
                break;
            case 4:
                movementPressed("right");
                break;
            default:
                break;
        }
    }

    // EFFECTS: performs enemy ai's attack
    private void aiAttack() {
        ArrayList<Character> targets = current.getInRange(current.getAttackFilter(), current.getRange());
        int target = enemyAI.determineAttack(current) - 1;
        if (target != -1) {
            attack(current, targets.get(target));
        }
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
    //          if load save: ask com if load success, input allies otherwise.
    //                        if loading error, send message inside inputallies
    //          if ask com: set com to appropiate answer then start game
    public void handleYesNo(int input) {
        if (questions.indexOf(currentQuestion) == 0) {
            if (input == 1) {
                if (loadFile()) {
                    askCom();
                } else {
                    allyPickerPanel.setPickerText("<html>Failed to load file!<br>"
                                + "Please input characters you want to have in your game</html>");
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
    //          asks for com and makes game
    public void pickerDone(ArrayList<Character> characters) {
        if (allies == null) {
            allies = characters;
            inputEnemies();
        } else {
            enemies = characters;
            game = new MokeGame(allies, enemies);
            game.startBoard();
            askCom();
        }
    }

    // REQUIRES: x and y < gameboard width/height
    // EFFECTS: does the proper action when a tile at position x,y is pressed
    //          if tile has no character on it, do nothing
    //          if in attack view, attack the character on the tile
    //          if not in attack view, view the character
    public void tileEvent(int x, int y) {
        Tile tile = game.getGameboard().findTile(x, y);
        if (tile.getCharacter() != null) {
            if (inAttackView) {
                if (current.getAttacksLeft() > 0 
                        && current.getInRange(current.getAttackFilter(),
                             current.getRange()).contains(tile.getCharacter())) {
                    attack(current, tile.getCharacter());
                }
            } else {
                viewerPanel.view(tile.getCharacter());
                mokeLayout.show(mainPanel, "View");
            }
        }
    }

    // EFFECTS: performs attack, updates, then adds dialogue
    private void attack(Character attacker, Character defender) {
        attacker.attack(defender);
        updateAll();
        gamePanel.addDialogue(attacker.getName() + " attacked " + defender.getName() + "!");
    }

    // EFFECTS: returns back to gameboard after done viewing
    public void doneViewing() {
        mokeLayout.show(mainPanel, "Game");
    }

    // EFFECTS: updates the whole game and checks if game is over
    public void updateAll() {
        game.checkDead();
        if (game.isGameOver()) {
            gameOver();
        }
        gamePanel.updateGame();
    }

    // EFFECTS: brings player to game over screen
    //          prints message if didn't save and quit
    private void gameOver() {
        mokeLayout.show(mainPanel, "GameOver");
        if (didSave) {
            gameOverLabel.setText("Thanks for playing!");
        } else if (game.didWin()) {
            gameOverLabel.setText("YOU WIN!!!");
        } else {
            gameOverLabel.setText("you lose...");
        }
    }

    // EFFECTS: moves current character in given direction if able
    //          otherwise tell player can't move that way
    //          or out of moves
    //          update afterwards if moved
    public void movementPressed(String dir) {
        if (current.getMovesLeft() > 0) {
            if (game.moveCharacter(dir)) {
                updateAll();
            } else {
                gamePanel.addDialogue("Can't move there!");
            }
        } else {
            gamePanel.addDialogue("Out of moves!");
        }
    }

    // EFFECTS: makes it game over
    public void concede() {
        game.setGameOver(false);
        gameOver();
    }

    // EFFECTS: ends the turn and starts new turn
    public void endTurn() {
        game.nextTurn();
        current = game.getCurrentCharacter();
        newTurn();
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
        return charMap.getCharacterImage(c);
    }

    // Centres frame on desktop
	// modifies: this
	// effects:  location of frame is set so frame is centred on desktop
    private void centreOnScreen() {
        Dimension scrn = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((scrn.width - getWidth()) / 2, (scrn.height - getHeight()) / 2);
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

    public MokeGame getGame() {
        return game;
    }

    public Character getCurrent() {
        return current;
    }

    public boolean inAttackView() {
        return inAttackView;
    }

    public void setAttackView(boolean inView) {
        inAttackView = inView;
    }

    // Referenced from the JsonSerialization Demo
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

    // EFFECTS: saves game and closes
    public void saveGame() {
        try {
            jsonWriter.open();
            jsonWriter.write(game);
            jsonWriter.close();
            didSave = true;
            gameOver();
        } catch (FileNotFoundException e1) {
            didSave = false;
        }
    }
    
    // EFFECTS: loads saved game and returns true if successful, false if not
    private boolean loadFile() {
        try {
            game = jsonReader.read();
            return true;
        } catch (IOException e1) {
            return false;
        }
    }
}
