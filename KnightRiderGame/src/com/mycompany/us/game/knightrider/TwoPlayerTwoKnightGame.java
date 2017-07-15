package com.mycompany.us.game.knightrider;

import java.io.File;

/**
 * @author Azaharuddin
 * @copyright Azaharuddin
 * TwoPlayerTwoKnightGame class. This class is actually play class for 2 player each having 1 knight game
 * Knight of a player is considered one if reaches 8,8 coordinate first.
 * It composes chess board and chessmen for each player
 */

import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mycompany.us.game.IGame;
import com.mycompany.us.game.board.ChessBoard;
import com.mycompany.us.game.board.IBoard;
import com.mycompany.us.game.board.IChessMan;
import com.mycompany.us.game.board.KnightMuzzle;
import com.mycompany.us.game.chessgame.coordinates.ICoordinates;
import com.mycompany.us.game.chessgame.utils.GameConst;
import com.mycompany.us.game.chessgame.utils.StringIdentifierConst;
import com.mycompany.us.game.chessgame.utils.TestPrint;
import com.mycompany.us.game.chessgame.utils.Utils;
import com.mycompany.us.game.file.strings.GameStrings;

public class TwoPlayerTwoKnightGame implements IGame {

	protected int m_iPlayer = 2;
	protected static final Logger m_ResultLogger = LogManager.getLogger("ResultLogger");
	protected IBoard m_iBoard = null;
	/* Player to list of chessman map */
	protected HashMap<Integer, HashMap<String, List<IChessMan>>> m_playerChessMenMap = null;

	private boolean m_bloop = true;
	private static final Logger log = LogManager.getLogger(TwoPlayerTwoKnightGame.class);
	private GameStrings m_GameStrings = null;

	public TwoPlayerTwoKnightGame() {
		setUp();
		if (m_bloop) {
			m_GameStrings = GameStrings.getInstance();
			Utils.insertResultLog(m_ResultLogger, Utils.NEW_LINE_SEPERATOR + Utils.NEW_LINE_SEPERATOR
					+ m_GameStrings.getString(StringIdentifierConst.COPY_RIGHT), false);
			Utils.insertResultLog(m_ResultLogger, m_GameStrings.getString(StringIdentifierConst.WELCOME_MSG), false);
			Utils.insertResultLog(m_ResultLogger, m_GameStrings.getString(StringIdentifierConst.PLAYER_NOTE), false);
			Utils.insertResultLog(m_ResultLogger, m_GameStrings.getString(StringIdentifierConst.WHO_WILL_WIN)
					+ Utils.NEW_LINE_SEPERATOR + Utils.NEW_LINE_SEPERATOR, false);
		}
	}

	public TwoPlayerTwoKnightGame(boolean skip) {
		setUp();
	}

	/**
	 * Setup method. Each player will have 1 knight muzzle. Later this class can
	 * be extended so that each play can have multiple chessman and accordingly
	 * Play API will change
	 */
	public void setUp() {
		log.entry();
		try {
			Utils.DIR_PATH = (new File(new File(".").getAbsolutePath())).getCanonicalPath();
			if (!Utils.ConfigSetup(Utils.DIR_PATH + GameConst.FILE_SEPERATOR + GameConst.CONFIG_FILE, Utils.LANG)) {
				Utils.insertResultLog(m_ResultLogger,
						StringIdentifierConst.EXCEPTION_FALIED + Utils.NEW_LINE_SEPERATOR + Utils.NEW_LINE_SEPERATOR,
						false);
				m_bloop = false;
				return;
			}
			m_playerChessMenMap = new HashMap<Integer, HashMap<String, List<IChessMan>>>();

			for (int i = 0; i < m_iPlayer; i++) {
				HashMap<String, List<IChessMan>> chessManAndTypeMap = new HashMap<String, List<IChessMan>>();
				List<IChessMan> chessManList = new ArrayList<IChessMan>();
				chessManList.add(new KnightMuzzle());
				chessManAndTypeMap.put(GameConst.KNIGHT_PLAYER_TYPE, chessManList);
				m_playerChessMenMap.put(i, chessManAndTypeMap);
			}

			// Instantiate Board
			m_iBoard = new ChessBoard(m_playerChessMenMap);
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(e.getMessage());
			Utils.err.add(StringIdentifierConst.SETUP_FAILED);
			Utils.err.add(StringIdentifierConst.EXCEPTION_FALIED);
		} finally {
			log.exit();
		}
	}

