package ui;

import javax.swing.*;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.Character;

// Code referenced from CPSC 210 lecture lab
// https://github.students.cs.ubc.ca/CPSC210/C3-LectureLabStarter

// A panel that views characters and their descriptions
@ExcludeFromJacocoGeneratedReport
public class CharacterViewGUI extends JPanel {
    private JPanel characterPanel;
    private JLabel characterImage;
    private JLabel descriptionLabel;
    private JButton doneButton;
    protected MokeGUI mokeGUI;

    // EFFECTS: creates a viewer panel with a done button
    public CharacterViewGUI(MokeGUI gui) {
        mokeGUI = gui;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        characterPanel = new JPanel();
        add(characterPanel);

        descriptionLabel = new JLabel("");
        add(descriptionLabel);

        doneButton = new JButton("Done");
        doneButton.setActionCommand("done");
        doneButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                doneViewing();
            }
        });
        add(doneButton);
    }

    // EFFECTS: tells moke gui that it is finished viewing and returns to gameboard
    public void doneViewing() {
        
    }

    // EFFECTS: views character and their stats + description + statuses if any
    //          displays image for character
    public void view(Character c) {
        String output = "<html>" + c.getName()
                    + ":<br>hp: " + c.getHealth() + "/" + c.getMaxHealth() 
                    + "<br>atk: " + c.getAttack() 
                    + "<br>range: " + c.getRange() 
                    + "<br>moves: " + c.getMove() 
                    + "<br>spd: " + c.getSpeed() 
                    + "<br>ability: " + c.getAbility() + "<br><html>";
        if (!c.getStatuses().isEmpty()) {
            output = output + "statuses: " + c.getStatuses();
        }
        descriptionLabel.setText(output);

        // TODO
        remoeExistingImage();
    }

    private void remoeExistingImage() {
        if (characterImage != null) {
            characterPanel.remove(characterImage);
        }
    }
}
