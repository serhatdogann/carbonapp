package com.uygulamam.carbon;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.uygulamam.carbonapp.R;


public class BagisFragment extends Fragment {

    private ImageView imageView2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bagis, container, false);

        // Butonları tanımla ve tıklama olaylarını dinle
        AppCompatButton genel = view.findViewById(R.id.genel);
        AppCompatButton özel = view.findViewById(R.id.özel);
        AppCompatButton orman = view.findViewById(R.id.orman);
        AppCompatButton düzenliBagis = view.findViewById(R.id.düzenliBagis);

        genel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUrl("https://www.tema.org.tr/fidan-bagisi-secenekleri/genel-amacli-sertifika");
            }
        });

        özel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUrl("https://www.tema.org.tr/fidan-bagisi-secenekleri/ozel-amacli-sertifika");
            }
        });

        orman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUrl("https://www.tema.org.tr/dayanisma-ormani-bagis-secenekleri/tesekkur-sertifika");
            }
        });

        düzenliBagis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUrl("https://www.tema.org.tr/giris?returnUrl=az-ver-hep-ver?acceptGuest=1&showGuestButton=1");
            }
        });

        return view;
    }

    // URL'yi açan yardımcı metod
    private void openUrl(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }
}
