package com.mycompany.us.game.chessgame.coordinates;

/**
 * @author Azaharuddin
 * @copyright Azaharuddin
 * Coordinates Interface
 */

import com.mycompany.us.game.board.IChessMan;

public interface ICoordinates {

	void setRowCoordinate(int iRow);

	void setColCoordinate(int iCol);

	int getRowCoordinate();

	int getColCoordinate();

	int[] getCoordinates();
	
	void setCoordinates(IChessMan chessMan, int iPlayer, int iRow, int iCol);

	void setPlayer(int m_iPlayer);

	int getPlayer();

	void setChessMan(IChessMan m_IChessMan);

	IChessMan getChessMan();

}