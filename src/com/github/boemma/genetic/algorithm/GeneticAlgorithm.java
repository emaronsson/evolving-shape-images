package com.github.boemma.genetic.algorithm;


import java.util.Random;

import com.github.boemma.genetic.algorithm.gene.Color;
import com.github.boemma.genetic.algorithm.gene.Shape;
import com.github.boemma.genetic.algorithm.population.Population;
import com.github.boemma.genetic.algorithm.population.ShapeImage;
import com.github.boemma.ui.javafx.ReferenceImage;

/**
 * Genetic algorithm for generating shape images (images built up
 * only by one specific shape). Fitness is calculated by comparing shape images
 * with a reference image.
 * 
 * @author BoEmma
 *
 */
public class GeneticAlgorithm {

	/** Algorithm settings */
	private int populationSize;
	private int numberOfGenes;
	private double mutationRate;
	
	/** Image specifications */
	private int imageWidth;
	private int imageHeight;
	
	private Population population;
	/** Currently highest fitness of an individual in the population */
	private double highestFitness = 0;
	

	/**
	 * Create a new genetic algorithm with the given population size, number of genes
	 * and mutation rate.
	 * @param populationSize - number of individuals (shape images)
	 * @param numberOfGenes - number of genes (shapes within each shape image)
	 * @param mutationRate - mutation rate
	 */
	public GeneticAlgorithm(int populationSize, int numberOfGenes, double mutationRate) {
		this.populationSize = populationSize;
		this.numberOfGenes = numberOfGenes;
		this.mutationRate = mutationRate;
		
		population = new Population(populationSize, numberOfGenes, true);
		imageWidth = ReferenceImage.getInstance().getImageWidth();
		imageHeight = ReferenceImage.getInstance().getImageHeight();
	}
	
	/**
	 * Run the evolution for one generation. Return the most fit individual (the shape image
	 * most similar to the reference image) after the completion of this generation.
	 * @return shape image with highest fitness
	 */
	public ShapeImage runOneGeneration() {
		Population newPopulation = new Population(populationSize, numberOfGenes, false);
		
		//Save the fittest individual
		newPopulation.setShapeImage(0, population.getFittest());
		
		for (int i = 1; i < populationSize; i++) {
			ShapeImage image1 = selectParentIndividual();
			ShapeImage image2 = selectParentIndividual();
			ShapeImage newImage = recombine(image1, image2);
			newPopulation.setShapeImage(i, newImage);
			
			mutate(newPopulation.getShapeImageIndividual(i));
			newPopulation.getShapeImageIndividual(i).recalculateFitness();
		}
		
		population = newPopulation;
		highestFitness = newPopulation.getFittest().getFitness();
		return newPopulation.getFittest();
	}
	
	/**
	 * Returns the individual (shape image) with the highest fitness.
	 * @return shape image with highest fitness
	 */
	public ShapeImage getFittest(){
		return population.getFittest();
	}
	
	/**
	 * Check if evolution is completed (a shape image similar enough
	 * to the reference image has been found).
	 * @return true if evolution is completed, false if not
	 */
	public boolean isEvolutionCompleted(){
		if(highestFitness < 100) { //TODO: For now 100% is hard coded
			return false;
		}
		return true;
	}

	/**
	 * Select a parent to use for crossover by selecting the most fit individual 
	 * from a selection of randomly chosen individuals.
	 * 
	 * @return shape image parent
	 */
	private ShapeImage selectParentIndividual() {
		Random random = new Random();
		ShapeImage fittest = null;
		for (int i = 0; i < 10; i++) { //TODO: Make this configurable through GUI
			int randomId = random.nextInt(populationSize);
			ShapeImage randomShape = population.getShapeImageIndividual(randomId);
			if(fittest == null || fittest.getFitness() < randomShape.getFitness()) {
				fittest = randomShape;
			}
		}
		return fittest;
	}
	
	/**
	 * Recombine using the two given individuals.
	 * Produces a new individual which is returned, by picking genes randomly 
	 * from the parents.
	 * 
	 * @param image1 - first parent
	 * @param image2 - second parent
	 * @return new individual
	 */
	private ShapeImage recombine(ShapeImage image1, ShapeImage image2) {
		ShapeImage newImage = new ShapeImage(numberOfGenes, false);

		for (int i = 0; i < numberOfGenes; i++) {
			if (Math.random() < 0.5) {
				newImage.setShape(i, new Shape(image1.getShape(i).getXCoordinates(), 
												image1.getShape(i).getYCoordinates(),
												image1.getShape(i).getColor()));
			} else {
				newImage.setShape(i, new Shape(image2.getShape(i).getXCoordinates(), 
												image2.getShape(i).getYCoordinates(),
												image2.getShape(i).getColor()));
			}
		}
		return newImage;
	}
	
	/**
	 * Mutate the genes of the given shape image individual based on 
	 * the mutation rate.
	 * @param shapeImage - individual to mutate
	 */
	private void mutate(ShapeImage shapeImage) {
		for (int i = 0; i < numberOfGenes; i++) {
			
			Shape shape = shapeImage.getShapes()[i];

			double[] xCoordinates = shape.getXCoordinates().clone();
			double[] yCoordinates = shape.getYCoordinates().clone();
			
			double mutateValue = Math.random();
			if (mutateValue <= mutationRate) {
				
				//Mutate position
				for(int j = 0; j<shape.getXCoordinates().length;j++) {
					xCoordinates[j] = addRandomOffsetToValue(xCoordinates[j], 0, imageWidth, (int)Math.round(imageWidth*0.1)); //TODO: Make this value configurable
					yCoordinates[j] = addRandomOffsetToValue(yCoordinates[j], 0, imageHeight, (int)Math.round(imageHeight*0.1));
				}
				shape.setXCoordinates(xCoordinates);
				shape.setYCoordinates(yCoordinates);
			
				//Mutate color
				int r = addRandomOffsetToValue(shape.getColor().getRed(), 0, 255, 10);
				int g = addRandomOffsetToValue(shape.getColor().getGreen(), 0, 255, 10);
				int b = addRandomOffsetToValue(shape.getColor().getBlue(), 0, 255, 10);
				double a = addRandomOffsetToValue(shape.getColor().getAlpha(), 0, 1, 0.01);
	
				Color color = new Color(r,g,b,a);
				shape.setColor(color);
			
				shapeImage.setShape(i, shape);
			}
		}
	}
	
	/**
	 * Adds a random offset to the given value. The resulting value will be within the specified 
	 * interval. The mutation value determines how large the offset can be. A larger value can give
	 * a more significant change.
	 * @param value - old value
	 * @param min - min limit for new value
	 * @param max - max limit for new value
	 * @param mutationValue
	 * 
	 * @return old value added with offset
	 */
	private int addRandomOffsetToValue(int value, int min, int max, int mutationValue) {
		Random random = new Random();
		int offsetValue = (int) Math.round((value + 2*random.nextDouble() * mutationValue-mutationValue));
		return Math.max(Math.min(offsetValue, max),min);
	}
	
	/**
	 * Adds a random offset to the given value. The resulting value will be within the specified 
	 * interval. The mutation value determines how large the offset can be. A larger value can give
	 * a more significant change.
	 * @param value - old value
	 * @param min - min limit for new value
	 * @param max - max limit for new value
	 * @param mutationValue
	 * 
	 * @return old value added with offset
	 */
	private double addRandomOffsetToValue(double value, int min, int max, double mutationValue) {
		Random random = new Random();
		double offsetValue =  value + 2*random.nextDouble() * mutationValue-mutationValue;
		return Math.max(Math.min(offsetValue, max),min);
	}
}
