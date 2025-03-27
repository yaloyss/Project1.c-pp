package com.kyrpushko.reminders;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;

public class RemindersRepository {
    private static final String FILE_NAME = "reminders.json";
    List<Reminders> remindersList = new ArrayList<>();

    private static final Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()) // Додаємо адаптер
            .setPrettyPrinting().create();

    public boolean isEmpty() {
        return remindersList.isEmpty();
    }
    public void createReminder(String text, LocalDate date)
    {
        remindersList.add(new Reminders(text, date));
        System.out.println("Reminder created! Date at which you will be reminded: " + date);
    }

    public void deleteReminder() {
        int indexToDelete = getValidIndex("delete");
        if (indexToDelete >= 0 && indexToDelete < remindersList.size())
        {
            remindersList.remove(indexToDelete);
            System.out.println("Reminder deleted.");
        } else {
            System.out.println("Invalid reminder number.");
        }
    }

    public void showReminders() {
        if (remindersList.isEmpty())
        {
            System.out.println("No reminders available.");
        } else {
            System.out.println("\nReminders list:");
            for (Reminders reminder : remindersList)
            {
                System.out.println(reminder);
            }
        }
    }

    public void updateReminder() {
        int indexToUpdate = getValidIndex("update");

        if (indexToUpdate >= 0 && indexToUpdate < remindersList.size())
        {
            Reminders reminderToUpdate = remindersList.get(indexToUpdate);
            System.out.print("Enter new reminder text: ");
            String newText = System.console().readLine();
            System.out.print("Mark as completed? (yes/no): ");
            String completedInput = System.console().readLine();
            boolean isCompleted = completedInput.equalsIgnoreCase("yes");

            reminderToUpdate.setText(newText);
            reminderToUpdate.setCompleted(isCompleted);
            System.out.println("Reminder updated.");
        } else {
            System.out.println("Invalid reminder number.");
        }
    }

    public List<Reminders> searchReminderByText(String searchedText) {
        List<Reminders> remindersWithSearchedText = new ArrayList<>();
        for (Reminders reminder : remindersList)
        {
            if (containsWord(reminder.getText(), searchedText))
            {
                remindersWithSearchedText.add(reminder);
            }
        }
        return remindersWithSearchedText;
    }

    public List<Reminders> searchReminderByDate(LocalDate date) {
        List<Reminders> foundReminders = new ArrayList<>();

        for (Reminders reminder : remindersList)
        {
            if (reminder.getDate().equals(date))
            {
                foundReminders.add(reminder);
            }
        }
        return foundReminders;
    }

    private boolean containsWord(String text, String word) {
        String originalText = " " + text.toLowerCase() + " ";
        String searchedInText = " " + word.toLowerCase() + " ";
        return originalText.contains(searchedInText);
    }

    public void sortReminders(String sortCriteria) {
        switch (sortCriteria) {
            case "time":
                remindersList.sort(new Comparator<Reminders>() {
                    @Override
                    public int compare(Reminders r1, Reminders r2) {
                        return r1.getDate().compareTo(r2.getDate());
                    }
                });
                System.out.println("Reminders sorted by time:");
                break;
            case "text":
                remindersList.sort(new Comparator<Reminders>() {
                    @Override
                    public int compare(Reminders r1, Reminders r2) {
                        return r1.getText().compareToIgnoreCase(r2.getText());
                    }
                });
                System.out.println("Reminders sorted by text alphabetically:");
                break;
            case "completed":
                remindersList.sort(new Comparator<Reminders>() {
                    @Override
                    public int compare(Reminders r1, Reminders r2) {
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
    }

    private int getValidIndex(String action) {
        System.out.println("\nChoose the reminder number to " + action + ":");
        for (int i = 0; i < remindersList.size(); i++)
        {
            System.out.println(i + 1 + ". " + remindersList.get(i));
        }

        try {
            System.out.print(">");
            int index = Integer.parseInt(System.console().readLine()) - 1;
            return index;
        } catch (NumberFormatException e) {
            System.out.println("Please input a valid number.");
            return -1;
        }
    }

    public static LocalDate getValidDate(Scanner scanner, DateTimeFormatter formatter) {
        while (true)
        {
            System.out.print("Enter the reminder date (yyyy-MM-dd): ");
            String dateInput = scanner.nextLine().trim();
            try
            {
                return LocalDate.parse(dateInput, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please enter the date in 'yyyy-MM-dd' format.");
            }
        }
    }

    public void saveToFile()
    {
        try (FileWriter saver = new FileWriter(FILE_NAME)) {
            gson.toJson(remindersList, saver);
        } catch (IOException e) {
            System.out.println("Error while saving reminders: " + e.getMessage());
        }
    }

    public void loadFromFile ()
    {
        try (FileReader loader = new FileReader(FILE_NAME)) {
            Type listType = new TypeToken<List<Reminders>>() {
            }.getType();
            remindersList = gson.fromJson(loader, listType);
            if (remindersList == null) {
                remindersList = new ArrayList<>();
            }
        } catch (IOException e) {
             System.out.println("No previous reminders found.");
        }
    }
}
