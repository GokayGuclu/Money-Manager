package com.mobileapp.moneymanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class TransactionsFragment extends Fragment {

    private Spinner yearSpinner;
    private Spinner monthSpinner;
    private Spinner transactionTypeSpinner;
    private ListView transactionsListView;
    private FloatingActionButton addTransactionButton;

    public TransactionsFragment() {
        // Boş kurucu metot gereklidir.
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transactions, container, false);

        yearSpinner = view.findViewById(R.id.yearSpinner);
        monthSpinner = view.findViewById(R.id.monthSpinner);
        transactionTypeSpinner = view.findViewById(R.id.transactionTypeSpinner);
        transactionsListView = view.findViewById(R.id.transactionsListView);
        addTransactionButton = view.findViewById(R.id.addTransactionButton);

        // Örnek yıl ve ay verilerini oluştur
        List<String> yearList = new ArrayList<>();
        yearList.add("2022");
        yearList.add("2023");
        yearList.add("2024");
        // Diğer yılları da ekleyebilirsiniz.

        List<String> monthList = new ArrayList<>();
        monthList.add("Ocak");
        monthList.add("Şubat");
        monthList.add("Mart");
        monthList.add("Nisan");
        monthList.add("Mayıs");
        monthList.add("Haziran");
        monthList.add("Temmuz");
        monthList.add("Ağustos");
        monthList.add("Eylül");
        monthList.add("Ekim");
        monthList.add("Kasım");
        monthList.add("Aralık");
        // Diğer ayları da ekleyebilirsiniz.

        // Tür spinner'ı için örnek veri
        List<String> typeList = new ArrayList<>();
        typeList.add("Gelir");
        typeList.add("Gider");
        typeList.add("Tümü");

        // Yeni işlem ekle butonuna tıklama olayını ekle
        addTransactionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Yeni işlem ekleme ekranını başlat
                startAddTransactionActivity();
            }
        });


        ArrayAdapter<String> yearAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, yearList);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<String> monthAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, monthList);
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, typeList);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        yearSpinner.setAdapter(yearAdapter);
        monthSpinner.setAdapter(monthAdapter);
        transactionTypeSpinner.setAdapter(typeAdapter);

        // Örnek veri oluştur
        List<String> transactionList = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            transactionList.add("İşlem " + i);
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, transactionList);
        transactionsListView.setAdapter(adapter);


        yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedYear = yearList.get(position);
                Toast.makeText(requireContext(), "Seçilen Yıl: " + selectedYear, Toast.LENGTH_SHORT).show();

                // TODO: Seçilen yılı kullanarak işlemleri filtrele ve güncelle
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Bir şey seçilmediğinde bir şey yapmaya gerek yok
            }
        });

        return view;
    }


    private void startAddTransactionActivity() {
        //Intent intent = new Intent(requireContext(), AddTransactionActivity.class);
        //startActivity(intent);
    }
}