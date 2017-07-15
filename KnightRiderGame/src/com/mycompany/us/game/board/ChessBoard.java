package com.mycompany.us.game.board;

/**
 * @author Azaharuddin
 * @copyright Azaharuddin
 * ChessBorad Class
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mycompany.us.game.chessgame.coordinates.ICoordinates;
import com.mycompany.us.game.chessgame.coordinates.ChessmanCoordinates;
import com.mycompany.us.game.chessgame.movevalidator.IMoveValidator;
import com.mycompany.us.game.chessgame.movevalidator.KnightMoveValidator;
import com.mycompany.us.game.chessgame.utils.GameConst;
import com.mycompany.us.game.chessgame.utils.StringIdentifierConst;
import com.mycompany.us.game.chessgame.utils.Utils;

public class ChessBoard implements IBoard {

	private HashMap<Integer, HashMap<String, List<IChessMan>>> m_PlayerChessMenMap = null;
	private HashMap<String, ICoordinates> m_OccupiedCoordsList = null;
	private IMoveValidator m_knightMoveValidtor = null;
	private static final Logger log = LogManager.getLogger(ChessBoard.class);
	
	public HashMap<String, ICoordinates> getOccupiedCoordList() {
		return m_OccupiedCoordsList;
	}


	public ChessBoard(HashMap<Integer, HashMap<String, List<IChessMan>>> playerChessMenMap) {
		m_PlayerChessMenMap = playerChessMenMap;
		m_knightMoveValidtor = new KnightMoveValidator();
		m_OccupiedCoordsList = new HashMap<String, ICoordinates>();
	}

	@Override
	/* Get All ChessMan for a given player */
	public HashMap<String, List<IChessMan>> getAllChessManList(int iPlayer) {
		HashMap<String, List<IChessMan>> iAllChessMenList = null;
		if (m_PlayerChessMenMap != null) {
			iAllChessMenList = m_PlayerChessMenMap.get(iPlayer);
		}
		return iAllChessMenList;
	}

	@Override
	/* Get All ChessMan of a particular type for a given player */
	public List<IChessMan> getChessMenListByType(int iPlayer, String strType) {
		List<IChessMan> iChessMenList = null;
		if (m_PlayerChessMenMap != null) {
			iChessMenList = m_PlayerChessMenMap.get(iPlayer) != null ? m_PlayerChessMenMap.get(iPlayer).get(strType)
					: null;
		}
		return iChessMenList;
	}

	@Override
	/* Move ChessMan to give row, col coordinate for given player */
	public List<ICoordinates> move(int iPlayer, IChessMan iChessMan, ICoordinates currentCoord, int iRow, int iCol) {
		// Validate and move
		List<ICoordinates> resultList = null;
		String type = iChessMan.getType();
		/*
		 * Validate move for chessman of type Knight muzzle. Later this code can be
		 * extended and added for different types of chessman
		 */
		if (type.equalsIgnoreCase(GameConst.KNIGHT_PLAYER_TYPE)) {
			resultList = validateAndUpdateKnightCoords(iPlayer, iChessMan, currentCoord, iRow, iCol);
		}
		return resultList;

	}

	/**
	 * @param iPlayer
	 * @param iChessMan
	 * @param currentCoord
	 * @param iRow
	 * @param iCol
	 * @param resultList
	 * @return
	 */
	private List<ICoordinates> validateAndUpdateKnightCoords(int iPlayer, IChessMan iChessMan,
			ICoordinates currentCoord, int iRow, int iCol) {
		log.entry();
		String coordKey = getKey(iRow, iCol);
		List<ICoordinates> resultList = null;
		try {
			/*
			 * Check whether the coordinate is already occupied by any other
			 * knight muzzle
			 */
			boolean bExist = m_OccupiedCoordsList.containsKey(coordKey);
			if (currentCoord != null && !bExist) {
				/*Validate the knight muzzle move*/
				resultList = validateKnightMove(iPlayer, iChessMan, currentCoord, iRow, iCol);
			} else {
				if (!bExist) {
					resultList = new ArrayList<ICoordinates>();
					ICoordinates coords = new ChessmanCoordinates(iChessMan, iPlayer, iRow, iCol);
					resultList.add(coords);
				}
			}
			if (resultList != null) {
				if (currentCoord != null) {
					/*Remove old coordinate occupancy in chessboard*/
					m_OccupiedCoordsList
							.remove(getKey(currentCoord.getRowCoordinate(), currentCoord.getColCoordinate()));
				}
				/*Set coordinate occupied in chessboard*/
				m_OccupiedCoordsList.put(coordKey, resultList.get(resultList.size() - 1));
			}
			if (bExist) {
				ICoordinates existCoord = m_OccupiedCoordsList.get(coordKey);
				if (existCoord != null) {
					Utils.insertResultLog(log, "", false);
				}
			}
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(e.getMessage());
			Utils.err.add(StringIdentifierConst.KNIGHT_MOVE_VAL_FAILED);
			Utils.err.add(StringIdentifierConst.EXCEPTION_FALIED);
		} finally {
			log.exit();
		}
		return resultList;
	}

	/*Validate Knight Move using Knight Move Validator*/
	private List<ICoordinates> validateKnightMove(int iPlayer, IChessMan iChessMan, ICoordinates currentCoord, int iRow,
			int iCol) {
		/*Delegate Call to Knight Move Validator to validate knight move to row, col coordinate*/
		List<ICoordinates> resultList = m_knightMoveValidtor.validateMove(iPlayer, iChessMan, currentCoord, iRow, iCol);
		return resultList;
	}

	public String getKey(int iRow, int iCol) {
		return iRow + "<->" + iCol;
	}

}
