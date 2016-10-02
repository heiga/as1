package ca.ualberta.cs.lonelytwitter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by phillip2 on 9/28/16.
 */
public class Habit {

    private String name;
    private String notes;
    private Date habitDate;
    private ArrayList<Date> completedDates;
    private ArrayList<String> daysOfWeek;

    public Habit(String habitName, String habitNotes,
                      Date dateOfHabit, ArrayList<String> weekDays) {
        this.name = habitName;
        this.notes = habitNotes;
        this.habitDate = dateOfHabit;
        this.daysOfWeek = weekDays;
        this.completedDates = new ArrayList<Date>();
    }

    public String getName() {
        return this.name;
    }

    public String getNotes() {
        return this.notes;
    }

    public Date getCreationDate() {
        return this.habitDate;
    }

    public ArrayList<Date> getCompletedDates() {
        return this.completedDates;
    }

    public ArrayList<String> getDaysOfWeek() {
        return this.daysOfWeek;
    }

    public void habitCompletion(Date completionDate) {
        completedDates.add(completionDate);
    }

    public void habitRemoval(Date completionDate) {
        completedDates.remove(completionDate);
    }

    public int getHabitCount() {
        return completedDates.size();
    }


}
