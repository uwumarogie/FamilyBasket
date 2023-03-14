package com.example.pages;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.familybasket.ChooseLoginOption;
import com.example.familybasket.R;
import com.example.familybasket.databinding.ActivityMainBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private ArrayList<String> items;
    private ArrayAdapter<String> itemsAdapter;
    private ListView listView;
    private Button button;
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBinding();
        setContentView(binding.getRoot());
        database = FirebaseDatabase.getInstance(getString(R.string.firebaseUrl));
        myRef = database.getReference();
        initToolbar();
        initListView();
        retrieveDataFromTheDatabase(database);
        initButton();
        initNavigation();
    }

    private void retrieveDataFromTheDatabase(FirebaseDatabase database) {

        DatabaseReference reference = database.getReference("tasks");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Task> tasks = new ArrayList<>();
                for (DataSnapshot taskSnapshot : dataSnapshot.getChildren()) {
                    Task task = taskSnapshot.getValue(Task.class);
                    tasks.add(task);
                }
                checkIfTasksIsEmpty(tasks);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });
    }

    private void checkIfTasksIsEmpty(List<Task> tasks) {
        if (tasks.size() == 0) {
            return;
        }
        displayAllTaskFromDataBaseToTheListView(tasks);
    }

    private void displayAllTaskFromDataBaseToTheListView(List<Task> tasks) {
        for (int i = 0; i < tasks.size(); i++) {
            if (!tasks.get(i).getTasks().equals("")) {
                itemsAdapter.add(tasks.get(i).getTasks());
            }
        }
    }

    public void initBinding() {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
    }

    public void initToolbar() {
        setSupportActionBar(binding.toolbar);
    }

    public void initButton() {
        button = findViewById(R.id.buttonFirst);
        button.setOnClickListener(this::addItem);
    }

    public void initListView() {
        listView = findViewById(R.id.ListView);
        button = findViewById(R.id.buttonFirst);
        items = new ArrayList<>();
        itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(itemsAdapter);
        setUpListViewListener();
    }

    public void initNavigation() {
        NavController navController = Navigation.findNavController(
                this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController,
                appBarConfiguration);
    }

    private void setUpListViewListener() {
        listView.setOnItemLongClickListener((adapterView, view, position, l) -> {
            Context context = getApplicationContext();
            Toast.makeText(context, "Items Removed", Toast.LENGTH_LONG).show();
            removeTaskFromTheDatabase(items.get(position));
            itemsAdapter.remove(items.get(position));
            itemsAdapter.notifyDataSetChanged();
            return true;
        });
    }

    private void removeTaskFromTheDatabase(String item) {
        DatabaseReference reference = database.getReference("tasks");
        Query query = reference.orderByChild("tasks").equalTo(item);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot taskSnapshot : snapshot.getChildren()) {
                    taskSnapshot.getRef().removeValue();
                }
                Toast.makeText(MainActivity.this, "Task removed from database",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Failed to remove task from database",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addItem(View view) {

        EditText input = findViewById(R.id.editTextTextPersonName);
        input.setSelection(input.getText().length());
        String item = input.getText().toString().trim();

        pushNewTaskToTheDataBase(item);
        displaySingleTask(item, input);
    }

    private void displaySingleTask(String item, EditText input) {
        if (!(item.equals(""))) {
            itemsAdapter.add(item);
            input.setText("");
        } else {
            Toast.makeText(getApplicationContext(), "Please enter text...", Toast.LENGTH_LONG)
                    .show();
        }
    }

    public void pushNewTaskToTheDataBase(String item) {
        Task newTask = new Task(item, new Date().toString(), false);
        myRef.child("tasks").push().setValue(newTask);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            returnToTheStartPage();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void returnToTheStartPage() {
        Intent intent = new Intent(getApplicationContext(), ChooseLoginOption.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this,
                R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}