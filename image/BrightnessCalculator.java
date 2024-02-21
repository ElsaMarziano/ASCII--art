package image;

import java.awt.*;

/**
 * This is a utility class whose only role is to calculate the brightness of a given picture.
 */
public class BrightnessCalculator {

    private static final double RED_WEIGHT = 0.2126;
    private static final double GREEN_WEIGHT = 0.7152;
    private static final double BLUE_WEIGHT = 0.0722;

    /**
     * This function receives an image or a sub-image and calculates its brightness
     * @param image the image to calculate the brightness of - an array of arrays of Colors
     * @return the overall brightness of the image
     */
    public static double calculateBrightness(Color[][] image) {
        int totalPixels = 0;
        double totalBrightness = 0;
        // Go over image and turn each pixel to grey
        for(Color[] row: image) {
            for(Color pixel: row) {
                totalBrightness += turnPixelToGrey(pixel);
                totalPixels += 1;
            }
        }
        return totalBrightness / totalPixels;
    }

    /* Helper function to calculate Brightness. This function receives a colored pixel and
    turns it grey;
     */
    private static double turnPixelToGrey(Color pixel) {
        return pixel.getRed() * RED_WEIGHT + pixel.getGreen() * GREEN_WEIGHT
                + pixel.getBlue() * BLUE_WEIGHT;
    }
}
