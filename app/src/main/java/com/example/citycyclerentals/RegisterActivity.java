package com.example.citycyclerentals;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    EditText email, nic, name, phone, password, comPassword;
    Button registerBtn;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        dbHelper = new DatabaseHelper(this);

        email = findViewById(R.id.email);
        nic = findViewById(R.id.nic);
        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        password = findViewById(R.id.password);
        comPassword = findViewById(R.id.comPassword);
        registerBtn = findViewById(R.id.register_btn);

        registerBtn.setOnClickListener(v -> {
            String emailStr = email.getText().toString();
            String nicStr = nic.getText().toString();
            String nameStr = name.getText().toString();
            String phoneStr = phone.getText().toString();
            String passwordStr = password.getText().toString();
            String comPasswordStr = comPassword.getText().toString();

            if (emailStr.isEmpty() || nicStr.isEmpty() || nameStr.isEmpty() || phoneStr.isEmpty() || passwordStr.isEmpty() || comPasswordStr.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            } else if (!passwordStr.equals(comPasswordStr)) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            } else {
                boolean registered = dbHelper.registerUser(emailStr, nicStr, nameStr, phoneStr, passwordStr);
                if (registered) {
                    Toast.makeText(this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, UserLogin.class));
                } else {
                    Toast.makeText(this, "Registration Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
