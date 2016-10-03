package ca.ualberta.cs.lonelytwitter;

import android.app.Activity;
import android.content.Intent;
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
    TextView completeNumber;
    TextView lastCompleted;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_habit);
        loadFromFile();

        Intent intent = getIntent();
        position = (Integer) intent.getSerializableExtra("habitPosition");
        habit = habitList.returnHabit(position);

        TextView title = (TextView) findViewById(R.id.viewHabitName);
        title.setText(habit.getName());
        TextView desc = (TextView) findViewById(R.id.viewHabitNotes);
        desc.setText(habit.getNotes());
        TextView date = (TextView) findViewById(R.id.viewHabitDate);
        date.setText(dateToString(habit.getCreationDate()));
        TextView days = (TextView) findViewById(R.id.viewHabitDays);
        days.setText(daysToString(habit.getDaysOfWeek()));

        completeNumber = (TextView) findViewById(R.id.completedNumber);
        lastCompleted = (TextView) findViewById(R.id.lastCompletionDate);

        printChanges();

        Button deleteHabitButton = (Button) findViewById(R.id.deleteHabitButton);
        deleteHabitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setResult(RESULT_OK);
                habitList.deleteHabit(position);
                saveInFile();
                finish();
            }
        });

        Button completeHabitButton = (Button) findViewById(R.id.completeHabitButton);
        completeHabitButton.setOnClickListener( new View.OnClickListener() {
            public void onClick(View v) {
                setResult(RESULT_OK);
                Date completionDate = new Date();
                habitList.returnHabit(position).habitCompletion(completionDate);
                saveInFile();
                printChanges();
            }
        });

        Button completionHistoryButton = (Button) findViewById(R.id.viewCompletionButton);
        completionHistoryButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setResult(RESULT_OK);
                Intent intent = new Intent(getApplicationContext(), CompletionHistory.class);
                intent.putExtra("habitPosition", position);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
        loadFromFile();
        printChanges();
    }

    private void printChanges() {
        habit = habitList.returnHabit(position);
        if(habit.getCompletedDates().size() > 0) {
            int index = habit.getCompletedDates().size() - 1;
            lastCompleted.setText(dateToString(habit.getCompletedDates().get(index)));
            completeNumber.setText(String.valueOf(habit.getHabitCount()));
        }
        else {
            lastCompleted.setText("never");
            completeNumber.setText("0");
        }
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
