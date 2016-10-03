package ca.ualberta.cs.lonelytwitter;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

public class TODO_habits extends HabitListActivity {

    // This is supposed to be the same as HabitListActivity except it only displays
    // habits to do today

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_habits);
    }
}
