package controller;

import controller.OutGame.MenuController;
import model.OutGame.OutGameModel;
import model.SingleMode.BoardModel;
import model.VersusMode.VersusModelStateChangeListener;
import model.VersusMode.VsBoardModel;
import model.VersusMode.VsItemBoardModel;
import model.VersusMode.VsTimeBoardModel;
import view.SidePanelView;
import view.VsBoardView;

import InProgress.DefenseBlockView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

public class VersusBoardController extends BoardController implements VersusModelStateChangeListener {
    protected BoardModel P2Model;
    private VsBoardView P2View;
    private SidePanelView P2SidePanelView;

    protected DefenseBlockView p2DefenseBlockView;

    public VersusBoardController() {
        // model = new BoardModel();
        this.model.addModelStateChangeListener(this);
    }
    public VersusBoardController(BoardModel P1Model, BoardModel P2Model) {
        super(P1Model);
        this.model = P1Model;
        this.P2Model = P2Model;
        this.P2Model.addModelStateChangeListener(this);
    }
    @Override
    public void notifyAttck(int playerType) {
        /*
        만약 playerType == 0이라면
        P1의 model에서 AttackBlock을 가져와서
        P2의 model에게 전달한다
        */

        if (model instanceof VsBoardModel) {
            if (playerType == 0) {
                ((VsBoardModel)P2Model).saveDefenseBlock(((VsBoardModel)model).getAttackBlock());
            }
            else {
                ((VsBoardModel)model).saveDefenseBlock(((VsBoardModel)P2Model).getAttackBlock());
            }
        }
        else if (model instanceof VsItemBoardModel) {
            if (playerType == 0) {
                ((VsItemBoardModel)P2Model).saveDefenseBlock(((VsItemBoardModel)model).getAttackBlock());
            }
            else {
                ((VsItemBoardModel) model).saveDefenseBlock(((VsItemBoardModel) P2Model).getAttackBlock());
            }
        }
        else {
            System.out.println("Error: notifyAttack");
        }
    }

    @Override
    public void notifyGameOver(int playerType) {
        model.setPaused(true);
        P2Model.setPaused(true);
        if (model instanceof VsTimeBoardModel) {
            if (!((VsTimeBoardModel) model).checkTimeLimit()) {
                gameOver(playerType);
                return;
            }
            else {
                gameOver();
                return;
            }
        }
        gameOver(playerType);
    }


    @Override
    public void initView() {
        view = new VsBoardView();
        sidePanelView = new SidePanelView();
        P2View = new VsBoardView();
        P2SidePanelView = new SidePanelView();

        DefenseBlockView = new DefenseBlockView();
        p2DefenseBlockView = new DefenseBlockView();

        // sidePanelView를 view의 EAST에 배치
        JPanel panel = new JPanel(new GridLayout(3, 1,0,10));
        panel.setBackground(Color.LIGHT_GRAY);
        panel.add(sidePanelView);
        panel.add(DefenseBlockView);
        view.getContentPane().add(panel, BorderLayout.EAST);

        // sidePanelView를 view의 EAST에 배치
        JPanel P2panel = new JPanel(new GridLayout(3, 1,0,10));
        P2panel.setBackground(Color.LIGHT_GRAY);
        P2panel.add(P2SidePanelView);
        P2panel.add(p2DefenseBlockView);
        P2View.getContentPane().add(P2panel, BorderLayout.EAST);

        view.getContentPane().add(P2View.getContentPane(), BorderLayout.WEST);
        view.setController(this);
        view.setVisible(true);
        playerKeyListener = new PlayerKeyListener();
        // addKeyListener, setFocusable, requestFocus를 BoardView의 메서드로 대체
        view.addKeyListenerToFrame(playerKeyListener);
        view.setSize(OutGameModel.getResX() * 4 / 3, OutGameModel.getResY());
    }

    @Override
    public void pauseGame() {
        if (!model.isPaused() || !P2Model.isPaused()) {
            model.setPaused(true);
            P2Model.setPaused(true);
            model.getTimer().stop();
            P2Model.getTimer().stop();
            view.showPauseScreen(true);
        }
    }

