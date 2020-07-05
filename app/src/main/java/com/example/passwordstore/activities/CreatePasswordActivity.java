package com.example.passwordstore.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.passwordstore.R;
import com.example.passwordstore.model.Password;
import com.example.passwordstore.model.PasswordViewModel;

public class CreatePasswordActivity extends AppCompatActivity {
    EditText titleEdit, userEdit, passwordEdit;
    private PasswordViewModel passwordViewModel;
    String TAG = "CreatePasswordActivity";
    int fromWhere,id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_password);

        titleEdit = findViewById(R.id.title_edit);
        userEdit = findViewById(R.id.username_edit);
        passwordEdit = findViewById(R.id.password_edit);

        Intent intent = getIntent();
        titleEdit.setText(intent.getStringExtra("title"));
        userEdit.setText(intent.getStringExtra("subtitle"));
        passwordEdit.setText(intent.getStringExtra("password"));
        id = intent.getIntExtra("id",0);

        fromWhere = intent.getIntExtra("fromWhere",0);

        passwordViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(PasswordViewModel.class);

    }
    public void go_main(View view){
        finish();
    }

    public void savePassword(View view) {
        String title = titleEdit.getText().toString();
        String username = userEdit.getText().toString();
        String pass = passwordEdit.getText().toString();
        Password password = new Password(pass, title, username);

        if (fromWhere==MainActivity.CREATE_PASSWORD){
            passwordViewModel.insert(password);
        }
        else{
            password.setId(id);
            passwordViewModel.updateItem(password);
        }
        finish();
    }
}
