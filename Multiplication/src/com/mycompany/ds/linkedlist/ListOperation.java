package com.mycompany.ds.linkedlist;


/**
 * @author Azaharuddin
 * @copyright Azaharuddin
 * List Operation Class
 */

import java.util.LinkedList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mycompany.ds.utils.StringIdentifierConst;

public class ListOperation implements IListOperation {

	private static final Logger log = LogManager.getLogger(ListOperation.class);

	/* (non-Javadoc)
	 * @see com.mycompany.ds.linkedlist.IListOperation#multiply(java.util.LinkedList, java.util.LinkedList)
	 */
	@Override
	//multiply two list
	public LinkedList<Integer> multiply(LinkedList<Integer> listA, LinkedList<Integer> listB) {
		if (listA == null || listB == null || listA.getLast() == null || listB.getLast() == null) {
			return null;
		}
		LinkedList<Integer> tempA = new LinkedList<Integer>();
		LinkedList<Integer> tempB = new LinkedList<Integer>();
		LinkedList<Integer> tempC = new LinkedList<Integer>();
		try {
			Integer val = listB.pollLast();
			int count = 0;
			//Get each digit of list B
			while (val != null) {
				//multiply list A with digit of list B
				if (tempA.peekLast() != null) {
					sigleMultiply(listA, tempB, val, count);
				} else {
					sigleMultiply(listA, tempA, val, count);
				}
				if (tempA.peekLast() != null && tempB.peekLast() != null) {
					// printListA(tempA);
					// printListA(tempB);
					//Keep summing the intermediate list of multiplication
					sumList(tempA, tempB, tempC);
					for (Integer node : tempC) {
						tempA.add(node);
					}
					
					//printListA(tempA);
					//System.out.println("");
					tempC.clear();
				}
				count++;
				val = listB.pollLast();
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			log.error(StringIdentifierConst.MULTIPLICATION_FAILED);
			log.error(StringIdentifierConst.EXCEPTION_FALIED);
		}
		//System.out.println("");
		//printListA(tempA);
		if(tempA.get(0) == 0){
			tempA.clear();
			tempA.add(0);
		}
		return tempA;
	}

	/* (non-Javadoc)
	 * @see com.mycompany.ds.linkedlist.IListOperation#sumList(java.util.LinkedList, java.util.LinkedList, java.util.LinkedList)
	 */
	@Override
	//sum of two list
	public void sumList(LinkedList<Integer> tempA, LinkedList<Integer> tempB, LinkedList<Integer> tempC) {
		Integer valA = tempA.pollLast();
		Integer valB = tempB.pollLast();
		Integer car = 0;
		try {
			while (valA != null && valB != null) {
				valA += valB + car;
				car = Math.abs(valA / 10);
				valA -= car * 10;
				tempC.addFirst(valA);
				valA = tempA.pollLast();
				valB = tempB.pollLast();
			}
			
			while (valA != null) {
				valA += car;
				car = Math.abs(valA / 10);
				valA -= car * 10;
				tempC.addFirst(valA);
				valA = tempA.pollLast();
			}
			
			while (valB != null) {
				valB += car;
				car = Math.abs(valB / 10);
				valB -= car * 10;
				tempC.addFirst(valB);
				valB = tempB.pollLast();
			}
			
			if(car > 0){
				tempC.addFirst(car);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			log.error(StringIdentifierConst.MULTIPLICATION_FAILED);
			log.error(StringIdentifierConst.EXCEPTION_FALIED);
		}
	}

	//list multiplication by single digit helper method
	private void sigleMultiply(LinkedList<Integer> listA, LinkedList<Integer> tempA, Integer val, int count) {
		Integer idata = 0;
		Integer car = 0;
		try {
			for (Integer node : listA) {
				idata = val * node + car;
				car = Math.abs(idata / 10);
				idata -= car * 10;
				tempA.addFirst(idata);
			}
			if(car > 0){
				tempA.addFirst(car);
			}
			for (int i = 0; i < count; i++) {
				tempA.addLast(0);
			}
			//printListA(tempA);
			//System.out.println("");
		} catch (Exception e) {
			log.error(e.getMessage());
			log.error(StringIdentifierConst.MULTIPLICATION_FAILED);
			log.error(StringIdentifierConst.EXCEPTION_FALIED);
		}
	}

}
