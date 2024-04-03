package controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import view.MenuView;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

//by chatGPT3.5
public class MenuControllerTest {

    private MenuController menuController;

    @BeforeEach
    public void setUp() {
        menuController = MenuController.getInstance();
        menuController.initFrame();
    }

    @Test
    public void testGetInstance() {
        assertNotNull(menuController);
        MenuController secondInstance = MenuController.getInstance();
        assertEquals(menuController, secondInstance, "Instances should be the same");
    }

    @Test
    public void testInitFrame() {
        assertNotNull(menuController.view, "MenuView should be initialized");
    }

    @Test
    public void testInitFocus() {
        assertNotNull(menuController.view.getRootPane().getInputMap(), "InputMap should not be null");
        assertNotNull(menuController.view.getRootPane().getActionMap(), "ActionMap should not be null");
    }

    @Test
    public void testEnterMenuCase0() {
        // 뷰를 Mocking 합니다
        MenuView mockedView = mock(MenuView.class);
        menuController.view = mockedView;

        menuController.enterMenu(0);

        // 뷰의 가시성이 false로 설정되고 dispose() 메서드가 호출되는지 확인합니다
        verify(mockedView).setVisible(false);
        verify(mockedView).dispose();
        // 더 이상의 상호작용이 없는지 확인합니다
        verifyNoMoreInteractions(mockedView);

        assertNull(menuController.view, "MenuView는 null이 되어야 합니다");
    }

    @Test
    public void testEnterMenuCase1() {
        // 뷰를 Mocking 합니다
        MenuView mockedView = mock(MenuView.class);
        menuController.view = mockedView;

        menuController.enterMenu(1);

        // view.setVisible(false); 가 호출되었는지 확인
        verify(mockedView).setVisible(false);
        // view.dispose(); 가 호출되었는지 확인
        verify(mockedView).dispose();
        // view = null; 이 되었는지 확인
        assertNull(menuController.view);
    }

    @Test
    public void testEnterMenuCase2() {
        // 뷰를 Mocking 합니다
        MenuView mockedView = mock(MenuView.class);
        menuController.view = mockedView;

        menuController.enterMenu(2);

        // view.setVisible(false); 가 호출되었는지 확인
        verify(mockedView).setVisible(false);
        // view.dispose(); 가 호출되었는지 확인
        verify(mockedView).dispose();
        // view = null; 이 되었는지 확인
        assertNull(menuController.view);
    }

    @Test
    public void testEnterMenuCase3() {

    }
}
