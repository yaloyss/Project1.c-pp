import java.time.LocalDateTime;

public class Reminders
{
    private String text;
    private final LocalDateTime time;
    private boolean isCompleted;

    public Reminders (String text, LocalDateTime time)
    {
        this.text = text;
        this.time = time;
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
    public LocalDateTime getTime()
    {
        return time;
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
        return "Reminder: " + text  + ", created at: " + time +
                ", is completed=" + isCompleted + '.';
    }
}

