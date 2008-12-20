package com.deadtroll.backoff.engine;


public class EnemyFactory {

	private static EnemyFactory instance;
	
	private EnemyFactory() {
	}
	
	public static EnemyFactory getInstance() {
		if (EnemyFactory.instance==null) {
			EnemyFactory.instance = new EnemyFactory();
		}
		return EnemyFactory.instance;
	}
	
	public IEnemy getEnemyInstance(String enemyName, EnemyDescriptionMap map) {
		if (map.getEnemyDescriptionsMap().containsKey(enemyName)) {
			EnemyDescription desc = map.getEnemyDescriptionsMap().get(enemyName);
			GenericEnemy enemy = new GenericEnemy(desc.getDescription());
			enemy.setDamage(desc.getDamage());
			enemy.setEnergy(desc.getEnergy());
			enemy.setScore(desc.getScore());
			enemy.setSpeed(desc.getSpeed());
			enemy.setSpriteSheet(desc.getSpriteSheet());
			return enemy;
		} else {
			throw new RuntimeException("Invalid enemy name for given enemy description map");
		}
		
	}
	
}
