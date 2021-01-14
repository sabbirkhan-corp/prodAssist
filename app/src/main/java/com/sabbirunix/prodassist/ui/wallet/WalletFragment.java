package com.sabbirunix.prodassist.ui.wallet;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sabbirunix.prodassist.R;
import com.sabbirunix.prodassist.ui.notes.NotesAdapter;
import com.sabbirunix.prodassist.ui.notes.NotesAddActivity;
import com.sabbirunix.prodassist.ui.notes.NotesDBHelper;

import java.util.ArrayList;

public class WalletFragment extends Fragment {

    private WalletViewModel walletViewModel;
    WalletDBHelper walletDBHelper;
    WalletAdapterChild walletAdapterChild;
    ArrayList<String> walletDate, walletDetails, walletCategory, walletAmount;
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

        walletDBHelper = new WalletDBHelper(getContext());
//        walletDate = new ArrayList<>();
        walletDetails = new ArrayList<>();
        walletCategory = new ArrayList<>();
        walletAmount = new ArrayList<>();
        displayWalletItems();

        walletAdapterChild = new WalletAdapterChild(getContext(), walletDetails, walletCategory, walletAmount);
        rvWalletMain.setAdapter(walletAdapterChild);
        rvWalletMain.setLayoutManager(new LinearLayoutManager(getContext()));
//        recyclerViewNote.setLayoutManager(new GridLayoutManager(getContext(), 2));
//        recyclerViewNote.setLayoutManager(new StaggeredGridLayoutManager(2, RecyclerView.HORIZONTAL));

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

    void displayWalletItems() {
        Cursor cursor = walletDBHelper.displayWalletItem();
        if (cursor.getCount() == 0) {
            emptyWalletImg.setVisibility(View.VISIBLE);
            emptyWalletTxt.setVisibility(View.VISIBLE);
            Toast.makeText(getContext(), "No data found", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
//                walletDate.add(cursor.getString(1));
                walletDetails.add(cursor.getString(2));
                walletCategory.add(cursor.getString(3));
                walletAmount.add(cursor.getString(4));
            }
            emptyWalletImg.setVisibility(View.GONE);
            emptyWalletTxt.setVisibility(View.GONE);
        }
    }

}