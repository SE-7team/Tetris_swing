package view.OutGame;

import model.OutGame.OutGameModel;
import model.OutGame.ScoreBoardModel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

public class ScoreBoardViewTest {
    @BeforeAll
    static void setUp() {
        // Mock the static methods and data that are used by ScoreBoardView
        mockStatic(ScoreBoardModel.class);
        mockStatic(OutGameModel.class);

        // Mock the column string array
        String[] columns = {"Rank", "Difficulty", "Mode", "Name", "Score"};
        when(ScoreBoardModel.getColumnString()).thenReturn(columns);

        // Mock the resolution size
        when(OutGameModel.getResX()).thenReturn(800);
        when(OutGameModel.getResY()).thenReturn(600);

        // Mock the JSON array returned by ScoreBoardModel
        JSONArray jsonArray = new JSONArray();

        JSONObject score1 = new JSONObject();
        score1.put("name", "Alice");
        score1.put("difficulty", "Easy");
        score1.put("score", 100L);
        score1.put("mode", "Solo");

        JSONObject score2 = new JSONObject();
        score2.put("name", "Bob");
        score2.put("difficulty", "Hard");
        score2.put("score", 200L);
        score2.put("mode", "Duo");

        jsonArray.add(score1);
        jsonArray.add(score2);

        when(ScoreBoardModel.getJsonArr()).thenReturn(jsonArray);
    }

    @Test
    void testScoreBoardViewInitialization() {
        // Create an instance of ScoreBoardView
        ScoreBoardView view = new ScoreBoardView();

        // Validate the JFrame properties
        assertEquals("User Score Board", view.getTitle());
        assertEquals(JFrame.EXIT_ON_CLOSE, view.getDefaultCloseOperation());
        assertEquals(Color.BLACK, view.getContentPane().getBackground());

        // Validate the table model
        DefaultTableModel model = (DefaultTableModel) view.getTable().getModel();
        assertEquals(2, model.getRowCount());
        assertEquals(5, model.getColumnCount());

        // Validate the first row data
        assertEquals(1, model.getValueAt(0, 0));  // Rank
        assertEquals("Easy", model.getValueAt(0, 1));  // Difficulty
        assertEquals("Solo", model.getValueAt(0, 2));  // Mode
        assertEquals("Alice", model.getValueAt(0, 3));  // Name
        assertEquals(100L, model.getValueAt(0, 4));  // Score

        // Validate the second row data
        assertEquals(2, model.getValueAt(1, 0));  // Rank
        assertEquals("Hard", model.getValueAt(1, 1));  // Difficulty
        assertEquals("Duo", model.getValueAt(1, 2));  // Mode
        assertEquals("Bob", model.getValueAt(1, 3));  // Name
        assertEquals(200L, model.getValueAt(1, 4));  // Score
    }

    @Test
    void testScoreBoardViewWithHighlight() {
        // Create an instance of ScoreBoardView with parameters for highlighting
        String testName = "Alice";
        int testScore = 100;
        ScoreBoardView view = new ScoreBoardView(testName, testScore);

        // Validate the JFrame properties
        assertEquals("User Score Board", view.getTitle());
        assertEquals(JFrame.EXIT_ON_CLOSE, view.getDefaultCloseOperation());
        assertEquals(Color.BLACK, view.getContentPane().getBackground());

        // Validate the table model
        DefaultTableModel model = (DefaultTableModel) view.getTable().getModel();
        assertEquals(2, model.getRowCount());
        assertEquals(5, model.getColumnCount());

        // Validate the first row data
        assertEquals(1, model.getValueAt(0, 0));  // Rank
        assertEquals("Easy", model.getValueAt(0, 1));  // Difficulty
        assertEquals("Solo", model.getValueAt(0, 2));  // Mode
        assertEquals("Alice", model.getValueAt(0, 3));  // Name
        assertEquals(100L, model.getValueAt(0, 4));  // Score

        // Validate the second row data
        assertEquals(2, model.getValueAt(1, 0));  // Rank
        assertEquals("Hard", model.getValueAt(1, 1));  // Difficulty
        assertEquals("Duo", model.getValueAt(1, 2));  // Mode
        assertEquals("Bob", model.getValueAt(1, 3));  // Name
        assertEquals(200L, model.getValueAt(1, 4));  // Score

        // Validate the highlight
        for (int i = 0; i < model.getRowCount(); i++) {
            String nameValue = (String) model.getValueAt(i, 3);
            Long scoreValue = (Long) model.getValueAt(i, 4);

            if (nameValue.equals(testName) && scoreValue == testScore) {
                // Check if the highlighted row has the correct foreground color and font
                Component cellComponent = view.getTable().prepareRenderer(view.getTable().getCellRenderer(i, 3), i, 3);
                assertEquals(Color.ORANGE, cellComponent.getForeground());
                assertTrue(cellComponent.getFont().isBold());
            } else {
                // Check if the non-highlighted rows have default foreground color and font
                Component cellComponent = view.getTable().prepareRenderer(view.getTable().getCellRenderer(i, 3), i, 3);
                assertEquals(Color.BLACK, cellComponent.getForeground());
                assertFalse(cellComponent.getFont().isBold());
            }
        }
    }

    @Test
    void testUpdateRanks() {
        // Create an instance of ScoreBoardView
        ScoreBoardView view = new ScoreBoardView();

        // Set up the table model with initial data
        DefaultTableModel model = (DefaultTableModel) view.getTable().getModel();
        model.setRowCount(0); // Clear any existing rows
        model.addRow(new Object[]{1, "Easy", "Solo", "Alice", 100L});
        model.addRow(new Object[]{2, "Hard", "Duo", "Bob", 200L});
        model.addRow(new Object[]{3, "Medium", "Trio", "Charlie", 150L});

        // Call updateRanks() method
        view.updateRanks();

        // Validate the ranks after update
        assertEquals(1, model.getValueAt(0, 0));  // Rank of the first row
        assertEquals(2, model.getValueAt(1, 0));  // Rank of the second row
        assertEquals(3, model.getValueAt(2, 0));  // Rank of the third row
    }
}