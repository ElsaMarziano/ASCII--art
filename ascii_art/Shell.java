package ascii_art;

import image_char_matching.SubImgCharMatcher;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;
import java.util.function.Consumer;


public class Shell {
    private static final String COMMAND_DOESNT_EXIT = "Did not execute due to incorrect command";
    private static final SubImgCharMatcher imgCharMatcher = new SubImgCharMatcher(new char[]{'0',
            '1', '2',
            '3', '4', '5', '6', '7', '8', '9'});

    public static void main(String[] args) throws InvocationTargetException,
            IllegalAccessException {
//        Image image = new Image("./board.jpeg");
//        AsciiArtAlgorithm algo = new AsciiArtAlgorithm(image, 2,
//                new char[]{'o', 'm'});
//        System.out.print(Arrays.deepToString(algo.run()));
        Shell.run();
    }

    // TODO Check what the others exception are
    public static void run() throws InvocationTargetException,
            IllegalAccessException {
        System.out.print(">>> ");
        String command;
        while (true) {
//            try {
            command = KeyboardInput.readLine();
            String methodName = command.split(" ")[0];
            String params = command.split(" ").length > 1 ? command.split(" ")[1] : null;
            switch (methodName) {
                case "exit" -> Shell.exit();
                case "chars" -> Shell.chars();
                case "add" -> Shell.changeCharSet(Objects.requireNonNull(params),
                        Shell.imgCharMatcher::addChar);
                case "remove" -> Shell.changeCharSet(Objects.requireNonNull(params),
                        Shell.imgCharMatcher::removeChar);
            }

            System.out.print(">>> ");
//            }
//            catch (NoSuchMethodException err) {
//                System.out.println(COMMAND_DOESNT_EXIT);
//                System.out.print(">>> ");
//            }
        }
    }

    private static void chars() {
        Shell.imgCharMatcher.printCharSet();
    }

    private static void exit() {
        System.exit(0);
    }

    private static void changeCharSet(String param, Consumer<Character> consumer) throws IllegalArgumentException {
        if (param.equals("all")) {
            //TODO magic numbers
            for (int i = 32; i <= 126; i++) {
                consumer.accept((char) i);
            }
        } else if (param.equals("space"))
            consumer.accept(' ');
        else if (param.length() == 1)
            consumer.accept(param.charAt(0));
        else if (param.matches(".-.")) {
            char char1 = param.charAt(0);
            char char2 = param.charAt(2);
            for (int i = Math.min(char1, char2); i <= Math.max(char1, char2); i++) {
                consumer.accept((char) i);
            }
        } else {
            //TODO Change it to be generic
            throw new IllegalArgumentException("Did not add due to incorrect format.");
        }
    }


    private static void res() {

    }

    private static void image() {

    }

    private static void output() {

    }

    private static void asciiArt() {

    }
}
