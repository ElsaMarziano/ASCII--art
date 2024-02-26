package image;

import java.awt.*;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;


/**
 * This class regroups every function in charge of editing the image in any way whatsoever -
 * padding it, dividing it to subimages and so on. Every function is static.
 */
public class ImageEditor {
    private static final Map<Map.Entry<Image, Integer>, Image[][]> parsedImages = new HashMap<>();

    /**
     * This function receives an image and pads it so each side will be the size of a power of 2
     *
     * @param image The image to pad.
     */
    public static Image imagePadding(Image image) {
        //Calculate the correct dimensions of the image
        int newWidth = nearestPowerOf2(image.getWidth());
        int newHeight = nearestPowerOf2(image.getHeight());

        int widthDifference = newWidth - image.getWidth();
        int heightDifference = newHeight - image.getHeight();
        Color[][] paddedImage = new Color[newHeight][newWidth];

        // Apply white padding to the necessary areas
        for (int row = 0; row < newHeight; row++) {
            for (int col = 0; col < newWidth; col++) {
                if (row < heightDifference / 2 ||
                        row >= newHeight - (heightDifference / 2) ||
                        col < widthDifference / 2 ||
                        col >= newWidth - (widthDifference / 2)) {
                    paddedImage[row][col] = Color.WHITE;
                } else {
                    paddedImage[row][col] =
                            image.getPixel(row - heightDifference / 2, col - widthDifference / 2);
                }
            }
        }
        return new Image(paddedImage, newWidth, newHeight);
    }

    private static int nearestPowerOf2(int value) {
        return (int) Math.pow(2, Math.ceil(Math.log(value) / Math.log(2)));
    }

    /**
     * This function separates the image into subimages according to the resolution
     *
     * @param resolution the desired resolution
     * @return an array of arrays of subimages
     */
    public static Image[][] parseImage(int resolution, Image image) {
        // Check if we already calculated the sub-images for this image and resolution
        if (ImageEditor.parsedImages.containsKey(new AbstractMap.SimpleImmutableEntry<>(image,
                resolution)))
            return ImageEditor.parsedImages.get(new AbstractMap.SimpleImmutableEntry<>(image,
                    resolution));

        // Else, calculate it and save it
        int size = image.getWidth() / resolution;
        Image[][] subImages = new Image[resolution][resolution];
        for (int i = 0; i < resolution; i++) {
            for (int j = 0; j < resolution; j++) {
                subImages[i][j] = getSubImage(size, i * size, j * size, image);
            }
        }
        ImageEditor.parsedImages.put(new AbstractMap.SimpleImmutableEntry<>(image,
                resolution), subImages);
        return subImages;
    }


    /*
    This function return the subimage corresponding to a row and column
     */
    private static Image getSubImage(int size, int row, int col, Image image) {
        // Create a Color[][] array to store the pixels of the sub-image
        Color[][] subImagePixels = new Color[size][size];

        // Copy the pixels from the original image to the sub-image array
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int originalRow = row + i;
                int originalCol = col + j;

                // Ensure the originalRow and originalCol are within bounds
                if (originalRow < image.getHeight() && originalCol < image.getWidth()) {
                    subImagePixels[i][j] = image.getPixel(originalRow, originalCol);
                }
            }
        }

        // Create and return a new Image object for the sub-image
        return new Image(subImagePixels, size, size);
    }
}
