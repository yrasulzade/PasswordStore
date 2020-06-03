package com.example.passwordstore.activities;

import android.content.Intent;
import android.os.Bundle;

import com.example.passwordstore.PasswordAdapter;
import com.example.passwordstore.R;
import com.example.passwordstore.model.Password;
import com.example.passwordstore.model.PasswordViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.multidex.MultiDex;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private PasswordViewModel passwordViewModel;
    TextView appbarTitle;
    PasswordAdapter passwordAdapter;
    List<Password> passwordList;
    String TAG = "mainactivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        MultiDex.install(this);

        appbarTitle = findViewById(R.id.textView);
        appbarTitle.setText("Şifrə anbarı");
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        passwordViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(PasswordViewModel.class);

        passwordViewModel.getPasswords().observe(this, new Observer<List<Password>>() {
            @Override
            public void onChanged(List<Password> passwords) {

                passwordAdapter = new PasswordAdapter(MainActivity.this, passwords);
                recyclerView.setAdapter(passwordAdapter);
                passwordList = passwords;
            }
        });



        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.DOWN | ItemTouchHelper.UP) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                Toast.makeText(MainActivity.this, "on Move", Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                //Remove swiped item from list and notify the RecyclerView
                int position = viewHolder.getAdapterPosition();
//                arrayList.remove(position);
                Toast.makeText(MainActivity.this, "on Swiped " + position, Toast.LENGTH_SHORT).show();
//                        passwords;
                passwordViewModel.deleteItem(passwordList.get(position).getId());
                passwordAdapter.notifyDataSetChanged();

            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        ////////////////////////////
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insert(view);
            }
        });
    }


    public void insert(View view){
//        Password noDo = new Password("qasim");
//        passwordViewModel.insert(noDo);
        startActivity(new Intent(getApplicationContext(),CreatePasswordActivity.class));
    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            passwordViewModel.deleteAll();
//            Log.d(TAG, "onOptionsItemSelected: " + "********");
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
