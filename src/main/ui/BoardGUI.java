package ui;

import javax.swing.*;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;

import java.awt.*;

// Code referenced from CPSC 210 lecture lab
// https://github.students.cs.ubc.ca/CPSC210/C3-LectureLabStarter

// A board that has an arbitrary number of scaled tiles
@ExcludeFromJacocoGeneratedReport
public class BoardGUI extends JPanel {
    private TileButton[][] tiles;

    // EFFECTS: creates a board with tiles equal to size^2
    //          adds the tiles in an array to make columns and rows
    public BoardGUI(int size, GameGUI ggui, MokeGUI mgui) {
        setLayout(new GridLayout(size, size));
        tiles = new TileButton[size][size];

        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {
                TileButton tileButton = new TileButton(row, column, mgui, ggui);
                tiles[row][column] = tileButton;
                add(tileButton);
            }
        }
    }

    // EFFECTS: updates all tiles' images
    public void update() {
        for (int row = 0; row < tiles.length; row++) {
            for (int column = 0; column < tiles[row].length; column++) {
                tiles[row][column].update();
            }
        }
    }
}
