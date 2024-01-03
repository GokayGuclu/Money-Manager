package com.mobileapp.moneymanager;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainPageActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    int[] menuItems = {R.id.action_transactions, R.id.action_wallets, R.id.action_settings};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        // Listener ekle
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == menuItems[0]) {
                loadFragment(new TransactionsFragment());
            } else if (id == menuItems[1]) {
                loadFragment(new WalletsFragment());
            } else if (id == menuItems[2]) {
                loadFragment(new SettingFragment());
            }
            return true;
        });


        loadFragment(new TransactionsFragment());
    }


    private void loadFragment(Fragment fragment) {
        if (fragment != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }
}

