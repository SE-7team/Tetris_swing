package view.OutGame;

import static org.junit.jupiter.api.Assertions.*;

import javax.swing.*;

import IO.ImportSettings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import view.OutGame.OutGameView;

import java.awt.*;

public class OutGameViewTest {

    private OutGameView outGameView;
    @Mock
    private ImportSettings importSettingsMock;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);  // Initialize mocks annotated with @Mock
        outGameView = new OutGameView();
    }

    @Test
    public void testInitPanel() {
        outGameView.initPanel();
        assertNotNull(outGameView.mainPanel);
        assertTrue(outGameView.mainPanel.getLayout() instanceof BorderLayout);
    }

    @Test
    public void testInitTitle() {
        outGameView.initTitle();
        assertNotNull(outGameView.titleLabel);
        assertEquals("", outGameView.titleLabel.getText());
        assertEquals(new Font("malgun gothic", Font.BOLD, 20), outGameView.titleLabel.getFont());
    }

    /*@Test
    public void testInitWindow() {
        // Correct use of Mockito's when method
        when(importSettingsMock.getSetting("ResolutionSizeX")).thenReturn("800");
        when(importSettingsMock.getSetting("ResolutionSizeY")).thenReturn("600");

        outGameView.setImportSettings(importSettingsMock);  // Ensure the settings mock is used in outGameView
        outGameView.initWindow();

        assertEquals(800, outGameView.getSize().width);
        assertEquals(600, outGameView.getSize().height);
        assertFalse(outGameView.isResizable());
        assertEquals(JFrame.EXIT_ON_CLOSE, outGameView.getDefaultCloseOperation());
    }*/

    @Test
    public void testShowWarning() {
        // Testing GUI elements like JOptionPane should be done with caution in unit tests
        // For demonstration purposes only, not a best practice
        String message = "Test Warning";
        JOptionPane.showMessageDialog(null, message, "Warning", JOptionPane.WARNING_MESSAGE);
        // Verify interactions or use a GUI testing framework in practice
    }

    @Test
    public void testShowQuestion() {
        // Similar handling as `testShowWarning`
        String message = "Test Question";
        int result = JOptionPane.showConfirmDialog(null, message, "Warning", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        // Verification of GUI components should be done with proper GUI testing tools
    }

    @Test
    public void testInitButtons() {
        String[] buttonStrings = {"Button1", "Button2"};
        assertDoesNotThrow(() -> outGameView.initButtons(buttonStrings));
    }

    @Test
    public void testPaintFocus() {
        int focus = 1;
        assertDoesNotThrow(() -> outGameView.paintFocus(focus));
    }

    @Test
    public void testInitLabel() {
        String[] labelStrings = {"Label1", "Label2"};
        assertDoesNotThrow(() -> outGameView.initLable(labelStrings));
    }

    @Test
    public void testInitTitle_WithParams() {
        String[] buildString = {"Title Part 1", "Title Part 2"};
        int buildType = 1;
        outGameView.initTitle(buildString, buildType);

        assertNotNull(outGameView.titleLabel, "Title label should not be null");
        assertEquals("", outGameView.titleLabel.getText(), "Title label text should be empty");
    }
}
