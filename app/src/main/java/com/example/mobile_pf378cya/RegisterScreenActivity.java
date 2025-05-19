package com.example.mobile_pf378cya;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class RegisterScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register_screen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText txtUsername = findViewById(R.id.usernameTxtRegister);
        EditText txtPassword = findViewById(R.id.passwordTxtRegister);
        EditText txtFullName = findViewById(R.id.fullNameTxtRegister);
        EditText txtPhoneNumber = findViewById(R.id.phoneNumberTxtRegister);
        Button btnRegister = findViewById(R.id.btnRegister);
        

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = txtUsername.getText().toString().trim();
                String password = txtPassword.getText().toString().trim();
                String fullName = txtFullName.getText().toString().trim();
                String phoneNumber = txtPhoneNumber.getText().toString().trim();

                if (username.isEmpty() || password.isEmpty() || fullName.isEmpty() || phoneNumber.isEmpty()) {
                    Toast.makeText(RegisterScreenActivity.this, "Harap isi semua data!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (phoneNumber.length() < 10 || phoneNumber.length() > 15) {
                    Toast.makeText(RegisterScreenActivity.this, "Nomor telepon harus 10-15 digit!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(RegisterScreenActivity.this, "Password minimal 6 karakter!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (Patterns.PHONE.matcher(phoneNumber).matches()){
                    Toast.makeText(RegisterScreenActivity.this, "Wrong Pattern Phone", Toast.LENGTH_SHORT).show();
                }

                JSONObject registerData = new JSONObject();
                try {
                    registerData.put("name", username);
                    registerData.put("password", password);
                    registerData.put("fullname", fullName);
                    registerData.put("phoneNumber", phoneNumber);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(RegisterScreenActivity.this, "Terjadi kesalahan saat membuat data registrasi!", Toast.LENGTH_SHORT).show();
                    return;
                }

                APIHelper apiRegister = new APIHelper("http://10.0.2.2:5000/api/Register", "POST");
                try {
                    String result = apiRegister.execute(registerData.toString()).get();
                    int responseCode = apiRegister.connection.getResponseCode();

                    if (responseCode == 200) {
                        Toast.makeText(RegisterScreenActivity.this, "Registrasi berhasil!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterScreenActivity.this, LoginActivity.class);
                        startActivity(intent);
                    } else if (responseCode == 400) {
                        Toast.makeText(RegisterScreenActivity.this, "Username sudah dipakai!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(RegisterScreenActivity.this, "Registrasi gagal: " + result, Toast.LENGTH_SHORT).show();
                    }
                } catch (ExecutionException | InterruptedException | IOException e) {
                    e.printStackTrace();
                    Toast.makeText(RegisterScreenActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
