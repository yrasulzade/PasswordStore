package com.example.passwordstore.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.example.passwordstore.CustomDialogClass;
import com.example.passwordstore.PasswordAdapter;
import com.example.passwordstore.R;
import com.example.passwordstore.databinding.ActivityMainBinding;
import com.example.passwordstore.databinding.ContentMainBinding;
import com.example.passwordstore.model.Password;
import com.example.passwordstore.model.PasswordViewModel;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.TextView;

import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class MainActivity extends AppCompatActivity implements CustomDialogClass.OnItemClickListener {
    private PasswordViewModel passwordViewModel;
    private PasswordAdapter passwordAdapter;
    List<Password> passwordList;
    String TAG = "mainactivity";
    public static final int CREATE_PASSWORD = 1;
    public static final int UPDATE_PASSWORD = 2;
    int id, position;
    Password password;
    boolean isUndone = false;
    ActivityMainBinding activityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

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
                isUndone = false;
                position = viewHolder.getAdapterPosition();
                id = passwordList.get(position).getId();
                password = passwordList.get(position);

                showDialog();
            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                        .addSwipeLeftActionIcon(R.drawable.ic_baseline_delete_24)
                        .addSwipeLeftBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.red))
                        .create()
                        .decorate();

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

    }

    public void showDialog() {
        CustomDialogClass customDialog = new CustomDialogClass(MainActivity.this);
        customDialog.setCanceledOnTouchOutside(true);
        customDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        customDialog.setOnItemClickListener(MainActivity.this);
        customDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                passwordAdapter.notifyItemChanged(position);
            }
        });
        customDialog.show();
    }

    public void createPassword(View view) {

        Intent intent = new Intent(getApplicationContext(), CreatePasswordActivity.class);
        intent.putExtra("fromWhere", CREATE_PASSWORD);
        startActivity(intent);
    }

    @Override
    public void onItemClick(boolean confirm) {

        if (confirm) {
            passwordList.remove(position);
            passwordAdapter.notifyDataSetChanged();

            Snackbar snackbar = Snackbar.make(activityMainBinding.getRoot(), password.getTitle() + " deleted",
                    Snackbar.LENGTH_LONG);
            View snackBarView = snackbar.getView();
            snackBarView.setBackgroundColor(Color.argb(255, 8, 20, 37));
            snackbar.addCallback(new Snackbar.Callback() {
                @Override
                public void onDismissed(Snackbar snackbar, int event) {
                    super.onDismissed(snackbar, event);
                    if (!isUndone) {
                        passwordViewModel.deleteItem(password.getId());
                        passwordAdapter.notifyDataSetChanged();
                    }
                }
            });
            snackbar.setAction(getResources().getString(R.string.undo), new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isUndone = true;
                    passwordList.add(position, password);
                    passwordAdapter.notifyItemInserted(position);
                }
            });
            snackbar.show();
        }
    }
}
