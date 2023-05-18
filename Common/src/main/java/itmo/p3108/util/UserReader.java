package itmo.p3108.util;

import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Class UserReader  response for proper reading from console
 */
final public class UserReader {
    private static final Scanner scanner = new Scanner(System.in);

    private UserReader() {
    }

    public static String read() {
        System.out.print("$ ");
        try {
            return scanner.nextLine();
        } catch (NoSuchElementException e) {
            System.out.println("exit from program");
            System.exit(-1);
            return "";
        }
    }

    public static String readWithoutDollar() {
        try {
            return scanner.nextLine();
        } catch (NoSuchElementException e) {
            System.out.println("exit from program");
            System.exit(-1);
            return "";
        }
    }
}
