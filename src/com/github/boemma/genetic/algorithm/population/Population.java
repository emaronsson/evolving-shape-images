package com.github.boemma.genetic.algorithm.population;

import java.util.Arrays;

/**
 * A population of shape images.
 * 
 * @author BoEmma
 *
 */
public class Population {

	/** The shape images */
	private ShapeImage[] images;
	
	/**
	 * Create a new population of the given size, where each
	 * individual will have the given number of genes. 
	 * 
	 * @param size - size of population
	 * @param numberOfGenes - number of genes (shapes) for each individual
	 * @param initialize - true if the individuals of the population should be initialized
	 */
	public Population(int size, int numberOfGenes, boolean initializeIndividuals) {	
		images = new ShapeImage[size];
		if(initializeIndividuals) {
			for (int i = 0; i < size; i++) {
				ShapeImage image = new ShapeImage(numberOfGenes, true);
				images[i] = image;
				image.recalculateFitness();
			}
		}
    }
	
	/**
	 * Get the individual with the highest fitness from the population.
	 * @return individual (shape image) with highest fitness
	 */
	public ShapeImage getFittest() {
		return Arrays.stream(images).max((shapeImage1, shapeImage2) 
				-> Double.compare(shapeImage1.getFitness(), shapeImage2.getFitness())).get();
	}
	
	/**
	 * Add the given shape image to the population at the specified
	 * index.
	 * @param index - index in population
	 * @param image - image to add to population
	 */
	public void setShapeImage(int index, ShapeImage image) {
		images[index] = image;
	}
	
	/**
	 * Gets the shape image with the given index from the population.
	 * @param index - index in population
	 * @return shape image with given index
	 */
	public ShapeImage getShapeImageIndividual(int index) {
		return images[index];
	}
}
