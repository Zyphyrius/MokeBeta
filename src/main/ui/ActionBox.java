package ui;

import javax.swing.*;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Code referenced from CPSC 210 lecture lab
// https://github.students.cs.ubc.ca/CPSC210/C3-LectureLabStarter

// A box that contains buttons for player input during game
@ExcludeFromJacocoGeneratedReport
public class ActionBox extends JPanel {
    private JButton attackButton;
    private JButton moveButton;
    private JButton endButton;
    private JButton concedeButton;
    private JButton saveButton;
    private JButton attackBackButton;
    private JButton moveBackButton;
    private JButton leftButton;
    private JButton rightButton;
    private JButton upButton;
    private JButton downButton;

    private JPanel mainPanel;
    private JPanel movePanel;

    private MokeGUI mokeGUI;
    private CardLayout actionLayout;
    private String currentPanel;

    // EFFECTS: creates an action box that can switch between
    //          start, attack, and move layouts
    public ActionBox(MokeGUI mgui) {
        mokeGUI = mgui;
        actionLayout = new CardLayout();
        setLayout(actionLayout);
        initButtons();
        mainPanel = new JPanel();
        mainPanel.setLayout(new FlowLayout());
        mainPanel.add(attackButton);
        mainPanel.add(moveButton);
        mainPanel.add(endButton);
        mainPanel.add(concedeButton);
        mainPanel.add(saveButton);
        add(mainPanel, "MAIN");
        add(attackBackButton, "ATTACK");
        movePanel = new JPanel();
        movePanel.setLayout(new BorderLayout());
        movePanel.add(upButton, BorderLayout.NORTH);
        movePanel.add(downButton, BorderLayout.SOUTH);
        movePanel.add(leftButton, BorderLayout.WEST);
        movePanel.add(rightButton, BorderLayout.EAST);
        movePanel.add(moveBackButton, BorderLayout.CENTER);
        add(movePanel, "MOVE");
        actionLayout.show(this, "MAIN");
        currentPanel = "MAIN";
    }

    // EFFECTS: initializes all buttons
    @SuppressWarnings("methodlength")
    private void initButtons() {
        attackButton = createButton("ATTACK", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                attackPressed();
                mokeGUI.setAttackView(true);
                mokeGUI.updateAll();
            }
        });
        moveButton = createButton("MOVE", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                movePressed();
            }
        });
        endButton = createButton("END", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mokeGUI.endTurn();
            }
        });
        concedeButton = createButton("CONCEDE", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mokeGUI.concede();
            }
        });
        saveButton = createButton("SAVE+QUIT", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mokeGUI.saveGame();
            }
        });
        attackBackButton = createButton("BACK", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                backPressed();
                mokeGUI.setAttackView(false);
                mokeGUI.updateAll();
            }
        });
        moveBackButton = createButton("BACK", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                backPressed();
            }
        });
        leftButton = createButton("LEFT", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mokeGUI.movementPressed("left");
            }
        });
        rightButton = createButton("RIGHT", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mokeGUI.movementPressed("right");
            }
        });
        upButton = createButton("UP", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mokeGUI.movementPressed("up");
            }
        });
        downButton = createButton("DOWN", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mokeGUI.movementPressed("down");
            }
        });
    }

    // EFFECTS: brings player back to main panel
    private void backPressed() {
        actionLayout.show(this, "MAIN");
        currentPanel = "MAIN";
    }

    // EFFECTS: brings player to move panel
    private void movePressed() {
        actionLayout.show(this, "MOVE");
        currentPanel = "MOVE";
    }

    // EFFECTS: brings player attack panel
    private void attackPressed() {
        actionLayout.show(this, "ATTACK");
        currentPanel = "ATTACK";
    }

    public String getCurrentPanel() {
        return currentPanel;
    }

    // EFFECTS: creates a button with given parameters and returns it
    private JButton createButton(String text, ActionListener listener) {
        JButton button = new JButton(text);
        button.setActionCommand(text);
        button.addActionListener(listener);
        return button;
    }
}
