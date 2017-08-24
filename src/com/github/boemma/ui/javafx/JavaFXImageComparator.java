package com.github.boemma.ui.javafx;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

/**
 * Utility class for simple comparison of JavaFX images.
 * 
 * @author BoEmma
 *
 */
public class JavaFXImageComparator {

	/**
	 * Compare the given image with the currently selected reference
	 * image, looking at the RGB value differences for each pixel.
	 * @param image - image to compare with reference image
	 * 
	 * @return similarity between image and reference image in percent
	 */
	public static double compareWithReferenceImage(Image image) {
		
		Image referenceImage = ReferenceImage.getInstance().getImage();
		
		if(image.getHeight() != referenceImage.getHeight()||image.getWidth() != referenceImage.getWidth()) {
			//TODO: Replace
			 System.err.println("Error: Images dimensions mismatch");
		     System.exit(1);
		}
		
		double difference = 0;
	    for (int y = 0; y < image.getHeight(); y++) {
	      for (int x = 0; x < image.getWidth(); x++) {
	        Color rgb1 = image.getPixelReader().getColor(x, y);
	        Color rgb2 = referenceImage.getPixelReader().getColor(x, y);
	        difference += Math.abs(rgb1.getRed()   - rgb2.getRed());
	        difference += Math.abs(rgb1.getGreen() - rgb2.getGreen());
	        difference += Math.abs(rgb1.getBlue()  - rgb2.getBlue());
	      }
	    }
	    
	    double n  = image.getWidth() * image.getHeight() * 3;
	    double p1 = difference / n;
	   
	    return 100 - (p1 * 100.0);
	}
}
