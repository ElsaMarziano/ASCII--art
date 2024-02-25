package image_char_matching;

import image.BrightnessCalculator;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is responsible for matching between a charset with a certain brightness and the
 * corresponding ASCII character in the set
 */
public class SubImgCharMatcher {
    private final static int TOTAL_SQUARES = CharConverter.DEFAULT_PIXEL_RESOLUTION ^ 2;
    private final Map<Character, Double> charset = new HashMap<>();
    private final Map<Double, Character> normalizedCharset = new HashMap<>();
    private double minBrightness = Double.MAX_VALUE;
    private double maxBrightness = -1;


    /**
     * Constructor for the SubImgCharMatcher class.
     *
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
     *
     * @param brightness the brightness we're looking for
     * @return the ASCII symbol to be printed
     */
    public char getCharByImageBrightness(double brightness) {
        if (this.normalizedCharset.isEmpty() || !this.normalizedCharset.containsKey(brightness)) {
            double difference = Double.MAX_VALUE;
            char closestChar = 0;
            for (Map.Entry<Character, Double> currentChar : charset.entrySet()) {
                // Calculate char brightness according to the rest of the chars in the set
                double newBrightness = this.calculateNormalizedBrightness(currentChar.getValue());
                double newDifference = Math.abs(newBrightness - brightness);
                // Update char closest to brightness
                if (newDifference < difference) {
                    difference = newDifference;
                    closestChar = currentChar.getKey();
                }
                // If the difference is the same, return the char with the lowest ASCII value
                else if (newDifference == difference) {
                    closestChar = (char) Math.min(closestChar, currentChar.getKey());
                }
            }
            normalizedCharset.put(brightness, closestChar);
            return closestChar;
        } else {
            return normalizedCharset.get(brightness);
        }
    }

    /**
     * Add a char to the charset map with its corresponding value
     *
     * @param c the char to be added
     */
    public void addChar(char c) {
        double charBrightness = BrightnessCalculator.getCharBrightness(c, TOTAL_SQUARES);
        this.charset.put(c, charBrightness);
        // Updates the min and max brightness if needed
        if (charBrightness > maxBrightness) {
            maxBrightness = charBrightness;
            this.normalizedCharset.clear();
        } else if (charBrightness < minBrightness) {
            minBrightness = charBrightness;
            this.normalizedCharset.clear();
        } else {
            double normalizedBrightness = this.calculateNormalizedBrightness(charBrightness);
            Character identicalChar = this.normalizedCharset.putIfAbsent(normalizedBrightness, c);
            if (identicalChar != null) {
                this.normalizedCharset.replace(normalizedBrightness, (char) Math.min(c,
                        identicalChar));
            }
            // If it's in the set, check if need to replace
            // Else, put it in set
        }
        //TODO Change normalized set

    }

    /**
     * Remove a char from the charset map
     *
     * @param c the char to be removed
     */
    public void removeChar(char c) {
        Double charBrightness = this.charset.get(c);
        this.charset.remove(c);
        // Update min and max brightness if needed
        if (charBrightness == this.minBrightness || charBrightness == this.maxBrightness) {
            minBrightness = Double.MAX_VALUE;
            maxBrightness = -1;
            this.normalizedCharset.clear();
            for (Map.Entry<Character, Double> currentChar : charset.entrySet()) {
                Double currentBrightness = currentChar.getValue();
                if (currentBrightness > maxBrightness) maxBrightness = currentBrightness;
                else if (currentBrightness < minBrightness) minBrightness = currentBrightness;
            }
        }
    }

    private double calculateNormalizedBrightness(double brightness) {
        double maxMinusMin = this.maxBrightness - this.minBrightness;
        return (brightness - this.minBrightness) / maxMinusMin;
    }

}
