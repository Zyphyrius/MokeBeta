package persistence;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Character;
import model.FridgeWagonMotor;
import model.Immobilize;
import model.LordFishbowl;
import model.MWBerserker;
import model.MokeGame;
import model.Strengthen;
import model.Stun;

import static org.junit.jupiter.api.Assertions.*;

// Referenced from the JsonSerialization Demo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;

@ExcludeFromJacocoGeneratedReport
public class JsonWriterTest {
    JsonWriter writer;
    JsonReader reader;
    Character c1;
    Character c2;
    Character c3;
    MokeGame game;

    @BeforeEach
    void runBefore() {
        c1 = new MWBerserker();
        c1.setName("Murky Water Berserker 2");
        c2 = new LordFishbowl();
        c2.setName("Lord Fishbowl 1");
        c2.setX(4);
        c3 = new FridgeWagonMotor();
        c3.setName("Fridge Wagon Motor 2");
        c3.setX(2);
        c3.setY(3);
        game = new MokeGame(2, new ArrayList<Character>(List.of(c2, c3)), new ArrayList<Character>(List.of(c1)), 5, 5);
        c1.addStatus(new Stun(2));
        c1.addStatus(new Strengthen(1, 10));
        c1.addStatus(new Immobilize(5));
        c2.setMovesLeft(1);
        c2.setAttacksLeft(0);
        c3.hurt(20, c3);
    }

    @Test
    void testWriteInvalidFile() {
        try {
            writer = new JsonWriter("./data/\0idontexist.json");
            writer.open();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriteGameGeneral() {
        try {
            writer = new JsonWriter("./data/testWriterGeneral.json");
            writer.open();
            writer.write(game);
            writer.close();

            reader = new JsonReader("./data/testWriterGeneral.json");
            game = reader.read();
            assertEquals("Lord Fishbowl 1", game.getCurrentCharacter().getName());
            assertEquals(1, game.getCurrentCharacter().getMovesLeft());
            assertEquals(0, game.getCurrentCharacter().getAttacksLeft());
            assertEquals(3, MokeGame.getTurnOrder().size());
            assertEquals(180, MokeGame.getTurnOrder().get(0).getHealth());
            assertEquals(3, MokeGame.getTurnOrder().get(1).getStatuses().size());
            assertEquals(25, game.getGameboard().getTiles().size());
        } catch (IOException e) {
            fail("No exception expected");
        }
    }
}
