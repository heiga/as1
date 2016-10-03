package ca.ualberta.cs.lonelytwitter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

public class EditHabit extends Activity {

    private static final String FILENAME = "file.sav";
    private HabitList habitList = new HabitList();
    private Habit habit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_habit);
        loadFromFile();
        Intent intent = getIntent();
        int position = (Integer) intent.getSerializableExtra("habitPosition");
        habit = habitList.returnHabit(position);

        TextView title = (TextView) findViewById(R.id.viewHabitName);
        title.setText(habit.getName());
        TextView desc = (TextView) findViewById(R.id.viewHabitNotes);
        desc.setText(habit.getNotes());
        TextView date = (TextView) findViewById(R.id.viewHabitDate);
        date.setText(dateToString(habit.getCreationDate()));
        TextView days = (TextView) findViewById(R.id.viewHabitDays);
        days.setText(daysToString(habit.getDaysOfWeek()));

        Button deleteHabitButton = (Button) findViewById(R.id.deleteHabitButton);
        deleteHabitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                habitList.deleteHabit(habit);
                saveInFile();
                finish();
            }
        });
    }

    public void deleteHabit(View view) {
        //habitList.deleteHabit(habit);
        //saveInFile();
        finish();
    }

    private String dateToString(Date date){
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        return df.format(date);
    }

    private String daysToString(ArrayList<String> days) {
        String daysString = "";
        for (int i = 0; i < days.size(); i++) {
            daysString = daysString.concat(days.get(i));
            daysString = daysString.concat(" ");
        }
        return daysString;
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