    @Override
    public void resumeGame() {
        if (model.isPaused()) {
            model.setPaused(false);
            P2Model.setPaused(false);
            view.showPauseScreen(false);
        }
    }

    @Override
    public void gameOver() {
        if (model instanceof VsTimeBoardModel) {
            if (!((VsTimeBoardModel) model).checkTimeLimit()) {
            }
            else if ((model.getTotalscore() > P2Model.getTotalscore())) {
                view.showConfirmDialog(
                        "Player 1 Win!\n" +
                                "Player 1 : " + model.getTotalscore() + "\n" +
                                "Player 2 : " + P2Model.getTotalscore(),
                        "Game Over"
                );
            } else if (model.getTotalscore() < P2Model.getTotalscore()) {
                view.showConfirmDialog(
                        "Player 2 Win!\n" +
                                "Player 1 : " + model.getTotalscore() + "\n" +
                                "Player 2 : " + P2Model.getTotalscore(),
                        "Game Over"
                );
            } else {
                view.showConfirmDialog(
                        "Draw!\n" +
                                "Player 1 : " + model.getTotalscore() + "\n" +
                                "Player 2 : " + P2Model.getTotalscore(),
                        "Game Over"
                );
            }
        }
        returnMenu();
    }

    public void gameOver(int loser) {
        if (loser == 1) {
            view.showConfirmDialog(
                    "Player 1 Win!\n",
                    "Game Over"
            );
        } else if (loser == 0) {
            view.showConfirmDialog(
                    "Player 2 Win!\n",
                    "Game Over"
            );
        } else {
            view.showConfirmDialog(
                    "Draw!\n",
                    "Game Over"
            );
        }
        returnMenu();
    }

    public void returnMenu() {
        int response = view.showConfirmDialog(
                "Game Over. 시작 메뉴로 돌아가시겠습니까?\n (No : 게임 종료)",
                "Game Over"
        );

        if (response == JOptionPane.YES_OPTION) {
            view.setVisible(false);
            P2View.setVisible(false);
            MenuController.getInstance().initFrame();
        } else {
            // 게임 종료
            System.exit(0);
        }
    }

    @Override
    // 게임 상태 업데이트 후 View update를 위한 메서드
    public void updateBoard() {
        // 게임 로직 처리...
        // 예를 들어, 게임 보드 상태를 업데이트하는 로직 수행

        // View에 게임 보드 그리기 요청
        view.drawBoard(model.getBoard(), model.getBoard_color(), model.getBoard_text());
        // SidePanel에 다음 블럭 넘기기
        sidePanelView.drawBoard(model.getNextBlock());
        // 사이드 패널의 점수를 view에 넘겨야 한다
        sidePanelView.setScoreText(model.getTotalscore());

        DefenseBlockView.drawBoard(model.getDefenseBlockChunk());
    }


    public void updateP2Board() {
            // 게임 로직 처리...
            // 예를 들어, 게임 보드 상태를 업데이트하는 로직 수행
            // View에 게임 보드 그리기 요청
            P2View.drawBoard(P2Model.getBoard(), P2Model.getBoard_color(), P2Model.getBoard_text());
            // SidePanel에 다음 블럭 넘기기
            P2SidePanelView.drawBoard(P2Model.getNextBlock());
            // 사이드 패널의 점수를 view에 넘겨야 한다
            P2SidePanelView.setScoreText(P2Model.getTotalscore());

            p2DefenseBlockView.drawBoard(P2Model.getDefenseBlockChunk());
    }

    @Override
    public void onModelStateChanged(int playerType) {
        // Model에서 상태 변경 시 호출될 메서드
        // 여기서 View의 갱신 로직 호출
        if (playerType == 0) {
            model.moveDown();
            updateBoard();
        } else {
            P2Model.moveDown();
            updateP2Board();
        }
    }

    @Override
    public void notifyUpdateBoard(int playerType) {
        if (playerType == 0) {
            updateBoard();
        } else {
            updateP2Board();
        }
    }


    public class PlayerKeyListener implements KeyListener {
        private HashMap<String, String> keyMap;

