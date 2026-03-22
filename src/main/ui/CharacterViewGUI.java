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
    public CharacterViewGUI(MokeGUI mgui) {
        mokeGUI = mgui;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        characterPanel = new JPanel();
        characterPanel.setAlignmentX(LEFT_ALIGNMENT);
        characterPanel.setPreferredSize(new Dimension((int) (mokeGUI.getWidth() * 0.5),
                            (int) (mokeGUI.getHeight() * 0.5)));
        add(characterPanel);
        

        descriptionLabel = new JLabel("");
        descriptionLabel.setAlignmentX(LEFT_ALIGNMENT);
        add(descriptionLabel);

        doneButton = new JButton("Done");
        doneButton.setActionCommand("done");
        doneButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                doneViewing();
            }
        });
        doneButton.setAlignmentX(LEFT_ALIGNMENT);
        add(doneButton);
    }

    // EFFECTS: tells moke gui that it is finished viewing and returns to gameboard
    public void doneViewing() {
        mokeGUI.doneViewing();
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
                    + "<br>ability: " + c.getAbility() + "<br>";
        if (!c.getStatuses().isEmpty()) {
            output += "statuses: " + c.getStatuses();
        }
        output += "</html>";
        descriptionLabel.setText(output);

        removeExistingImage();
        characterImage = new JLabel(mokeGUI.getCharacterIcon(c));
        characterPanel.add(characterImage);
    }

    private void removeExistingImage() {
        if (characterImage != null) {
            characterPanel.remove(characterImage);
        }
    }
}
