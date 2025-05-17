package client;

import java.util.Scanner;

public class ConsoleManager {
    private final Scanner scanner = new Scanner(System.in);

    public String[] readCommand() {
        System.out.print(">>> ");
        String line = scanner.nextLine().trim();
        return line.split("\s+", 2); // [0] - команда, [1] - аргументы (если есть)
    }
}