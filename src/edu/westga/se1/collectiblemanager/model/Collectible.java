package edu.westga.se1.collectiblemanager.model;

public class Collectible {
	private String name;
	private int quantity;
	
	/**
	 * Constructor for Collectible object
	 * 
	 * @precondition name != null && !name.isEmpty() && quantity > 0
	 * @param name the name of the collectible
	 * @param quantity
	 */
	public Collectible(String name) {
		if (name == null) {
			throw new IllegalArgumentException("name cannot be null");
		}
		if (name.isEmpty()) {
			throw new IllegalArgumentException("name cannot be empty");
		}
		
		this.name = name;
	}
	
	/**
	 * Gets the name of the collectible
	 * 
	 * @precondition none
	 * @postcondition none
	 * @return gets the name of the collectible
	 */
	public String getName() {
		return this.name;
	}

}
