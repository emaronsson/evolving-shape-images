package com.github.boemma.ui.javafx;

import com.github.boemma.genetic.algorithm.gene.Shape;
import com.github.boemma.genetic.algorithm.population.ShapeImage;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

/**
 * Utility class for drawing individuals (ShapeImages) as 
 * JavaFX Images and Canvases.
 * 
 * @author BoEmma
 */
public class JavaFXPaintUtils {
	
	/**
	 * Paints the given ShapeImage as a JavaFX Image.
	 * @param shapeImage - ShapeImage to be drawn as Image
	 * 
	 * @return JavaFX image
	 */
	public static Image paintShapeImageAsJavaFXImage(ShapeImage shapeImage) {
		Canvas canvas = paintShapeImageOnJavaFXCanvas(shapeImage);
		return canvas.snapshot(new SnapshotParameters(), null);
	}
	
	/**
	 * Paints the given ShapeImage on a JavaFX Canvas.
	 * @param shapeImage - ShapeImage to be drawn on canvas
	 * 
	 * @return canvas
	 */
	public static Canvas paintShapeImageOnJavaFXCanvas(ShapeImage shapeImage) {
		Canvas canvas = new Canvas();
		canvas.setWidth(shapeImage.getWidth());
		canvas.setHeight(shapeImage.getHeight());
		
		paintShapeImageOnJavaFXCanvas(shapeImage, canvas, false);
		return canvas;	
	}
	
	/**
	 * Paints the given ShapeImage on the given JavaFX Canvas.
	 * 
	 * @param shapeImage - ShapeImage to be drawn on canvas
	 * @param canvas - Canvas to be drawn on
	 * @param clearCanvas - true if the canvas should be cleared before drawing on it
	 */
	public static void paintShapeImageOnJavaFXCanvas(ShapeImage shapeImage, Canvas canvas, boolean clearCanvas) {
		GraphicsContext context = canvas.getGraphicsContext2D();
		if(clearCanvas) { 
			context.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		}
		
		for(Shape shape : shapeImage.getShapes()) {
			context.setStroke(Color.rgb(shape.getColor().getRed(), shape.getColor().getGreen(), shape.getColor().getBlue(), shape.getColor().getAlpha()));
			context.setFill(Color.rgb(shape.getColor().getRed(), shape.getColor().getGreen(), shape.getColor().getBlue(), shape.getColor().getAlpha()));
			context.fillPolygon(shape.getXCoordinates(), shape.getYCoordinates(), shape.getXCoordinates().length);
		}
	}
}
