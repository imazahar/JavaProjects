package com.mycompany.us.game.knightrider;

/**
 * @author Azaharuddin
 * @copyright Azaharuddin
 * Driver Program
 */

import com.mycompany.us.game.IGame;
import com.mycompany.us.game.chessgame.utils.GameConst;

public class KnightDriver {

	public static void main(String[] args) {

		IGame game = null;
		if (args == null || args.length == 0) {
			game = new TwoPlayerTwoKnightGame();
		} else {
			if(args.length == 1 && args[0].equalsIgnoreCase(GameConst.MULTIPLE_PLAYER)){
				game = new NPlayerNKnightGame();
			}
		}
		if (game != null) {
			game.play();
		}

	}

}
