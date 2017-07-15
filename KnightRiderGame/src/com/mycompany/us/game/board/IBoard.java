package com.mycompany.us.game.board;

/**
 * @author Azaharuddin
 * @copyright Azaharuddin
 * Board Interface. This has been provide to extend to various types of boards if required
 */

import java.util.HashMap;
import java.util.List;

import com.mycompany.us.game.chessgame.coordinates.ICoordinates;

public interface IBoard {

	List<ICoordinates> move(int iPlayer, IChessMan iChessMan, ICoordinates currentCoord, int iRow, int iCol);

	public HashMap<String, List<IChessMan>> getAllChessManList(int iPlayer) ;

	List<IChessMan> getChessMenListByType(int iPlayer, String strType);
	
	HashMap<String, ICoordinates> getOccupiedCoordList();
	
	String getKey(int iRow, int iCol);
}