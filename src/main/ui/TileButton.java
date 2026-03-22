package ui;

import javax.swing.*;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;
import model.Tile;
import model.Character;

import java.util.ArrayList;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// Code referenced from CPSC 210 lecture lab
// https://github.students.cs.ubc.ca/CPSC210/C3-LectureLabStarter

// A tile that acts as a button
@ExcludeFromJacocoGeneratedReport
public class TileButton extends JPanel {
    private int cordX;
    private int cordY;
    private MokeGUI mokeGUI;
    private GameGUI gameGUI;

    private Image characterImage;
    private JLabel nameLabel;

    // EFFECTS: creates a tile with a name and character image both empty
    //          and the x,y coordinate of the tile
    //          calls tileEvent when clicked
    public TileButton(int x, int y, MokeGUI mgui, GameGUI ggui) {
        cordX = x;
        cordY = y;
        mokeGUI = mgui;
        gameGUI = ggui;
        setLayout(new BorderLayout());
        setBackground(Color.GRAY);
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        setOpaque(true);
        nameLabel = new JLabel();
        add(nameLabel, BorderLayout.NORTH);

        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                mokeGUI.tileEvent(x, y);
            }
        });
    }

    // EFFECTS: updates background to either:
    //          blue if its current character
    //          red if targetable character and is in attacking view
    //          grey otherwise
    public void updateBackground() {
        Character current = mokeGUI.getCurrent();
        Character tileCharacter = mokeGUI.getGame().getGameboard().findTile(cordX, cordY).getCharacter();
        ArrayList<Character> targetable = current.getInRange(current.getAttackFilter(), current.getRange());
        if (current == tileCharacter) {
            setBackground(Color.BLUE);
        } else if (targetable.contains(tileCharacter)) {
            setBackground(Color.RED);
        } else {
            setBackground(Color.GRAY);
        }
    }

    // EFFECTS: checks to see if the tile has a character and updates image
    //          and updates background
    public void update() {
        Tile tile = mokeGUI.getGame().getGameboard().findTile(cordX, cordY);
        if (tile.getCharacter() != null) {
            characterImage = mokeGUI.getCharacterIcon(tile.getCharacter()).getImage();
        } else {
            characterImage = null;
        }
        updateBackground();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (characterImage != null) {
            g.drawImage(characterImage, 0, 0, getWidth(), getHeight(), null);
        }
    }
}
