package com.mobileapp.moneymanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText usernameEditText, passwordEditText;
    private DatabaseHelper databaseHelper;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        Button registerButton = findViewById(R.id.registerButton);
        Button loginButton = findViewById(R.id.loginButton);

        databaseHelper = new DatabaseHelper(this);
        sharedPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);

        // Otomatik giriş kontrolü
        String savedUsername = sharedPreferences.getString("username", null);
        String savedPassword = sharedPreferences.getString("password", null);

        if (savedUsername != null && savedPassword != null) {
            Log.d("Giriş", savedUsername + savedPassword);
            if (databaseHelper.checkUser(savedUsername, savedPassword)) {
                // Otomatik giriş başarılıysa, diğer işlemleri gerçekleştirin.
                Toast.makeText(MainActivity.this, "Otomatik Giriş Başarılı", Toast.LENGTH_SHORT).show();
                // Giriş başarılıysa MainPageActivity'yi başlat
                Intent intent = new Intent(MainActivity.this, MainPageActivity.class);
                startActivity(intent);
                finish(); // Bu aktiviteyi kapat
            }
        }

        // Kayıt işlemi
        registerButton.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            // Kullanıcı adı ya da şifre boşsa uyarı ver
            if (username.equals("") || password.equals("")) {
                Toast.makeText(MainActivity.this, "Kullanıcı adı veya şifre boş olamaz.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Aynı kullanıcı adında kontrol
            if (databaseHelper.checkUsernameExist(username)) {
                Toast.makeText(MainActivity.this, "Bu kullanıcı adı zaten kullanılmaktadır.", Toast.LENGTH_SHORT).show();
            } else {
                long result = databaseHelper.addUser(username, password);

                if (result != -1) {
                    // Kayıt başarılıysa otomatik olarak giriş yap
                    sharedPreferences.edit().putString("username", username).apply();
                    sharedPreferences.edit().putString("password", password).apply();

                    Toast.makeText(MainActivity.this, "Kayıt ve Giriş Başarılı", Toast.LENGTH_SHORT).show();
                    // Giriş başarılıysa MainPageActivity'yi başlat
                    Intent intent = new Intent(MainActivity.this, MainPageActivity.class);
                    startActivity(intent);
                    finish(); // Bu aktiviteyi kapat
                } else {
                    Toast.makeText(MainActivity.this, "Kayıt Başarısız", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Giriş işlemi
        loginButton.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            // Kullanıcı adı ya da şifre boşsa uyarı ver
            if (username.equals("") || password.equals("")) {
                Toast.makeText(MainActivity.this, "Kullanıcı adı veya şifre boş olamaz.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (databaseHelper.checkUser(username, password)) {
                sharedPreferences.edit().putString("username", username).apply();
                sharedPreferences.edit().putString("password", password).apply();

                // Giriş başarılıysa MainPageActivity'yi başlat
                Intent intent = new Intent(MainActivity.this, MainPageActivity.class);
                startActivity(intent);
                finish(); // Bu aktiviteyi kapat
            } else {
                Toast.makeText(MainActivity.this, "Geçersiz kullanıcı adı veya şifre", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
