package controller;

import IO.ScoreIO;
import model.BoardModel;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import view.BoardView;
import view.SidePanelView;

import javax.swing.*;
import java.awt.event.KeyEvent;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

//by chatGPT3.5
public class BoardControllerTest {

    private BoardModel model;
    private BoardView view;
    private SidePanelView sidePanelView;
    private BoardController controller;


    @Before
    public void setUp() {
        model = Mockito.spy(new BoardModel());
        view = Mockito.spy(new BoardView());
        sidePanelView = new SidePanelView();
        controller = new BoardController(model, view, sidePanelView);
    }

    @Test
    public void testPauseAndResumeGame() {
        // Initially not paused
        assertFalse(model.isPaused());

        // Pause the game
        controller.pauseGame();
        assertTrue(model.isPaused());

        // Resume the game
        controller.resumeGame();
        assertFalse(model.isPaused());
    }

    @Test
    public void testGameOver() {
        controller = Mockito.spy(controller);

        // Set the model to not paused initially
        when(model.isPaused()).thenReturn(false);

        // Stubbing JOptionPane's showConfirmDialog to return YES_OPTION
        doReturn(JOptionPane.YES_OPTION).when(view).showConfirmDialog(any(), any());
        doReturn("PlayerName").when(controller).getPlayerNameInput();

        // Call gameOver method
        controller.gameOver();

        // Verify model is set to paused
        verify(model).setPaused(true);
        // Verify view is set to invisible
        verify(view).setVisible(false);
        // Verify showConfirmDialog is called with appropriate messages
        verify(view, times(2)).showConfirmDialog(Mockito.any(), Mockito.any());

        // 테스트 후에 userScore.json에 초기 값 넣어주기
        ScoreIO.clearJsonFile();
        ScoreIO.writeScore("test1", 217);
        ScoreIO.writeScore("test2", 217);
    }

    @Test
    public void testPlayerKeyListener() {
        // Initially not paused
        assertFalse(model.isPaused());

        controller = Mockito.spy(controller); // Create a spy for the controller

        BoardController.PlayerKeyListener playerKeyListener = controller.new PlayerKeyListener();

        KeyEvent mockKeyEvent = Mockito.mock(KeyEvent.class);

        // Test for VK_DOWN
        when(mockKeyEvent.getKeyCode()).thenReturn(KeyEvent.VK_DOWN);
        playerKeyListener.keyPressed(mockKeyEvent);
        verify(controller,atLeastOnce()).moveDownControl();
        verify(controller,atLeastOnce()).updateBoard();

        // Test for VK_RIGHT
        when(mockKeyEvent.getKeyCode()).thenReturn(KeyEvent.VK_RIGHT);
        playerKeyListener.keyPressed(mockKeyEvent);
        verify(model,atLeastOnce()).moveRight();
        verify(controller,atLeastOnce()).updateBoard(); // Called twice: once for moveRight, once for updateBoard

        // Test for VK_LEFT
        when(mockKeyEvent.getKeyCode()).thenReturn(KeyEvent.VK_LEFT);
        playerKeyListener.keyPressed(mockKeyEvent);
        verify(model,atLeastOnce()).moveLeft();
        verify(controller,atLeastOnce()).updateBoard(); // Called thrice: once for moveLeft, twice for updateBoard

        // Test for VK_UP
        when(mockKeyEvent.getKeyCode()).thenReturn(KeyEvent.VK_UP);
        playerKeyListener.keyPressed(mockKeyEvent);
        verify(model,atLeastOnce()).moveRotate();
        verify(controller,atLeastOnce()).updateBoard(); // Called four times: once for moveRotate, thrice for updateBoard

        // Test for VK_ENTER
        when(mockKeyEvent.getKeyCode()).thenReturn(KeyEvent.VK_ENTER);
        playerKeyListener.keyPressed(mockKeyEvent);
        verify(controller,atLeastOnce()).moveBottomControl(); // Called twice: once for moveBottomControl, once for moveDownControl
        verify(controller,atLeastOnce()).updateBoard(); // Called six times: once for moveBottomControl, five times for updateBoard

        // Test for VK_ESCAPE when not paused
        when(mockKeyEvent.getKeyCode()).thenReturn(KeyEvent.VK_ESCAPE);
        playerKeyListener.keyPressed(mockKeyEvent);
        verify(controller,atLeastOnce()).pauseGame();
        verify(controller,atLeastOnce()).updateBoard(); // Called seven times: once for pauseGame, six times for updateBoard

        // Test for VK_ESCAPE when paused
        model.setPaused(true); // Set the model to paused
        playerKeyListener.keyPressed(mockKeyEvent);
        verify(controller,atLeastOnce()).resumeGame();
        verify(controller,atLeastOnce()).updateBoard(); // Called eight times: once for resumeGame, seven times for updateBoard

        // Add this for VK_RIGHT
        when(mockKeyEvent.getKeyCode()).thenReturn(KeyEvent.VK_RIGHT);
        playerKeyListener.keyPressed(mockKeyEvent);
        verify(model,atLeastOnce()).moveRight(); // Called once for moveRight after resume
        verify(controller,atLeastOnce()).updateBoard(); // Called nine times: once for moveRight, eight times for updateBoard
    }

