package com.sabbirunix.prodassist.ui.wallet;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.sabbirunix.prodassist.R;
import com.sabbirunix.prodassist.ui.notes.NotesUpdateActivity;

import java.util.ArrayList;
import java.util.List;

public class WalletAdapter extends RecyclerView.Adapter<WalletAdapter.WalletHolder> {

    private Context context;
    private ArrayList walletDateA;
    private ArrayList walletIncomeExpenseA;
    private List<SectionWallet> sectionWalletList;

    public WalletAdapter(Context context, ArrayList walletDateA, ArrayList walletIncomeExpenseA) {
        this.context = context;
        this.walletDateA = walletDateA;
        this.walletIncomeExpenseA = walletIncomeExpenseA;
    }

    @NonNull
    @Override
    public WalletHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.fragment_wallet_section, parent, false);
        return new WalletHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WalletHolder holder, int position) {
        holder.walletDateH.setText(String.valueOf(walletDateA.get(position)));
        holder.walletIncomeExpenseH.setText(String.valueOf(walletIncomeExpenseA.get(position)));
    }

    @Override
    public int getItemCount() {
        return walletDateA.size();
    }


    public class WalletHolder extends RecyclerView.ViewHolder {
        CardView walletCardH;
        TextView walletDateH, walletIncomeExpenseH;
        RecyclerView rvWalletSectionH;

        public WalletHolder(@NonNull View itemView) {
            super(itemView);
            walletCardH = itemView.findViewById(R.id.wallet_card);
            walletDateH = itemView.findViewById(R.id.wallet_date);
            walletIncomeExpenseH = itemView.findViewById(R.id.wallet_income_expense);
            rvWalletSectionH = itemView.findViewById(R.id.wallet_recyclerview_section);
        }
    }
}
