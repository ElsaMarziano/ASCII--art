package ascii_art;

import ascii_output.ConsoleAsciiOutput;
import ascii_output.HtmlAsciiOutput;
import image.Image;
import image_char_matching.SubImgCharMatcher;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.function.Consumer;

/**
 * The Shell class provides a command-line interface for interacting with the ASCII art generator.
 * It allows users to perform various actions such as changing the character set, adjusting
 * resolution, loading images, and generating ASCII art.
 *
 * @author Elsa Sebagh and Aharon Saksonov
 */
public class Shell {
    // Constants for error messages

    private static final String INVALID_COMMAND_MESSAGE =
            "Did not execute due to incorrect format.";
//    private static final String INVALID_CHARS_MESSAGE =
//            "Did not change resolution due to incorrect format.";
    private static final String INVALID_RESOLUTION_COMMAND_MESSAGE =
            "Did not change resolution due to incorrect format.";
    private static final String INVALID_OUTPUT_COMMAND_MESSAGE =
            "Did not change output method due to incorrect format.";
    private static final String FAIL_IN_CHANGING_RESOLUTION_MESSAGE =
            "Did not change resolution due to exceeding boundaries.";
    private static final String COMMAND_DOESNT_EXIT = "Did not execute due to" +
            " incorrect command.";
    private static final String INVALID_ADD_MESSAGE = "Did not add due to" +
            " incorrect format.";
    private static final String INVALID_IMAGE_MESSAGE = "Did not change image due to" +
            " problem with image file.";


