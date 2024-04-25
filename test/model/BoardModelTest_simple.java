package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class BoardModelTest_simple {

    @Test
    public void testSetPaused() {
        // Given
        BoardModel boardModel = new BoardModel();

        // When
        boardModel.setPaused(true);

        // Then
        assertTrue(boardModel.isPaused()); // 일시 정지되어 있어야 함

        // When
        boardModel.setPaused(false);

        // Then
        assertFalse(boardModel.isPaused()); // 일시 정지되어 있지 않아야 함
    }

    @Test
    void testUpdateScore() {
        // Given
        BoardModel boardModel = new BoardModel();
        int initialScore = boardModel.getTotalscore();

        // When & Then
        for (int i = 0; i <= 3; i++) {
            switch (i) {
                case 0:
                    boardModel.updateScore(i);
                    assertEquals(initialScore + 1, boardModel.getTotalscore());
                    break;
                case 1:
                    boardModel.updateScore(i);
                    assertEquals(initialScore + 2, boardModel.getTotalscore());
                    break;
                case 2:
                    boardModel.updateScore(i);
                    assertEquals(initialScore - 5, boardModel.getTotalscore());
                    break;
                case 3:
                    boardModel.updateScore(i);
                    assertEquals(initialScore + 20, boardModel.getTotalscore());
                    break;
            }
        }
    }

    @Test
    public void testUpdateTimer() {
        // Given
        BoardModel boardModel = new BoardModel();
        int initialInterval = boardModel.initInterval;

        // When
        boardModel.updateTimer(1); // 예제에서는 1을 넣었으므로, 해당 값을 테스트

        // Then
        assertEquals(720, boardModel.initInterval); // 타이머가 예상대로 업데이트되었는지 확인
    }

    @Test
    public void testCheckForScore() {
        // Given
        BoardModel boardModel = new BoardModel();
        int initialScore = boardModel.getTotalscore();

        // When
        boardModel.checkForScore(); // checkForScore 메서드 호출

        // Then
        assertEquals(initialScore - 5, boardModel.getTotalscore()); // 점수가 5 감소했는지 확인
    }

    @Test
    void testGenerateBlock() {
        // Given
        BoardModel boardModel = new BoardModel();
        int initialBlockCount = boardModel.blockCount;

        // When
        boardModel.setY(1);
        boardModel.generateBlock();

        // Then
        assertEquals(initialBlockCount + 1, boardModel.blockCount); // 블록 수가 1 증가했는지 확인
        assertNotNull(boardModel.curr); // 현재 블록이 null이 아닌지 확인
        assertNotNull(boardModel.getNextBlock()); // 다음 블록이 null이 아닌지 확인
        assertNotNull(boardModel.getNextBlock().getText()); // 다음 블록에 텍스트가 할당되었는지 확인
    }
}