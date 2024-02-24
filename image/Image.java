package image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * A package-private class of the package image.
 *
 * @author Dan Nirel
 */
public class Image {

    private final Color[][] pixelArray;
    private final int width;
    private final int height;

    public Image(String filename) throws IOException {
        BufferedImage im = ImageIO.read(new File(filename));
        width = im.getWidth();
        height = im.getHeight();


        pixelArray = new Color[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                pixelArray[i][j] = new Color(im.getRGB(j, i));
            }
        }
    }

    public Image(Color[][] pixelArray, int width, int height) {
        this.pixelArray = pixelArray;
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Color getPixel(int x, int y) {
        return pixelArray[x][y];
    }

    public void saveImage(String fileName) {
        // Initialize BufferedImage, assuming Color[][] is already properly populated.
        BufferedImage bufferedImage = new BufferedImage(pixelArray[0].length,
                pixelArray.length, BufferedImage.TYPE_INT_RGB);
        // Set each pixel of the BufferedImage to the color from the Color[][].
        for (int x = 0; x < pixelArray.length; x++) {
            for (int y = 0; y < pixelArray[x].length; y++) {
                bufferedImage.setRGB(y, x, pixelArray[x][y].getRGB());
            }
        }
        File outputfile = new File(fileName + ".jpeg");
        try {
            ImageIO.write(bufferedImage, "jpeg", outputfile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This function separates the image into subimages according to the resolution
     *
     * @param resolution the desired resolution
     * @return an array of arrays of subimages
     */
    public Image[][] parseImage(int resolution) {
        int size = this.getWidth() / resolution;
        Image[][] subImages = new Image[resolution][resolution];

        for (int i = 0; i < resolution; i++) {
            for (int j = 0; j < resolution; j++) {
                subImages[i][j] = getSubImage(size, i * size, j * size);
            }
        }
        return subImages;
    }


    /*
    This function return the subimage corresponding to a row and column
     */
    private Image getSubImage(int size, int row, int col) {
        // Create a Color[][] array to store the pixels of the sub-image
        Color[][] subImagePixels = new Color[size][size];

        // Copy the pixels from the original image to the sub-image array
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int originalRow = row + i;
                int originalCol = col + j;

                // Ensure the originalRow and originalCol are within bounds
                if (originalRow < height && originalCol < width) {
                    subImagePixels[i][j] = this.getPixel(originalRow, originalCol);
                }
            }
        }

        // Create and return a new Image object for the sub-image
        return new Image(subImagePixels, size, size);
    }

}
