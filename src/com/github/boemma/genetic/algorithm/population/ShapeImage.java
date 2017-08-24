package com.github.boemma.genetic.algorithm.population;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

import com.github.boemma.genetic.algorithm.gene.Color;
import com.github.boemma.genetic.algorithm.gene.Shape;
import com.github.boemma.ui.javafx.JavaFXImageComparator;
import com.github.boemma.ui.javafx.JavaFXPaintUtils;
import com.github.boemma.ui.javafx.ReferenceImage;

import javafx.application.Platform;
import javafx.scene.image.Image;

/**
 * A representation of an image consisting of a number of 
 * shapes with different positions and colors.
 * 
 * In the genetic algorithm, this is an individual, and the shapes
 * are its genes. An individual has a fitness based on how similar it is to 
 * an original image, when drawn as an image.
 * 
 * @author BoEmma
 *
 */
public class ShapeImage {

	/** The genes of this individual */
	private Shape[] shapes;
	
	/** The current fitness of this individual */
	private double fitness;
	
	/** Image specifications */
	private int imageHeight;
	private int imageWidth;

	/**
	 * Create a new shape image with the given number of genes (shapes).
	 * @param numberOfGenes - number of genes/shapes
	 * @param initializeGenes - true if initialize with random genes
	 */
	public ShapeImage(int numberOfGenes, boolean initializeGenes) {
		this.shapes = new Shape[numberOfGenes];
		this.imageHeight = ReferenceImage.getInstance().getImageHeight();
		this.imageWidth = ReferenceImage.getInstance().getImageWidth();
	
		if(initializeGenes) {
			Random random = new Random();
		
			for(int i = 0; i<numberOfGenes; i++) {
				Color color = new Color(random.nextInt(256), random.nextInt(256), 
											random.nextInt(256), random.nextDouble());
				//TODO: For now, only create triangles, but can easily extended to use shapes with n number of corners
				//Pass number of corners as argument to the constructor
				shapes[i] = new Shape(new double[]{random.nextInt(imageWidth), random.nextInt(imageWidth), random.nextInt(imageWidth)}, 
					new double[]{random.nextInt(imageHeight), random.nextInt(imageHeight), random.nextInt(imageHeight)}, color);
			}
		}	
	}
	
	/**
	 * Recalculates the fitness of this shape image.
	 */
	public void recalculateFitness() {
		if(Platform.isFxApplicationThread()) {
			Image image = JavaFXPaintUtils.paintShapeImageAsJavaFXImage(this);
			fitness = JavaFXImageComparator.compareWithReferenceImage(image);
		}
		else {
			ShapeImage thisImage = this;
			final CountDownLatch latch = new CountDownLatch(1);
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					Image image = JavaFXPaintUtils.paintShapeImageAsJavaFXImage(thisImage);
					fitness = JavaFXImageComparator.compareWithReferenceImage(image);
					latch.countDown();
				}
			});
	
			try {
				latch.await();
			} catch (InterruptedException e) {}
		}
	}
	
	/**
	 * Returns the fitness of this shape image.
	 * @return fitness
	 */
	public double getFitness() {
		return fitness;
	}
	
	/**
	 * Get all the shapes(genes) of this shape image.
	 * @return all shapes
	 */
	public Shape[] getShapes() {
		return shapes;
	}
	
	/** 
	 * Get the shape(gene) with the given index.
	 * @param index - shape index
	 * @return shape with given index
	 */
	public Shape getShape(int index) {
		return shapes[index];
	}
	
	/**
	 * Set the shape(gene) with the given index.
	 * @param index - shape index
	 * @param shape - shape to set as gene with given index
	 */
	public void setShape(int index, Shape shape) {
		shapes[index] = shape;
	}
	
	/**
	 * Get the height of this shape image.
	 * @return height
	 */
	public int getHeight() {
		return imageHeight;
	}
	
	/**
	 * Get the width of this shape image.
	 * @return width
	 */
	public int getWidth() {
		return imageWidth;
	}
}