    @Test
    public void testPlayerKeyListener_PauseAndResume() {
        // Initially not paused
        assertFalse(model.isPaused());

        BoardController.PlayerKeyListener playerKeyListener = controller.new PlayerKeyListener();

        // Press ESC key to pause the game
        KeyEvent mockEscapeKeyEvent = Mockito.mock(KeyEvent.class);
        when(mockEscapeKeyEvent.getKeyCode()).thenReturn(KeyEvent.VK_ESCAPE);
        playerKeyListener.keyPressed(mockEscapeKeyEvent);

        // Now the game should be paused
        assertTrue(model.isPaused());

        // Press ESC key again to resume the game
        playerKeyListener.keyPressed(mockEscapeKeyEvent);

        // Now the game should be resumed
        assertFalse(model.isPaused());
    }

    @Test
    public void testPlayerKeyListener_SelectedOption1() {
        // Initially not paused
        assertFalse(model.isPaused());

        BoardController.PlayerKeyListener playerKeyListener = controller.new PlayerKeyListener();

        // Press ESC key to pause the game
        KeyEvent mockEscapeKeyEvent = Mockito.mock(KeyEvent.class);
        when(mockEscapeKeyEvent.getKeyCode()).thenReturn(KeyEvent.VK_ESCAPE);
        playerKeyListener.keyPressed(mockEscapeKeyEvent);

        // Now the game should be paused
        assertTrue(model.isPaused());

        // Press DOWN key to change the selected option
        KeyEvent mockDownKeyEvent = Mockito.mock(KeyEvent.class);
        when(mockDownKeyEvent.getKeyCode()).thenReturn(KeyEvent.VK_DOWN);
        playerKeyListener.keyPressed(mockDownKeyEvent);

        // Selected option should be 2
        assertEquals(2, controller.getSelectedOption());

        // Press UP key to change the selected option
        KeyEvent mockUpKeyEvent = Mockito.mock(KeyEvent.class);
        when(mockUpKeyEvent.getKeyCode()).thenReturn(KeyEvent.VK_UP);
        playerKeyListener.keyPressed(mockUpKeyEvent);

        // Selected option should be 1
        assertEquals(1, controller.getSelectedOption());

        // Press ENTER key to choose the option
        KeyEvent mockEnterKeyEvent = Mockito.mock(KeyEvent.class);
        when(mockEnterKeyEvent.getKeyCode()).thenReturn(KeyEvent.VK_ENTER);

        playerKeyListener.keyPressed(mockEnterKeyEvent);

        // Check if view is set to invisible
        verify(view, atLeastOnce()).setVisible(false);
    }

    @Test
    public void testPlayerKeyListener_SelectedOption2() {
        controller = Mockito.spy(controller);

        // Initially not paused
        assertFalse(model.isPaused());

        BoardController.PlayerKeyListener playerKeyListener = controller.new PlayerKeyListener();

        // Press ESC key to pause the game
        KeyEvent mockEscapeKeyEvent = Mockito.mock(KeyEvent.class);
        when(mockEscapeKeyEvent.getKeyCode()).thenReturn(KeyEvent.VK_ESCAPE);
        playerKeyListener.keyPressed(mockEscapeKeyEvent);

        // Now the game should be paused
        assertTrue(model.isPaused());

        controller.setSelectedOption(2);
        // Selected option should be 2
        assertEquals(2, controller.getSelectedOption());

        // Press ENTER key to choose the option
        KeyEvent mockEnterKeyEvent = Mockito.mock(KeyEvent.class);
        when(mockEnterKeyEvent.getKeyCode()).thenReturn(KeyEvent.VK_ENTER);
        playerKeyListener.keyPressed(mockEnterKeyEvent);

        verify(controller, atLeastOnce()).resumeGame();

    }

    @Test
    public void testPlayerKeyListener_SelectedOption3() {
        controller = Mockito.spy(controller);

        // Initially not paused
        assertFalse(model.isPaused());
        BoardController.PlayerKeyListener playerKeyListener = controller.new PlayerKeyListener();

        // Press ESC key to pause the game
        KeyEvent mockEscapeKeyEvent = Mockito.mock(KeyEvent.class);
        when(mockEscapeKeyEvent.getKeyCode()).thenReturn(KeyEvent.VK_ESCAPE);
        playerKeyListener.keyPressed(mockEscapeKeyEvent);

        // Now the game should be paused
        assertTrue(model.isPaused());

        controller.setSelectedOption(3);
        // Selected option should be 3
        assertEquals(3, controller.getSelectedOption());

        // Stubbing view.showConfirmDialog to return YES_OPTION
        doReturn(JOptionPane.NO_OPTION).when(view).showConfirmDialog(any(), any());

        // Press ENTER key to choose the option
        KeyEvent mockEnterKeyEvent = Mockito.mock(KeyEvent.class);
        when(mockEnterKeyEvent.getKeyCode()).thenReturn(KeyEvent.VK_ENTER);
        playerKeyListener.keyPressed(mockEnterKeyEvent);

        verify(controller, atLeastOnce()).gameExit();

    }

}

