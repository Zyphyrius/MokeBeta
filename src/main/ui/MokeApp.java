package ui;

import model.MokeGame;
import model.MopedMarauder;
import model.Tile;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.AnAverageHailey;
import model.BarcelonaBeefBogger;
import model.Character;
import model.EnemyAI;
import model.FridgeWagonMotor;
import model.Gameboard;
import model.HotMould;
import model.LordFishbowl;
import model.MWBerserker;
import model.MWCultist;
import model.MWNukeRain;
import model.MWTrooper;

public class MokeApp {
    private Scanner input = new Scanner(System.in);
    private int command;
    private MokeGame game;
    private Character current;
    private EnemyAI enemyAI;
    private boolean newTurn;
    private static ArrayList<Character> allAllies = new ArrayList<>(List.of(new LordFishbowl(), new HotMould(),
        new FridgeWagonMotor(), new BarcelonaBeefBogger(), new MopedMarauder(), new AnAverageHailey()));
    private static ArrayList<Character> allEnemies = new ArrayList<>(List.of(new MWCultist(), new MWTrooper(), 
        new MWBerserker(), new MWNukeRain()));
    private String red = "\u001B[31m";
    private String blue = "\u001B[34m";
    private String resetColour = "\u001B[0m";

    // EFFECTS: runs the best game ever (moke) and handles all inputs
    public MokeApp() {
        runMoke();
    }

