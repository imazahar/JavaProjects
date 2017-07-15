package com.mycompany.ds.linkedlist;

/**
 * @author Azaharuddin
 * @copyright Azaharuddin
 * List Operation Interface
 */

import java.util.LinkedList;

public interface IListOperation {

	LinkedList<Integer> multiply(LinkedList<Integer> listA, LinkedList<Integer> listB);

	void sumList(LinkedList<Integer> tempA, LinkedList<Integer> tempB, LinkedList<Integer> tempC);

}