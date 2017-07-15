package com.mycompany.us.game.file.strings;

/**
 * @author auddin
 *
 */

import java.io.File;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.mycompany.us.game.chessgame.utils.GameConst;
import com.mycompany.us.game.chessgame.utils.StringIdentifierConst;
import com.mycompany.us.game.chessgame.utils.Utils;

public class GameStrings {
	private static GameStrings m_GameStrings = null;
	private HashMap<String, String> m_StringValueMap = null;
	private static final Logger log = LogManager.getLogger(GameStrings.class);

	// Protected Constructor to allow sub classing in future
	protected GameStrings() {
		m_StringValueMap = new HashMap<String, String>();
		// No Instantiation
	}

	public HashMap<String, String> getStringValueMap() {
		return m_StringValueMap;
	}

	// OnDemand and thread safe instantiation
	public synchronized static GameStrings getInstance() {
		if (m_GameStrings == null) {
			m_GameStrings = new GameStrings();
		}
		return m_GameStrings;
	}

	private void LoadDictionary(NodeList nodeList) {

		try {
			for (int count = 0; count < nodeList.getLength(); count++) {

				Node tempNode = nodeList.item(count);

				// make sure it's element node.
				if (tempNode.getNodeType() == Node.ELEMENT_NODE) {

					if (!tempNode.getNodeName().equalsIgnoreCase(GameConst.NODE_MESSAGES)) {
						this.m_StringValueMap.put(tempNode.getNodeName(), tempNode.getTextContent());
					}
					if (tempNode.hasChildNodes()) {

						// loop again if has child nodes
						LoadDictionary(tempNode.getChildNodes());

					}
				}
			}
		} catch (Exception e) {
			//e.printStackTrace();
			log.error(e.getMessage());
			Utils.err.add(StringIdentifierConst.GEN_STRING_FAILED);
			Utils.err.add(StringIdentifierConst.EXCEPTION_FALIED);
		}
	}

	public boolean LoadFile(String strFilePath) {
		boolean bRet = true;
		try {

			File file = new File(Utils.DIR_PATH + GameConst.FILE_SEPERATOR + strFilePath);

			DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

			Document doc = dBuilder.parse(file);

			if (doc.getDocumentElement().getNodeName().compareToIgnoreCase(GameConst.NODE_MESSAGES) == 0
					&& doc.hasChildNodes()) {
				LoadDictionary(doc.getChildNodes());
			}
			//printDictionary();

		} catch (Exception e) {
			log.error(e.getMessage());
			bRet = false;
		}
		return bRet;
	}

	public String getString(String node) {
		return this.m_StringValueMap.get(node);
	}

	public void setString(String node, String nodeval) {
		if (node != null && nodeval != null) {
			this.m_StringValueMap.put(node, nodeval);
		}
	}

	public void setStringValueMap(HashMap<String, String> nodeValueMap) {
		this.m_StringValueMap.putAll(nodeValueMap);
	}

	public void printDictionary() {
		for (String key : this.m_StringValueMap.keySet()) {
			System.out.println(key + ":" + m_StringValueMap.get(key));
		}
	}

}
