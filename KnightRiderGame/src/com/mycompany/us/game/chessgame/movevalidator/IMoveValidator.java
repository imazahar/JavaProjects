package com.mycompany.us.game.chessgame.movevalidator;

/**
 * @author Azaharuddin
 * @copyright Azaharuddin
 * Chess Man Move Validator Interface. This interface can be used to implement move validator for any kind of chessman
 */

import java.util.List;

import com.mycompany.us.game.board.IChessMan;
import com.mycompany.us.game.chessgame.coordinates.ICoordinates;

public interface IMoveValidator {

	List<ICoordinates> validateMove(int iPlayer, IChessMan iChessMan, ICoordinates currentCoord, int iRow, int iCol);

}