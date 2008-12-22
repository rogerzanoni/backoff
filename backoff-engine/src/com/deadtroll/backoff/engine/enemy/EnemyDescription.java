package com.deadtroll.backoff.engine.enemy;

import org.newdawn.slick.SpriteSheet;

/**
 * An enemy type description.
 * @author Luiz Cavalcanti
 * @version 1.0
 */
public class EnemyDescription {

	private String name;
	private String description;
	private int damage;
	private int energy;
	private int score;
	private int speed;
	private SpriteSheet spriteSheet;

	/**
	 * Returns the name of the foe.
	 * @return Foe's name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Defines the name of the foe.
	 * @param name Foe's name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns foe's name description.
	 * @return Foe's name description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Defines foe's name description.
	 * @param description Foe's name description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Returns the amount of damage the foe can cause.
	 * @return The amount of damage
	 */
	public int getDamage() {
		return damage;
	}

	/**
	 * Defines the amount of damage the foe can cause.
	 * @param damage The amount of damage
	 */
	public void setDamage(int damage) {
		this.damage = damage;
	}

	/**
	 * Returns the amount of energy (life) the foe has. 
	 * @return The amount of energy
	 */
	public int getEnergy() {
		return energy;
	}

	/**
	 * Defines the amount of energy (life) the foe has.
	 * @param energy The amount of energy
	 */
	public void setEnergy(int energy) {
		this.energy = energy;
	}

	/**
	 * Returns the amount of points earned by defeating the foe.
	 * @return The amount of points
	 */
	public int getScore() {
		return score;
	}

	/**
	 * Defines the amount of points earned by defeating the foe.
	 * @param score The amount of points
	 */
	public void setScore(int score) {
		this.score = score;
	}

	/**
	 * Returns the foe speed.
	 * @return The foe speed
	 */
	public int getSpeed() {
		return speed;
	}

	/**
	 * Defines the foe speed.
	 * @param speed The foe speed
	 */
	public void setSpeed(int speed) {
		this.speed = speed;
	}

	/**
	 * Returns foe's sprite sheet.
	 * @return Foe's sprite sheet
	 */
	public SpriteSheet getSpriteSheet() {
		return spriteSheet;
	}

	/**
	 * Defines foe's sprite sheet.
	 * @param spriteSheet Foe's sprite sheet
	 */
	public void setSpriteSheet(SpriteSheet spriteSheet) {
		this.spriteSheet = spriteSheet;
	}
	
}