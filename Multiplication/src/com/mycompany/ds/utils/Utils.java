
package com.mycompany.ds.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
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

public final class Utils {
	private static final Logger log = LogManager.getLogger(Utils.class);
	public static final String NEW_LINE_SEPERATOR = System.getProperty("line.separator");
	public static String DIR_PATH = null;
	public static List<String> err = new ArrayList<String>();
	public static Logger m_ResultLogger = LogManager.getLogger("ResultLogger");
	private static LinkedList<LinkedList<Integer>> m_numberLists = new LinkedList<LinkedList<Integer>>();

	public static void insertResultLog(Logger logObj, String strMsg, boolean bNoline) {
		logObj.info(strMsg);
		if (bNoline) {
			System.out.print(strMsg);
		} else {
			System.out.println(strMsg);
		}
	}

	//read numbers from files in file folder e.g. file//number1.txt, file//number2.txt
	private static void readNumbers(String strPath) {
		FileReader inputStream = null;
		LinkedList<Integer> listNum = new LinkedList<Integer>();
		try {
			try {
				inputStream = new FileReader(strPath);
			} catch (FileNotFoundException e) {
				log.error(e.getMessage());
			}

			int c;
			System.out.println("");
			try {
				while ((c = inputStream.read()) != -1) {
					if (c >= DSConst.MIN_BOUND && c <= DSConst.MAX_BOUND) {
						listNum.addLast(Character.getNumericValue(c));
					} else {
						//In case input is not a digit, exit with logging error.
						listNum.clear();
						Utils.insertResultLog(log, StringIdentifierConst.INCORRECT_DATA + " " + 
								Character.toString ((char) c), false);
						break;
					}
				}
			} catch (IOException e) {
				log.error(e.getMessage());
			}
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					log.error(e.getMessage());
				}
			}

		}
		Utils.insertResultLog(Utils.m_ResultLogger, "Number " + (m_numberLists.size()+1) + "  = " + getStringList(listNum), false);
		m_numberLists.add(listNum);

	}
	
	//print digits in list
	public static void printListA(LinkedList<Integer> list) {
		try {
			for (Integer node : list) {
				System.out.print(node);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
	
	//get string form of digits in list
	public static String getStringList(LinkedList<Integer> list) {
		String strResult = "";
		try {
			for (Integer node : list) {
				strResult += node;
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return strResult;
	}

	//config set up using config.properties file
	public static boolean ConfigSetup(String strConfigFile) {
		Properties prop = new Properties();
		InputStream input = null;
		boolean bRet = true;
		try {

			input = new FileInputStream(strConfigFile);

			// load a properties file
			prop.load(input);

			readNumbers(Utils.DIR_PATH + DSConst.FILE_SEPERATOR + prop.getProperty(DSConst.NUMBER_1));
			readNumbers(Utils.DIR_PATH + DSConst.FILE_SEPERATOR + prop.getProperty(DSConst.NUMBER_2));

			if (m_numberLists != null && m_numberLists.size() == 2 && m_numberLists.get(0).size() != 0
					&& m_numberLists.get(1).size() != 0) {
				if (m_numberLists.get(0).size() > m_numberLists.get(1).size()) {
					Collections.reverse(m_numberLists.get(0));
				} else {
					Collections.reverse(m_numberLists.get(1));
				}
			}

		} catch (IOException ex) {
			log.error(ex.getMessage());
			bRet = false;
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					log.error(e.getMessage());
					bRet = false;
				}
			}
		}
		return bRet;

	}

	/**
	 * @return the m_numberLists
	 */
	public static LinkedList<LinkedList<Integer>> getNumberLists() {
		return m_numberLists;
	}

}
