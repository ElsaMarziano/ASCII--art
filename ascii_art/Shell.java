package ascii_art;

import ascii_output.ConsoleAsciiOutput;
import ascii_output.HtmlAsciiOutput;
import image.Image;
import image_char_matching.SubImgCharMatcher;

import java.io.IOException;
import java.util.function.Consumer;


public class Shell {
    private static final String INVALID_COMMAND_MESSAGE =
            "Did not execute due to incorrect format.";
    private static final String INVALID_CHARS_MESSAGE =
            "Did not change resolution due to incorrect format.";
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

    private static final SubImgCharMatcher imgCharMatcher = new SubImgCharMatcher(
            new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'});
    private static final String PROMPT = ">>> ";
    private static final int ASCII_START = 32;
    private static final int ASCII_END = 126;
    private static final ConsoleAsciiOutput console = new ConsoleAsciiOutput();
    private static final HtmlAsciiOutput htmlAsciiOutput = new HtmlAsciiOutput("out.html",
            "Courier New");
    // Default to console output
    private static String selectedOutputStream = "console";
    private static int resolution = 128;
    private static Image loadedImage;

    public static void main(String[] args) {
        try {
            Shell.loadedImage = new Image("cat.jpeg");
            Shell.run();
        } catch (IOException error) {
            System.out.println(error.getMessage());
        }

    }

    public static void run() {
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
                            throw new IllegalArgumentException(INVALID_COMMAND_MESSAGE);
                        System.exit(0);
                    }
                    case "chars" -> {
                        if (params != null)
                            throw new IllegalArgumentException(INVALID_CHARS_MESSAGE);
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
                            throw new IllegalArgumentException(INVALID_COMMAND_MESSAGE);
                        Shell.asciiArt();
                    }
                    default -> System.out.println(COMMAND_DOESNT_EXIT);
                }
                System.out.print(PROMPT);
            } catch (IllegalArgumentException err) {
                System.out.println(err.getMessage());
                System.out.print(PROMPT);
            }
        }
    }

    /*
    This function can add or remove chars from the charset according to the consumer passed as a
    parameter
     */
    private static void changeCharSet(String param, Consumer<Character> consumer,
                                      String action)
            throws IllegalArgumentException {
        if (param == null || param.trim().isEmpty())
            throw new IllegalArgumentException(INVALID_ADD_MESSAGE);
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
            throw new IllegalArgumentException("Did not " + action +
                    " due to incorrect format.");
        }
    }


    private static void res(String change) throws IllegalArgumentException {
        if (change == null)
            throw new IllegalArgumentException(INVALID_RESOLUTION_COMMAND_MESSAGE);
        int minCharsInRows = Math.max(1,
                Shell.loadedImage.getWidth() / Shell.loadedImage.getHeight());
        switch (change) {
            case "up":
                if (Shell.resolution * 2 > Shell.loadedImage.getWidth())
                    throw new IllegalArgumentException(FAIL_IN_CHANGING_RESOLUTION_MESSAGE);
                else Shell.resolution *= 2;
                break;
            case "down":
                if (Shell.resolution / 2 < minCharsInRows)
                    throw new IllegalArgumentException(FAIL_IN_CHANGING_RESOLUTION_MESSAGE);
                else Shell.resolution /= 2;
                break;
            default:
                throw new IllegalArgumentException(INVALID_RESOLUTION_COMMAND_MESSAGE);
        }
        System.out.println("Resolution set to " + Shell.resolution);
    }

    private static void image(String path) throws IllegalArgumentException {
        try {
            // TODO Check this doesn't erase the image if wrong path is entered
            if (path == null || path.isEmpty()) {
                throw new IllegalArgumentException(INVALID_IMAGE_MESSAGE);
            }
            Shell.loadedImage = new Image(path);
        } catch (IOException e) {
            throw new IllegalArgumentException(INVALID_IMAGE_MESSAGE);
        }
    }

    private static void output(String outputStream) throws IllegalArgumentException {
        if (outputStream == null)
            throw new IllegalArgumentException(INVALID_OUTPUT_COMMAND_MESSAGE);
        switch (outputStream) {
            case "console":
            case "html":
                selectedOutputStream = outputStream;
                break;
            default:
                throw new IllegalArgumentException(INVALID_OUTPUT_COMMAND_MESSAGE);
        }
    }

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


