package com.mycompany.us.game.chessgame.utils;

/**
 * @author Azaharuddin
 * @copyright Azaharuddin
 * Constants File
 */

public class GameConst {
	//String Const 
	public static String KNIGHT_RIDER_INPUT_SEPERATOR    =",";
	public static String KNIGHT_PLAYER_TYPE    ="KNIGHT";
	public static String MULTIPLE_PLAYER    ="Multiple";
	public static String STRINGS    ="strings";
	public static String CONFIG_FILE    ="config\\config.properties";
	public static String FILE_SEPERATOR = "//";
	public static String NODE_MESSAGES = "messages";
	public static String LANG = "Language";
	public static String CONT = "Continue";
	public static String TRUE = "TRUE";
	public static String FALSE = "FALSE";
	
	//Int const
	public static int LAST_MOVE = 8;
	
    private GameConst(){
        throw new AssertionError();
    }
}
