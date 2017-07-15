package com.mycompany.us.game.knightrider;

/**
 * @author Azaharuddin
 * @copyright Azaharuddin
 * NPlayerNKnightGame class. This class has been built to play N Player each having 1 knight game
 * This is derived from TwoPlayerTwoKnightGame class.
 * Knight of a player is considered one if reaches 8,8 coordinate first.
 */

import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mycompany.us.game.IGame;
import com.mycompany.us.game.chessgame.utils.Utils;

public class NPlayerNKnightGame extends TwoPlayerTwoKnightGame implements IGame {
	
	private static final Logger log = LogManager.getLogger(NPlayerNKnightGame.class);

	public NPlayerNKnightGame() {
		super(false);
		setUp();
	}

	/**
	 * Setup method. Asks for how many players want to play games. valid range is 2-63. Its a funny range ;-).
	 * Please choose right number of players which can play and really one. Refrain from choosing non feasible number
	 * of players
	 */
	public void setUp() {
		log.entry();
		boolean bRightNoOfPlayers = false;
		@SuppressWarnings("resource")
		Scanner console = new Scanner(System.in);
		while (!bRightNoOfPlayers) {
			Utils.insertResultLog(m_ResultLogger, "Welcome to N Player Knight Rider Game: ", false);
			Utils.insertResultLog(m_ResultLogger, "Each Player will have 1 knight: ", false);
			Utils.insertResultLog(m_ResultLogger, "Please Enter Number of Players: ", false);
			m_iPlayer = -1;
			try {
				m_iPlayer = Integer.parseInt(console.nextLine());
				if (m_iPlayer < 2 || m_iPlayer > 63) {
					Utils.insertResultLog(m_ResultLogger, "Incorrect number of players.", false);
					continue;
				}
				bRightNoOfPlayers = true;
			} catch (Exception e) {
				// e.printStackTrace();
				log.error(e.getMessage());
				Utils.insertResultLog(log, "Bad Data. Exception. Incorrect number of players.", false);
				continue;
			}
		}
		log.exit();
	}
}