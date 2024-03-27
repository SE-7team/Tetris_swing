package component;

import blocks.*;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class SidePanel extends JPanel {
    static int totalscore;
    public static final int HEIGHT2 = 6;
    public static final int WIDTH2 = 6;
    public static final char BORDER_CHAR1 = ' ';
    static SimpleAttributeSet styleSet1;

    static int [][] board2;
    public static ArrayList<Block> BlockQueue;
    static int x1 = 2; //Default Position.
    static int y1= 0;
    static JTextPane nextPiece = new JTextPane();
    static JPanel scorePanel= new JPanel();
    static JTextPane score=new JTextPane();

    public static void setNextPanel(){
        nextPiece.setEditable(false);
        nextPiece.setBackground(Color.BLACK);
//        CompoundBorder border = BorderFactory.createCompoundBorder(
//                BorderFactory.createLineBorder(Color.CYAN, 5),
//                BorderFactory.createLineBorder(Color.DARK_GRAY, 5));
//        nextPiece.setBorder(border);
        styleSet1 = new SimpleAttributeSet();
        StyleConstants.setFontSize(styleSet1, 18);
        StyleConstants.setFontFamily(styleSet1, "Courier New");
        StyleConstants.setBold(styleSet1, true);

        StyleConstants.setAlignment(styleSet1, StyleConstants.ALIGN_CENTER);


    }
    static Block getRandomBlock() {
        Random rnd = new Random(System.currentTimeMillis());
        int block = rnd.nextInt(7);
        switch(block) {
            case 0:
                return new IBlock();
            case 1:
                return new JBlock();
            case 2:
                return new LBlock();
            case 3:
                return new ZBlock();
            case 4:
                return new SBlock();
            case 5:
                return new TBlock();
            case 6:
                return new OBlock();
        }
        return new LBlock();
    }
    //현재 문제: 띄워지는 블록의 색깔이 처음 nextblock의 색깔로 계속 유지됨
    //문제의심점들을 하나하나 gpt에게 제시한 후, 문제점을 찾아 해결
    public static void paintNextPiece(){
        nextPiece.removeAll();
        Block s=getRandomBlock();
        BlockQueue.add(s);
        board2=new int[HEIGHT2][WIDTH2];
        placeblock(s);
        BlockQueue.remove(0);
        JLabel nexttext=new JLabel("Next");
        nexttext.setForeground(Color.WHITE);
        nexttext.setFont(new Font("Courier New", Font.PLAIN,20));
        nexttext.setBorder(new EmptyBorder(0,0,10,0));
        nextPiece.add(nexttext, BorderLayout.NORTH);
        drawBoard();
    }

    public static Block getNextBlock(){
        return BlockQueue.get(0);
    }
    public static void placeblock(Block cur){
        StyledDocument doc = nextPiece.getStyledDocument();
        SimpleAttributeSet styles = new SimpleAttributeSet();
        StyleConstants.setForeground(styles, cur.getColor());
        for(int j=0; j<cur.height(); j++) {
            int rows = y1+j == 0 ? 0 : y1+j-1;
            int offset = rows * (WIDTH+3) + x1 + 1;
            doc.setCharacterAttributes(offset, cur.width(), styles, true);
            for(int i=0; i<cur.width(); i++) {
                board2[y1+j][x1+i] = cur.getShape(i, j);
            }
        }
        nextPiece.setStyledDocument(doc);
    }


    public static void drawBoard() {
        StringBuffer sb = new StringBuffer();
        for(int t=0; t<WIDTH2+2; t++) sb.append(BORDER_CHAR1);
        sb.append("\n");
        for(int t=0; t<WIDTH2+2; t++) sb.append(BORDER_CHAR1);
        sb.append("\n");
        for(int i=0; i < board2.length; i++) {
            sb.append(BORDER_CHAR1);
            for(int j=0; j < board2[i].length; j++) {
                if(board2[i][j] == 1) {
                    sb.append("O");
                } else {
                    sb.append(" ");
                }
            }
            sb.append(BORDER_CHAR1);
            sb.append("\n");
        }

        for(int t=0; t<WIDTH2+2; t++) sb.append(BORDER_CHAR1);
        nextPiece.setText(sb.toString());


        StyledDocument doc = nextPiece.getStyledDocument();
        SimpleAttributeSet styles = new SimpleAttributeSet();
        Block curBlock = getNextBlock(); // 다음 블록 가져오기
        StyleConstants.setForeground(styles, curBlock.getColor()); // 다음 블록 색상 설정
        StyleConstants.setFontFamily(styles, "Courier New");
        doc.setCharacterAttributes(0, doc.getLength(), styles, false); // 모든 문자의 속성을 새로 설정
        nextPiece.setStyledDocument(doc);

    }

    //점수 업데이트
    public static void updateScore(int a){
        switch(a){
            //디폴트
            case 0:
                totalscore+=1;
                break;
            //타이머가 빨라진 경우
            case 1:
                totalscore+=2;
                break;
            //한번도 다운 or 엔터키를 누르지 않은 경우(머뭇거린 경우)
            case 2:
                totalscore-=5;
                break;
            //빠른 시간내에 블록을 떨어트린 경우
            case 3:
                totalscore+=20;
                break;
            default:
                totalscore++;
        }

    }
    //점수 출력 업데이트
    public static void setScore(){
        score.setText("score: "+totalscore);
    }
    public SidePanel(){
        //

        //sidepanel에 다음블록패널 추가
        this.setLayout(new BorderLayout());
        nextPiece.setLayout(new BorderLayout());
        nextPiece.setBorder(new LineBorder(Color.BLACK));
        nextPiece.setPreferredSize(new Dimension(80,100));
        this.add(nextPiece, BorderLayout.NORTH);

        //
        scorePanel.setLayout(new BorderLayout());
        scorePanel.setBackground(Color.BLACK);
        //scorePanel.setPreferredSize(new Dimension(20,80));
        this.add(scorePanel,BorderLayout.CENTER);
        //점수패널
        totalscore=0;
        score.setLayout(new BorderLayout());
        score.setBorder(new LineBorder(Color.CYAN));
        score.setBackground(Color.BLACK);
        score.setForeground(Color.YELLOW);
        score.setFont(new Font("Courier New", Font.PLAIN,20));
        score.setText("score: "+totalscore);
        score.setPreferredSize(new Dimension(10,50));
        scorePanel.add(score,BorderLayout.NORTH);
        //next텍스트 표시
        JLabel nexttext=new JLabel("Next");
        nexttext.setForeground(Color.WHITE);
        nexttext.setFont(new Font("Courier New", Font.PLAIN,20));
        nexttext.setBorder(new EmptyBorder(0,0,15,0));
        nextPiece.add(nexttext, BorderLayout.NORTH);
        nextPiece.setBorder(new EmptyBorder(5, 5, 5, 5));
        //nextpiece 초기상태 설정
        board2= new int[HEIGHT2][WIDTH2];
        BlockQueue=new ArrayList<Block>();
        Block cur=getRandomBlock();
        setNextPanel();
        StyleConstants.setForeground(styleSet1, cur.getColor());
        BlockQueue.add(cur);
        placeblock(BlockQueue.get(0));
        drawBoard();
    }
}
