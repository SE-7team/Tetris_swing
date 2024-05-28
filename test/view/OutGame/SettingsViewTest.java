package view.OutGame;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import view.OutGame.SettingsView;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class SettingsViewTest {

    @Test
    void initTitle() {
        SettingsView view = new SettingsView();
        view.initTitle();

        JLabel titleLabel = view.getTitleLabel();
        assertNotNull(titleLabel);
        assertEquals("Options", titleLabel.getText());
        assertEquals(Font.BOLD, titleLabel.getFont().getStyle());
        assertEquals(20, titleLabel.getFont().getSize());
    }

    @Test
    void initLabel() {
        SettingsView view = new SettingsView();
        String[] options = {"Option 1", "Option 2", "Option 3"};
        view.initLabel(options);

        JLabel[] optionLabels = view.getOptionLabels();
        assertNotNull(optionLabels);
        assertEquals(3, optionLabels.length);
        for (int i = 0; i < options.length; i++) {
            assertEquals(options[i], optionLabels[i].getText());
        }
    }

    @Test
    void initResPanel() {
        SettingsView view = new SettingsView();
        String[] resolutions = {"1920x1080", "1280x720", "800x600"};
        String[] options = {"Option 1", "Option 2", "Option 3"};
        view.initLabel(options);
        view.initResPanel(resolutions);

        JButton[] resButtons = view.getResButtons();
        assertNotNull(resButtons);
        assertEquals(3, resButtons.length);
        for (int i = 0; i < resolutions.length; i++) {
            assertEquals(resolutions[i], resButtons[i].getText());
        }
    }

    @Test
    void initKeyMapPanel() {
        SettingsView view = new SettingsView();
        String[] options = {"Option 1", "Option 2", "Option 3"};
        view.initLabel(options);
        String[] keyString = {"Move Left", "Move Right", "Rotate", "Drop", "Hold"};
        HashMap<String, String> keyMap = new HashMap<>();
        keyMap.put("Move Left1P", "A");
        keyMap.put("Move Right1P", "D");
        keyMap.put("Rotate1P", "W");
        keyMap.put("Drop1P", "S");
        keyMap.put("Hold1P", "Space");

        view.initKeyMapPanel(keyString, keyMap);

        JButton[] keyButtons = view.getKeyButtons1P();
        JLabel[] keyLabels = view.getKeyLabels1P();

        assertNotNull(keyButtons);
        assertNotNull(keyLabels);
        assertEquals(5, keyButtons.length);
        assertEquals(5, keyLabels.length);

        for (int i = 0; i < keyString.length; i++) {
            assertEquals(keyString[i], keyButtons[i].getText());
            assertEquals(keyMap.get(keyString[i] + "1P"), keyLabels[i].getText());
        }
    }

    @Test
    void initKeyMapPanel2() {
        SettingsView view = new SettingsView();
        String[] options = {"Option 1", "Option 2", "Option 3"};
        view.initLabel(options);
        String[] keyString = {"Move Left", "Move Right", "Rotate", "Drop", "Hold"};
        HashMap<String, String> keyMap = new HashMap<>();
        keyMap.put("Move Left2P", "J");
        keyMap.put("Move Right2P", "L");
        keyMap.put("Rotate2P", "I");
        keyMap.put("Drop2P", "K");
        keyMap.put("Hold2P", "Enter");

        view.initKeyMapPanel2(keyString, keyMap);

        JButton[] keyButtons = view.getKeyButtons2P();
        JLabel[] keyLabels = view.getKeyLabels2P();

        assertNotNull(keyButtons);
        assertNotNull(keyLabels);
        assertEquals(5, keyButtons.length);
        assertEquals(5, keyLabels.length);

        for (int i = 0; i < keyString.length; i++) {
            assertEquals(keyString[i], keyButtons[i].getText());
            assertEquals(keyMap.get(keyString[i] + "2P"), keyLabels[i].getText());
        }
    }

    @Test
    void initCBlindPanel() {
        SettingsView view = new SettingsView();
        String[] options = {"Option 1", "Option 2", "Option 3", "Option 4"};
        view.initLabel(options);
        String[] patternString = {"Pattern 1", "Pattern 2", "Pattern 3"};
        view.initCBlindPanel(patternString);

        JButton[] blindButtons = view.getBlindButtons();
        assertNotNull(blindButtons);
        assertEquals(3, blindButtons.length);
        for (int i = 0; i < patternString.length; i++) {
            assertEquals(patternString[i], blindButtons[i].getText());
        }
    }

    @Test
    void initResetPanel() {
        SettingsView view = new SettingsView();
        String[] options = {"Option 1", "Option 2", "Option 3", "Option 4", "Option 5"};
        view.initLabel(options);
        String[] resetString = {"Reset 1", "Reset 2", "Reset 3"};
        view.initResetPanel(resetString);

        JButton[] resetButtons = view.getResetButtons();
        assertNotNull(resetButtons);
        assertEquals(3, resetButtons.length);
        for (int i = 0; i < resetString.length; i++) {
            assertEquals(resetString[i], resetButtons[i].getText());
        }
    }

    @Test
    void initDifficultyPanel() {
        SettingsView view = new SettingsView();
        String[] options = {"Option 1", "Option 2", "Option 3", "Option 4", "Option 5", "Option 6"};
        view.initLabel(options);
        String[] difficultyString = {"Easy", "Normal", "Hard"};
        view.initDifficultyPanel(difficultyString);

        JButton[] difficultyButtons = view.getDifficultyButtons();
        assertNotNull(difficultyButtons);
        assertEquals(3, difficultyButtons.length);
        for (int i = 0; i < difficultyString.length; i++) {
            assertEquals(difficultyString[i], difficultyButtons[i].getText());
        }
    }

    @Test
    public void testInitWindow() {
        SettingsView settingsView = new SettingsView();
        settingsView.initTitle();
        settingsView.initPanel();

        // Initialize necessary components before calling initWindow
        String[] optionString = {"Resolution", "Key Mapping 1P", "Key Mapping 2P", "Color Blind Mode", "Reset", "Difficulty"};
        settingsView.initLabel(optionString);
        settingsView.initResPanel(new String[]{"800x600", "1024x768", "1920x1080"});
        settingsView.initKeyMapPanel(new String[]{"Up", "Down", "Left", "Right"}, new HashMap<>());
        settingsView.initKeyMapPanel2(new String[]{"Up", "Down", "Left", "Right"}, new HashMap<>());
        settingsView.initCBlindPanel(new String[]{"Off", "Mode 1", "Mode 2"});
        settingsView.initResetPanel(new String[]{"Yes", "No"});
        settingsView.initDifficultyPanel(new String[]{"Easy", "Medium", "Hard"});

        // Call initWindow with sample resolution
        int resX = 800;
        int resY = 600;
        settingsView.initWindow(resX, resY);

        // Assertions to verify components are initialized and added correctly
        assertNotNull(settingsView.getTitleLabel(), "Title label should not be null");
        assertEquals("Option", settingsView.getTitle(), "Window title should be 'Option'");
        assertNotNull(settingsView.getOptionLabels(), "Option labels should not be null");
        assertEquals(6, settingsView.getOptionLabels().length, "There should be 6 option labels");

        assertNotNull(settingsView.getResButtons(), "Resolution buttons should not be null");
        assertEquals(3, settingsView.getResButtons().length, "There should be 3 resolution buttons");

        assertNotNull(settingsView.getKeyButtons1P(), "Key buttons for 1P should not be null");
        assertEquals(4, settingsView.getKeyButtons1P().length, "There should be 4 key buttons for 1P");

        assertNotNull(settingsView.getKeyLabels1P(), "Key labels for 1P should not be null");
        assertEquals(4, settingsView.getKeyLabels1P().length, "There should be 4 key labels for 1P");

        assertNotNull(settingsView.getKeyButtons2P(), "Key buttons for 2P should not be null");
        assertEquals(4, settingsView.getKeyButtons2P().length, "There should be 4 key buttons for 2P");

        assertNotNull(settingsView.getKeyLabels2P(), "Key labels for 2P should not be null");
        assertEquals(4, settingsView.getKeyLabels2P().length, "There should be 4 key labels for 2P");

        assertNotNull(settingsView.getBlindButtons(), "Color blind buttons should not be null");
        assertEquals(3, settingsView.getBlindButtons().length, "There should be 3 color blind buttons");

        assertNotNull(settingsView.getResetButtons(), "Reset buttons should not be null");
        assertEquals(2, settingsView.getResetButtons().length, "There should be 2 reset buttons");

        assertNotNull(settingsView.getDifficultyButtons(), "Difficulty buttons should not be null");
        assertEquals(3, settingsView.getDifficultyButtons().length, "There should be 3 difficulty buttons");

        // Check the visibility of main components
        assertTrue(settingsView.getTitleLabel().isVisible(), "Title label should be visible");
        assertTrue(settingsView.getOptionLabels()[0].isVisible(), "First option label should be visible");
    }

    @Test
    void testUpdateKeyMap() {
        SettingsView settingsView = new SettingsView();
        settingsView.initLabel(new String[]{"Resolution", "Key Mapping 1P", "Key Mapping 2P", "Color Blind Mode", "Reset Settings", "Difficulty"});
        settingsView.initResPanel(new String[]{"800x600", "1024x768", "1280x1024"});
        settingsView.initKeyMapPanel(new String[]{"UP", "DOWN", "LEFT", "RIGHT"}, new HashMap<String, String>() {{
            put("UP1P", "W");
            put("DOWN1P", "S");
            put("LEFT1P", "A");
            put("RIGHT1P", "D");
        }});
        settingsView.initKeyMapPanel2(new String[]{"UP", "DOWN", "LEFT", "RIGHT"}, new HashMap<String, String>() {{
            put("UP2P", "I");
            put("DOWN2P", "K");
            put("LEFT2P", "J");
            put("RIGHT2P", "L");
        }});

        String[] keyString = {"UP", "DOWN", "LEFT", "RIGHT"};
        HashMap<String, String> newKeyMap = new HashMap<>();
        newKeyMap.put("UP1P", "U");
        newKeyMap.put("DOWN1P", "D");
        newKeyMap.put("LEFT1P", "L");
        newKeyMap.put("RIGHT1P", "R");

        settingsView.updateKeyMap(keyString, newKeyMap, "1P");

        for (int i = 0; i < keyString.length; i++) {
            assertEquals(newKeyMap.get(keyString[i] + "1P"), settingsView.getKeyLabels1P()[i].getText());
        }

        newKeyMap.clear();
        newKeyMap.put("UP2P", "W");
        newKeyMap.put("DOWN2P", "S");
        newKeyMap.put("LEFT2P", "A");
        newKeyMap.put("RIGHT2P", "D");

        settingsView.updateKeyMap(keyString, newKeyMap, "2P");

        for (int i = 0; i < keyString.length; i++) {
            assertEquals(newKeyMap.get(keyString[i] + "2P"), settingsView.getKeyLabels2P()[i].getText());
        }
    }

    @Test
    void testMoveOption() {
        SettingsView settingsView = new SettingsView();
        settingsView.initTitle();
        settingsView.initPanel();

        // Initialize necessary components before calling initWindow
        String[] optionString = {"Resolution", "Key Mapping 1P", "Key Mapping 2P", "Color Blind Mode", "Reset", "Difficulty"};
        settingsView.initLabel(optionString);
        settingsView.initResPanel(new String[]{"800x600", "1024x768", "1920x1080"});
        settingsView.initKeyMapPanel(new String[]{"Up", "Down", "Left", "Right"}, new HashMap<>());
        settingsView.initKeyMapPanel2(new String[]{"Up", "Down", "Left", "Right"}, new HashMap<>());
        settingsView.initCBlindPanel(new String[]{"Off", "Mode 1", "Mode 2"});
        settingsView.initResetPanel(new String[]{"Yes", "No"});
        settingsView.initDifficultyPanel(new String[]{"Easy", "Medium", "Hard"});

        settingsView.moveOption(1);

        assertEquals(Color.YELLOW, settingsView.getOptionLabels()[1].getForeground());
        assertTrue(settingsView.optionPanel[1].isVisible());

        for (int i = 0; i < settingsView.getOptionLabels().length; i++) {
            if (i != 1) {
                assertEquals(Color.BLACK, settingsView.getOptionLabels()[i].getForeground());
                assertFalse(settingsView.optionPanel[i].isVisible());
            }
        }
    }

}