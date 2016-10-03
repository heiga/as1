package ca.ualberta.cs.lonelytwitter;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.Menu;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import ca.ualberta.cs.lonelytwitter.Habit;
import ca.ualberta.cs.lonelytwitter.HabitList;

// All DatePicker code from http://www.tutorialspoint.com/android/android_datepicker_control.htm
// Usage from this site is freely allowed for educational purposes

public class AddHabit extends Activity {

    private static final String FILENAME = "file.sav";
    private DatePicker datePicker;
    private Calendar calendar;
    private TextView dateView;
    private int year, month, day;

    private HabitList habitList = new HabitList();
    private ArrayAdapter<Habit> adapter;

    private EditText habitName;
    private EditText habitNotes;
    private Date habitDate;



    private ToggleButton Mon;
    private ToggleButton Tue;
    private ToggleButton Wed;
    private ToggleButton Thu;
    private ToggleButton Fri;
    private ToggleButton Sat;
    private ToggleButton Sun;
    /*
    private ToggleButton Mon = (ToggleButton) findViewById(R.id.toggleButton1);
    private ToggleButton Tue = (ToggleButton) findViewById(R.id.toggleButton2);
    private ToggleButton Wed = (ToggleButton) findViewById(R.id.toggleButton3);
    private ToggleButton Thu = (ToggleButton) findViewById(R.id.toggleButton4);
    private ToggleButton Fri = (ToggleButton) findViewById(R.id.toggleButton5);
    private ToggleButton Sat = (ToggleButton) findViewById(R.id.toggleButton6);
    private ToggleButton Sun = (ToggleButton) findViewById(R.id.toggleButton7);
    */

    private ArrayList<String> daysOfWeek;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_habit);

        Mon = (ToggleButton) findViewById(R.id.toggleButton1);
        Tue = (ToggleButton) findViewById(R.id.toggleButton2);
        Wed = (ToggleButton) findViewById(R.id.toggleButton3);
        Thu = (ToggleButton) findViewById(R.id.toggleButton4);
        Fri = (ToggleButton) findViewById(R.id.toggleButton5);
        Sat = (ToggleButton) findViewById(R.id.toggleButton6);
        Sun = (ToggleButton) findViewById(R.id.toggleButton7);

        dateView = (TextView) findViewById(R.id.dateDisplay);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month+1, day);

        Button addHabitButton = (Button) findViewById(R.id.addHabitButton);
        habitName = (EditText) findViewById(R.id.editHabitName);
        habitNotes = (EditText) findViewById(R.id.editHabitNotes);

        addHabitButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                loadFromFile();
                setResult(RESULT_OK);
                /*
                String text = bodyText.getText().toString();
                Tweet newTweet = new NormalTweet(text);
                tweetList.add(newTweet);
                adapter.notifyDataSetChanged();
                */
                habitDate = calendar.getTime();
                daysOfWeek = getDaysOfWeek();
                habitList.addHabit(habitName.getText().toString(), habitNotes.getText().toString(),
                        habitDate, daysOfWeek);

                Context context = getApplicationContext();
                CharSequence text = habitList.returnHabit(0).getName();
                Toast toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
                toast.show();

                saveInFile();
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
    }

    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
        Toast.makeText(getApplicationContext(), "ca", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this, myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
            // arg1 = year
            // arg2 = month
            // arg3 = day
            showDate(arg1, arg2+1, arg3);
        }
    };

    private void showDate(int year, int month, int day) {
        dateView.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
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

    private ArrayList<String> getDaysOfWeek() {
        ArrayList<String> daysOfWeek = new ArrayList<String>();
        if(Mon.isChecked()){
            daysOfWeek.add("M");
        }

        if(Tue.isChecked()){
            daysOfWeek.add("T");
        }
        if(Wed.isChecked()){
            daysOfWeek.add("W");
        }
        if(Thu.isChecked()){
            daysOfWeek.add("R");
        }
        if(Fri.isChecked()){
            daysOfWeek.add("F");
        }
        if(Sat.isChecked()){
            daysOfWeek.add("S");
        }
        if(Sun.isChecked()){
            daysOfWeek.add("U");
        }
        return daysOfWeek;
    }
}
