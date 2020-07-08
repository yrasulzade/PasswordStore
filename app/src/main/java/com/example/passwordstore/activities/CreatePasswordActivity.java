package com.example.passwordstore.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.passwordstore.ClickHandler;
import com.example.passwordstore.R;
import com.example.passwordstore.databinding.ActivityCreatePasswordBinding;
import com.example.passwordstore.model.Password;
import com.example.passwordstore.model.PasswordViewModel;

public class CreatePasswordActivity extends AppCompatActivity {
    int fromWhere, id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_password);

        ActivityCreatePasswordBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_create_password);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String username = intent.getStringExtra("subtitle");
        String pass = intent.getStringExtra("password");
        id = intent.getIntExtra("id", -1);

        fromWhere = intent.getIntExtra("fromWhere", 0);
        binding.setFromWhere(fromWhere);

        ClickHandler clickHandler = new ClickHandler(getApplication());
        Password password = new Password(pass,title, username);

        binding.setPassword(password);
        binding.setHandler(clickHandler);
        binding.setId(id);

    }
}