	@Override
	/**
	 * Play API which orchestrate this game
	 */
	public void play() {
		String input;
		int iCount = m_iPlayer;

		int iRow = -1;
		int iCol = -1;
		iCount = 0;
		Scanner console = new Scanner(System.in);

		while (m_bloop) {
			if (Utils.err.size() > 0 && Utils.err.get(0) != null
					&& Utils.err.get(Utils.err.size() - 1).equalsIgnoreCase(StringIdentifierConst.EXCEPTION_FALIED)) {
				if (Utils.err.size() > 1) {
					Utils.insertResultLog(m_ResultLogger, Utils.err.get(Utils.err.size() - 2)
							+ Utils.NEW_LINE_SEPERATOR + Utils.NEW_LINE_SEPERATOR, false);
				}
				Utils.insertResultLog(m_ResultLogger,
						Utils.err.get(Utils.err.size() - 1) + Utils.NEW_LINE_SEPERATOR + Utils.NEW_LINE_SEPERATOR,
						false);
				console.close();
				return;
			}
			List<IChessMan> chessManList = m_iBoard.getChessMenListByType(iCount, GameConst.KNIGHT_PLAYER_TYPE);
			playerHelpMove(iCount, chessManList);

			Utils.insertResultLog(m_ResultLogger, m_GameStrings.getString(StringIdentifierConst.STOP_MSG) + " ", true);
			Utils.insertResultLog(m_ResultLogger, m_GameStrings.getString(StringIdentifierConst.ENTER_COORDINATE) + " "
					+ (iCount + 1) + " like x,y : ", true);
			input = console.nextLine();

			if (input.equalsIgnoreCase("Stop")) {
				stopGame();
			} else if (input.equalsIgnoreCase("help")) {
				helpMethod(chessManList);
			} else {

				String arrInput[] = Utils.getSplitString(input, GameConst.KNIGHT_RIDER_INPUT_SEPERATOR);
				if (!Utils.knightStringValidator(arrInput)) {
					Utils.insertResultLog(m_ResultLogger,
							m_GameStrings.getString(StringIdentifierConst.INCORRECT_COORDINATES), false);
					continue;
				}

				iRow = Utils.knightCoordinateValidator(arrInput[0]);
				if (iRow == -1) {
					Utils.insertResultLog(m_ResultLogger, m_GameStrings.getString(StringIdentifierConst.INCORRECT_ROW),
							false);
					continue;
				}

				iCol = Utils.knightCoordinateValidator(arrInput[1]);
				if (iCol == -1) {
					Utils.insertResultLog(m_ResultLogger, m_GameStrings.getString(StringIdentifierConst.INCORRECT_COL),
							false);
					continue;
				}

				// TestPrint.printPaths(Utils.getAllPaths(iRow, iCol, 8, 8));

				if (chessManList != null) {
					iCount = validateMove(iCount, iRow, iCol, console, chessManList);
				} else {
					Utils.insertResultLog(m_ResultLogger,
							m_GameStrings.getString(StringIdentifierConst.INCORRECT_SETUP), false);
					Utils.insertResultLog(m_ResultLogger, m_GameStrings.getString(StringIdentifierConst.ABORT), false);
					break;
				}

				if (iCount == m_iPlayer) {
					iCount = 0;
				}
			}

		}
		console.close();
	}

	/**
	 * @param chessManList
	 */
	private void helpMethod(List<IChessMan> chessManList) {
		String strHelp = chessManList.get(0).getNextMoveHelp(m_iBoard);
		if (strHelp.isEmpty()) {
			Utils.insertResultLog(m_ResultLogger, m_GameStrings.getString(StringIdentifierConst.NO_HELP) + " ", true);
		} else {
			Utils.insertResultLog(m_ResultLogger, m_GameStrings.getString(StringIdentifierConst.NEXT_MOVE) + strHelp,
					true);
		}
	}

	/**
	 * @param iCount
	 * @param iRow
	 * @param iCol
	 * @param console
	 * @param chessManList
	 * @return
	 */
	private int validateMove(int iCount, int iRow, int iCol, Scanner console, List<IChessMan> chessManList) {
		String input;
		String strRet;
		strRet = chessManList.get(0).setCoordinates(iCount, m_iBoard, iRow, iCol, true);
		if (strRet.equalsIgnoreCase(GameConst.TRUE)) {
			generateMoveInfo(iCount, chessManList);
			iCount++;
		} else if (strRet.equalsIgnoreCase(GameConst.CONT)) {
			input = console.nextLine();
			if (input.equalsIgnoreCase("no")) {
				strRet = chessManList.get(0).setCoordinates(iCount, m_iBoard, iRow, iCol, false);
				if (strRet.equalsIgnoreCase(GameConst.TRUE)) {
					generateMoveInfo(iCount, chessManList);
					iCount++;
				}
			}

		} else {
			Utils.insertResultLog(m_ResultLogger,
					m_GameStrings.getString(StringIdentifierConst.INCORRECT_MOVE) + " " + (iCount + 1), false);
		}
		return iCount;
	}

