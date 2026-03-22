package ui;

import javax.swing.*;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;

import java.awt.*;

import model.Character;

// Code referenced from CPSC 210 lecture lab
// https://github.students.cs.ubc.ca/CPSC210/C3-LectureLabStarter

// A GUI that has both a board, action box, and dialogue and interacts between them
@ExcludeFromJacocoGeneratedReport
public class GameGUI extends JPanel {
    private JLabel dialogueLabel;
    private JPanel bottomPanel;
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
        actionBox = new ActionBox(mgui);
        bottomPanel = new JPanel();
        setLayout(new BorderLayout());
        add(boardGUI, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.add(dialogueLabel, BorderLayout.NORTH);
        bottomPanel.add(actionBox, BorderLayout.CENTER);
        bottomPanel.setPreferredSize(new Dimension(0, (int) (mgui.getHeight() * 0.2)));
    }

    // EFFECTS: updates dialogue, then adds the given dialogue on top of previous one
    public void addDialogue(String text) {
        updateDialogue();
        String prevText = dialogueLabel.getText();
        dialogueLabel.setText("<html>" + text + "<br>" + prevText + "</html>");
    }

    // EFFECTS: updates board and updates dialogue depending on what panel
    public void updateGame() {
        updateDialogue();
        boardGUI.updateTiles();
    }

    // EFFECTS: sets action box to empty
    public void showEmpty() {
        actionBox.showEmpty();
    }

    // EFFECTS: shows main menu in action box
    public void showMainMenu() {
        actionBox.mainMenu();
    }

    // EFFECTS: updates dialogue according to current panel
    private void updateDialogue() {
        String currentPanel = actionBox.getCurrentPanel();
        Character current = mokeGUI.getCurrent();
        if (currentPanel.equals("MAIN") || currentPanel.equals("EMPTY")) {
            dialogueLabel.setText("It's " + current.getName() + "'s turn!");
        } else if (currentPanel.equals("ATTACK")) {
            dialogueLabel.setText(Integer.toString(current.getAttacksLeft()) + " attacks left");
        } else if (currentPanel.equals("MOVE")) {
            dialogueLabel.setText(Integer.toString(current.getMovesLeft()) + " moves left");
        }
    }
}
