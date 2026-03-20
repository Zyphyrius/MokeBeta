package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Code referenced from CPSC 210 lecture labs
// https://github.students.cs.ubc.ca/CPSC210/C3-LectureLabStarter

// a panel that asks the player yes or no questions
public class YesNoSelectorGUI extends JPanel {
    private static final int VGAP = 50;
    private JLabel questionLabel;
    private JButton yesButton;
    private JButton noButton;
    private MokeGUI mokeGUI;
    
    // EFFECTS: creates a panel that asks questions and returns
    //          1 if yes, 0 if no
    public YesNoSelectorGUI(MokeGUI gui) {
        mokeGUI = gui;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(Box.createVerticalStrut(VGAP));
        questionLabel = new JLabel("");
        add(questionLabel);

        yesButton = new JButton("Yes");
        yesButton.setActionCommand("yes");
        yesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mokeGUI.handleYesNo(1);
            }
		});
        add(yesButton);
        noButton = new JButton("No");
        noButton.setActionCommand("no");
        noButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mokeGUI.handleYesNo(0);
            }
		});
        add(noButton);
        add(Box.createVerticalStrut(VGAP));
    }

    // updates question label to new question
    public void askQuestion(String question) {
        questionLabel.setText(question);
    }

}
