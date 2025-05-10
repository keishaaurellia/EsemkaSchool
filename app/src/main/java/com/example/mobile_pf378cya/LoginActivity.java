package com.example.mobile_pf378cya;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class LoginActivity extends AppCompatActivity {

    RecyclerView recViewItemExtracurricular;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText txtUsername = findViewById(R.id.loginTxtUsername);
        EditText txtPassword = findViewById(R.id.loginTxtPassword);
        TextView txtLinkRegister = findViewById(R.id.txtLinkRegister);
        Button btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginActivity.this, "halo", Toast.LENGTH_SHORT).show();
                String usernameUser = txtUsername.getText().toString();
                String passwordUser = txtPassword.getText().toString();

                Toast.makeText(LoginActivity.this, usernameUser + "," + passwordUser, Toast.LENGTH_SHORT).show();

                JSONObject datalogin = new JSONObject();
                try {
                    datalogin.put("username", usernameUser);
                    datalogin.put("password", passwordUser);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                APIHelper apiLogin = new APIHelper("http://10.0.2.2:5000/api/Auth", "POST");
                try {
                    String result = apiLogin.execute(datalogin.toString()).get();
                    Integer responseCode = apiLogin.connection.getResponseCode();

                    if(responseCode == 200) {
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(intent);
                        Toast.makeText(LoginActivity.this, "Username atau password salah", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(LoginActivity.this, "succes", Toast.LENGTH_SHORT).show();

                    }
                    Toast.makeText(LoginActivity.this, responseCode.toString(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(LoginActivity.this, result, Toast.LENGTH_SHORT).show();
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                Toast.makeText(LoginActivity.this, datalogin.toString(), Toast.LENGTH_SHORT).show();
            }
        });


        txtLinkRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterScreenActivity.class);
                startActivity(intent);
            }
        });


    }
}