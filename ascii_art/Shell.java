package ascii_art;

import image.Image;
import image_char_matching.SubImgCharMatcher;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.Objects;
import java.util.function.Consumer;


public class Shell {
    private static final String COMMAND_DOESNT_EXIT = "Did not execute due to incorrect command";
    private static final SubImgCharMatcher imgCharMatcher = new SubImgCharMatcher(new char[]{'0',
            '1', '2', '3', '4', '5', '6', '7', '8', '9'});
    private static int resolution = 128;
    private static Image loadedImage;

    public static void main(String[] args) {
        try {
            Shell.loadedImage = new Image("./cat.jpeg");
            Shell.run();
        } catch (IOException error) {
            System.out.println(error.getMessage());
        }

    }

    public static void run() {
        System.out.print(">>> ");
        String command;
        while (true) {
            try {
                command = KeyboardInput.readLine();
                String methodName = command.split(" ")[0];
                String params = command.split(" ").length > 1 ? command.split(" ")[1] : null;
                switch (methodName) {
                    case "exit" -> System.exit(0);
                    case "chars" -> Shell.imgCharMatcher.printCharSet();
                    case "add" -> Shell.changeCharSet(Objects.requireNonNull(params),
                            Shell.imgCharMatcher::addChar, "add");
                    case "remove" -> Shell.changeCharSet(Objects.requireNonNull(params),
                            Shell.imgCharMatcher::removeChar, "remove");
                    case "res" -> Shell.res(params);
                    case "image" -> Shell.image(params);
                    case "output" -> Shell.output(params);
                    case "asciiArt" -> Shell.asciiArt();
                }

                System.out.print(">>> ");
            } catch (IllegalArgumentException err) {
                System.out.println(err.getMessage());
                System.out.print(">>> ");
            }
        }
    }


    private static void changeCharSet(String param, Consumer<Character> consumer,
                                      String action) throws IllegalArgumentException {
        if (param.equals("all")) {
            //TODO magic numbers and strings
            for (int i = 32; i <= 126; i++) {
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
            throw new IllegalArgumentException("Did not " + action + " due to incorrect format.");
        }
    }


    private static void res(String change) {
        // TODO make this function shorter
        int minCharsInRows = Math.max(1,
                Shell.loadedImage.getWidth() / Shell.loadedImage.getHeight());
        switch (change) {
            case "up":
                if (Shell.resolution * 2 > Shell.loadedImage.getWidth())
                    throw new IllegalArgumentException("Did not change resolution due to " +
                            "exceeding boundaries.");
                else Shell.resolution *= 2;
                System.out.println("Resolution set to " + Shell.resolution);
                break;
            case "down":
                if (Shell.resolution / 2 < minCharsInRows)
                    throw new IllegalArgumentException("Did not change resolution due to " +
                            "exceeding boundaries.");
                Shell.resolution /= 2;
                System.out.println("Resolution set to " + Shell.resolution);
                break;
            default:
                throw new IllegalArgumentException("Did not change resolution due to incorrect " +
                        "format.");

        }

    }

    private static void image(String path) {
        try {
            // TODO Check this doesn't erase the image if wrong path is entered
            Shell.loadedImage = new Image(path);
        } catch (IOException e) {
            throw new InvalidParameterException("Did not execute due to problem with image file" +
                    ".\n");
        }
    }

    private static void output(String outputStream) {

    }

    private static void asciiArt() {
//        AsciiArtAlgorithm algo = new AsciiArtAlgorithm(Shell.loadedImage, Shell.resolution,
//                {});
//        System.out.print(Arrays.deepToString(algo.run()));
    }
}
