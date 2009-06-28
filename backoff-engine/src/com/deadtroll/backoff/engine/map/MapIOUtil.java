package com.deadtroll.backoff.engine.map;

import java.io.File;
import java.io.FileOutputStream;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class MapIOUtil {

	public static Map loadMap(String src) throws Exception {
		Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File(src));
		Map map = xmlToMap(doc);
		return map;
	}
	
	public static void saveMap(Map map, String dst) throws Exception {
		Document mapDocument = mapToXml(map);
		String output = xmlToString(mapDocument);
		FileOutputStream fos = new FileOutputStream(new File(dst));
		fos.write(output.getBytes());
		fos.close();
	}
	
	private static Map xmlToMap(Document doc) throws SlickException {
		Map map = new Map();
		Element root = doc.getDocumentElement();
		map.setDescription(root.getAttribute("description"));
		map.setSpriteSheetPath(root.getAttribute("spriteSheet"));
		map.setSpriteSheetWidth(Integer.parseInt(root.getAttribute("spriteWidth")));
		map.setSpriteSheetHeight(Integer.parseInt(root.getAttribute("spriteHeight")));
		map.setMapWidth(Integer.parseInt(root.getAttribute("width")));
		map.setMapHeight(Integer.parseInt(root.getAttribute("height")));
		//map.setPlayerLayer(Integer.parseInt(root.getAttribute("playerLayer")));
		map.setLayers(new MapLayer[root.getChildNodes().getLength()]);
		
		for (int i=0; i<root.getChildNodes().getLength(); i++) {
			Element layerElement = (Element)root.getChildNodes().item(i);
			MapLayer ml = new MapLayer();
			ml.setId(layerElement.getAttribute("id"));
			ml.setZOrder(Integer.parseInt(layerElement.getAttribute("zOrder")));
			ml.setMatrix(new MapBlock[map.getMapWidth()][map.getMapHeight()]);
			for (int j=0; j<layerElement.getChildNodes().getLength(); j++) {
				Element blockElement = (Element)layerElement.getChildNodes().item(j);
				MapBlock mb = new MapBlock();
				mb.setSpriteId(Integer.parseInt(blockElement.getAttribute("sprite")));
				int x = Integer.parseInt(blockElement.getAttribute("x"));
				int y = Integer.parseInt(blockElement.getAttribute("y"));
				ml.getMatrix()[x][y] = mb;
			}
			map.getLayers()[i] = ml;
		}
		Image img = new Image(map.getSpriteSheetPath());
		map.setMapSpriteSheet(new SpriteSheet(map.getSpriteSheetPath(),img.getWidth()/map.getSpriteSheetWidth(),img.getHeight()/map.getSpriteSheetHeight()));
		
		return map;
	}
	
	private static Document mapToXml(Map map) throws Exception {
		Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
		Element rootNode = doc.createElement("map");
		rootNode.setAttribute("description", map.getDescription());
		rootNode.setAttribute("spriteSheet", map.getSpriteSheetPath());
		rootNode.setAttribute("spriteWidth", ""+map.getSpriteSheetWidth());
		rootNode.setAttribute("spriteHeight", ""+map.getSpriteSheetHeight());
		rootNode.setAttribute("width", ""+map.getMapWidth());
		rootNode.setAttribute("height", ""+map.getMapHeight());
		//rootNode.setAttribute("playerLayer", ""+map.getPlayerLayer());
		
		int zOrder = 0; 
		for (MapLayer ml : map.getLayers()) {
			Element layerNode = doc.createElement("layer");
			layerNode.setAttribute("id", ml.getId());
			layerNode.setAttribute("zOrder", ""+zOrder);
			for (int i=0; i<map.getMapWidth(); i++) {
				for (int j=0; j<map.getMapHeight(); j++) {
					MapBlock mb = ml.getMatrix()[i][j];
					if (mb!=null) {
						Element blockElement = doc.createElement("block");
						blockElement.setAttribute("x", ""+i);
						blockElement.setAttribute("y", ""+j);
						blockElement.setAttribute("sprite", ""+mb.getSpriteId());
						layerNode.appendChild(blockElement);
					}
				}
			}
			rootNode.appendChild(layerNode);
			zOrder++;
		}
		doc.appendChild(rootNode);		
		return doc;
	}
	
	public static String xmlToString(Document doc) throws Exception {
	    Source source = new DOMSource(doc.getDocumentElement());
	    StringWriter stringWriter = new StringWriter();
	    Result result = new StreamResult(stringWriter);
	    TransformerFactory factory = TransformerFactory.newInstance();
	    Transformer transformer = factory.newTransformer();
	    transformer.transform(source, result);
	    return stringWriter.getBuffer().toString();
	}
	
}
