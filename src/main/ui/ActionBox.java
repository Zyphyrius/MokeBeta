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
        movePanel.add(leftButton, BorderLayout.EAST);
        movePanel.add(rightButton, BorderLayout.WEST);
        movePanel.add(moveBackButton, BorderLayout.CENTER);
        add(movePanel, "MOVE");
        actionLayout.show(this, "MAIN");
    }

    // EFFECTS: initializes all buttons
    @SuppressWarnings("methodlength")
    private void initButtons() {
        attackButton = createButton("ATTACK", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                attackPressed();
                mokeGUI.setAttackView(true);
            }
        });
        moveButton = createButton("MOVE", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                movePressed();
            }
        });
        endButton = createButton("END", new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        });
        concedeButton = createButton("CONCEDE", new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        });
        saveButton = createButton("SAVE", new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        });
        attackBackButton = createButton("BACK", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                backPressed();
                mokeGUI.setAttackView(false);
            }
        });
        moveBackButton = createButton("BACK", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                backPressed();
            }
        });
        leftButton = createButton("LEFT", new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        });
        rightButton = createButton("RIGHT", new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        });
        upButton = createButton("UP", new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        });
        downButton = createButton("DOWN", new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    // EFFECTS: brings player back to main panel
    private void backPressed() {
        actionLayout.show(this, "MAIN");
    }

    // EFFECTS: brings player to move panel
    private void movePressed() {
        actionLayout.show(this, "MOVE");
    }

    // EFFECTS: brings player attack panel
    private void attackPressed() {
        actionLayout.show(this, "ATTACK");
    }

    // EFFECTS: creates a button with given parameters and returns it
    private JButton createButton(String text, ActionListener listener) {
        JButton button = new JButton(text);
        button.setActionCommand(text);
        button.addActionListener(listener);
        return button;
    }
}
