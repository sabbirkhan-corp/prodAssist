package com.sabbirunix.prodassist.ui.wallet;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sabbirunix.prodassist.R;
import com.sabbirunix.prodassist.ui.notes.NotesAddActivity;

public class WalletFragment extends Fragment {

    private WalletViewModel walletViewModel;
    FloatingActionButton fabAddWallet;
    RecyclerView rvWalletMain;
    ImageView emptyWalletImg;
    TextView emptyWalletTxt;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        walletViewModel =
                new ViewModelProvider(this).get(WalletViewModel.class);
        View root = inflater.inflate(R.layout.fragment_wallet, container, false);

        fabAddWallet = root.findViewById(R.id.fab_wallet);
        emptyWalletImg = root.findViewById(R.id.empty_wallet_img);
        emptyWalletTxt = root.findViewById(R.id.empty_wallet_txt);

        rvWalletMain = root.findViewById(R.id.wallet_recyclerview_main);


        //Handling the userClick on fabRegular
        fabAddWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), WalletAddActivity.class);
                startActivity(intent);
            }
        });




//        final TextView textView = root.findViewById(R.id.text_notifications);
//        walletViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }
}