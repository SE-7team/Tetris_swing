package env;
import component.Board;
import main.Tetris;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Scoreboard extends JFrame {
    private JScrollPane scroll;
    private DefaultTableModel model;
    private JTable table;
    //문제가 생길경우 파일 경로를 확인해주세요.
    private String JSON_FILE="Tetris_swing/userScore.json";
    private KeyListener playerKeyListener;
    public static JSONArray jarray=new JSONArray();
    public  Scoreboard()  {
        super("User Score Board");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setBackground(Color.BLACK);
        model = new DefaultTableModel();
        model.addColumn("순위");
        model.addColumn("이름");
        model.addColumn("점수");

        initTable();
        //간격 설정, 가운데 정렬
        settingForTable();
        //키보드 입력설정
        playerKeyListener = new PlayerKeyListener();
        addKeyListener(playerKeyListener);
        setFocusable(true);
        requestFocus();
        loadScoreBoard();
        //각 칼럼을 누르면 칼럼별로 정렬됨
        TableRowSorter<DefaultTableModel> rowSorter = new TableRowSorter(model);
        table.setRowSorter(rowSorter);
        rowSorter.addRowSorterListener(e -> updateRanks());
    }
    public  Scoreboard(String name, int score)  {
        super("User Score Board");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setBackground(Color.BLACK);
        model = new DefaultTableModel();
        model.addColumn("순위");
        model.addColumn("이름");
        model.addColumn("점수");
        // 테이블 생성
        initTable();
        settingForTable();
        updateScoreBoard(name,score);
        playerKeyListener = new PlayerKeyListener();
        addKeyListener(playerKeyListener);
        setFocusable(true);
        requestFocus();
        loadScoreBoard();
        //각 칼럼을 누르면 칼럼별로 정렬됨
        TableRowSorter rowSorter = new TableRowSorter<TableModel>(table.getModel());
        rowSorter.addRowSorterListener(e -> updateRanks());
        table.setRowSorter(rowSorter);
    }
    private void initTable(){
        // 테이블 생성
        table = new JTable(model);
        scroll = new JScrollPane(table);
        add(scroll, BorderLayout.CENTER);
        scroll.setBackground(Color.BLACK);
        CompoundBorder border = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.CYAN, 10),
                BorderFactory.createLineBorder(Color.DARK_GRAY, 5));
        scroll.setBorder(border);
        //테이블을 수정불가능하게 해줘
        table.setEnabled(false);
    }
    private void settingForTable(){
        DefaultTableCellRenderer celAlignCenter = new DefaultTableCellRenderer();
        celAlignCenter.setHorizontalAlignment(JLabel.CENTER);
        table.getColumn("순위").setCellRenderer(celAlignCenter);
        table.getColumn("이름").setCellRenderer(celAlignCenter);
        table.getColumn("점수").setCellRenderer(celAlignCenter);
        table.setRowHeight(50);
        table.getColumnModel().getColumn(0).setPreferredWidth(50);
        Font newFont = new Font("Malgun Gothic", Font.PLAIN, 20);
        table.setFont(newFont);
        this.getContentPane().add(scroll, BorderLayout.CENTER);
    }
    private void loadScoreBoard()   {
        try{
            File file=new File(JSON_FILE);
            if(file.exists()){
                JSONParser parser = new JSONParser();
                JSONArray jsonArr = (JSONArray) parser.parse(new FileReader(JSON_FILE));

                // jsonArr를 점수 기준으로 내림차순 정렬(초기상태)
                jsonArr.sort((o1, o2) -> {
                    long score1 = (Long) ((JSONObject) o1).get("score");
                    long score2 = (Long) ((JSONObject) o2).get("score");
                    return Long.compare(score2, score1);
                });
                // 일일이 꺼내서 JSONObject로 사용
                if(jsonArr.size() > 0 ) {
                    for(int i=0; i<jsonArr.size(); i++){
                        JSONObject jsonObj = (JSONObject)jsonArr.get(i);
                        String name=(String)jsonObj.get("name");
                        Long score=(Long)jsonObj.get("score");
                        model.addRow(new Object[]{i+1, name, score});
                    }
                }
            }else{
                JOptionPane.showMessageDialog(this,"저장된 스코어가 없습니다.", "Message",JOptionPane.INFORMATION_MESSAGE);
            }
        } catch(IOException | ParseException e){
            e.printStackTrace();
        }


    }
    //게임이 끝난 후 스코어보드를 보여줄 때
    public void updateScoreBoard(String name, int score){
        JLabel nowUserScore=new JLabel();
        nowUserScore.setText(name+": "+score);
        nowUserScore.setForeground(Color.ORANGE);
        nowUserScore.setFont(new Font("Malgun Gothic", Font.PLAIN,20));
        nowUserScore.setBorder(new EmptyBorder(0,0,15,0));
        this.getContentPane().add(nowUserScore, BorderLayout.NORTH);
    }
    //정렬 순서에 따른 순위 갱신 for Chatgpt 3.5
    private void updateRanks() {
        // Get the number of rows in the table model
        int rowCount = model.getRowCount();

        // Update ranks based on sorted indices
        for (int i = 0; i < rowCount; i++) {
            int modelIndex = table.convertRowIndexToModel(i); // Convert view index to model index
            model.setValueAt(i + 1, modelIndex, 0); // Set rank in the model
        }
    }

    public class PlayerKeyListener implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {
        }
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {

                case KeyEvent.VK_ENTER:
                    Tetris.fromScoretoMain();
                    break;
                case KeyEvent.VK_P:
                    System.exit(0);
                    break;
                }
        }
        @Override
        public void keyReleased(KeyEvent e) {
        }
    }
}
