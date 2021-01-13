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

import java.util.ArrayList;

public class WalletAdapterChild extends RecyclerView.Adapter<WalletAdapterChild.WalletChildHolder> {
    private Context context;
    private ArrayList transactionTitle;
    private ArrayList transactionCategory;
    private ArrayList transactionAmount;

    public WalletAdapterChild(Context context, ArrayList transactionTitle, ArrayList transactionCategory, ArrayList transactionAmount) {
        this.context = context;
        this.transactionTitle = transactionTitle;
        this.transactionCategory = transactionCategory;
        this.transactionAmount = transactionAmount;
    }


    @NonNull
    @Override
    public WalletChildHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.fragment_wallet_single_row_recycler, parent, false);
        return new WalletChildHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WalletChildHolder holder, int position) {
        holder.tsaTitle.setText(String.valueOf(transactionTitle.get(position)));
        holder.tsaCategory.setText(String.valueOf(transactionCategory.get(position)));
        holder.tsaAmount.setText(String.valueOf(transactionAmount.get(position)));
        holder.walletCardSingle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, WalletUpdateActivity.class);
                intent.putExtra("wTitle", String.valueOf(transactionTitle.get(position)));
                intent.putExtra("wCategory", String.valueOf(transactionCategory.get(position)));
                intent.putExtra("wAmount", String.valueOf(transactionAmount.get(position)));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return transactionAmount.size();
    }

    public class WalletChildHolder extends RecyclerView.ViewHolder {
        CardView walletCardSingle;
        TextView tsaTitle, tsaCategory, tsaAmount;

        public WalletChildHolder(@NonNull View itemView) {
            super(itemView);
            walletCardSingle = itemView.findViewById(R.id.wallet_card_single);
            tsaTitle = itemView.findViewById(R.id.transaction_title);
            tsaCategory = itemView.findViewById(R.id.transaction_category);
            tsaAmount = itemView.findViewById(R.id.transaction_amount);
        }
    }
}
