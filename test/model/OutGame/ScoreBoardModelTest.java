package model.OutGame;

import static org.junit.Assert.*;
import org.json.simple.JSONArray;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.*;

public class ScoreBoardModelTest {
    private ScoreBoardModel scoreBoardModel;

    @BeforeEach
    public void setUp() {
        scoreBoardModel = new ScoreBoardModel();
    }

    @Test
    public void testInitData() {
        assertNotNull(scoreBoardModel.getJsonArr());
    }

    @Test
    public void testSortScore() {
        scoreBoardModel.sortScore();
        assertNotNull(scoreBoardModel.getJsonArr());
    }

    @Test
    public void testgetColumnString() {
        assertNotNull(scoreBoardModel.getColumnString());
    }

}