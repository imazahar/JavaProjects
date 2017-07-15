package com.mycompany.us.game.chessgame.utils;

/**
 * @author Azaharuddin
 * @copyright Azaharuddin
 * Print file. Print data in logs using logger.
 */

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mycompany.us.game.board.IBoard;
import com.mycompany.us.game.board.IChessMan;
import com.mycompany.us.game.chessgame.coordinates.ICoordinates;
import com.mycompany.us.game.file.strings.GameStrings;
import com.mycompany.us.game.chessgame.coordinates.ChessmanCoordinates;

public final class TestPrint {
	private static final Logger log = LogManager.getLogger(TestPrint.class);

	public static void printAllPlayerCoordinates(List<List<ChessmanCoordinates>> playerList) {
		log.entry();
		try {
			for (int i = 0; playerList != null && i < playerList.size(); i++) {
				List<ChessmanCoordinates> playerCoords = playerList.get(i);
				System.out.println(GameStrings.getInstance().getString(StringIdentifierConst.PLAYER) + " " + (i + 1)
						+ " " + GameStrings.getInstance().getString(StringIdentifierConst.COORDINATES) + ": ");
				for (int j = 0; playerCoords != null && j < playerCoords.size(); j++) {
					int[] arr = playerCoords.get(j).getCoordinates();
					System.out.print("(" + arr[0] + "," + arr[1] + ")");
				}
				System.out.println("");
			}
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(e.getMessage());
			Utils.err.add(StringIdentifierConst.PRINT_ALL_FAILED);
			Utils.err.add(StringIdentifierConst.EXCEPTION_FALIED);

		} finally {
			log.exit();
		}
	}

	public static void printAllPlayerMoves(Logger logObj, int iPlayer, IBoard iBoard) {
		log.entry();
		try {
			for (int i = 0; i < iPlayer; i++) {
				Utils.insertResultLog(logObj,
						GameStrings.getInstance().getString(StringIdentifierConst.PLAYER) + " " + (i + 1), false);
				HashMap<String, List<IChessMan>> playerChessMan = iBoard.getAllChessManList(i);
				for (Entry<String, List<IChessMan>> entry : playerChessMan.entrySet()) {
					List<IChessMan> chessMenList = entry.getValue();
					for (int j = 0; chessMenList != null && j < chessMenList.size(); j++) {
						LinkedHashMap<Integer, List<ICoordinates>> allMoves = chessMenList.get(j).getAllMoves();
						if (allMoves == null || allMoves.size() == 0) {
							Utils.insertResultLog(logObj,
									GameStrings.getInstance().getString(StringIdentifierConst.NO_MOVES), false);
						}
						for (Entry<Integer, List<ICoordinates>> entryMoves : allMoves.entrySet()) {
							List<ICoordinates> iCoordList = entryMoves.getValue();
							printCoordinates(logObj, entryMoves.getKey(), iCoordList);
							Utils.insertResultLog(logObj, "", false);
						}
					}
					Utils.insertResultLog(logObj, "", false);
				}
			}
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(e.getMessage());
			Utils.err.add(StringIdentifierConst.PRINT_MOVE_FAILED);
			Utils.err.add(StringIdentifierConst.EXCEPTION_FALIED);

		} finally {
			log.exit();
		}

	}

	public static void printCoordinates(Logger logObj, int Move, List<ICoordinates> iCoordList) {
		log.entry();
		try {
			Utils.insertResultLog(logObj,
					GameStrings.getInstance().getString(StringIdentifierConst.MOVE) + " " + Move + " : ", true);
			String strDataInfo = "";
			for (int k = 0; iCoordList != null && k < iCoordList.size(); k++) {
				strDataInfo += "(" + iCoordList.get(k).getRowCoordinate() + "," + iCoordList.get(k).getColCoordinate()
						+ ")";

			}
			if (!strDataInfo.isEmpty()) {
				Utils.insertResultLog(logObj, strDataInfo, true);
			}
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(e.getMessage());
			Utils.err.add(StringIdentifierConst.PRINT_COORD_FAILED);
			Utils.err.add(StringIdentifierConst.EXCEPTION_FALIED);

		} finally {
			log.exit();
		}
	}
	
	public static void printPaths(LinkedList<LinkedList<ICoordinates>> paths){
		if(paths == null){
			return;
		}
		for (LinkedList<ICoordinates> list : paths){
			for (ICoordinates node : list) {
				Utils.insertResultLog(log, "(" + node.getRowCoordinate() + "," + node.getColCoordinate() + ")", true);
			}
			Utils.insertResultLog(log, "", false);
		}
		
	}
	
	public static String getPathString(LinkedList<ICoordinates> path){
		String strPath = "";
		if(path == null){
			return strPath;
		}
		for (ICoordinates node : path) {
			strPath += "(" + node.getRowCoordinate() + "," + node.getColCoordinate() + ")";
		}
		return strPath;
	}
}
