package ascii_art;

import image.BrightnessCalculator;
import image.Image;
import image.ImageEditor;
import image_char_matching.SubImgCharMatcher;

import java.io.IOException;
import java.util.Arrays;

public class AsciiArtAlgorithm {
    private final SubImgCharMatcher charMatcher;
    private final int resolution;
    //    private final char[] setOfChars;
    private Image image;

    public AsciiArtAlgorithm(Image image, int resolution, char[] setOfChars) {
        this.image = image;
        this.resolution = resolution;
//        this.setOfChars = setOfChars;
        this.charMatcher = new SubImgCharMatcher(setOfChars);
    }

    public static void main(String[] args) throws IOException {
        Image image = new Image("./board.jpeg");
        AsciiArtAlgorithm algo = new AsciiArtAlgorithm(image, 2,
                new char[]{'o', 'm'});
        System.out.print(Arrays.deepToString(algo.run()));
    }

    public char[][] run() {
        image = ImageEditor.imagePadding(image);
        Image[][] subImages = ImageEditor.parseImage(resolution, image);
        //TODO Check the lengths aren't mixed up
        char[][] finalPicture = new char[subImages.length][subImages[0].length];
        for (int i = 0; i < subImages.length; i++) {
            for (int j = 0; j < subImages[i].length; j++) {
                double imageBrightness = BrightnessCalculator.calculateBrightness(subImages[i][j]);
                finalPicture[i][j] = this.charMatcher.getCharByImageBrightness(imageBrightness);
            }
        }
        return finalPicture;
    }
}
