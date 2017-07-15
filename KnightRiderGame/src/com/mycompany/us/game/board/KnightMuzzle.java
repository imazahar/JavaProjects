package com.mycompany.us.game.board;

/**
 * @author Azaharuddin
 * @copyright Azaharuddin
 * Knight Muzzle Class
 */

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mycompany.us.game.chessgame.coordinates.*;
import com.mycompany.us.game.chessgame.utils.GameConst;
import com.mycompany.us.game.chessgame.utils.StringIdentifierConst;
import com.mycompany.us.game.chessgame.utils.Utils;
import com.mycompany.us.game.file.strings.GameStrings;

public class KnightMuzzle implements IChessMan {
	private LinkedHashMap<Integer, List<ICoordinates>> m_AllMoves = null;
	private static final Logger log = LogManager.getLogger(KnightMuzzle.class);
	private LinkedList<LinkedList<ICoordinates>> m_paths = null;
	private ICoordinates m_CurrentCoord = null;
	private int m_iMove = 0;
	private String m_strType = GameConst.KNIGHT_PLAYER_TYPE;

	@Override
	public LinkedHashMap<Integer, List<ICoordinates>> getAllMoves() {
		return m_AllMoves;
	}

	@Override
	public ICoordinates getCurrentCoord() {
		return m_CurrentCoord;
	}

	public KnightMuzzle() {
		m_AllMoves = new LinkedHashMap<Integer, List<ICoordinates>>();
	}

	@Override
	/* Set new coordinates of knight muzzle */
	public String setCoordinates(int iPlayer, IBoard iBoard, int iRow, int iCol, boolean bCont) {
		log.entry();
		String strRet = GameConst.FALSE;
		try {
			List<ICoordinates> lCoords = m_AllMoves.get(m_iMove);
			if (lCoords == null) {
				/* Call Board to validate new coordinates before moving */
				/*
				 * Get all list of coordinates traversed to move from current
				 * coordinates to new coordinates. And set that list with move.
				 * For First move do not allow 8,8 coordinate move.
				 */
				if (m_iMove > 0 || (m_iMove == 0 && (iRow < 8 || iCol < 8))) {
					if (m_iMove == 0) {
						/* Generate Path for help to user */
						m_paths = Utils.getPath(iRow, iCol, 8, 8);
						/*
						 * If Path is not generated it means from start
						 * position, player will never reach winning position
						 * 8,8. Inform player about this.
						 */
						if (m_paths != null && m_paths.size() == 0) {
							Utils.insertResultLog(log,
									GameStrings.getInstance().getString(StringIdentifierConst.INAPPROPRIATE_MOVE),
									false);
						} else {
							strRet = GameConst.TRUE;
						}
					} else {
						strRet = GameConst.TRUE;
					}
					if (strRet.equalsIgnoreCase(GameConst.TRUE)) {
						lCoords = iBoard.move(iPlayer, this, m_CurrentCoord, iRow, iCol);
						if (lCoords != null) {
							if (m_iMove > 0 && m_paths != null && m_paths.size() > 0 && bCont) {
								boolean bfound = false;
								/*
								 * Find out if move of player is same as in
								 * system learned shortest path move to win
								 */
								for (ICoordinates cord : m_paths.getFirst()) {
									if (cord.getRowCoordinate() == lCoords.get(lCoords.size() - 1).getRowCoordinate()
											&& cord.getColCoordinate() == lCoords.get(lCoords.size() - 1)
													.getColCoordinate()) {
										bfound = true;
										break;
									}
								}
								/*
								 * Make sure is system learned move is not
								 * pre-occupied by another player
								 */
								if (!bfound && !getNextMoveHelp(iBoard).isEmpty()) {
									Utils.insertResultLog(log, GameStrings.getInstance()
											.getString(StringIdentifierConst.CORRECT_BUT_NOT_FAST_MOVE), false);
									iBoard.getOccupiedCoordList().remove(iBoard.getKey(iRow, iCol));
									return GameConst.CONT;
								}
							}
							/*
							 * Update Current Coordinates and move informations
							 */
							m_CurrentCoord = lCoords.get(lCoords.size() - 1);
							m_AllMoves.put(m_iMove, lCoords);
							strRet = GameConst.TRUE;
							m_iMove++;
						} else {
							strRet = GameConst.FALSE;
						}
					}

				}
			}
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(e.getMessage());
			Utils.err.add(StringIdentifierConst.KNIGHT_MOVE_SETTING_FAILED);
			Utils.err.add(StringIdentifierConst.EXCEPTION_FALIED);
		} finally {
			log.exit();
		}
		return strRet;
	}

	@Override
	public String getType() {
		return m_strType;
	}

	@Override
	public void setType(String strType) {
		m_strType = strType;
	}

	@Override
	public String getNextMoveHelp(IBoard iBoard) {
		m_paths.clear();
		m_paths = Utils.getPath(m_CurrentCoord.getRowCoordinate(), m_CurrentCoord.getColCoordinate(), 8, 8);
		String result = "";
		if (iBoard != null && m_paths != null && m_paths.size() > 0 && m_paths.getFirst().size() > 1
				&& m_paths.getFirst().get(1) != null && !iBoard.getOccupiedCoordList().containsKey(iBoard.getKey(
						m_paths.getFirst().get(1).getRowCoordinate(), m_paths.getFirst().get(1).getColCoordinate()))) {
			result += "(" + m_paths.getFirst().get(1).getRowCoordinate() + ","
					+ m_paths.getFirst().get(1).getColCoordinate() + ")";
		}
		return result;
	}

}
