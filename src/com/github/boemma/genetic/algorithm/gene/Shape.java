package com.github.boemma.genetic.algorithm.gene;

/**
 * Representation of a shape with a position and a color.
 * In the genetic algorithm, this is a gene.
 * 
 * @author BoEmma
 *
 */
public class Shape {
	
	private double[] xCoordinates;
	private double[] yCoordinates;
	private Color color;
	
	public Shape(double[] xCoordinates, double[] yCoordinates, Color color) {
		this.xCoordinates = xCoordinates.clone();
		this.yCoordinates = yCoordinates.clone();
		this.color = color;
	}
	
	/**
	 * Returns the x-coordinates of the corners of this shape.
	 * @return x-coordinates
	 */
	public double[] getXCoordinates() {
		return xCoordinates;
	}
	
	/**
	 * Returns the y-coordinates of the corners of this shape.
	 * @return y-coordinates
	 */
	public double[] getYCoordinates() {
		return yCoordinates;
	}
	
	/**
	 * Returns the color of this shape.
	 * @return color
	 */
	public Color getColor() {
		return color;
	}
	
	/**
	 * Sets the x-coordinates of the corners of this shape.
	 * @param x-coordinates
	 */
	public void setXCoordinates(double[] xCoordinates) {
		this.xCoordinates = xCoordinates.clone();
	}
	
	/**
	 * Sets the y-coordinates of the corners of this shape.
	 * @param y-coordinates
	 */
	public void setYCoordinates(double[] yCoordinates) {
		this.yCoordinates = yCoordinates.clone();
	}
	
	/**
	 * Sets the color of this shape.
	 * @param color
	 */
	public void setColor(Color color) {
		this.color = color;
	}
}
