package com.mycompany.us.game.chessgame.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

/**
 * @author Azaharuddin
 * @copyright Azaharuddin
 * Utility File
 */

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.mycompany.us.game.chessgame.coordinates.ChessmanCoordinates;
import com.mycompany.us.game.chessgame.coordinates.ICoordinates;
import com.mycompany.us.game.file.strings.GameStrings;

public final class Utils {
	private static final Logger log = LogManager.getLogger(Utils.class);
	public static final String NEW_LINE_SEPERATOR = System.getProperty("line.separator");
	public static String DIR_PATH = null;
	private static String m_strStringPath = "";
	public static String LANG = "";
	public static List<String> err = new ArrayList<String>();

	public static String[] getSplitString(String strSource, String strSeperator) {
		if (strSource == null) {
			String[] retArray = new String[2];
			retArray[0] = "-1";
			retArray[1] = "-1";
			return retArray;
		}
		return strSource.split(strSeperator);
	}

	public static boolean knightStringValidator(String[] strArrSource) {
		boolean bRet = true;
		if (strArrSource == null || strArrSource.length != 2) {
			bRet = false;
		}
		return bRet;
	}

	public static int knightCoordinateValidator(String strCoordinate) {
		int iCoordiante = -1;
		try {
			iCoordiante = Integer.parseInt(strCoordinate.trim());
			if (iCoordiante < 1 || iCoordiante > 8) {
				iCoordiante = -1;
			}
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(e.getMessage());
			Utils.err.add(StringIdentifierConst.COORD_VAL_FAILED);
			Utils.err.add(StringIdentifierConst.EXCEPTION_FALIED);
			iCoordiante = -1;
		}
		return iCoordiante;
	}

	public static void insertResultLog(Logger logObj, String strMsg, boolean bNoline) {
		logObj.info(strMsg);
		if (bNoline) {
			System.out.print(strMsg);
		} else {
			System.out.println(strMsg);
		}
	}

	public static boolean ConfigSetup(String strConfigFile, String strlang) {
		Properties prop = new Properties();
		InputStream input = null;
		boolean bRet = true;
		try {

			input = new FileInputStream(strConfigFile);

			// load a properties file
			prop.load(input);

			// get the property value and print it out
			String propPath = (GameConst.STRINGS + "_" + prop.getProperty(GameConst.LANG).toLowerCase()).toLowerCase();
			m_strStringPath = prop.getProperty(propPath);

			if (m_strStringPath == null) {
				log.error(propPath + " " + StringIdentifierConst.PROPERTY_DOES_NOT_EXIST);
			}
			bRet = GameStrings.getInstance().LoadFile(m_strStringPath);
			
			LANG = prop.getProperty(GameConst.LANG);

			// printSortedMap();

		} catch (IOException ex) {
			log.error(ex.getMessage());
			bRet = false;
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					log.error(e.getMessage());
					Utils.err.add(StringIdentifierConst.CONFIG_SETUP_FAILED);
					Utils.err.add(StringIdentifierConst.EXCEPTION_FALIED);
					bRet = false;
				}
			}
		}
		return bRet;

	}

	public static String getStringFilePath() {
		return m_strStringPath;
	}

	public static IGraph createGraph(int iRow, int iCol) {
		IGraph graph = new CoordinatesGraph();
		try {
			ICoordinates start = new ChessmanCoordinates(iRow, iCol);
			createGraphForCoords(graph, start);
			//graph.printGraph();
		} catch (Exception e) {
			//e.printStackTrace();
			log.error(e.getMessage());
			Utils.err.add(StringIdentifierConst.GRAPH_CREATE_FAILED);
			Utils.err.add(StringIdentifierConst.EXCEPTION_FALIED);
		}
		return graph;
	}

	public static LinkedList<LinkedList<ICoordinates>> getPath(int iStartRow, int iStartCol, int iEndRow,
			int iEndCol) {
		LinkedList<LinkedList<ICoordinates>> paths = null;
		try {
			ICoordinates start = new ChessmanCoordinates(iStartRow, iStartCol);
			ICoordinates end = new ChessmanCoordinates(iEndRow, iEndCol);
			IGraph graph = createGraph(iStartRow, iStartCol);
			if(graph != null){
				paths = graph.getPath(start, end);
				graph.getGraphObject().clear();
			}
		} catch (Exception e) {
			//e.printStackTrace();
			log.error(e.getMessage());
			Utils.err.add(StringIdentifierConst.GET_PATH_FAILED);
			Utils.err.add(StringIdentifierConst.EXCEPTION_FALIED);
		}
		return paths;
	}

	public static void createGraphForCoords(IGraph graph, ICoordinates start) {
		if(start == null){
			return;
		}
		if (start.getColCoordinate() == GameConst.LAST_MOVE && start.getRowCoordinate() == GameConst.LAST_MOVE) {
			return;
		}

		int iRow = start.getRowCoordinate();
		int iCol = start.getColCoordinate();
		boolean bAdd = false;
		ICoordinates next = null;

		// 2 block vertical(row) and 1 block horizontal(col)
		if (iRow + 2 <= GameConst.LAST_MOVE) {

			if (iCol + 1 <= GameConst.LAST_MOVE) {
				next = new ChessmanCoordinates(iRow + 2, iCol + 1);
			}
			bAdd = graph.addEdge(start, next);

			if (iCol - 1 > 0) {
				next = new ChessmanCoordinates(iRow + 2, iCol - 1);
				bAdd = graph.addEdge(start, next);
			}
		}

		if (iRow - 2 > 0) {
			if (iCol - 1 > 0) {
				next = new ChessmanCoordinates(iRow - 2, iCol - 1);
				bAdd = graph.addEdge(start, next);
			}
			if (iCol + 1 <= GameConst.LAST_MOVE) {
				next = new ChessmanCoordinates(iRow - 2, iCol + 1);
				bAdd = graph.addEdge(start, next);
			}
		}

		// 1 block vertical(row) and 2 block horizontal(col)
		if (iRow + 1 <= GameConst.LAST_MOVE) {
			if (iCol + 2 <= GameConst.LAST_MOVE) {
				next = new ChessmanCoordinates(iRow + 1, iCol + 2);
				bAdd = graph.addEdge(start, next);
			}
			if (iCol - 2 > 0) {
				next = new ChessmanCoordinates(iRow + 1, iCol - 2);
				bAdd = graph.addEdge(start, next);
			}
		}

		if (iRow - 1 > 0) {
			if (iCol - 2 > 0) {
				next = new ChessmanCoordinates(iRow - 1, iCol - 2);
				bAdd = graph.addEdge(start, next);
			}
			if (iCol + 2 <= GameConst.LAST_MOVE) {
				next = new ChessmanCoordinates(iRow - 1, iCol + 2);
				bAdd = graph.addEdge(start, next);
			}
		}

		if (bAdd) {
			LinkedList<ICoordinates> nodes = graph.getList(start);
			for (ICoordinates node : nodes) {
				createGraphForCoords(graph, node);
			}
		}
		else{
			return;
		}
	}

}
