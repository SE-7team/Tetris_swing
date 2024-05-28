package view.OutGame;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

public class MenuViewTest {
    MenuView menuView;

    @BeforeEach
    void setUp() {
        menuView = new MenuView();

        // mainPanel 초기화
        menuView.mainPanel = new JPanel();
        menuView.mainPanel.setLayout(new BorderLayout());

        // titleLabel 초기화
        menuView.titleLabel = new JLabel();

        // menuButton 배열 초기화
        menuView.menuButton = new JButton[3]; // 예시로 3개의 버튼을 생성
    }

    @Test
    void testInitLabel() {
        String[] gameModeString = {"Mode 1", "Mode 2", "Mode 3"};
        menuView.initLable(gameModeString);
        assertNotNull(menuView.gameModeLabel);
        assertEquals(gameModeString.length, menuView.gameModeLabel.length);
    }

    @Test
    void testInitTitle() {
        String[] buildString = {"Build 1", "Build 2", "Build 3"};
        menuView.initTitle(buildString, 0);
        assertNotNull(menuView.titleLabel);
        assertEquals("Tetris Build 1", menuView.titleLabel.getText());
    }

    @Test
    void testInitButtons() {
        String[] menuString = {"Button 1", "Button 2", "Button 3"};
        menuView.initButtons(menuString);
        assertNotNull(menuView.menuButton);
        assertEquals(menuString.length, menuView.menuButton.length);
    }

    @Test
    void testInitWindow() {
        String[] menuString = {"Button 1", "Button 2", "Button 3"};
        menuView.initButtons(menuString);

        int resX = 800;
        int resY = 600;
        menuView.initWindow(resX, resY);
        assertEquals("Tetris", menuView.getTitle());
        assertEquals(resX, menuView.getWidth());
        assertEquals(resY, menuView.getHeight());
        assertFalse(menuView.isResizable());
        assertEquals(JFrame.EXIT_ON_CLOSE, menuView.getDefaultCloseOperation());
    }

    @Test
    void testPaintFocus() {
        String[] menuString = {"Button 1", "Button 2", "Button 3"};
        menuView.initButtons(menuString);
        menuView.paintFocus(1);
        assertNotEquals(BorderFactory.createLineBorder(Color.YELLOW, 3), menuView.menuButton[0].getBorder());
    }

}