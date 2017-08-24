package com.github.boemma.genetic.algorithm.gene;

/**
 * Representation of a color.
 * 
 * @author BoEmma
 *
 */
public class Color {

	private int red;
	private int green;
	private int blue;
	private double alpha;
	
	public Color(int red, int green, int blue, double alpha) {
		if(!isWithinRange(red, 0, 255) || !isWithinRange(green, 0, 255) || 
		   !isWithinRange(blue, 0, 255) || !isWithinRange(alpha, 0, 1)) {
			throw new IllegalArgumentException(
					"Color value r:" + red + " g:" + green + " b:" + blue + " a:" + alpha + " out of range!");
		}
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.alpha = alpha;
	}
	
	/**
	 * Returns the red part of the color as an integer value.
	 * @return red
	 */
	public int getRed() {
		return red;
	}
	
	/**
	 * Returns the blue part of the color as an integer value.
	 * @return blue
	 */
	public int getBlue(){
		return blue;
	}
	
	/**
	 * Returns the green part of the color as an integer value.
	 * @return green
	 */
	public int getGreen() {
		return green;
	}
	
	/**
	 * Returns the alpha value of this color as a double between
	 * 0 and 1.
	 * @return alpha
	 */
	public double getAlpha() {
		return alpha;
	}
	
	/**
	 * Verifies that the given color value is the specified range.
	 * @param value - color value
	 * @param min - minimum value for the color
	 * @param max - maximum value for the color
	 * @return - true if color value is between 0 and max
	 */
	private boolean isWithinRange(double value, double min, double max) {
		return value <= max && value >= min;
	}
}
