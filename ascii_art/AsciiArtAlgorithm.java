package ascii_art;

import image.BrightnessCalculator;
import image.Image;
import image.ImageEditor;
import image_char_matching.SubImgCharMatcher;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

public class AsciiArtAlgorithm {
    private final SubImgCharMatcher charMatcher;
    private final int resolution;
    private final Map<Map.Entry<Image, Integer>, char[][]> subImagesBrightness = new HashMap<>();
    //    private final char[] setOfChars;
    private Image image;

    public AsciiArtAlgorithm(Image image, int resolution, SubImgCharMatcher charMatcher) {
        this.image = image;
        this.resolution = resolution;
//        this.setOfChars = setOfChars;
        this.charMatcher = charMatcher;
    }

    public char[][] run() {
        if (this.subImagesBrightness.containsKey(new AbstractMap.SimpleImmutableEntry<>(image,
                resolution)))
            return this.subImagesBrightness.get(new AbstractMap.SimpleImmutableEntry<>(image,
                    resolution));
        else {
            image = ImageEditor.imagePadding(image);
            Image[][] subImages = ImageEditor.parseImage(resolution, image);
            //TODO Check the lengths aren't mixed up
            char[][] finalPicture = new char[subImages.length][subImages[0].length];
            for (int i = 0; i < subImages.length; i++) {
                for (int j = 0; j < subImages[i].length; j++) {
                    double imageBrightness =
                            BrightnessCalculator.calculateBrightness(subImages[i][j]);
                    System.out.println((imageBrightness));
                    finalPicture[i][j] = this.charMatcher.getCharByImageBrightness(imageBrightness);
                }
            }
            this.subImagesBrightness.put(new AbstractMap.SimpleImmutableEntry<>(image,
                    resolution), finalPicture);
            return finalPicture;
        }
    }
}
