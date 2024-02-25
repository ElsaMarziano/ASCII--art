package ascii_art;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class Shell {
    public static void main(String[] args) throws InvocationTargetException,
            NoSuchMethodException, IllegalAccessException {
//        Image image = new Image("./board.jpeg");
//        AsciiArtAlgorithm algo = new AsciiArtAlgorithm(image, 2,
//                new char[]{'o', 'm'});
//        System.out.print(Arrays.deepToString(algo.run()));
        Shell.run();
    }

    private static void exit() {
        System.exit(0);
    }

    public static void run() throws NoSuchMethodException, InvocationTargetException,
            IllegalAccessException {
        System.out.print(">>> ");
        String command;
        while (true) {
            command = KeyboardInput.readLine();
            Method exitMethod = Shell.class.getDeclaredMethod(command);
            exitMethod.setAccessible(true);
            exitMethod.invoke(null);
            System.out.print(">>> ");
        }
    }

}
