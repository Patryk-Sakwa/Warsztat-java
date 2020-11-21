package pl.coderslab;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) {
        String[][] tasks = tasks();
        tasks = menu(tasks);

    }

    public static String[][] tasks() {
        String fileName = "tasks.csv";
        Path path = Paths.get(fileName);
        String[] tasks = new String[1];
        if (Files.exists(path)) {
            int row = 0, length = 1;
            File file = new File("tasks.csv");
            try (Scanner scan = new Scanner(file);) {
                while (scan.hasNextLine()) {
                    tasks = Arrays.copyOf(tasks, length);
                    length++;
                    tasks[row] = scan.nextLine();
                    row++;
                }
            } catch (FileNotFoundException e) {
                System.out.println("Nie znaleziono pliku");
                System.exit(0);

            }
        } else {
            try {
                Files.createFile(path);
                tasks();
            } catch (IOException e) {
                System.out.println("Nie można tutaj utworzyć pliku");
                System.exit(0);
            }
        }
        int i = 0;
        String[][] result = new String[tasks.length][3];
        for (int j = 0; j < tasks.length; j++) {
            String[] parts = tasks[i].split(", ");
            result[j][0] = parts[0];
            result[j][1] = parts[1];
            result[j][2] = parts[2];
            i++;
        }

        return result;
    }

    public static String[][] menu(String[][] tasks) {
        System.out.println(ConsoleColors.BLUE + "Please select an option");
        System.out.println(ConsoleColors.RESET + "1. add\n2. remove\n3. list\n4. exit");
        int choice = 5;
        Scanner scan = new Scanner(System.in);
        while (choice <= 0 || choice > 4) {
            System.out.println("Provide a number from 0 to 4");
            try {
                choice = scan.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("The input is incorrect");
                menu(tasks);
            }
        }
        switch (choice) {
            case 1:
                menu(add(tasks));
                break;
            case 2:
                menu(remove(tasks));
                break;
            case 3:
                int number = 0;
                for (int i = 0; i < tasks.length; i++) {
                    System.out.println(number + " : " + tasks[i][0] + " " + tasks[i][1] + " " + tasks[i][2]);
                    number++;
                }
                menu(tasks);
                break;
            case 4:
                System.out.println(ConsoleColors.RED + "Bye,bye...");
                save(tasks);
                break;
        }
        return tasks;
    }

    public static void save(String[][] tasks) {
        Path path = Paths.get("tasks.csv");
        try (FileWriter fileWriter = new FileWriter("tasks.csv", false)) {
            for (int i = 0; i < tasks.length; i++) {
                for (int j = 0; j < tasks[i].length; j++) {
                    if (j < 2) {
                        fileWriter.append(tasks[i][j] + ", ");
                    } else {
                        fileWriter.append(tasks[i][j] + "\n");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }

    public static String[][] add(String[][] tasks) {
        Scanner scan = new Scanner(System.in);
        String[] add = new String[(tasks.length * 3) + 3];
        System.out.println("Please add task description");
        add[add.length - 3] = scan.nextLine();
        System.out.println("Please add task due date");
        add[add.length - 2] = scan.nextLine();
        System.out.println("Is your task important? True / False");
        add[add.length - 1] = scan.nextLine();

        int number = 0;
        for (int i = 0; i < tasks.length; i++) {
            for (int j = 0; j < tasks[i].length; j++) {
                add[number] = tasks[i][j];
                number++;
            }
        }
        number = 0;
        String[][] result = new String[tasks.length + 1][3];
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[i].length; j++) {
                result[i][j] = add[number];
                number++;
            }
        }

        return result;
    }

    public static String[][] remove(String[][] tasks){
        Scanner scan = new Scanner(System.in);

        System.out.println("Which task would you like to remove?");
        int number = scan.nextInt();
        for (int i = number ; i < tasks.length-1; i++) {
            tasks[i][0] = tasks[i+1][0];
            tasks[i][1] = tasks[i+1][1];
            tasks[i][2] = tasks[i+1][2];

        }
        String[][] result = new String[tasks.length-1][3];
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[i].length ; j++) {
                result[i][j] = tasks[i][j];
            }
        }
        return result;
    }
}
