package persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import model.MokeGame;

// Referenced from the JsonSerialization Demo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;

@ExcludeFromJacocoGeneratedReport
public class JsonReaderTest {
    JsonReader reader;
    MokeGame game;

    @Test
    void testReadInvalidFile() {
        reader = new JsonReader("./data/goodluckfindingme.json");
        try {
            game = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReadCharacterDontExist() {
        reader = new JsonReader("./data/testReaderFakeValues.json");
        try {
            game = reader.read();
            assertEquals(2, MokeGame.getTurnOrder().size());
            assertEquals(1, MokeGame.getTurnOrder().get(0).getStatuses().size());
        } catch (IOException e) {
            fail("Couldn't read file");
        }
    }

    @Test
    void testReadGameGeneral() {
        reader = new JsonReader("./data/testReaderGeneral.json");
        try {
            game = reader.read();
            assertEquals("Lord Fishbowl 1", game.getCurrentCharacter().getName());
            assertEquals(1, game.getCurrentCharacter().getMovesLeft());
            assertEquals(1, game.getCurrentCharacter().getAttacksLeft());
            assertEquals(3, MokeGame.getTurnOrder().size());
            assertEquals(150, MokeGame.getTurnOrder().get(0).getHealth());
            assertEquals("Fridge Wagon Motor 2", MokeGame.getTurnOrder().get(0).getName());
            assertEquals("Murky Water Berserker 2", MokeGame.getTurnOrder().get(1).getName());
            assertEquals(2, MokeGame.getTurnOrder().get(1).getStatuses().get(0).getDuration());
            assertEquals(1, MokeGame.getTurnOrder().get(1).getStatuses().get(1).getDuration());
            assertEquals(30, game.getGameboard().getTiles().size());
        } catch (IOException e) {
            fail("Couldn't read file");
        }
    }
}