        private String moveLeft1P;
        private String moveLeft2P;

        private String moveRight1P;
        private String moveRight2P;

        private String moveDown1P;
        private String moveDown2P;
        private String drop1P;
        private String drop2P;

        private String rotate1P;
        private String rotate2P;
        private String pause1P;
        private String pause2P;

        public PlayerKeyListener() {
            keyMap = OutGameModel.getInstance().getKeyMap();
            // 아래는 getKeyText를 통해 저장된 값임
            moveLeft1P = keyMap.get("moveLeft1P");
            moveRight1P = keyMap.get("moveRight1P");
            drop1P = keyMap.get("drop1P");
            moveDown1P = keyMap.get("moveDown1P");
            rotate1P = keyMap.get("rotate1P");
            pause1P = keyMap.get("pause1P");
            moveLeft2P = keyMap.get("moveLeft2P");
            moveRight2P = keyMap.get("moveRight2P");
            drop2P = keyMap.get("drop2P");
            moveDown2P = keyMap.get("moveDown2P");
            rotate2P = keyMap.get("rotate2P");
            pause2P = keyMap.get("pause2P");
        }


        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            currentKey = KeyEvent.getKeyText(e.getKeyCode());
            if (!model.isPaused()) {
                if (currentKey.equals(moveDown1P)) {
                    if(!model.isDowned()){
                        model.setDowned(true);
                    }
                    model.moveDown();
                    updateBoard();
                }
                else if (currentKey.equals(moveRight1P)) {
                    model.moveRight();
                    updateBoard();
                }
                else if (currentKey.equals(moveLeft1P)) {
                    model.moveLeft();
                    updateBoard();
                }
                else if (currentKey.equals(rotate1P)) {
                    model.moveRotate();
                    updateBoard();
                }
                else if (currentKey.equals(drop1P)) {
                    if(!model.isDowned()){
                        model.setDowned(true);
                    }
                    // 위치 이동 메서드
                    model.moveBottom();
                    updateBoard();
                }
                else if (currentKey.equals(pause1P)) {
                    pauseGame();
                }
                else if (currentKey.equals(moveDown2P)) {
                    if(!P2Model.isDowned()){
                        P2Model.setDowned(true);
                    }
                    P2Model.moveDown();
                    updateP2Board();
                }
                else if (currentKey.equals(moveRight2P)) {
                    P2Model.moveRight();
                    updateP2Board();
                }
                else if (currentKey.equals(moveLeft2P)) {
                    P2Model.moveLeft();
                    updateP2Board();
                }
                else if (currentKey.equals(rotate2P)) {
                    P2Model.moveRotate();
                    updateP2Board();
                }
                else if (currentKey.equals(drop2P)) {
                    if(!model.isDowned()){
                        P2Model.setDowned(true);
                    }
                    // 위치 이동 메서드
                    P2Model.moveBottom();
                    updateP2Board();
                }
                else if (currentKey.equals(pause2P)) {
                    pauseGame();
                }

            }
            else {
                // 일시 정지 상태인 경우, 스위치 문을 사용하여 추가 키를 처리합니다.
                // KeyEvent.VK_P까지 by chatGPT3.5 이후는 추가 작성
                switch (e.getKeyCode()) {
                    // 두벌식에서 P를 누르는 경우 인식X --> ESC 키로 변경
                    case KeyEvent.VK_ESCAPE:
                        resumeGame();
                        break;
                    case KeyEvent.VK_DOWN:
                        selectedOption++;
                        if(selectedOption>3) selectedOption=1;
                        view.glassRepaint();
                        break;
                    case KeyEvent.VK_UP:
                        selectedOption--;
                        if(selectedOption<1) selectedOption=3;
                        view.glassRepaint();
                        break;
                    case KeyEvent.VK_ENTER:
                        switch(selectedOption){
                            case 1: //메인메뉴
                                view.setVisible(false);
                                MenuController.getInstance().initFrame();
                                break;
                            case 2: //재시작
                                resumeGame();
                                break;
                            case 3: //게임종료
                                gameExit();
                                break;
                        }
                }
            }

        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }


}