    // MODIFIES: this
    // EFFECTS: starts game and processes inputs while game isnt over
    private void runMoke() {
        init();
        while (!game.isGameOver()) {
            printBoard();
            System.out.println("\nTurn Order:");
            printList(charactersToNames(MokeGame.getTurnOrder()));
            try {
                Thread.sleep(800);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            if (newTurn) {
                newTurn = false;
                System.out.println("\nIt's " + current.getName() + "'s turn!\n");
                if (!current.getStatuses().isEmpty()) {
                    System.out.println(current.getName() + " is " + current.getStatuses());
                }
            }
            command = getTurnInput();
            handleTurn(command);
            game.checkDead();
        }
        gameOver();
    }

    // MODIFIES: this
    // EFFECTS: asks if tutorial needed then for character inputs and if AI is needed, 
    //          then initializes board, character names, and starts new turn
    private void init() {
        System.out.println("Welcome to MOKE!\n");
        askTutorial();
        inputCharacters();
        game.startBoard();
        enemyAINeeded();
        numberCharacters();
        current = game.getCurrentCharacter();
        newTurn = true;
    }

    // EFFECTS: return player's turn input or enemy ais input depending on the current character and if ai is wanted
    private int getTurnInput() {
        if (MokeGame.getEnemies().contains(current) & enemyAI.getNeededForGame()) {
            return enemyAI.determineAction(current);
        } else {
            System.out.println("\n\t1. Attack\n\t2. Move\n\t3. View\n\t4. End Turn\n\t5. Concede");
            return input.nextInt();
        }
    }

    // EFFECTS: return player's attack input or enemy ais input depending on the current character and if ai is wanted
    private int getAttackInput(ArrayList<Character> inRange) {
        if (MokeGame.getEnemies().contains(current) & enemyAI.getNeededForGame()) {
            return enemyAI.determineAttack(current);
        } else {
            System.out.println("\nYou have " + Integer.toString(current.getAttacksLeft())
                    + " attacks left\n\t0. Back");
            if (inRange.isEmpty()) {
                System.out.println("\tno one in range...\n");
            } else {
                printList(charactersToNames(inRange));
            }
            return input.nextInt();
        }
    }

    // EFFECTS: return player's move input or enemy ais input depending on the current character and if ai is wanted
    private int getMoveInput() {
        if (MokeGame.getEnemies().contains(current) & enemyAI.getNeededForGame()) {
            return enemyAI.determineMove(current);
        } else {
            System.out.println("You have " + Integer.toString(current.getMovesLeft()) 
                    + " moves left\n\t0. Back" + "\n\t1. Up\n\t2. Down\n\t3. Left\n\t4. Right");
            return input.nextInt();
        }
    }

    // EFFECTS: creates a new enemy ai and prompts the player if they want it or not
    private void enemyAINeeded() {
        System.out.println("Would you like to face COM or another player?\n\t1. COM\n\t2. PLAYER\n");
        command = input.nextInt();
        if (command == 1) {
            enemyAI = new EnemyAI(game, true);
            System.out.println("Going against COM!\n");
        } else if (command == 2) {
            enemyAI = new EnemyAI(game, false);
            System.out.println("Going against player!\n");
        } else {
            enemyAI = new EnemyAI(game, true);
            System.out.println("Invalid response, you fight COM");
        }
    }

    // MODIFIES: MokeGame
    // EFFECTS: handles the command and performs the turn action
    private void handleTurn(int command) {
        switch (command) {
            case 1:
                handleAttack();
                break;
            case 2:
                handleMove();
                break;
            case 3:
                handleView();
                break;
            case 4:
                handleEndTurn();
                break;
            case 5:
                game.setGameOver(false);
                break;
            default:
                System.out.println("Invalid input\n");
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: starts next turn and resets attacked, moved, and newTurn
    private void handleEndTurn() {
        System.out.println(current.getName() 
                + " ended turn\n____________________________________\n");
        game.nextTurn();
        current = game.getCurrentCharacter();
        enemyAI.setAttacked(false);
        enemyAI.setMoved(false);
        newTurn = true;
    }

    // EFFECTS: finds all attackers in range, then attacks inputted target. will go back if no more attacks
    private void handleAttack() {
        if (current.getAttacksLeft() > 0) {
            ArrayList<Character> inRange = current.getInRange(current.getAttackFilter(), current.getRange());
            int attackCommand = getAttackInput(inRange);
            if (attackCommand > 0 & attackCommand <= inRange.size()) {
                Character target = inRange.get(attackCommand - 1);
                int prevHP = target.getHealth();
                game.attackCharacter(target);
                System.out.println(target.getName() + " went from " + Integer.toString(prevHP) 
                        + " to " + Integer.toString(target.getHealth()) + " hp!\n");
            } else if (attackCommand != 0) {
                System.out.println("Invalid input\n");
            }
        } else {
            System.out.println("No more attacks left!\n");
        }
    }

    // EFFECTS: handles one instance of movement during a turn. will go back if no more moves
    private void handleMove() {
        if (current.getMovesLeft() > 0) {
            int moveCommand = getMoveInput();
            if (!game.moveCharacter(handleDirection(moveCommand)) & moveCommand != 0) {
                System.out.println("Invalid spot\n");
            }
        } else {
            System.out.println("No more moves left!\n");
        }
    }

    // EFFECTS: returns direction corresponding to player's input
    private String handleDirection(int moveCommand) {
        switch (moveCommand) {
            case 0:
                break;
            case 1:
                return "up";
            case 2:
                return "down";
            case 3:
                return "left";
            case 4:
                return "right";
            default:
                System.out.println("Invalid input\n");
                break;
        }
        return "";
    }

    // EFFECTS: shows all characters then views the one inputted
    private void handleView() {
        int viewCommand;
        System.out.println("Who do you want to view?\n\t0. Back");
        printList(charactersToNames(MokeGame.getTurnOrder()));
        viewCommand = input.nextInt();
        if (viewCommand > 0 & viewCommand <= MokeGame.getTurnOrder().size()) {
            view(MokeGame.getTurnOrder().get(viewCommand - 1));
        } else if (viewCommand != 0) {
            System.out.println("Invalid input\n");
        }
    }

    // EFFECTS: views a character and prints all relevant info on them
    private void view(Character c) {
        System.out.println(c.getName() + ":\nhp: " + c.getHealth() + "/" + c.getMaxHealth() + "\natk: " 
                + c.getAttack() + "\nrange: " + c.getRange() + "\nmoves: " + c.getMove() + "\nspd: " 
                + c.getSpeed() + "\nability: " + c.getAbility() + "\n");
    }

    // EFFECTS: gives a finishing prompt after the game is over
    private void gameOver() {
        if (game.didWin()) {
            System.out.println("ALLIES WON!!!");
        } else {
            System.out.println("allies lost...");
        }
        System.out.println("bye! hope you had fun!");
    }

    // MODIFIES: this
    // EFFECTS: asks player to input are an arbitrary number of allies and enemies into the game
    private void inputCharacters() {
        ArrayList<Character> allies;
        ArrayList<Character> enemies;
        allies = inputAllies();
        if (allies.isEmpty()) {
            System.out.println("You fool you didn't add a character! You get Lord Fishbowl\n");
            allies.add(new LordFishbowl());
        }
        enemies = inputEnemies();
        if (enemies.isEmpty()) {
            System.out.println("You fool you didn't add a character! You get Murky Water Cultist\n");
            enemies.add(new MWCultist());
        }
        game = new MokeGame(allies, enemies);
    }

    // MODIFIES: this
    // EFFECTS: asks player to input an arbitrary number of allies then returns the list created
    private ArrayList<Character> inputAllies() {
        boolean stillSelecting = true;
        ArrayList<Character> allies = new ArrayList<Character>();
        while (stillSelecting) {
            System.out.println("Please select your allies:\n\t0. Done with adding allies");
            printList(allAllies);
            command = input.nextInt();
            if (command <= allAllies.size() & command > 0) {
                allies.add(inputAlly(command));
            } else if (command == 0) {
                stillSelecting = false;
            } else {
                System.out.println("Invalid input\n");
            }
            System.out.println("\n" + charactersToNames(allies) + "\n");
        }
        return allies;
    }

    // REQUIRES: index <= allAllies.size()
    // EFFECTS: returns which ally should be added
    private Character inputAlly(int index) {
        String allyName = charactersToNames(allAllies).get(index - 1);
        switch (allyName) {
            case "Lord Fishbowl":
                return new LordFishbowl();
            case "Hot Mould":
                return new HotMould();
            case "Fridge Wagon Motor":
                return new FridgeWagonMotor();
            case "Barcelona Beef Bogger":
                return new BarcelonaBeefBogger();
            case "Moped Marauder":
                return new MopedMarauder();
            case "An Average Hailey":
                return new AnAverageHailey();
            default:
                return null;
        }
    }

    // MODIFIES: this
    // EFFECTS: asks player to input an arbitrary number of enemies then returns the list created
    private ArrayList<Character> inputEnemies() {
        boolean stillSelecting = true;
        ArrayList<Character> enemies = new ArrayList<Character>();
        while (stillSelecting) {
            System.out.println("Please select your enemies:\n\t0. Done with adding enemies");
            printList(allEnemies);
            command = input.nextInt();
            if (command <= allEnemies.size() & command > 0) {
                enemies.add(inputEnemy(command));
            } else if (command == 0) {
                stillSelecting = false;
            } else {
                System.out.println("Invalid input\n");
            }
            System.out.println("\n" + charactersToNames(enemies) + "\n");
        }
        return enemies;
    }

    // REQUIRES: index <= allEnemies.size()
    // EFFECTS: returns which enemy should be added
    private Character inputEnemy(int index) {
        String enemyName = charactersToNames(allEnemies).get(index - 1);
        switch (enemyName) {
            case "Murky Water Cultist":
                return new MWCultist();
            case "Murky Water Trooper":
                return new MWTrooper();
            case "Murky Water Berserker":
                return new MWBerserker();
            case "Murky Water Nuke Rain":
                return new MWNukeRain();
            default:
                return null;
        }
    }

    // MODIFIES: this
    // EFFECTs: asks player if they want a tutorial, if yes print a short explanation
    private void askTutorial() {
        System.out.println("\nIs this your first time playing?\n\t1. Yes tutorial please\n\t2. I'm a pro!\n");
        command = input.nextInt();
        if (command != 2) {
            System.out.println("MOKE is a turn based game where you as allies will face off " 
                    + "against the evil Murky Water Cult on a board with tiles.\n"
                    + "You win if all enemies are defeated.\n\n Each character has "
                    + "hp, atk, range, moves, speed, and a special ability."
                    + "\n\n\tHp is a characters health. Don't let it hit 0"
                    + "\n\n\tAtk is the amount of damage a character deals. A character can attack once per turn"
                    + "\n\n\tRange is how many tiles a character's attack can reach"
                    + "\n\n\tMoves is how many tiles a character can move each turn"
                    + "\n\n\tSpeed determines where the character lands on the turn order\n\n"
                    + "Each turn you can move and attack, then you end the turn when you are ready."
                    + "\nThat's about it. Good luck!\n");
        }
    }

    // EFFECTS: prints the given list with corresponding numbers
    private void printList(ArrayList<?> listToPrint) {
        for (Object s : listToPrint) {
            System.out.println('\t' + Integer.toString(listToPrint.indexOf(s) + 1) + ". " + s);
        }
    }

    // EFFECTS: turns a list of characters into a list of strings with their names
    private ArrayList<String> charactersToNames(ArrayList<Character> characters) {
        ArrayList<String> characterNames = new ArrayList<String>();
        for (Character c : characters) {
            characterNames.add(c.getName());
        }
        return characterNames;
    }

    // EFFECTS: prints out the board with numbers to indicate each character based off turn order and coloured
    private void printBoard() {
        Gameboard gb = game.getGameboard();
        String board = "";
        for (Tile t : gb.getTiles()) {
            if (t.getCharacter() != null) {
                if (MokeGame.getAllies().contains(t.getCharacter())) {
                    board += blue + Integer.toString(MokeGame.getTurnOrder().indexOf(t.getCharacter()) + 1) 
                                + " " + resetColour;
                } else {
                    board += red + Integer.toString(MokeGame.getTurnOrder().indexOf(t.getCharacter()) + 1) 
                                + " " + resetColour;
                }   
            } else {
                board += "x ";
            }
            if ((gb.getTiles().indexOf(t) + 1) % gb.getRowLength() == 0) {
                board += "\n";
            }
        }
        System.out.println(board);
    }

    // MODIFIES: character
    // EFFECTS: makes each character's name unique by adding a number at the end of their name
    //          and coloured based on if they are an ally or enemy
    private void numberCharacters() {
        ArrayList<String> nameHolder = new ArrayList<String>();
        
        for (Character c : MokeGame.getTurnOrder()) {
            nameHolder.add(c.getName());
            if (MokeGame.getAllies().contains(c)) {
                c.setName(blue + c.getName() + " "
                        + Integer.toString(countRepeatNames(c.getName(), nameHolder)) + resetColour);
            } else {
                c.setName(red + c.getName() + " " 
                        + Integer.toString(countRepeatNames(c.getName(), nameHolder)) + resetColour);
            }
            
        }
    }

    // EFFECTS: looks at a list and returns how many times the given string is found
    private int countRepeatNames(String name, ArrayList<String> names) {
        int count = 0;
        for (String currentName : names) {
            if (currentName == name) {
                count += 1;
            }
        }
        return count;
    }

}