    // Character set matcher
    private static final SubImgCharMatcher imgCharMatcher = new SubImgCharMatcher(
            new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'});
    // Prompt string
    private static final String PROMPT = ">>> ";
    // Default image file name
    private static final String DEFAULT_IMAGE = "cat.jpeg";
    private static final int ASCII_START = 32;
    private static final int ASCII_END = 126;
    private static final ConsoleAsciiOutput console = new ConsoleAsciiOutput();
    private static final HtmlAsciiOutput htmlAsciiOutput = new HtmlAsciiOutput("out.html",
            "Courier New");

    // Default to console output
    private static String selectedOutputStream = "console";
    private static int resolution = 128;
    private static Image loadedImage;

    /**
     * Main method to start the ASCII art shell.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        try {
            Shell.loadedImage = new Image(DEFAULT_IMAGE);
            Shell.run();
        } catch (IOException error) {
            System.out.println(error.getMessage());
            //TODO Check if there more exceptions to catch
        }

    }


    /**
     * Runs the ASCII art shell, allowing users to input commands and interact with the generator.
     *
     * @throws InvalidActionException If an invalid action is encountered.
     */
    public static void run() throws InvalidActionException{
        System.out.print(PROMPT);
        String command;
        while (true) {
            try {
                command = KeyboardInput.readLine();
                String methodName = command.split(" ")[0];
                String params = command.split(" ").length > 1 ?
                        command.split(" ")[1] : null;
                switch (methodName) {
                    case "exit" -> {
                        if (params != null)
                            throw new InvalidActionException(INVALID_COMMAND_MESSAGE);
                        System.exit(0);
                    }
                    case "chars" -> {
                        if (params != null)
                            //TODO Check if the correction of this constant is OK
                            throw new InvalidActionException(INVALID_COMMAND_MESSAGE);
                        Shell.imgCharMatcher.printCharSet();
                    }
                    case "add" -> Shell.changeCharSet(params,
                            Shell.imgCharMatcher::addChar, "add");
                    case "remove" -> Shell.changeCharSet(params,
                            Shell.imgCharMatcher::removeChar, "remove");
                    case "res" -> Shell.res(params);
                    case "image" -> Shell.image(params);
                    case "output" -> Shell.output(params);
                    case "asciiArt" -> {
                        if (params != null)
                            throw new InvalidActionException(INVALID_COMMAND_MESSAGE);
                        Shell.asciiArt();
                    }
                    default -> System.out.println(COMMAND_DOESNT_EXIT);
                }
                System.out.print(PROMPT);
            } catch (InvalidActionException err) {
                System.out.println(err.getMessage());
                System.out.print(PROMPT);
            }
        }
    }

    /*
     * Changes the character set according to the given parameters.
     *
     * @param param   The parameter indicating the character set modification.
     * @param consumer The consumer function for character set modification.
     * @param action  The action performed (add or remove).
     * @throws InvalidActionException If an invalid action is encountered.
     */
    private static void changeCharSet(String param, Consumer<Character> consumer,
                                      String action)
            throws InvalidActionException {
        if (param == null || param.trim().isEmpty())
            throw new InvalidActionException(INVALID_ADD_MESSAGE);
        else if (param.equals("all")) {
            for (int i = ASCII_START; i <= ASCII_END; i++) {
                consumer.accept((char) i);
            }
        } else if (param.equals("space"))
            consumer.accept(' ');
        else if (param.length() == 1)
            consumer.accept(param.charAt(0));
            // Handle commands of the form p-m
        else if (param.matches(".-.")) {
            // Make sure p-m = m-p
            char char1 = param.charAt(0);
            char char2 = param.charAt(2);
            for (int i = Math.min(char1, char2); i <= Math.max(char1, char2); i++) {
                consumer.accept((char) i);
            }
        } else {
            throw new InvalidActionException("Did not " + action +
                    " due to incorrect format.");
        }
    }


    /*
     * Changes the resolution of the ASCII art.
     *
     * @param change The change in resolution ("up" or "down").
     * @throws IllegalArgumentException If the change parameter is invalid.
     */
    private static void res(String change) throws InvalidActionException {
        if (change == null)
            throw new InvalidActionException(INVALID_RESOLUTION_COMMAND_MESSAGE);
        int minCharsInRows = Math.max(1,
                Shell.loadedImage.getWidth() / Shell.loadedImage.getHeight());
        switch (change) {
            case "up":
                if (Shell.resolution * 2 > Shell.loadedImage.getWidth())
                    throw new InvalidActionException(
                            FAIL_IN_CHANGING_RESOLUTION_MESSAGE);
                else Shell.resolution *= 2;
                break;
            case "down":
                if (Shell.resolution / 2 < minCharsInRows)
                    throw new InvalidActionException(FAIL_IN_CHANGING_RESOLUTION_MESSAGE);
                else Shell.resolution /= 2;
                break;
            default:
                throw new InvalidActionException(INVALID_RESOLUTION_COMMAND_MESSAGE);
        }
        System.out.println("Resolution set to " + Shell.resolution);
    }

    private static void image(String path) throws InvalidParameterException {
        try {
            // TODO Check this doesn't erase the image if wrong path is entered
            if (path == null || path.isEmpty()) {
                throw new InvalidParameterException(INVALID_IMAGE_MESSAGE);
            }
            Shell.loadedImage = new Image(path);
        } catch (IOException e) {
            //TODO Correct the exception here
            throw new InvalidParameterException(INVALID_IMAGE_MESSAGE);
        }
    }

    /*
     * Changes the output method.
     *
     * @param outputStream The output method to be changed to.
     * @throws InvalidActionException If an invalid action is encountered.
     */
    private static void output(String outputStream) throws InvalidActionException {
        if (outputStream == null)
            throw new InvalidActionException(INVALID_OUTPUT_COMMAND_MESSAGE);
        switch (outputStream) {
            case "console":
                selectedOutputStream = "console";
                break;
            case "html":
                selectedOutputStream = outputStream;
                break;
            default:
                throw new InvalidActionException(INVALID_OUTPUT_COMMAND_MESSAGE);
        }
    }

    /*
     * Generates ASCII art from the loaded image and displays it using the selected output method.
     */
    private static void asciiArt() {
        AsciiArtAlgorithm algo = new AsciiArtAlgorithm(Shell.loadedImage,
                Shell.resolution, imgCharMatcher);
        char[][] asciiArt = algo.run();
        if (selectedOutputStream.equals("console")) {
            console.out(asciiArt);
        } else if (selectedOutputStream.equals("html")) {
            htmlAsciiOutput.out(asciiArt);
        }
    }
}


