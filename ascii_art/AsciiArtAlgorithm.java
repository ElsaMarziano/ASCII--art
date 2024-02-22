package ascii_art;

import image.Image;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class AsciiArtAlgorithm {
    private Image image;
    private int resolution;
    private Set<Character> setOfChars;

    public AsciiArtAlgorithm(Image image, int resolution, Set<Character> setOfChars){
        this.image = image;
        this.resolution = resolution;
        this.setOfChars = setOfChars;
    }

    public void run(){
        Image[][] subImages = image.parseImage(resolution);

        for (Image[] row : subImages) {
            for (Image subImage : row) {

            }
        }
    }
}
