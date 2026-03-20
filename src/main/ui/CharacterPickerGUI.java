package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;

import model.Character;

// Code referenced from CPSC 210 lecture lab
// https://github.students.cs.ubc.ca/CPSC210/C3-LectureLabStarter

// A picker that allows the user to add allies and enemies
public class CharacterPickerGUI extends CharacterViewGUI {
    private JComboBox<String> characterCombo;
    private JButton addButton;
    private JLabel currentCharactersLabel;
    private int viewIndex;
    private ArrayList<Character> currentCharacters;

    // EFFECTS: creates a character picker with a dropdown box for all characters in list
    //          and an add button to add characters
    //          with no current characters and view index 0
    //          immediately view first character in list
    public CharacterPickerGUI(MokeGUI gui, ArrayList<Character> characters) {
        super(gui);
        viewIndex = 0;
        currentCharacters = new ArrayList<>();
        ArrayList<String> characterNames = mokeGUI.charactersToNames(characters);
        characterCombo = new JComboBox<>(characterNames.toArray(new String[characterNames.size()]));
        characterCombo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                viewIndex = characterCombo.getSelectedIndex();
                view(characters.get(viewIndex));
            }
        });
        add(characterCombo);
        addButton = new JButton("Add");
        addButton.setActionCommand("add");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                currentCharacters.add(characters.get(viewIndex));
                currentCharactersLabel.setText(mokeGUI.charactersToNames(currentCharacters).toString());
            }
        });
        add(addButton);
        currentCharactersLabel = new JLabel("Please input characters you want to have in your game");
        add(currentCharactersLabel);
        view(characters.get(0));
    }

    @Override
    // EFFECTS: tells moke gui that it is finished picking and send list
    //          will tell player to add more characters if list is null
    public void doneViewing() {
        if (currentCharacters.isEmpty()) {
            currentCharactersLabel.setText("Need at least 1 character!");
        } else {
            mokeGUI.pickerDone(currentCharacters);
        }
    }
}
