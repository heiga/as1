package ca.ualberta.cs.lonelytwitter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CompletionHistory extends Activity {

    private int position;
    private Habit habit;
    private HabitList habitList;
    private ArrayAdapter<String> adapter;
    private ListView listView;
    private ArrayList<String> dateArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completion_history);

        Intent intent = getIntent();
        position = (Integer) intent.getSerializableExtra("habitPosition");
        habit = habitList.returnHabit(position);

        adapter = new ArrayAdapter<String>(this, R.layout.list_item, dateArray);

        listView.setAdapter(adapter);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                habit.getCompletedDates().remove(position);
                update();
                adapter.notifyDataSetChanged();
                return true;
            }
        });
    }

    private void update() {
        for(int i = 0; i < habit.getCompletedDates().size(); i++) {
            dateArray.clear();
            dateArray.add(dateToString(habit.getCompletedDates().get(i)));
        }
    }

    private String dateToString(Date date){
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        return df.format(date);
    }
}
