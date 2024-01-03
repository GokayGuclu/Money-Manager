package com.mobileapp.moneymanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class WalletAdapter extends ArrayAdapter<Wallet> {

    private Context context;
    private int resource;

    public WalletAdapter(@NonNull Context context, int resource, @NonNull List<Wallet> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(resource, null);
        }

        Wallet wallet = getItem(position);

        if (wallet != null) {
            TextView walletNameTextView = view.findViewById(R.id.walletNameTextView);
            TextView walletBalanceTextView = view.findViewById(R.id.walletBalanceTextView);

            walletNameTextView.setText(wallet.name);
            walletBalanceTextView.setText("Bakiye: " + wallet.balance + " TL");
        }

        return view;
    }
}

