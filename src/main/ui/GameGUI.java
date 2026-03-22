package ui;

import javax.swing.*;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

// Code referenced from CPSC 210 lecture lab
// https://github.students.cs.ubc.ca/CPSC210/C3-LectureLabStarter

// A GUI that has both a board, action box, and dialogue and interacts between them
@ExcludeFromJacocoGeneratedReport
public class GameGUI extends JPanel {
    private JLabel dialogueLabel;
    private ActionBox actionBox;
    private BoardGUI boardGUI;
    private MokeGUI mokeGUI;

    // EFFECTS: creates a game panel with a empty dialogue,
    //          board with boardSize size,
    //          and an action box
    public GameGUI(MokeGUI mgui, int boardSize) {
        mokeGUI = mgui;
        boardGUI = new BoardGUI(boardSize, this, mgui);
        dialogueLabel = new JLabel();
    }
}
