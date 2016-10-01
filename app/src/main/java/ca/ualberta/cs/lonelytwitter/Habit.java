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
    private List<String> daysOfWeek;

    public Habit(String habitName, String habitNotes,
                      Date dateOfHabit, List<String> weekDays) {
        this.name = habitName;
        this.notes = habitNotes;
        this.habitDate = dateOfHabit;
        this.daysOfWeek = weekDays;
    }

    public String getName() {
        return this.name;
    }

    public String getNotes() {
        return this.notes;
    }

    public Date returnCreationDate() {
        return this.habitDate;
    }

    public ArrayList<Date> returnCompletedDates() {
        return this.completedDates;
    }

    public List<String> returnDaysOfWeek() {
        return this.daysOfWeek;
    }

    public void habitCompletion(Date completionDate) {
        completedDates.add(completionDate);
    }

    public void habitRemoval(Date completionDate) {
        completedDates.remove(completionDate);
    }

    public int viewHabitCount() {
        return completedDates.size();
    }


}
