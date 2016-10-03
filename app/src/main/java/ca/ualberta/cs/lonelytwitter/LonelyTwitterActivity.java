package ca.ualberta.cs.lonelytwitter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

// The main menu
public class LonelyTwitterActivity extends Activity {

	private static final String FILENAME = "file.sav";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		File tryFile = new File(FILENAME);
		try {
			tryFile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		setContentView(R.layout.main);
	}

	public void addHabit(View view) {
		Intent intent = new Intent(this, AddHabit.class);
		startActivity(intent);
	}

	public void habitList(View view) {
		Intent intent = new Intent(this, HabitListActivity.class);
		startActivity(intent);
	}

	public void todoToday(View view) {
		Intent intent = new Intent(this, TODO_habits.class);
		startActivity(intent);
	}


}