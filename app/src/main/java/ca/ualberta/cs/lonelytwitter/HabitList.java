package ca.ualberta.cs.lonelytwitter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Phillip Hoang on 2016-10-01.
 */
public class HabitList {
    private ArrayList<Habit> habitList;

    public HabitList(){
        habitList = new ArrayList<Habit>();
    }

    public void addHabit(String habitName, String habitNotes,
                         Date dateOfHabit, ArrayList<String> weekDays) {
        Habit habitToAdd = new Habit(habitName, habitNotes, dateOfHabit, weekDays);
        habitList.add(habitToAdd);
    }

    public void addHabit(Habit habit) {
        habitList.add(habit);
    }

    public void deleteHabit(Habit habitToDelete) {
        habitList.remove(habitToDelete);
    }

    public void deleteHabit(int index) {
        habitList.remove(index);
    }

    public int getHabitListCount() {
        return habitList.size();
    }

    public Habit returnHabit(int index) {
        if (habitList.size() == 0) {
            return null;
        }
        return habitList.get(index);
    }

}
