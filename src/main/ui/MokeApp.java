package ui;

import model.MokeGame;
import model.Tile;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.Character;
import model.Gameboard;
import model.HotMould;
import model.LordFishbowl;
import model.MWCultist;
import model.MWTrooper;

public class MokeApp {
    private Scanner input = new Scanner(System.in);
    private int command;
    private MokeGame game;
    private static ArrayList<String> allAllies = new ArrayList<String>(List.of("Lord Fishbowl", 
                "Hot Mould"));
    private static ArrayList<String> allEnemies = new ArrayList<String>(List.of("Murky Water Cultist", 
                "Murky Water Trooper"));
    
    // EFFECTS: runs the best game ever (moke) and handles all inputs
    public MokeApp() {
        runMoke();
    }

    // MODIFIES: this
    // EFFECTS: starts game and processes inputs while game isnt over
    private void runMoke() {
        inputCharacters();
        game.startBoard();
        numberCharacters();
        while (!game.isGameOver()) {
            printBoard();
            System.out.println("\nTurn Order:");
            printList(charactersToNames(MokeGame.getTurnOrder()));
            System.out.println("\nIt's " + game.getCurrentCharacter().getName()
                    + "'s turn!\n\t1. Attack\n\t2. Move\n\t3. View\n\t4. End Turn\n\t5. Concede");
            command = input.nextInt();
            handleTurn(command);
            game.checkDead();
        }
        gameOver();
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
                game.nextTurn();
                break;
            case 5:
                game.setGameOver(false);
                break;
            default:
                System.out.println("Invalid input\n");
                break;
        }
    }

    // EFFECTS: finds all attackers in range, then attacks inputted target. will go back if no more attacks
    private void handleAttack() {
        if (game.getCurrentCharacter().getAttacksLeft() > 0) {
            System.out.println("\nYou have " + Integer.toString(game.getCurrentCharacter().getAttacksLeft())
                    + " attacks left\n\t0. Back");
            ArrayList<Character> inRange = 
                    game.getCurrentCharacter().getInRange(game.getCurrentCharacter().getAttackFilter());
            if (inRange.isEmpty()) {
                System.out.println("\tno one in range...\n");
            } else {
                printList(charactersToNames(inRange));
            }
            int attackCommand = input.nextInt();
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
        if (game.getCurrentCharacter().getMovesLeft() > 0) {
            int moveCommand;
            System.out.println("You have " + Integer.toString(game.getCurrentCharacter().getMovesLeft()) 
                    + " moves left\n\t0. Back" + "\n\t1. Up\n\t2. Down\n\t3. Left\n\t4. Right");
            moveCommand = input.nextInt();
            if (!game.moveCharacter(handleDirection(moveCommand))) {
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
            System.out.println("YOU WON!!!");
        } else {
            System.out.println("you lost...");
        }
        System.out.println("bye! hope you had fun!");
    }

    // MODIFIES: this
    // EFFECTS: asks player to input are an arbitrary number of allies and enemies into the game
    private void inputCharacters() {
        ArrayList<Character> allies;
        ArrayList<Character> enemies;
        System.out.println("Welcome to MOKE!\n\n");
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
        }
        return allies;
    }

    // REQUIRES: index <= allAllies.size()
    // EFFECTS: returns which ally should be added
    private Character inputAlly(int index) {
        String allyName = allAllies.get(index - 1);
        switch (allyName) {
            case "Lord Fishbowl":
                return new LordFishbowl();
            case "Hot Mould":
                return new HotMould();
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
        }
        return enemies;
    }

    // REQUIRES: index <= allEnemies.size()
    // EFFECTS: returns which enemy should be added
    private Character inputEnemy(int index) {
        String enemyName = allEnemies.get(index - 1);
        switch (enemyName) {
            case "Murky Water Cultist":
                return new MWCultist();
            case "Murky Water Trooper":
                return new MWTrooper();
            default:
                return null;
        }
    }

    // EFFECTS: prints the given list with corresponding numbers
    private void printList(ArrayList<String> listToPrint) {
        for (String s : listToPrint) {
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

    // EFFECTS: prints out the board with numbers to indicate each character based off turn order
    private void printBoard() {
        Gameboard gb = game.getGameboard();
        String board = "";
        for (Tile t : gb.getTiles()) {
            if (t.getCharacter() != null) {
                board += Integer.toString(MokeGame.getTurnOrder().indexOf(t.getCharacter()) + 1) + " ";
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
    private void numberCharacters() {
        ArrayList<String> nameHolder = new ArrayList<String>();
        for (Character c : MokeGame.getTurnOrder()) {
            nameHolder.add(c.getName());
            c.setName(c.getName() + " " + Integer.toString(countRepeatNames(c.getName(), nameHolder)));
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
