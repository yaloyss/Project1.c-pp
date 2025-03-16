package com.kyrpushko.reminders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class RemindersRepository {

    List<Reminders> remindersList = new ArrayList<>();

    public boolean isEmpty() {
        return remindersList.isEmpty();
    }
    public void createReminder(String text, LocalDate date)
    {
        remindersList.add(new Reminders(text, date));
        System.out.println("Reminder created! Time: " + date);
    }

    public void deleteReminder() {
        int indexToDelete = getValidIndex("delete");
        if (indexToDelete >= 0 && indexToDelete < remindersList.size()) {
            remindersList.remove(indexToDelete);
            System.out.println("Reminder deleted.");
        } else {
            System.out.println("Invalid reminder number.");
        }
    }

    public void showReminders() {
        if (remindersList.isEmpty()) {
            System.out.println("No reminders available.");
        } else {
            System.out.println("\nReminders list:");
            for (Reminders reminder : remindersList) {
                System.out.println(reminder);
            }
        }
    }

    public void updateReminder() {
        int indexToUpdate = getValidIndex("update");

        if (indexToUpdate >= 0 && indexToUpdate < remindersList.size()) {
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

    public List<Reminders> searchReminder(String searchText) {
        List<Reminders> foundReminders = new ArrayList<>();
        for (Reminders reminder : remindersList)
        {
            if (reminder.getText().toLowerCase().contains(searchText.toLowerCase()))
            {
                foundReminders.add(reminder);
            }
        }
        return foundReminders;
    }
    public void sortReminders(String sortCriteria) {
        switch (sortCriteria) {
            case "time":
                remindersList.sort(new Comparator<Reminders>() {
                    @Override
                    public int compare(Reminders r1, Reminders r2) {
                        return r1.getTime().compareTo(r2.getTime());
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
        for (Reminders reminder : remindersList) {
            System.out.println(reminder);
        }
    }

    private int getValidIndex(String action) {
        System.out.println("\nChoose the reminder number to " + action + ":");
        for (int i = 0; i < remindersList.size(); i++) {
            System.out.println(i + 1 + ". " + remindersList.get(i));
        }

        try {
            System.out.print("> ");
            int index = Integer.parseInt(System.console().readLine()) - 1;
            return index;
        } catch (NumberFormatException e) {
            System.out.println("Please input a valid number.");
            return -1;
        }
    }
}
