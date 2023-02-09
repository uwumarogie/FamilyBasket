package com.example.pages;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.familybasket.R;
import com.example.familybasket.databinding.ActivityMainBinding;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private ArrayList<String> items;
    private ArrayAdapter<String> itemsAdapter;
    private ListView listView;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBinding();
        setContentView(binding.getRoot());
        initToolbar();
        initListView();
        initButton();
        initNavigation();
        Realm.init(this);
        String appID = "familybasket-mzzoj";
        App app = new App(new AppConfiguration.Builder(appID).build());
    }

    /*
     * This method initializes the binding object using the ActivityMainBinding.inflate method and the getLayoutInflater method.
     * The binding object is used to access views in the layout.
     */

    public void initBinding() {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
    }

    /*
     * This method sets the toolbar as the support action bar for the activity.
     * The binding.toolbar is used to access the toolbar view.
     */
    public void initToolbar() {
        setSupportActionBar(binding.toolbar);
    }

    /*
     * This method initializes the button object by finding the view with the id R.id.buttonFirst using the findViewById method.
     * It then sets an OnClickListener on the button to call the addItem method when the button is clicked.
     */

    public void initButton() {
        button = findViewById(R.id.buttonFirst);
        button.setOnClickListener(this::addItem);
    }

    /*
     * This method initializes the listView object by finding the view with the id R.id.ListView using the findViewById method.
     * It then initializes the items list, creates an ArrayAdapter for the list, and sets it as the adapter for the listView.
     * The method also calls the setUpListViewListener method to set up a listener for the listView.
     */

    public void initListView() {
        listView = findViewById(R.id.ListView);
        button = findViewById(R.id.buttonFirst);
        items = new ArrayList<>();
        itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(itemsAdapter);
        setUpListViewListener();
    }

    /*
     * Initializes NavController object
     * Creates AppBarConfiguration object
     * Sets up action bar with NavController using NavigationUI.setupActionBarWithNavController method
     */
    public void initNavigation() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
    }

    private void setUpListViewListener() {
        listView.setOnItemLongClickListener((adapterView, view, position, l) -> {
            Context context = getApplicationContext();
            Toast.makeText(context, "Items Removed", Toast.LENGTH_LONG).show();

            items.remove(position);
            itemsAdapter.notifyDataSetChanged();
            return true;
        });
    }

    private void addItem(View view) {

        EditText input = findViewById(R.id.editTextTextPersonName);
        input.setSelection(input.getText().length());
        String itemText = input.getText().toString();

        if (!(itemText.equals(""))) {
            itemsAdapter.add(itemText);
            input.setText("");
        } else {
            Toast.makeText(getApplicationContext(), "Please enter text..", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}