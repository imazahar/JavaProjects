package com.mycompany.us.game.chessgame.utils;

/**
 * @author Azaharuddin
 * @copyright Azaharuddin
 * Coordinate Graph Interface
 */

import java.util.HashMap;
import java.util.LinkedList;

import com.mycompany.us.game.chessgame.coordinates.ICoordinates;

public interface IGraph {

	HashMap<HashMap<String, ICoordinates>, LinkedList<HashMap<String, ICoordinates>>> getGraphObject();

	/*Add edge*/
	boolean addEdge(ICoordinates node1, ICoordinates node2);

	/*Get list of coordinates saved in graph for particular coordinate*/
	LinkedList<ICoordinates> getList(ICoordinates node1);

	/*Get path between start and end coordinates*/
	LinkedList<LinkedList<ICoordinates>> getPath(ICoordinates start, ICoordinates end);

	void printGraph();

}