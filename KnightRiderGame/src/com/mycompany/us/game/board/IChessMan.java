package com.mycompany.us.game.board;

/**
 * @author Azaharuddin
 * @copyright Azaharuddin
 * Chessman interface
 */

import java.util.LinkedHashMap;
import java.util.List;

import com.mycompany.us.game.chessgame.coordinates.ICoordinates;

public interface IChessMan {

	String setCoordinates(int iPlayer, IBoard iBoard, int iRow, int iCol, boolean bCont);
	
	String getType();
	
	void setType(String strType);

	ICoordinates getCurrentCoord();

	LinkedHashMap<Integer, List<ICoordinates>> getAllMoves();

	String getNextMoveHelp(IBoard iBoard);

}