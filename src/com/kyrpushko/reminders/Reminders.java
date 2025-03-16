package com.kyrpushko.reminders;
import java.time.LocalDate;

public class Reminders
{
    private String text;
    private final LocalDate date;
    private boolean isCompleted;

    public Reminders (String text, LocalDate date)
    {
        this.text = text;
        this.date = date;
        this.isCompleted = false;
    }
    public String getText()
    {
        return text;
    }
    public void setText(String text)
    {
        this.text = text;
    }
    public LocalDate getTime()
    {
        return date;
    }
    public boolean isCompleted()
    {
        return isCompleted;
    }
    public void setCompleted(boolean completed)
    {
        isCompleted = completed;
    }

    @Override
    public String toString()
    {
        return "Reminder: " + text  + ", created at: " + date +
                ", is completed=" + isCompleted + '.';
    }
}

