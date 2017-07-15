package com.mycompany.ds.multiplication.driver;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mycompany.ds.linkedlist.IListOperation;
import com.mycompany.ds.linkedlist.ListOperation;
import com.mycompany.ds.utils.DSConst;
import com.mycompany.ds.utils.StringIdentifierConst;
import com.mycompany.ds.utils.Utils;

public class MultiplicationDriver {
	
	private static final Logger log = LogManager.getLogger(MultiplicationDriver.class);

	public static void main(String[] args) {

		try {
			Utils.DIR_PATH = (new File(new File(".").getAbsolutePath())).getCanonicalPath();
		} catch (IOException e) {
			log.error(e.getMessage());
		}
		try {
			if (!Utils.ConfigSetup(Utils.DIR_PATH + DSConst.FILE_SEPERATOR + DSConst.CONFIG_FILE)) {
				Utils.insertResultLog(Utils.m_ResultLogger,
						StringIdentifierConst.EXCEPTION_FALIED + Utils.NEW_LINE_SEPERATOR + Utils.NEW_LINE_SEPERATOR,
						false);
				return;
			}

			LinkedList<LinkedList<Integer>> numberLists = Utils.getNumberLists();

			if (numberLists == null || numberLists.size() != 2 || numberLists.get(0).size() == 0
					|| numberLists.get(1).size() == 0) {
				return;
			}

			IListOperation lop = new ListOperation();

			String strResult = "";
			
			if (numberLists.get(0).size() > numberLists.get(1).size()) {
				strResult = Utils.getStringList(lop.multiply(numberLists.get(0), numberLists.get(1)));
			} else {
				strResult = Utils.getStringList(lop.multiply(numberLists.get(1), numberLists.get(0)));
			}

			Utils.insertResultLog(Utils.m_ResultLogger, "Multiplication Result = " + strResult, false);
		} catch (Exception e) {
			log.error(e.getMessage());
		}

		/*
		 * LinkedList<Integer> listA = new LinkedList<Integer>();
		 * LinkedList<Integer> listB = new LinkedList<Integer>();
		 * 
		 * listA.addLast(1); listA.addLast(2); listA.addLast(3);
		 * 
		 * 
		 * listB.addLast(2); listB.addLast(2); listB.addLast(2);
		 * 
		 * System.out.println(""); lop.multiply(listA, listB);
		 */
	}

}
