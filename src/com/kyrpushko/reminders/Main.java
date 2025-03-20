package com.kyrpushko.reminders;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
//import color.Color;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        var remindersRepository = new RemindersRepository();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        System.out.println("====================================");
        System.out.println("Welcome to the Reminder App!");
        System.out.println("Choose a command: create, delete, show, update, search, sort, exit");
        System.out.println("====================================");
//        System.out.println("Choose a command:" + " "
//                + Color.colorize(Color.COLOR_GREEN, "create") + ", "
//                + Color.colorize(Color.COLOR_BLUE, "delete") + ", "
//                + Color.colorize(Color.COLOR_YELLOW, "show") + ", "
//                + Color.colorize(Color.COLOR_CYAN, "update") + ", "
//                + Color.colorize(Color.COLOR_RED, "search") + ", "
//                + Color.colorize(Color.COLOR_WHITE, "sort") + ", "
//                + Color.colorize(Color.COLOR_PURPLE, "exit"));

        while (running) {
            System.out.print(">");
            String command = scanner.nextLine();

            switch (command) {
                case "create":
                    System.out.print("Enter reminder text: ");
                    String text = scanner.nextLine();
                    var reminderDate = remindersRepository.getValidDate(scanner, dateFormatter);
                    remindersRepository.createReminder(text, reminderDate);
                    break;

                case "delete":
                    if (remindersRepository.isEmpty()) {
                        System.out.println("No reminders to delete.");
                        break;
                    }
                    remindersRepository.deleteReminder();
                    break;

                case "show":
                    remindersRepository.showReminders();
                    break;

                case "update":
                    if (remindersRepository.isEmpty()) {
                        System.out.println("No reminders to update.");
                        break;
                    }
                    remindersRepository.updateReminder();
                    break;

                case "search":
                    System.out.print("Enter the reminder text to search for: ");
                    String searchText = scanner.nextLine();

                    List<Reminders> foundReminders = remindersRepository.searchReminder(searchText);
                    if (foundReminders.isEmpty()) {
                        System.out.println("No matching reminders found.");
                    } else {
                        System.out.println("\nFound reminders:");
                        for (Reminders reminder : foundReminders) {
                            System.out.println(reminder);
                        }
                    }
                    break;

                case "sort":
                    System.out.println("Choose sorting criteria: time, text, or completed");
                    String sortCriteria = scanner.nextLine();
                    remindersRepository.sortReminders(sortCriteria);
                    break;

                case "exit":
                    System.out.println("\nThe program is closing. Goodbye!");
                    running = false;
                    break;

                default:
                    System.out.println("\nInvalid command. Available commands: create, delete, show, update, search, sort, exit");
                    break;
            }
        }
        scanner.close();
    }
}
