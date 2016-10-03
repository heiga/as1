package ca.ualberta.cs.lonelytwitter;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
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
import java.util.ArrayList;

import ca.ualberta.cs.lonelytwitter.HabitList;

public class HabitListActivity extends Activity {

    private static final String FILENAME = "file.sav";
    private HabitList habitList = new HabitList();
    private ArrayList<String> habitArray = new ArrayList<String>();
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_list);

    }

    @Override
    protected void onStart() {
        super.onStart();
        loadFromFile();
        getList();
    }

    private void getList() {
        for(int i = 0; i < habitList.getHabitListCount(); i++){
            habitArray.add(habitList.returnHabit(i).getName());
        }
        adapter = new ArrayAdapter<String>(this, R.layout.list_item, habitArray);

        ListView listView = (ListView) findViewById(R.id.habitListView);
        listView.setAdapter(adapter);
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
