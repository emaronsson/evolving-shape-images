package com.github.boemma.genetic.algorithm.main;
	
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import com.github.boemma.genetic.algorithm.GeneticAlgorithm;
import com.github.boemma.ui.javafx.JavaFXPaintUtils;
import com.github.boemma.ui.javafx.ReferenceImage;
import com.sun.media.jfxmedia.logging.Logger;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * A program illustrating how a genetic algorithm transforms an image
 * consisting only of a number of coloured triangles to look more and 
 * more similar to an original image, selected by the user.
 * 
 * The genetic algorithm population consists of a number of images, 
 * and their genes are triangles with certain colors and positions.
 * 
 * The fitness function is a simple method comparing the 
 * similarity between pixels of the original image and the 
 * triangle images.
 * 
 * The application is developed as a small JavaFX application.
 * 
 * 
 * @author BoEmma
 * 
 */
public class Main extends Application {
	
	/** Original and triangle image */
	@FXML
	private ImageView originalImage;
	@FXML
	private Canvas evolvingImage;
	
	@FXML
	private AnchorPane originalImagePane;
	@FXML
	private AnchorPane evolvingImagePane;
	
	/** Configuration labels */
	@FXML
	private Label populationSizeLabel;
	@FXML
	private Label nrOfGenesLabel;
	@FXML
	private Label mutationRateLabel;
	
	private static final int POPULATIONSIZE_INCREASE_VALUE = 1;
	private static final int NROFGENES_INCREASE_VALUE = 1;
	private static final double MUTATIONRATE_INCREASE_VALUE = 0.01;

	private static final int POPULATIONSIZE_MIN_VALUE = 5;
	private static final int NROFGENES_MIN_VALUE = 1;
	private static final double MUTATIONRATE_MIN_VALUE = 0.01;
	
	private static final int POPULATIONSIZE_MAX_VALUE = 500;
	private static final int NROFGENES_MAX_VALUE = 500;
	private static final double MUTATIONRATE_MAX_VALUE = 1;
	
	/** Status labels */
	@FXML
	private Label nrOfGenerationsLabel;
	@FXML
	private Label similarityLabel;
	
	private Stage primaryStage;
	
	private EVOLUTION_STATUS evolutionStatus = EVOLUTION_STATUS.NOT_RUNNING;
	
	private static final String FXML_PATH = "genetic_algorithm_ui.fxml";
	
	private static final String LOAD_IMAGE_PROMPT_TITLE = "Open image file";
	private static final String LOAD_IMAGE_DESCRIPTION = "Image files";
	private static final String[] supportedFileExtensions = {"*.png", "*.jpg", "*.gif", "*.jpeg"};
	private static final String NO_IMAGE_ERROR_MESSAGE = "Please load a reference image before starting!";
	
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		try {
			primaryStage.setOnCloseRequest(e -> Platform.exit());
			Parent root = FXMLLoader.load(getClass().getResource(FXML_PATH));
			Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
			Scene scene = new Scene(root,screenBounds.getWidth() / 2 ,screenBounds.getHeight() / 2);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			Logger.logMsg(Logger.ERROR, "Failed to load fxml!");
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	/**
	 * Loads a new original image, and displays it on the screen. The evolving
	 * triangle image is reset, and width and height adjusted for the new 
	 * image.
	 * 
	 * @param event
	 */
	@FXML 
	protected void loadNewImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle(LOAD_IMAGE_PROMPT_TITLE);
		fileChooser.getExtensionFilters().addAll(
		         new ExtensionFilter(LOAD_IMAGE_DESCRIPTION, Arrays.asList(supportedFileExtensions)));
		         
		File file = fileChooser.showOpenDialog(primaryStage);
		if(file != null) {
			Image image = new Image(file.toURI().toString());
		
			originalImage = new ImageView(image);
		
			originalImagePane.getChildren().clear();
			originalImagePane.getChildren().add(originalImage);
        
			evolvingImage = new Canvas(image.getWidth(),image.getHeight());
			evolvingImagePane.getChildren().clear();
			evolvingImagePane.getChildren().add(evolvingImage);
			//TODO: Clear evolving image not working
			updateEvolutionStatus(EVOLUTION_STATUS.STOPPED);
			
		}
    }
	
	@FXML
	protected void exitProgram(ActionEvent event) {
		Platform.exit();
	}
	
	/**
	 * Methods for updating configuration values in UI text fields.
	 */
	
	@FXML
	public void decreasePopulationSize() {
		updateLabelIntegerValue(populationSizeLabel, -POPULATIONSIZE_INCREASE_VALUE, POPULATIONSIZE_MIN_VALUE, POPULATIONSIZE_MAX_VALUE);
	}
	@FXML
	public void increasePopulationSize() {
		updateLabelIntegerValue(populationSizeLabel, POPULATIONSIZE_INCREASE_VALUE, POPULATIONSIZE_MIN_VALUE, POPULATIONSIZE_MAX_VALUE);
	}
	@FXML
	public void decreaseNrOfGenes() {
		updateLabelIntegerValue(nrOfGenesLabel, -NROFGENES_INCREASE_VALUE, NROFGENES_MIN_VALUE, NROFGENES_MAX_VALUE);
	}
	@FXML
	public void increaseNrOfGenes() {
		updateLabelIntegerValue(nrOfGenesLabel, NROFGENES_INCREASE_VALUE, NROFGENES_MIN_VALUE, NROFGENES_MAX_VALUE);
	}
	
