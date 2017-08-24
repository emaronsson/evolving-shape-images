package com.github.boemma.genetic.algorithm.gene;

/**
 * Represents a 2D coordinate.
 * 
 * @author BoEmma
 *
 */
public class Coordinate {

	private int x;
	private int y;
	
	/**
	 * Create a new coordinate with x and y position.
	 * @param x - the x position
	 * @param y - the y position
	 */
	public Coordinate(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Get the x position of this coordinate.
	 * @return x-position
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * Get the y position of this coordinate.
	 * @return y-position
	 */
	public int getY(){
		return y;
	}
}
