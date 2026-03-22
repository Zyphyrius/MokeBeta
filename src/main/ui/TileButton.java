package ui;

import javax.swing.*;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;
import model.Tile;
import model.Character;

import java.util.ArrayList;
import java.awt.*;
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

    private Image characterImage;
    private JLabel nameLabel;

    // EFFECTS: creates a tile with a name and character image both empty
    //          and the x,y coordinate of the tile
    //          calls tileEvent when clicked
    public TileButton(int x, int y, MokeGUI mgui) {
        cordX = x;
        cordY = y;
        mokeGUI = mgui;
        setLayout(new BorderLayout());
        setBackground(Color.GRAY);
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        setOpaque(true);
        nameLabel = new JLabel();
        nameLabel.setForeground(Color.WHITE);
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
        } else if (targetable.contains(tileCharacter) && mokeGUI.inAttackView()) {
            setBackground(Color.RED);
        } else {
            setBackground(Color.GRAY);
        }
    }

    // EFFECTS: checks to see if the tile has a character and updates image
    //          and updates background
    public void updateTile() {
        Tile tile = mokeGUI.getGame().getGameboard().findTile(cordX, cordY);
        if (tile.getCharacter() != null) {
            characterImage = mokeGUI.getCharacterIcon(tile.getCharacter()).getImage();
            nameLabel.setText("<html>" + tile.getCharacter().getName() + "<br>"
                    + tile.getCharacter().getHealth() + "/" + tile.getCharacter().getMaxHealth() + "</hmtl>");
        } else {
            characterImage = null;
            nameLabel.setText("");
        }
        updateBackground();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (characterImage != null) {
            int width = getWidth();
            int height = getHeight();
            int sideMargin = (int) (width * 0.1);
            int topMargin = (int) (height * 0.3);
            g.drawImage(characterImage, sideMargin, topMargin, 
                    width - 2 * sideMargin, height - topMargin, null);
        }
    }
}
