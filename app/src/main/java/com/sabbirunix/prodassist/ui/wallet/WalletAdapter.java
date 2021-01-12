package com.sabbirunix.prodassist.ui.wallet;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sabbirunix.prodassist.R;
import com.sabbirunix.prodassist.ui.notes.NotesUpdateActivity;

import java.util.ArrayList;

public class WalletAdapter extends RecyclerView.Adapter<WalletAdapter.WalletHolder> {


    private Context context;
    private ArrayList noteID, noteTitle, noteCategory, noteDetails;

    @NonNull
    @Override
    public WalletHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.fragment_wallet_section, parent, false);
        return new WalletHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WalletHolder holder, int position) {
//        holder.rvMain.setAdapter();
    }

    @Override
    public int getItemCount() {
        return 0;
    }


    public class WalletHolder extends RecyclerView.ViewHolder {
        RecyclerView rvMain;

        public WalletHolder(@NonNull View itemView) {
            super(itemView);
            rvMain = itemView.findViewById(R.id.wallet_recyclerview_main);
        }
    }
}