	@FXML
	public void decreaseMutationRate() {
		updateLabelDoubleValue(mutationRateLabel, -MUTATIONRATE_INCREASE_VALUE, MUTATIONRATE_MIN_VALUE, MUTATIONRATE_MAX_VALUE);
	}
	
	@FXML
	public void increaseMutationRate() {
		updateLabelDoubleValue(mutationRateLabel, MUTATIONRATE_INCREASE_VALUE, MUTATIONRATE_MIN_VALUE, MUTATIONRATE_MAX_VALUE);
	}
	
	/**
	 * Updates the given label value with the given difference.
	 * If max or min is reached, the value will stay unchanged.
	 * 
	 * @param label - label to be updated
	 * @param difference - difference to be added to current label value
	 * @param min - minimum value
	 * @param max - maximum value
	 * @param showDecimals - true if we should show decimals on the label
	 */
	private void updateLabelDoubleValue(Label label, double difference, double min, double max) {
		double value = Double.parseDouble(label.getText());
		value += difference;
		if(value >= min && value <= max) {
			label.setText(Double.toString(Math.round(value*100.0)/100.0));
		}
	}
	
	/**
	 * Updates the given label value with the given difference.
	 * If max or min is reached, the value will stay unchanged.
	 * 
	 * @param label - label to be updated
	 * @param difference - difference to be added to current label value
	 * @param min - minimum value
	 * @param max - maximum value
	 * @param showDecimals - true if we should show decimals on the label
	 */
	private void updateLabelIntegerValue(Label label, double difference, double min, double max) {
		int value = Integer.parseInt(label.getText());
		value += difference;
		if(value >= min && value <= max) {
			label.setText(Integer.toString(value));
		}
	}
	
	/**
	 * Return the value of the given label as a double.
	 * @param label - label with value
	 * @return label value as double
	 */
	private double getLabelValueAsDouble(Label label) {
		return Double.parseDouble(label.getText());
	}
	
	/**
	 * Return the value of the given label as an integer.
	 * @param label - label with value
	 * @return label value as integer
	 */
	private int getLabelValueAsInt(Label label) {
		return (int) Math.round(Double.parseDouble((label.getText())));
	}
	
	/**
	 * Start the evolution of the triangle images. After each completed generation,
	 * the image with the highest fitness will be drawn on the screen.
	 */
	@FXML 
	protected void runEvolution() {
		
		if(originalImage.getImage() == null) {
			Alert alert = new Alert(AlertType.ERROR, NO_IMAGE_ERROR_MESSAGE);
			alert.show();
		}
		else if(evolutionStatus.equals(EVOLUTION_STATUS.NOT_RUNNING)){
			
			nrOfGenerationsLabel.setText("0");
			similarityLabel.setText("0");
			
			ReferenceImage.getInstance().setImage(originalImage.getImage());
			
			GeneticAlgorithm evolution = new GeneticAlgorithm(getLabelValueAsInt(populationSizeLabel), 
																getLabelValueAsInt(nrOfGenesLabel), 
																	getLabelValueAsDouble(mutationRateLabel));
			
			// A task to be executed in a separate non-ui thread to avoid blocking the ui
			Task<Void> task = new Task<Void>() {
				@Override
				protected Void call() throws Exception {
					do{
						evolution.runOneGeneration();
						JavaFXPaintUtils.paintShapeImageOnJavaFXCanvas(evolution.getFittest(), evolvingImage, true);
					
						// We need to wait for the ui thread to update labels
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								updateLabelIntegerValue(nrOfGenerationsLabel, 1, 0, Integer.MAX_VALUE);
								double similarity = evolution.getFittest().getFitness();
								double roundedSimilarity = Math.round(similarity * 100.0) / 100.0;
								similarityLabel.setText(Double.toString(roundedSimilarity));
							}
						});
					}
					while(!evolution.isEvolutionCompleted() && !evolutionStatus.equals(EVOLUTION_STATUS.STOPPED));
					
					updateEvolutionStatus(EVOLUTION_STATUS.NOT_RUNNING);
					return null;
				}
			};
			Thread th = new Thread(task);
			th.setDaemon(true);
			th.start();
			updateEvolutionStatus(EVOLUTION_STATUS.RUNNING);
		}
	}
	
	/**
	 * Updates the evolution status with the given value if applicable.
	 * Synchronized since the status can be updated by multiple threads.
	 * @param status - new evolution status
	 */
	private synchronized void updateEvolutionStatus(EVOLUTION_STATUS status) {
		if(status.equals(EVOLUTION_STATUS.STOPPED)) {
			if(evolutionStatus.equals(EVOLUTION_STATUS.RUNNING)){
				evolutionStatus = EVOLUTION_STATUS.STOPPED;
			}
		}
		else {
			evolutionStatus = status;
		}
	}
	
	@FXML
	protected void stopEvolution() {
		updateEvolutionStatus(EVOLUTION_STATUS.STOPPED);
	}
	
	private enum EVOLUTION_STATUS {
		NOT_RUNNING, RUNNING, STOPPED;
	}
}
