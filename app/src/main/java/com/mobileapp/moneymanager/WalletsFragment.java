package com.mobileapp.moneymanager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class WalletsFragment extends Fragment {

    private ListView walletsListView;
    private FloatingActionButton addTransactionButton;

    public WalletsFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wallets, container, false);

        walletsListView = view.findViewById(R.id.walletsListView);
        addTransactionButton = view.findViewById(R.id.addTransactionButton);

        // Cüzdan örnek verileri
        List<Wallet> walletList = new ArrayList<>();
        walletList.add(new Wallet("Cüzdan 1", 100.00));
        walletList.add(new Wallet("Cüzdan 2", 150.50));


        // Cüzdan listesi için özel bir ArrayAdapter
        WalletAdapter walletAdapter = new WalletAdapter(requireContext(), R.layout.wallet_item, walletList);
        walletsListView.setAdapter(walletAdapter);



        return view;
    }
}

