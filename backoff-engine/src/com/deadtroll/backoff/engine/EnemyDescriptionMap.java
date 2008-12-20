package com.deadtroll.backoff.engine;

import java.util.HashMap;

public class EnemyDescriptionMap {

	private HashMap<String, EnemyDescription> enemiesDescription;
	
	public EnemyDescriptionMap(String descriptionFilesLocation) {
		this.enemiesDescription = new HashMap<String, EnemyDescription>();
		// TODO carregar XMLs
		// TODO carregar descrições
	}

	// TODO addDescription

}