	/**
	 * @param iCount
	 * @param chessManList
	 */
	private void playerHelpMove(int iCount, List<IChessMan> chessManList) {
		if (chessManList != null) {
			ICoordinates iCurrCoord = chessManList.get(0).getCurrentCoord();
			if (iCurrCoord != null) {
				Utils.insertResultLog(m_ResultLogger,
						Utils.NEW_LINE_SEPERATOR + Utils.NEW_LINE_SEPERATOR
								+ m_GameStrings.getString(StringIdentifierConst.PLAYER) + (iCount + 1) + " "
								+ m_GameStrings.getString(StringIdentifierConst.IS_AT) + " " + "("
								+ iCurrCoord.getRowCoordinate() + "," + iCurrCoord.getColCoordinate() + ")",
						false);
				if (!chessManList.get(0).getNextMoveHelp(m_iBoard).isEmpty()) {
					Utils.insertResultLog(m_ResultLogger, m_GameStrings.getString(StringIdentifierConst.HELP_SYSTEM),
							false);
				}
			}
		} else {
			Utils.insertResultLog(m_ResultLogger, Utils.NEW_LINE_SEPERATOR + Utils.NEW_LINE_SEPERATOR, false);
		}
	}

	/**
	 * @param iCount
	 * @param chessManList
	 *            Generate each move information
	 */
	private void generateMoveInfo(int iCount, List<IChessMan> chessManList) {
		try {
			int move = chessManList.get(0).getAllMoves().size() - 1;
			List<ICoordinates> lCoords = chessManList.get(0).getAllMoves().get(move);
			Utils.insertResultLog(m_ResultLogger,
					m_GameStrings.getString(StringIdentifierConst.PLAYER) + " " + (iCount + 1) + " : ", true);
			TestPrint.printCoordinates(m_ResultLogger, move, lCoords);
			Utils.insertResultLog(m_ResultLogger, "", false);
			ICoordinates iLastCoord = lCoords.get(lCoords.size() - 1);
			if (iLastCoord.getColCoordinate() == GameConst.LAST_MOVE
					&& iLastCoord.getRowCoordinate() == GameConst.LAST_MOVE) {
				gameOver(iCount);
			}
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(e.getMessage());
			Utils.err.add(StringIdentifierConst.MOVE_INFO_GEN_FAILED);
			Utils.err.add(StringIdentifierConst.EXCEPTION_FALIED);
		}
	}

	/**
	 * @param iCount
	 *            Game over API is called when one of the player win i.e.
	 *            player's knight muzzle first reached 8.8 coordinate
	 */
	private void gameOver(int iCount) {
		log.entry();
		try {
			Utils.insertResultLog(m_ResultLogger, Utils.NEW_LINE_SEPERATOR + "Player " + (iCount + 1) + " "
					+ m_GameStrings.getString(StringIdentifierConst.WON_MSG) + Utils.NEW_LINE_SEPERATOR, false);
			printMoves(false);
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(e.getMessage());
			Utils.err.add(StringIdentifierConst.GAME_OVER_FAILED);
			Utils.err.add(StringIdentifierConst.EXCEPTION_FALIED);
		} finally {
			log.exit();
			m_bloop = false;
		}
	}

	/**
	 * Stop game API is called when "stop" is given on command. This API has
	 * been made public so that later this can be called from outside e.g. via
	 * UI channel
	 */
	public void stopGame() {
		log.entry();
		try {
			Utils.insertResultLog(m_ResultLogger, Utils.NEW_LINE_SEPERATOR
					+ m_GameStrings.getString(StringIdentifierConst.BROKE_MSG) + Utils.NEW_LINE_SEPERATOR, false);
			printMoves(true);
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(e.getMessage());
			Utils.err.add(StringIdentifierConst.GAME_STOP_FAILED);
			Utils.err.add(StringIdentifierConst.EXCEPTION_FALIED);
		} finally {
			log.exit();
			m_bloop = false;
		}
	}

	/**
	 * 
	 */
	private void printMoves(boolean bstop) {
		Utils.insertResultLog(m_ResultLogger,
				m_GameStrings.getString(StringIdentifierConst.PLAYER_MOVE) + Utils.NEW_LINE_SEPERATOR, false);
		TestPrint.printAllPlayerMoves(m_ResultLogger, m_iPlayer, m_iBoard);
		if (bstop) {
			Utils.insertResultLog(m_ResultLogger, m_GameStrings.getString(StringIdentifierConst.ABORT), false);
		} else {
			Utils.insertResultLog(m_ResultLogger, m_GameStrings.getString(StringIdentifierConst.OVER), false);
		}
		Utils.insertResultLog(m_ResultLogger, m_GameStrings.getString(StringIdentifierConst.BYE), false);
	}
}
