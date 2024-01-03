package com.mobileapp.moneymanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;
import android.content.DialogInterface;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SettingFragment extends Fragment {

    private DatabaseHelper databaseHelper;
    private SharedPreferences sharedPreferences;

    public SettingFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Ayarlar fragment'ının layout dosyasını yükle
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        // DatabaseHelper ve SharedPreferences tanımlamaları
        databaseHelper = new DatabaseHelper(requireContext());
        sharedPreferences = requireContext().getSharedPreferences("loginPrefs", requireContext().MODE_PRIVATE);

        // Kullanıcı adını gösteren metin
        TextView welcomeTextView = view.findViewById(R.id.welcomeTextView);
        String username = sharedPreferences.getString("username", "bilinmeyen kullanıcı");
        welcomeTextView.setText("Merhaba " + username);

        EditText currentPasswordEditText = view.findViewById(R.id.currentPasswordEditText);
        EditText newPasswordEditText = view.findViewById(R.id.newPasswordEditText);
        Button changePasswordButton = view.findViewById(R.id.changePasswordButton);

        Button logoutButton = view.findViewById(R.id.logoutButton);
        
        Button deleteAccountButton = view.findViewById(R.id.deleteAccountButton);

        deleteAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteAccountDialog();
            }
        });

        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentPassword = currentPasswordEditText.getText().toString();
                String newPassword = newPasswordEditText.getText().toString();

                if (databaseHelper.checkUser(username, currentPassword)) {
                    // Yeni şifreyi güncelle
                    databaseHelper.updatePassword(username, newPassword);

                    Toast.makeText(requireContext(), "Şifre değiştirme işlemi başarılı", Toast.LENGTH_SHORT).show();

                    currentPasswordEditText.setText("");
                    newPasswordEditText.setText("");
                } else {
                    Toast.makeText(requireContext(), "Mevcut şifre hatalı", Toast.LENGTH_SHORT).show();
                }
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("username");
                editor.remove("password");
                editor.apply();

                Toast.makeText(requireContext(), "Çıkış yapıldı", Toast.LENGTH_SHORT).show();


                Intent intent = new Intent(requireContext(), MainActivity.class);
                startActivity(intent);
                requireActivity().finish();
            }
        });

        return view;
    }

    private void showDeleteAccountDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Hesap Silme");
        builder.setMessage("Hesabınızı silmek istediğinizden emin misiniz? Bu işlem geri alınamaz.");

        builder.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String username = sharedPreferences.getString("username", "bilinmeyen kullanıcı");
                databaseHelper.deleteAccount(username);

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("username");
                editor.remove("password");
                editor.apply();

                Toast.makeText(requireContext(), "Hesap silme işlemi başarılı", Toast.LENGTH_SHORT).show();


                Intent intent = new Intent(requireContext(), MainActivity.class);
                startActivity(intent);


                requireActivity().finish();
            }
        });

        builder.setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // İptal işlemi, bir şey yapmaya gerek yok
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
