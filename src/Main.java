import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
//import color.Color;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Reminders> remindersList = new ArrayList<>();
        boolean running = true;

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
            System.out.print("n> ");
            String command = scanner.nextLine();

            switch (command) {
                case "create":
                    System.out.print("Enter reminder text: ");
                    String text = scanner.nextLine();
                    LocalDateTime time = LocalDateTime.now();
                    remindersList.add(new Reminders(text, time));
                    System.out.println("Reminder created! Time: " + time);
                    break;

                case "delete":
                    if (remindersList.isEmpty()) {
                        System.out.println("No reminders to delete.");
                        break;
                    }

                    System.out.println("\nChoose the reminder number to delete:");
                    for (int i = 0; i < remindersList.size(); i++) {
                        System.out.println(i + 1 + ". " + remindersList.get(i));
                    }

                    System.out.print("> ");
                    int index;
                    try {
                        index = Integer.parseInt(scanner.nextLine()) - 1;
                        if (index >= 0 && index < remindersList.size()) {
                            remindersList.remove(index);
                            System.out.println("Reminder deleted.");
                        } else {
                            System.out.println("Invalid reminder number.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Please input a number.");
                    }
                    break;

                case "show":
                    if (remindersList.isEmpty()) {
                        System.out.println("No reminders available.");
                    } else {
                        System.out.println("\nReminders list:");
                        for (Reminders reminder : remindersList) {
                            System.out.println(reminder);
                        }
                    }
                    break;

                case "update":
                    if (remindersList.isEmpty()) {
                        System.out.println("No reminders to update.");
                        break;
                    }

                    System.out.println("\nChoose the reminder number to update:");
                    for (int i = 0; i < remindersList.size(); i++) {
                        System.out.println(i + 1 + ". " + remindersList.get(i));
                    }

                    int updateIndex;
                    try {
                        updateIndex = Integer.parseInt(scanner.nextLine()) - 1;
                        if (updateIndex >= 0 && updateIndex < remindersList.size()) {
                            Reminders reminderToUpdate = remindersList.get(updateIndex);

                            System.out.print("Enter new reminder text: ");
                            String newText = scanner.nextLine();
                            reminderToUpdate.setText(newText);

                            System.out.print("Mark as completed? (yes/no): ");
                            String completedInput = scanner.nextLine();
                            if (completedInput.equalsIgnoreCase("yes")) {
                                reminderToUpdate.setCompleted(true);
                            } else {
                                reminderToUpdate.setCompleted(false);
                            }

                            System.out.println("Reminder updated.");
                        } else {
                            System.out.println("Invalid reminder number.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Please input a valid number.");
                    }
                    break;

                case "search":
                    List<Reminders> foundReminders = new ArrayList<>();
                    System.out.print("Enter the reminder text to search for: ");
                    String searchText = scanner.nextLine();

                    for (Reminders reminder : remindersList) {
                        if (reminder.getText().toLowerCase().contains(searchText.toLowerCase())) {
                            foundReminders.add(reminder);
                        }
                    }
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

                    switch (sortCriteria) {
                        case "time":
                            remindersList.sort(new Comparator<Reminders>() {
                                @Override
                                public int compare(Reminders r1, Reminders r2)
                                {
                                    return r1.getTime().compareTo(r2.getTime());
                                }
                            });
                            System.out.println("Reminders sorted by time:");
                            break;

                        case "text":
                            remindersList.sort(new Comparator<Reminders>() {
                                @Override
                                public int compare(Reminders r1, Reminders r2)
                                {
                                    return r1.getText().compareToIgnoreCase(r2.getText());
                                }
                            });
                            System.out.println("Reminders sorted by text alphabetically:");
                            break;

                        case "completed":
                            remindersList.sort(new Comparator<Reminders>() {
                                @Override
                                public int compare(Reminders r1, Reminders r2)
                                {
                                    return Boolean.compare(r1.isCompleted(), r2.isCompleted());
                                }
                            });
                            System.out.println("Reminders sorted by completion status:");
                            break;

                        default:
                            System.out.println("Invalid sorting criteria. Please choose 'time', 'text', or 'completed'.");
                            break;
                    }

                    for (Reminders reminder : remindersList)
                    {
                        System.out.println(reminder);
                    }
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
