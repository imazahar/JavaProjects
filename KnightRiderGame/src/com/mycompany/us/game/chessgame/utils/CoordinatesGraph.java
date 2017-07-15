package com.mycompany.us.game.chessgame.utils;

/**
 * @author Azaharuddin
 * @copyright Azaharuddin
 * Coordinates Graph Class. It maintains graph of coordinates. It is used to get shortest path from start to end.
 */

import java.util.LinkedList;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;

import com.mycompany.us.game.chessgame.coordinates.ICoordinates;

public class CoordinatesGraph implements IGraph {
	/*Complex graph data structure*/
	private HashMap<HashMap<String, ICoordinates>, LinkedList<HashMap<String, ICoordinates>>> m_Graph = null;
	private static final Logger log = LogManager.getLogger(CoordinatesGraph.class);

	public CoordinatesGraph() {
		m_Graph = new HashMap<HashMap<String, ICoordinates>, LinkedList<HashMap<String, ICoordinates>>>();
	}

	/* (non-Javadoc)
	 * @see com.mycompany.us.game.chessgame.utils.IGraph#getGraphObject()
	 */
	@Override
	public HashMap<HashMap<String, ICoordinates>, LinkedList<HashMap<String, ICoordinates>>> getGraphObject() {
		return m_Graph;
	}

	/*Add edge*/
	/* (non-Javadoc)
	 * @see com.mycompany.us.game.chessgame.utils.IGraph#addEdge(com.mycompany.us.game.chessgame.coordinates.ICoordinates, com.mycompany.us.game.chessgame.coordinates.ICoordinates)
	 */
	@Override
	public boolean addEdge(ICoordinates node1, ICoordinates node2) {
		if (node1 == null || node2 == null) {
			return false;
		}
		boolean bAdd = true;
		try {
			ICoordinates coord = null;
			LinkedList<HashMap<String, ICoordinates>> listcoord = null;
			HashMap<String, ICoordinates> key = null;
			LinkedList<ICoordinates> list = null;

			for (Entry<HashMap<String, ICoordinates>, LinkedList<HashMap<String, ICoordinates>>> entry : m_Graph
					.entrySet()) {
				key = entry.getKey();
				coord = key.get(genKey(node1));
				listcoord = entry.getValue();
				if (coord != null) {
					break;
				}
			}

			if (coord == null) {
				key = new HashMap<String, ICoordinates>();
				listcoord = new LinkedList<HashMap<String, ICoordinates>>();
			} else {
				list = getList(listcoord);
				for (ICoordinates node : list) {
					if (node != null && node2 != null && node.getRowCoordinate() == node2.getRowCoordinate()
							&& node.getColCoordinate() == node2.getColCoordinate()) {
						bAdd = false;
						break;
					}
				}
			}

			if (bAdd) {
				HashMap<String, ICoordinates> listdata = null;
				if (coord != null) {
					listdata = new HashMap<String, ICoordinates>();
					listdata.put(genKey(node2), node2);
					listcoord.add(listdata);
				} else {
					key.put(genKey(node1), node1);

					listdata = new HashMap<String, ICoordinates>();
					listdata.put(genKey(node2), node2);

					listcoord.add(listdata);

					m_Graph.put(key, listcoord);
				}
			}
		} catch (Exception e) {
			//e.printStackTrace();
			log.error(e.getMessage());
			Utils.err.add(StringIdentifierConst.GRAPH_SETUP_FAILED);
			Utils.err.add(StringIdentifierConst.EXCEPTION_FALIED);
		}

		return bAdd;
	}

	/*Key generator for coordinates*/
	private String genKey(ICoordinates node) {
		if (node != null) {
			return "(" + node.getRowCoordinate() + "," + node.getColCoordinate() + ")";
		}
		return "";
	}

	/*Get list of coordinates saved in graph for particular node*/
	private LinkedList<ICoordinates> getList(LinkedList<HashMap<String, ICoordinates>> listcoord) {
		LinkedList<ICoordinates> list = new LinkedList<ICoordinates>();
		try {
			for (HashMap<String, ICoordinates> node : listcoord) {
				for (Entry<String, ICoordinates> entry : node.entrySet()) {
					list.add(entry.getValue());
				}
			}
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(e.getMessage());
			Utils.err.add(StringIdentifierConst.GRAPH_SETUP_FAILED);
			Utils.err.add(StringIdentifierConst.EXCEPTION_FALIED);
		}
		return list;
	}

