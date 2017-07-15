package com.mycompany.us.game.chessgame.movevalidator;

/**
 * @author Azaharuddin
 * @copyright Azaharuddin
 * Knight muzzle move validator
 */

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mycompany.us.game.board.IChessMan;
import com.mycompany.us.game.chessgame.coordinates.ICoordinates;
import com.mycompany.us.game.chessgame.utils.StringIdentifierConst;
import com.mycompany.us.game.chessgame.utils.Utils;
import com.mycompany.us.game.chessgame.coordinates.ChessmanCoordinates;

public class KnightMoveValidator implements IMoveValidator {

	private static final Logger log = LogManager.getLogger(KnightMoveValidator.class);

	@Override
	public List<ICoordinates> validateMove(int iPlayer, IChessMan iChessMan, ICoordinates currentCoord, int iRow,
			int iCol) {
		log.entry();
		List<ICoordinates> resultList = null;
		try {
			int iRowVal = Math.abs(currentCoord.getRowCoordinate() - iRow);
			int iColVal = Math.abs(currentCoord.getColCoordinate() - iCol);
			int iFinalRow = -1;
			int iFinalCol = -1;

			
			if ((iRowVal == 2 && iColVal == 1) || (iRowVal == 1 && iColVal == 2)) {
				
				/*Add current coordinates in list to show move*/
				resultList = new ArrayList<ICoordinates>();
				currentCoord.setPlayer(-1);
				currentCoord.setChessMan(null);
				resultList.add(currentCoord);
				
				iFinalCol = currentCoord.getColCoordinate() < iCol ? currentCoord.getColCoordinate() + 1
						: currentCoord.getColCoordinate() - 1;
				iFinalRow = currentCoord.getRowCoordinate() < iRow ? currentCoord.getRowCoordinate() + 1
						: currentCoord.getRowCoordinate() - 1;
				
				/*Add next coordinates which has been traversed in list to show move*/
				// 1 block horizontal and 2 block vertical movement
				if (iRowVal == 2 && iColVal == 1) {
					// add horizontal movement coordinates
					resultList.add(new ChessmanCoordinates(currentCoord.getRowCoordinate(), iFinalCol));

					// add vertical movement coordinates
					resultList.add(new ChessmanCoordinates(iFinalRow, iFinalCol));
				}
				// 1 block vertical movement and 2 block horizontal
				else {
					// add vertical movement coordinates
					resultList.add(new ChessmanCoordinates(iFinalRow, currentCoord.getColCoordinate()));

					// add horizontal movement coordinates
					resultList.add(new ChessmanCoordinates(iFinalRow, iFinalCol));
				}
				
				/*Add target coordinates in list to show move*/
				ChessmanCoordinates coords = new ChessmanCoordinates(iRow, iCol);
				currentCoord.setPlayer(iPlayer);
				currentCoord.setChessMan(iChessMan);
				resultList.add(coords);
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

	public KnightMoveValidator() {

	}

}
