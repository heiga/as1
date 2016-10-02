package ca.ualberta.cs.lonelytwitter;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Phillip Hoang on 2016-10-01.
 */
public class HabitTrackerTest extends ActivityInstrumentationTestCase2 {

    public HabitTrackerTest() {
        super(ca.ualberta.cs.lonelytwitter.LonelyTwitterActivity.class);
    }

    public void testStart() throws Exception {
        Activity activity = getActivity();

    }

    public void testHabitCreation() {
        Date date = new Date();
        ArrayList<String> days = new ArrayList<String>();
        days.add("M");
        days.add("T");
        Habit habit = new Habit("some habit", "do some stuff", date, days);
        assertEquals(habit.getName(),"some habit");
        assertEquals(habit.getNotes(), "do some stuff");
        assertEquals(habit.getCreationDate(), date);
        assertTrue(habit.getDaysOfWeek().contains("M"));
    }

    public void testHabitCompletion() {
        Date date = new Date();
        ArrayList<String> days = new ArrayList<String>();
        days.add("M");
        days.add("T");
        Habit habit = new Habit("some habit", "do some stuff", date, days);
        habit.habitCompletion(date);
        assertFalse(habit.getCompletedDates().isEmpty());
    }

    public void testHabitCompletionDeletion() {
        Date date = new Date();
        ArrayList<String> days = new ArrayList<String>();
        days.add("M");
        days.add("T");
        Habit habit = new Habit("some habit", "do some stuff", date, days);
        habit.habitCompletion(date);
        habit.habitRemoval(date);
        assertTrue(habit.getCompletedDates().isEmpty());
    }

    public void testGetHabitCount() {
        Date date = new Date();
        Date date2 = new Date(1111);
        Date date3 = new Date(1112);
        ArrayList<String> days = new ArrayList<String>();
        days.add("M");
        days.add("T");
        Habit habit = new Habit("some habit", "do some stuff", date, days);
        assertEquals(habit.getHabitCount(), 0);
        habit.habitCompletion(date2);
        assertEquals(habit.getHabitCount(), 1);
        habit.habitCompletion(date3);
        assertEquals(habit.getHabitCount(), 2);
        habit.habitRemoval(date3);
        assertEquals(habit.getHabitCount(), 1);
        habit.habitRemoval(date2);
        assertEquals(habit.getHabitCount(), 0);
    }
}
