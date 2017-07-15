package com.mycompany.us.game.chessgame.coordinates;

/**
 * @author Azaharuddin
 * @copyright Azaharuddin
 * Chessman Coordinates Class
 */

import com.mycompany.us.game.board.IChessMan;

public class ChessmanCoordinates implements ICoordinates {
	private int m_iCoordinateDimension = 2;
	private int[] m_ArrayCoordinates = null;
	private IChessMan m_iChessMan = null;
	private int m_iPlayer = -1;
	
	public ChessmanCoordinates(int iRow, int iCol){
		m_ArrayCoordinates = new int[m_iCoordinateDimension];
		setRowCoordinate(iRow);
		setColCoordinate(iCol);
	}
	
	public ChessmanCoordinates(IChessMan knightMuzzle, int iPlayer, int iRow, int iCol){
		m_ArrayCoordinates = new int[m_iCoordinateDimension];
		setRowCoordinate(iRow);
		setColCoordinate(iCol);
		setChessMan(knightMuzzle);
		setPlayer(iPlayer);
	}

	@Override
	public void setRowCoordinate(int iRow){
		m_ArrayCoordinates[0] = iRow;
	}
	

	@Override
	public void setColCoordinate(int iCol){
		m_ArrayCoordinates[1] = iCol;
	}

	@Override
	public int getRowCoordinate(){
		return m_ArrayCoordinates[0];
	}
	

	@Override
	public int getColCoordinate(){
		return m_ArrayCoordinates[1];
	}

	@Override
	public int[] getCoordinates(){
		return m_ArrayCoordinates;
	}

	@Override
	public void setCoordinates(IChessMan chessMan, int iPlayer, int iRow, int iCol) {
		m_ArrayCoordinates[0] = iRow;
		m_ArrayCoordinates[1] = iCol;
	}

	/**
	 * @return the m_IChessMan
	 */
	@Override
	public IChessMan getChessMan() {
		return m_iChessMan;
	}

	/**
	 * @param m_IChessMan the m_IChessMan to set
	 */
	@Override
	public void setChessMan(IChessMan m_IChessMan) {
		this.m_iChessMan = m_IChessMan;
	}

	/**
	 * @return the m_iPlayer
	 */
	@Override
	public int getPlayer() {
		return m_iPlayer;
	}

	/**
	 * @param m_iPlayer the m_iPlayer to set
	 */
	@Override
	public void setPlayer(int m_iPlayer) {
		this.m_iPlayer = m_iPlayer;
	}
	
}
