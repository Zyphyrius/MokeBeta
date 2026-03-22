package persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

import org.json.*;

import model.MokeGame;
import model.StatusEffect;
import model.Strengthen;
import model.Stun;
import ui.MokeGUI;
import model.Character;
import model.Immobilize;

// Referenced from the JsonSerialization Demo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

// Represents a reader that reads mokegame from json data in stored file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader that reads source
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads game from file and returns it
    //          throws exception if error when reading file
    public MokeGame read() throws IOException {
        String jsonData = readFile(source);
        JSONObject json = new JSONObject(jsonData);
        return parseMokeGame(json);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }
        return contentBuilder.toString();
    }

    // EFFECTS: returns parsed mokegame from json object
    private MokeGame parseMokeGame(JSONObject json) {
        JSONObject board = json.getJSONObject("gameboard");
        ArrayList<Character> allies = parseCharacters(json.getJSONArray("allies"));
        ArrayList<Character> enemies = parseCharacters(json.getJSONArray("enemies"));
        int columnLen = board.getInt("columnLen");
        int rowLen = board.getInt("rowLen");
        int turnIndex = json.getInt("turnIndex");
        MokeGame game = new MokeGame(turnIndex, allies, enemies, columnLen, rowLen);
        return game;
    }

    // EFFECTS: parses characters from json and returns list
    private ArrayList<Character> parseCharacters(JSONArray jsonArray) {
        ArrayList<Character> characters = new ArrayList<Character>();
        for (Object json : jsonArray) {
            JSONObject nextCharacter = (JSONObject) json;
            Character parsedCharacter = parseCharacter(nextCharacter);
            if (parsedCharacter != null) {
                characters.add(parsedCharacter);
            }
        }
        return characters;
    }

    // EFFECTS: parses a character from json and returns it
    //          find character's name in allAllies and allEnemies then return copy of it
    //          return null if cant find
    private Character parseCharacter(JSONObject json) {
        String name = json.getString("name");
        ArrayList<Character> allCharacters = new ArrayList<Character>(MokeGUI.getAllAllies());
        allCharacters.addAll(MokeGUI.getAllEnemies());
        for (Character c : allCharacters) {
            if (name.contains(c.getName())) {
                Character newChar = c.newCopy();
                newChar.setName(json.getString("name"));
                newChar.setHealth(json.getInt("hp"));
                newChar.setX(json.getInt("x"));
                newChar.setY(json.getInt("y"));
                newChar.setAttacksLeft(json.getInt("atksLeft"));
                newChar.setMovesLeft(json.getInt("movesLeft"));
                parseStatuses(json.getJSONArray("statuses"), newChar);
                return newChar;
            }
        }
        return null;
    }

    // EFFECTS: parses all status effects and adds it to given character
    private void parseStatuses(JSONArray jsonArray, Character c) {
        for (Object json : jsonArray) {
            JSONObject nextStatus = (JSONObject) json;
            StatusEffect parsedStatus = parseStatus(nextStatus);
            if (parsedStatus != null) {
                c.addStatus(parsedStatus);
            }
        }
    }

    // EFFECTS: parse a status effect and returns the right type of status
    //          return null if cant find
    private StatusEffect parseStatus(JSONObject json) {
        switch (json.getString("name")) {
            case "strengthen":
                return new Strengthen(json.getInt("duration"), json.getInt("attack"));
            case "immobilize":
                return new Immobilize(json.getInt("duration"));
            case "stun":
                return new Stun(json.getInt("duration"));
        }
        return null;
    }
}
