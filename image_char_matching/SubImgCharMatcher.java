package image_char_matching;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is responsible for matching between a charset with a certain brightness and the
 * corresponding ASCII character in the set
 */
public class SubImgCharMatcher {
    private final Map<Character, Double> charset = new HashMap<>();
    private final static int TOTAL_SQUARES = CharConverter.DEFAULT_PIXEL_RESOLUTION ^2;
    private double minBrightness = Double.MAX_VALUE;
    private double maxBrightness = -1;

    /**
     * Constructor for the SubImgCharMatcher class.
     * @param charset the charset we have to choose from
     */
    public SubImgCharMatcher(char[] charset) {
        for (char c : charset) {
            this.addChar(c);
        }
    }

    /**
     * This function returns the ASCII symbol whose brigthness it the closest to the brightness
     * we've been given
     * @param brightness the brightness we're looking for
     * @return the ASCII symbol to be printed
     */
    public char getCharByImageBrightness(double brightness) {
        double difference = Double.MAX_VALUE;
        double maxMinusMin = this.maxBrightness - this.minBrightness;
        char closestChar = 0;
        for (Map.Entry<Character, Double> currentChar : charset.entrySet()) {
            double newBrightness = (currentChar.getValue()-this.minBrightness)/maxMinusMin;
            double newDifference = Math.abs(newBrightness - brightness);
            if(newDifference < difference) {
                difference = newDifference;
                closestChar = currentChar.getKey();
            }
            // If the difference is the same, return the char with the lowest ASCII value
            else if(newDifference == difference) {
                closestChar = (char) Math.min(closestChar, currentChar.getKey());
            }
        }
        return closestChar;
    }

    /**
     * Add a char to the charset map with its corresponding value
     * @param c the char to be added
     */
    public void addChar(char c){
        double charBrightness = this.getCharBrightness(c);
        this.charset.put(c, charBrightness);
        // Updates the min and max brightness if needed
        if(charBrightness > maxBrightness) maxBrightness = charBrightness;
        else if(charBrightness < minBrightness) minBrightness = charBrightness;
    }

    /**
     * Remove a char from the charset map
     * @param c the char to be removed
     */
    public void removeChar(char c){
        Double charBrightness = this.charset.get(c);
        this.charset.remove(c);
        // Update min and max brightness if needed
        if(charBrightness == this.minBrightness || charBrightness == this.maxBrightness) {
            minBrightness = Double.MAX_VALUE;
            maxBrightness = -1;
            for (Map.Entry<Character, Double> currentChar : charset.entrySet()) {
                Double currentBrightness = currentChar.getValue();;
                if(currentBrightness > maxBrightness) maxBrightness = currentBrightness;
                else if(currentBrightness < minBrightness) minBrightness = currentBrightness;
            }
        }
    }

    /*
    This function gets a char and returns its brightness
     */
    private double getCharBrightness(char c) {
        boolean[][] boolArray = CharConverter.convertToBoolArray(c);
        int countTrue = 0;
        for (boolean[] booleans : boolArray) {
            for(boolean bool: booleans) {
                if(bool) countTrue += 1;
            }
        }
        return (double) countTrue / TOTAL_SQUARES;
    }
}
