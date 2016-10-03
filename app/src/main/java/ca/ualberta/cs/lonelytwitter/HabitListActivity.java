package ca.ualberta.cs.lonelytwitter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
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

    private ArrayList<Habit> habitCollection = new ArrayList<Habit>();
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_list);
        listView = (ListView) findViewById(R.id.habitListView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        habitList = new HabitList();
        //habitArray.clear();
        loadFromFile();
        getList();
    }

    private void getList() {
        habitArray.clear();
        for(int i = 0; i < habitList.getHabitListCount(); i++){
            habitArray.add(habitList.returnHabit(i).getName());
            habitCollection.add(habitList.returnHabit(i));
        }
        adapter = new ArrayAdapter<String>(this, R.layout.list_item, habitArray);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getApplicationContext(), EditHabit.class);
                intent.putExtra("habitPosition", position);
                startActivity(intent);
            }
        });
    }

    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();
            //Code taken from http://stackoverflow.com/questions/12384064/gson-convert-from-json-to-a-typed-arraylistt
            //Semptember 22, 2016
            //Type listType = new TypeToken<HabitList>(){}.getType();
            //habitList = gson.fromJson(in, listType);
            habitList = gson.fromJson(in, habitList.getClass());

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            //throw new RuntimeException();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            //throw new RuntimeException();
        }
    }
}