	/*Get list of coordinates saved in graph for particular coordinate*/
	/* (non-Javadoc)
	 * @see com.mycompany.us.game.chessgame.utils.IGraph#getList(com.mycompany.us.game.chessgame.coordinates.ICoordinates)
	 */
	@Override
	public LinkedList<ICoordinates> getList(ICoordinates node1) {
		LinkedList<ICoordinates> list = new LinkedList<ICoordinates>();
		try {
			LinkedList<HashMap<String, ICoordinates>> listcoord = null;
			HashMap<String, ICoordinates> key = null;
			ICoordinates coord = null;
			for (Entry<HashMap<String, ICoordinates>, LinkedList<HashMap<String, ICoordinates>>> entry : m_Graph
					.entrySet()) {
				key = entry.getKey();
				coord = key.get(genKey(node1));
				listcoord = entry.getValue();
				if (coord != null) {
					break;
				}
			}
			for (HashMap<String, ICoordinates> node : listcoord) {
				for (Entry<String, ICoordinates> entry : node.entrySet()) {
					list.add(entry.getValue());
				}
			}
		} catch (Exception e) {
			//e.printStackTrace();
			log.error(e.getMessage());
			Utils.err.add(StringIdentifierConst.GRAPH_SETUP_FAILED);
			Utils.err.add(StringIdentifierConst.EXCEPTION_FALIED);
		}
		return list;
	}

	/*Get path between start and end coordinates*/
	/* (non-Javadoc)
	 * @see com.mycompany.us.game.chessgame.utils.IGraph#getPath(com.mycompany.us.game.chessgame.coordinates.ICoordinates, com.mycompany.us.game.chessgame.coordinates.ICoordinates)
	 */
	@Override
	public LinkedList<LinkedList<ICoordinates>> getPath(ICoordinates start, ICoordinates end) {
		LinkedList<LinkedList<ICoordinates>> paths = new LinkedList<LinkedList<ICoordinates>>();
		try {
			LinkedList<ICoordinates> visited = new LinkedList<ICoordinates>();
			LinkedList<ICoordinates> list = new LinkedList<ICoordinates>();
			getPathCoordinates(start, end, visited, list, paths);
		} catch (Exception e) {
			// e.printStackTrace();
			log.error(e.getMessage());
			Utils.err.add(StringIdentifierConst.GRAPH_PATH_FAILED);
			Utils.err.add(StringIdentifierConst.EXCEPTION_FALIED);
		}
		return paths;
	}

	@SuppressWarnings("unchecked")
	/*Get path coordinates between start and end coordinates recursively*/
	private void getPathCoordinates(ICoordinates start, ICoordinates end, LinkedList<ICoordinates> visited,
			LinkedList<ICoordinates> list, LinkedList<LinkedList<ICoordinates>> paths) {

		try {
			list.add(start);
			boolean bfound = false;

			if (start.getRowCoordinate() == end.getRowCoordinate()
					&& start.getColCoordinate() == end.getColCoordinate()) {
				// visited.addLast(end);
				paths.add((LinkedList<ICoordinates>) list.clone());
				// visited.removeLast();
			}

			for (ICoordinates nodeV : visited) {
				if (nodeV.getRowCoordinate() == start.getRowCoordinate()
						&& nodeV.getColCoordinate() == start.getColCoordinate()) {
					bfound = true;
					break;
				}
			}

			if (!bfound) {
				visited.add(start);
			}

			bfound = false;

			LinkedList<ICoordinates> nodes = getList(start);
			/*Traverse adjacent list*/
			for (ICoordinates node : nodes) {
				for (ICoordinates nodeV : visited) {
					if (nodeV.getRowCoordinate() == node.getRowCoordinate()
							&& nodeV.getColCoordinate() == node.getColCoordinate()) {
						bfound = true;
						break;
					}
				}

				if (!bfound) {
					getPathCoordinates(node, end, visited, list, paths);
				}
			}

			int index = 0;
			bfound = false;
			for (ICoordinates nodeV : visited) {
				if (nodeV.getRowCoordinate() == start.getRowCoordinate()
						&& nodeV.getColCoordinate() == start.getColCoordinate()) {
					bfound = true;
					break;
				}
				index++;
			}

			if (bfound) {
				visited.remove(index);
			}

			list.removeLast();
		} catch (Exception e) {
			//e.printStackTrace();
			log.error(e.getMessage());
			Utils.err.add(StringIdentifierConst.GRAPH_PATH_FAILED);
			Utils.err.add(StringIdentifierConst.EXCEPTION_FALIED);
		}
	}

	/* (non-Javadoc)
	 * @see com.mycompany.us.game.chessgame.utils.IGraph#printGraph()
	 */
	@Override
	public void printGraph() {
		LinkedList<HashMap<String, ICoordinates>> listcoord = null;
		HashMap<String, ICoordinates> key = null;

		for (Entry<HashMap<String, ICoordinates>, LinkedList<HashMap<String, ICoordinates>>> entry : m_Graph
				.entrySet()) {
			key = entry.getKey();
			for (Entry<String, ICoordinates> entry3 : key.entrySet()) {
				System.out.print(entry3.getKey());
			}
			listcoord = entry.getValue();
			for (HashMap<String, ICoordinates> nodeC : listcoord) {
				for (Entry<String, ICoordinates> entry2 : nodeC.entrySet()) {
					System.out.print(entry2.getKey());
				}
			}
			System.out.println("");
		}
	}

};
