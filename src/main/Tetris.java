package main;

import InProgress.ImportSettings;
import env.Scoreboard;
import env.TetrisMain;
import component.Board;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class Tetris {

	private static ImportSettings settings;

	private static TetrisMain main;

	private static Board tetrisGame;

	private static Scoreboard tetrisScore;
	private static int ResolutionSizeX;

	private static int ResolutionSizeY;

	private static void initialize() {
		settings = new ImportSettings();
		ResolutionSizeX = Integer.parseInt(settings.getSetting("ResolutionSizeX"));
		ResolutionSizeY = Integer.parseInt(settings.getSetting("ResolutionSizeY"));
	}

	public static void startGame() {
		main.setVisible(false);
		tetrisGame = new Board();
		tetrisGame.setSize(ResolutionSizeX, ResolutionSizeY);
		tetrisGame.setLocationRelativeTo(null);
		tetrisGame.setVisible(true);
	}
	public static void showScoreBoard()  {
		main.setVisible(false);
		tetrisScore = new Scoreboard();
		tetrisScore.setSize(ResolutionSizeX,ResolutionSizeY);
		tetrisScore.setLocationRelativeTo(null);
		tetrisScore.setVisible(true);
	}

	public static void showNowScoreBoard(String name,int score){
		main.setVisible(false);
		tetrisScore = new Scoreboard(name,score);
		tetrisScore.setSize(ResolutionSizeX,ResolutionSizeY);
		tetrisScore.setLocationRelativeTo(null);
		tetrisScore.setVisible(true);
	}
	public static void LevelMain() {
		tetrisGame.setVisible(false);
		main.setVisible(true);
	}
	public static void fromScoretoMain() {
		tetrisScore.setVisible(false);
		main.setVisible(true);
	}

//we
	public static void main(String[] args) {
		initialize();
		main = new TetrisMain(ResolutionSizeX,ResolutionSizeY);
		main.setLocationRelativeTo(null);
		main.setVisible(true);
	}
}

