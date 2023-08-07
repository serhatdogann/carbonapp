package com.uygulamam.carbon;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.cardview.widget.CardView;

import com.uygulamam.carbonapp.R;


public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        CardView bagisCardView = view.findViewById(R.id.bagis);
        bagisCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new BagisFragment(), "Bağış Yap");
            }
        });

        CardView hesaplaCardview = view.findViewById(R.id.imageCardView);
        hesaplaCardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new HesaplaFragment(), "Karbon Ayakizi Hesapla");
            }
        });

        CardView infoCardview = view.findViewById(R.id.info);
        infoCardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new InfoFragment(), "Bilgiler");
            }
        });

        CardView logoutCardview = view.findViewById(R.id.logout);
        logoutCardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Uygulamadan çıkış yapma işlemi
                getActivity().finish();
            }
        });

        CardView hakkimizdaCardview = view.findViewById(R.id.hakkimizda);
        hakkimizdaCardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new HakkimizdaFragment(), "Hakkımızda");
            }
        });

        CardView airport = view.findViewById(R.id.airport);
        airport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new AirportFragment(), "Mesafe Emisyon");
            }
        });

        return view;
    }

    private void replaceFragment(Fragment fragment, String title) {
        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container2, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
        updateActionBarTitle(title);
    }

    private void updateActionBarTitle(String title) {
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle(title);
    }
}
