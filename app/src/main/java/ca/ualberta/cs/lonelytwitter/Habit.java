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
    private Integer habitCount;

    public Habit(String habitName, String habitNotes,
                      Date dateOfHabit, List<String> weekDays) {
        this.name = habitName;
        this.notes = habitNotes;
        this.habitDate = dateOfHabit;
        this.daysOfWeek = weekDays;
        habitCount = 0;
    }

    public void habitCompletion(Date completionDate) {
        completedDates.add(completionDate);
        habitCount += 1;
    }

    public void habitRemoval(Date completionDate) {
        completedDates.remove(completionDate);
        habitCount -= 1;
    }

    public Integer viewHabitCount() {
        return habitCount;
    }


}
