package com.deadtroll.backoff.engine.enemy;

import java.io.File;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import org.newdawn.slick.SpriteSheet;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class EnemyDescriptionMap {

	private HashMap<String, EnemyDescription> enemiesDescription;
	
	public EnemyDescriptionMap(String descriptionFilesLocation) throws Exception {
		this.enemiesDescription = new HashMap<String, EnemyDescription>();
		File filesDirectory = new File(descriptionFilesLocation);
		if (filesDirectory.exists() && filesDirectory.isDirectory()) {
			for (File f : filesDirectory.listFiles()) {
				if (f.getName().toUpperCase().endsWith(".XML")) {
					this.loadDescriptionXML(f);
				}
			}
		} else {
			throw new RuntimeException("Can't find enemies descriptions. Location given isn't a directory");
		}
	}

	public void addDescription(String descriptionFileLocation) throws Exception {
		File file = new File(descriptionFileLocation);
		if (file.exists() && file.isFile()) {
			if (file.getName().toUpperCase().endsWith(".XML")) {
				this.loadDescriptionXML(file);
			}
		} else {
			throw new RuntimeException("Can't find enemies descriptions. Location given isn't a directory");
		}
	}
	
	public HashMap<String, EnemyDescription> getEnemyDescriptionsMap() {
		return this.enemiesDescription;
	}
	
	private void loadDescriptionXML(File file) throws Exception {
		Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);
		Node rootNode = doc.getDocumentElement();
		if (rootNode.getNodeName().equalsIgnoreCase("foe")) {
			EnemyDescription description = new EnemyDescription();
			XPath xpath = XPathFactory.newInstance().newXPath();
			description.setName(xpath.evaluate("name", rootNode));
			description.setDescription(xpath.evaluate("description", rootNode));
			description.setEnergy(Integer.parseInt(xpath.evaluate("energy", rootNode)));
			description.setSpeed(Integer.parseInt(xpath.evaluate("speed", rootNode)));
			description.setDamage(Integer.parseInt(xpath.evaluate("damage", rootNode)));
			description.setScore(Integer.parseInt(xpath.evaluate("score", rootNode)));
			
			int sprtW = Integer.parseInt(xpath.evaluate("spriteSheet/width", rootNode));
			int sprtH = Integer.parseInt(xpath.evaluate("spriteSheet/height", rootNode));
			String sprt = xpath.evaluate("spriteSheet/filename", rootNode);
			description.setSpriteSheet(new SpriteSheet(sprt,sprtW, sprtH));
			
			this.enemiesDescription.put(description.getName(), description);
		}
	}
}
