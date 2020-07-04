package com.example.passwordstore.activities;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import com.example.passwordstore.PasswordAdapter;
import com.example.passwordstore.R;
import com.example.passwordstore.model.Password;
import com.example.passwordstore.model.PasswordViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class MainActivity extends AppCompatActivity {
    private PasswordViewModel passwordViewModel;
    TextView appbarTitle;
    PasswordAdapter passwordAdapter;
    List<Password> passwordList;
    String TAG = "mainactivity";

    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appbarTitle = findViewById(R.id.textView);
        appbarTitle.setText(getResources().getString(R.string.app_name));

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

        //Remove swiped item from list and notify the RecyclerView
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                int position = viewHolder.getAdapterPosition();
                id = passwordList.get(position).getId();

                Toast.makeText(MainActivity.this, getResources().getString(R.string.deleteSuccess), Toast.LENGTH_SHORT).show();

                passwordViewModel.deleteItem(passwordList.get(position).getId());
                passwordAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                        .addSwipeLeftActionIcon(R.drawable.ic_baseline_delete_24)
                        .addSwipeLeftBackgroundColor(ContextCompat.getColor(MainActivity.this,R.color.red))
                        .create()
                        .decorate();

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),CreatePasswordActivity.class));
            }
        });
    }

}
