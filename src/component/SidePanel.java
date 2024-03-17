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
    public static final int HEIGHT2 = 5;
    public static final int WIDTH2 = 5;
    public static final char BORDER_CHAR1 = ' ';
    static SimpleAttributeSet styleSet1;
    static Block cur;
    static int [][] board2;
    public static ArrayList<Block> BlockQueue;
    static int x1 = 2; //Default Position.
    static int y1= 0;
    static JTextPane nextPiece = new JTextPane();
    static JPanel scorePanel= new JPanel();
    private JLabel score=new JLabel();

    public void setNextPanel(){
        nextPiece.setEditable(false);
        nextPiece.setBackground(Color.BLACK);
        CompoundBorder border = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.CYAN, 10),
                BorderFactory.createLineBorder(Color.DARK_GRAY, 5));
        nextPiece.setBorder(border);

        styleSet1 = new SimpleAttributeSet();
        StyleConstants.setFontSize(styleSet1, 18);
        StyleConstants.setFontFamily(styleSet1, "Courier New");
        StyleConstants.setBold(styleSet1, true);
        StyleConstants.setForeground(styleSet1, Color.WHITE);
        StyleConstants.setAlignment(styleSet1, StyleConstants.ALIGN_CENTER);

    }
    static Block getRandomBlock() {
        Random rnd = new Random(System.currentTimeMillis());
        int block = rnd.nextInt(6);
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
    public static void paintNextPiece(){
        nextPiece.removeAll();
        Block s=getRandomBlock();
        BlockQueue.add(s);
        board2=new int[2][6];
        placeblock(BlockQueue.get(1));
        BlockQueue.remove(0);
        JLabel nexttext=new JLabel("Next");
        nexttext.setForeground(Color.WHITE);
        nexttext.setFont(new Font("Arial", Font.PLAIN,20));
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
    }
    public static void drawBoard() {
        StringBuffer sb = new StringBuffer();
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
        doc.setParagraphAttributes(0, doc.getLength(), styleSet1, false);
        nextPiece.setStyledDocument(doc);
    }
    public SidePanel(){
        //sidepanel에 다음블록패널 추가
        this.setLayout(new BorderLayout());
        nextPiece.setLayout(new BorderLayout());
        nextPiece.setBorder(new LineBorder(Color.BLACK));
        this.add(nextPiece, BorderLayout.NORTH);
        //
        scorePanel.setLayout(new BorderLayout());
        scorePanel.setBackground(Color.BLACK);
        scorePanel.setPreferredSize(new Dimension(20,50));
        this.add(scorePanel,BorderLayout.CENTER);
        //점수패널
        totalscore=0;
        score.setLayout(new BorderLayout());
        score.setBorder(new LineBorder(Color.CYAN));
        score.setForeground(Color.YELLOW);
        score.setFont(new Font("Arial", Font.PLAIN,25));
        score.setText("score: "+totalscore);
        score.setPreferredSize(new Dimension(10,50));
        scorePanel.add(score,BorderLayout.NORTH);
        //next텍스트 표시
        JLabel nexttext=new JLabel("Next");
        nexttext.setForeground(Color.WHITE);
        nexttext.setFont(new Font("Arial", Font.PLAIN,25));
        nexttext.setBorder(new EmptyBorder(0,0,10,0));
        nextPiece.add(nexttext, BorderLayout.NORTH);
        nextPiece.setBorder(new EmptyBorder(5, 5, 5, 5));
        //nextpiece 초기상태 설정
        board2= new int[2][6];
        setNextPanel();
        BlockQueue=new ArrayList<Block>();
        cur=getRandomBlock();
        BlockQueue.add(cur);
        placeblock(BlockQueue.get(0));
        drawBoard();
    }
}
