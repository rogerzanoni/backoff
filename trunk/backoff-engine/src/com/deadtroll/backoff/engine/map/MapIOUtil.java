package com.deadtroll.backoff.engine.map;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class MapIOUtil {

	public static Map loadDTMMap(String src) throws Exception {
		Map map = DTMToMap(src);
		return map;
	}
	
	public static void saveDTMMap(Map map, String dst) throws Exception {
		FileOutputStream fos = new FileOutputStream(new File(dst));
		fos.write(mapToDTM(map));
		fos.close();
	}
	
	private static Map DTMToMap(String filePath) throws SlickException, IOException {
		Map map = new Map();
		File f = new File(filePath);
		BufferedReader br = new BufferedReader(new FileReader(f));
		map.setDescription(br.readLine());
		map.setSpriteSheetPath(br.readLine());
		map.setSpriteSheetWidth(new Integer(br.readLine()));
		map.setSpriteSheetHeight(new Integer(br.readLine()));
		map.setMapWidth(new Integer(br.readLine()));
		map.setMapHeight(new Integer(br.readLine()));
		map.setLayers(new MapLayer[new Integer(br.readLine())]);

		String line;
		MapLayer currentLayer = null;
	    while ((line = br.readLine()) != null) {
	      if (line.equals("==")) {
	    	  int zorder = new Integer(br.readLine());
	    	  currentLayer = new MapLayer();
	    	  currentLayer.setMatrix(new MapBlock[map.getMapWidth()][map.getMapHeight()]);
	    	  map.getLayers()[zorder] = currentLayer;
	    	  continue;
	      }
	      String[] params = line.split(",");
	      MapBlock mb = new MapBlock();
	      mb.setSpriteId(new Integer(params[2]));
	      currentLayer.getMatrix()[new Integer(params[0])][new Integer(params[1])] = mb; 
	    }
		
	    try {
			Image img = new Image(map.getSpriteSheetPath());
			map.setMapSpriteSheet(new SpriteSheet(map.getSpriteSheetPath(),img.getWidth()/map.getSpriteSheetWidth(),img.getHeight()/map.getSpriteSheetHeight()));
		} catch (Exception e) {
		}
		return map;
	}
	
	private static byte[] mapToDTM(Map map) throws Exception {
		StringBuilder builder = new StringBuilder();
		builder.append(map.getDescription()+"\n");
		builder.append(map.getSpriteSheetPath()+"\n");
		builder.append(map.getSpriteSheetWidth()+"\n");
		builder.append(map.getSpriteSheetHeight()+"\n");
		builder.append(map.getMapWidth()+"\n");
		builder.append(map.getMapHeight()+"\n");
		builder.append(map.getLayers().length+"\n");
		for (MapLayer ml : map.getLayers()) {
			builder.append("==\n");
			builder.append(ml.getZOrder()+"\n");
			for (int i=0; i<map.getMapWidth(); i++) {
				for (int j=0; j<map.getMapHeight(); j++) {
					MapBlock mb = ml.getMatrix()[i][j];
					if (mb!=null) {
						builder.append(i+","+j+","+mb.getSpriteId()+"\n");
					}
				}
			}
		}
		return builder.toString().getBytes();
	}
	
}
