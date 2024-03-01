package image;

import image_char_matching.CharConverter;

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
     *
     * @param image the image to calculate the brightness of - an array of arrays of Colors
     * @return the overall brightness of the image
     */
    public static double calculateBrightness(Image image) {
        int totalPixels = 0;
        double totalBrightness = 0;
        double maxBrightness = 0;
        double pixelBrightness = 0;
        // Go over image and turn each pixel to grey
        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                pixelBrightness = turnPixelToGrey(image.getPixel(i, j));
                totalBrightness += pixelBrightness;
                maxBrightness = Math.max(maxBrightness, pixelBrightness);
                totalPixels += 1;
            }
        }
        return totalBrightness / (totalPixels * maxBrightness);
    }

    /**
     * This function gets a char and returns its brightness
     *
     * @param c            the char we want to calculate the brightness of
     * @param total_pixels number of pixels in the char - default is 16*16
     * @return a double between 0 and 1 representing the brightness
     */
    public static double getCharBrightness(char c, float total_pixels) {
        boolean[][] boolArray = CharConverter.convertToBoolArray(c);
        int countTrue = 0;
        for (boolean[] booleans : boolArray) {
            for (boolean bool : booleans) {
                if (bool) countTrue += 1;
            }
        }
        return (double) countTrue / total_pixels;
    }

    /* Helper function to calculate Brightness. This function receives a colored pixel and
    turns it grey;
     */
    private static double turnPixelToGrey(Color pixel) {
        return pixel.getRed() * RED_WEIGHT + pixel.getGreen() * GREEN_WEIGHT
                + pixel.getBlue() * BLUE_WEIGHT;
    }


}
