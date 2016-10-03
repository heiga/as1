package ca.ualberta.cs.lonelytwitter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CompletionHistory extends Activity {

    private static final String FILENAME = "file.sav";
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

        dateArray = new ArrayList<String>();
        listView = (ListView) findViewById(R.id.completionListView);
        loadFromFile();
        Intent intent = getIntent();
        position = (Integer) intent.getSerializableExtra("habitPosition");
        habit = habitList.returnHabit(position);

        adapter = new ArrayAdapter<String>(this, R.layout.list_item, dateArray);
        listView.setAdapter(adapter);
        update();

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                habit.getCompletedDates().remove(position);
                update();
                adapter.notifyDataSetChanged();
                return true;
            }
        });
    }

    /*
    public void onResume() {
        super.onResume();
        loadFromFile();
        update();
    } */

    private void update() {
        dateArray.clear();
        for(int i = 0; i < habit.getCompletedDates().size(); i++) {
            dateArray.add(dateToString(habit.getCompletedDates().get(i)));
        }
        saveInFile();
        adapter.notifyDataSetChanged();
    }

    private String dateToString(Date date){
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        return df.format(date);
    }

    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();
            //Code taken from http://stackoverflow.com/questions/12384064/gson-convert-from-json-to-a-typed-arraylistt
            //Semptember 22, 2016
            Type listType = new TypeToken<HabitList>(){}.getType();
            habitList = gson.fromJson(in, listType);

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            //throw new RuntimeException();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            //throw new RuntimeException();
        }
    }

    private void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME, 0);
            OutputStreamWriter writer = new OutputStreamWriter(fos);
            Gson gson = new Gson();
            gson.toJson(habitList, writer);
            writer.flush();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }
    }
}
