package image;

import java.awt.*;
import java.awt.image.BufferedImage;
//import java.awt.Graphics2D;




public class ImageEditor {
    /**
     *
     * @param image
     */
    public static void imagePadding(Image image){
        //Calculate the correct dimensions of the image
        int newWidth = nearestPowerOf2(image.getWidth());
        int newHeight = nearestPowerOf2(image.getHeight());

        int widthDifference = newWidth - image.getWidth();
        int heightDifference = newHeight - image.getHeight();

        Color[][] paddedImage = new Color[newHeight][newWidth];

        // Apply white padding to the necessary areas
        for (int row = 0; row < newHeight; row++){
            for (int col = 0; col < newWidth; col++){
                if (row < heightDifference / 2 ||
                        row >= newHeight - heightDifference / 2 ||
                        col < widthDifference / 2 ||
                        col >= newWidth - widthDifference / 2) {
                    paddedImage[row][col] = Color.WHITE;
                }
                else{
                    paddedImage[row][col] =
                            image.getPixel(row - heightDifference,
                                    col - widthDifference);
                }
            }
        }

        image = new Image(paddedImage, newWidth, newHeight);

    }
    public static void parse(Image image){

    }

    private static int nearestPowerOf2(int value) {
        return (int) Math.pow(2, Math.ceil(Math.log(value) / Math.log(2)));
    }
}
