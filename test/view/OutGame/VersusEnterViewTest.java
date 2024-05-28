package view.OutGame;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;

public class VersusEnterViewTest {
    VersusEnterView versusEnterView;

    @BeforeEach
    void setUp() {
        versusEnterView = new VersusEnterView();
    }

    @Test
    void testInitTitle() {
        versusEnterView.initTitle();
        JLabel titleLabel = versusEnterView.getTitleLabel();
        assertNotNull(titleLabel);
        assertEquals("진입 모드 선택", titleLabel.getText());
        assertEquals(new Font("malgun gothic", Font.BOLD, 20), titleLabel.getFont());
    }

    @Test
    void testInitWindow() {
        versusEnterView.initPanel();
        versusEnterView.initTitle(); // titleLabel 초기화
        String[] menuString = {"Button 1", "Button 2", "Button 3"}; // 예시 메뉴 문자열
        versusEnterView.initButtons(menuString); // menuButton 배열 초기화

        int resX = 800;
        int resY = 600;
        versusEnterView.initWindow(resX, resY);

        // Title Label 테스트
        JLabel titleLabel = versusEnterView.getTitleLabel();
        assertNotNull(titleLabel);
        assertEquals("진입 모드 선택", titleLabel.getText());

        // Menu Buttons 테스트
        JButton[] menuButtons = versusEnterView.getMenuButtons();
        assertNotNull(menuButtons);
        assertEquals(3, menuButtons.length); // 예상되는 메뉴 버튼 개수
        for (JButton button : menuButtons) {
            assertNotNull(button);
            assertTrue(button.isEnabled()); // 버튼이 활성화되어 있는지 확인
            assertNotNull(button.getActionListeners()); // 버튼에 액션 리스너가 추가되었는지 확인
        }
    }

}