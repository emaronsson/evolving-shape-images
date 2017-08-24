package com.github.boemma.ui.javafx;

import javafx.scene.image.Image;

/**
 * Singleton class containing a JavaFX reference image.
 * 
 * @author BoEmma
 *
 */
public class ReferenceImage {

	private static final ReferenceImage instance = new ReferenceImage();
	
	private Image image;
    
    private ReferenceImage(){}

    public static ReferenceImage getInstance(){
        return instance;
    }
    
    /**
     * Set the JavaFX reference image.
     * @param image - image to be set as reference image
     */
    public void setImage(Image image) {
    	this.image = image;
    }
    
    /**
     * Get the JavaFX reference image.
     * @return reference image
     */
    public Image getImage() {
    	return image;
    }
    
    /**
     * Get the height of the JavaFX reference image.
     * @return height
     */
    public int getImageHeight() {
    	return (int) Math.round(image.getHeight());
    }
    
    /**
     * Get the width of the JavaFX reference image.
     * @return width
     */
    public int getImageWidth() {
    	return (int) Math.round(image.getWidth());
    }
}
