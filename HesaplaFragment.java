package com.uygulamam.carbon;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;


import com.google.android.material.navigation.NavigationView;
import com.uygulamam.carbonapp.R;

import java.text.DecimalFormat;

public class HesaplaFragment extends Fragment {
    private TextView sıfırText;
    private TextView sıfırText2;
    private TextView sıfırText3;
    private TextView sıfırText4;

    private TextView agac;
    private ImageView leftimageView;
    private ImageView rightimageView;
    private ImageView leftimageView2;
    private ImageView rightimageView2;
    private ImageView leftimageView3;
    private ImageView rightimageView3;
    private ImageView leftimageView4;
    private ImageView rightimageView4;

    private int counter = 0;
    private int counter2 = 0;
    private int counter3 = 0;
    private int counter4 = 0;

    private Button hesaplaButton;
    private Spinner fuelTypeSpinner;
    private Spinner ucusSayisi;
    private Spinner elektrikolcum;
    private Spinner isinmaSpinner;
    private LinearLayout linearLayout1;
    private LinearLayout linearLayout2;

    private LinearLayout linearLayout3;
    private LinearLayout linearLayout4;
    private LinearLayout linearLayout5;


    private TextView karaulasımı;
    private TextView havaulasımı;
    private TextView elektrik;
    private TextView isinma;

    private TextView toplam;
    private double top;

    private Button bagisYap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_hesapla, container, false);

        // Hide Nav_menu.xml
        NavigationView navigationView = getActivity().findViewById(R.id.nav_view);
        navigationView.setVisibility(View.GONE);

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        LinearLayout linearLayout1 = rootView.findViewById(R.id.linear1);
        LinearLayout linearLayout2 = rootView.findViewById(R.id.linear2);
        LinearLayout linearLayout3 = rootView.findViewById(R.id.linear3);
        LinearLayout linearLayout4 = rootView.findViewById(R.id.linear4);
        LinearLayout linearLayout5 = rootView.findViewById(R.id.linear5);

        sıfırText = rootView.findViewById(R.id.sıfırText);
        sıfırText2 = rootView.findViewById(R.id.sıfırText2);
        sıfırText3 = rootView.findViewById(R.id.sıfırText3);
        sıfırText4 = rootView.findViewById(R.id.sıfırText4);
        agac = rootView.findViewById(R.id.agac);

        bagisYap = rootView.findViewById(R.id.bagisButton);

        linearLayout1.setVisibility(View.INVISIBLE);
        linearLayout2.setVisibility(View.INVISIBLE);
        linearLayout3.setVisibility(View.INVISIBLE);
        linearLayout4.setVisibility(View.INVISIBLE);
        linearLayout5.setVisibility(View.INVISIBLE);

        leftimageView = rootView.findViewById(R.id.leftImageView);
        rightimageView = rootView.findViewById(R.id.rightImageView);
        leftimageView2 = rootView.findViewById(R.id.leftImageView2);
        rightimageView2 = rootView.findViewById(R.id.rightImageView2);
        leftimageView3 = rootView.findViewById(R.id.leftImageView3);
        rightimageView3 = rootView.findViewById(R.id.rightImageView3);
        leftimageView4 = rootView.findViewById(R.id.leftImageView4);
        rightimageView4 = rootView.findViewById(R.id.rightImageView4);

        hesaplaButton = rootView.findViewById(R.id.hesaplaButton);
        fuelTypeSpinner = rootView.findViewById(R.id.fuel_type_spinner);
        karaulasımı = rootView.findViewById(R.id.kara);
        ucusSayisi = rootView.findViewById(R.id.spinner2);
        havaulasımı = rootView.findViewById(R.id.hava);
        isinmaSpinner = rootView.findViewById(R.id.spinner4);
        isinma = rootView.findViewById(R.id.isinma);
        elektrik = rootView.findViewById(R.id.elektrik);
        bagisYap = rootView.findViewById(R.id.bagisButton);
        bagisYap.setVisibility(View.GONE);

        toplam = rootView.findViewById(R.id.toplam);

        sıfırText.setText(String.valueOf(counter));

        rightimageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter += 50;
                sıfırText.setText(String.valueOf(counter));
            }
        });

        leftimageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (counter > 0) {
                    counter -= 50;
                    sıfırText.setText(String.valueOf(counter));
                }
            }
        });

        sıfırText2.setText(String.valueOf(counter2));

        rightimageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter2++;
                sıfırText2.setText(String.valueOf(counter2));
            }
        });

        leftimageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (counter2 > 0) {
                    counter2--;
                    sıfırText2.setText(String.valueOf(counter2));
                }
            }
        });

        sıfırText3.setText(String.valueOf(counter3));

        rightimageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter3 += 100;
                sıfırText3.setText(String.valueOf(counter3));
            }
        });

        leftimageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (counter3 > 0) {
                    counter3 -= 100;
                    sıfırText3.setText(String.valueOf(counter3));
                }
            }
        });

        sıfırText4.setText(String.valueOf(counter4));

        rightimageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter4 += 100;
                sıfırText4.setText(String.valueOf(counter4));
            }
        });

        leftimageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (counter4 > 0) {
                    counter4 -= 100;
                    sıfırText4.setText(String.valueOf(counter4));
                }
            }
        });

        hesaplaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int sifirtValue = Integer.parseInt(sıfırText.getText().toString());
                int sifirtValue2 = Integer.parseInt(sıfırText2.getText().toString());
                int sifirtValue3 = Integer.parseInt(sıfırText3.getText().toString());
                int sifirtValue4 = Integer.parseInt(sıfırText4.getText().toString());

                String selectedFuelType = fuelTypeSpinner.getSelectedItem().toString();
                String selectedFuelType2 = ucusSayisi.getSelectedItem().toString();
                String selectedFuelType4 = isinmaSpinner.getSelectedItem().toString();

                linearLayout1.setVisibility(View.VISIBLE);
                linearLayout2.setVisibility(View.VISIBLE);
                linearLayout3.setVisibility(View.VISIBLE);
                linearLayout4.setVisibility(View.VISIBLE);
                linearLayout5.setVisibility(View.VISIBLE);

                double coefficient;
                if (selectedFuelType.equals("Benzin")) {
                    coefficient = 2.31;
                } else if (selectedFuelType.equals("Dizel")) {
                    coefficient = 2.59;
                } else if (selectedFuelType.equals("LPG")) {
                    coefficient = 1.67;
                } else {
                    coefficient = 0;
                }

                double result2 = sifirtValue2 * 51.46;

                double result = sifirtValue * coefficient / 10;
                karaulasımı.setText(String.format("%.2f ", result) + " Kg");
                havaulasımı.setText(String.format("%.2f ", result2) + " Kg");

                double result3 = sifirtValue3 * 47.8 / 100;
                DecimalFormat decimalFormat = new DecimalFormat("#");
                elektrik.setText(String.format("%.2f ", result3) + " Kg");

                double coefficient2;
                if (selectedFuelType4.equals("Doğalgaz")) {
                    coefficient2 = 202;
                } else if (selectedFuelType4.equals("Kömür")) {
                    coefficient2 = 204;
                } else if (selectedFuelType4.equals("Biyogaz")) {
                    coefficient2 = 0;
                } else {
                    coefficient2 = 0;
                }
                double result4 = (sifirtValue4 * coefficient2) / 100;
                isinma.setText(decimalFormat.format(result4) + " Kg ");

                double result5 = result + result2 + result3 + result4;
                toplam.setText(decimalFormat.format(result5) + " Kg");

                int treeCount;
                if (result5 < 0) {
                    treeCount = 0;
                } else if (result5 < 500) {
                    treeCount = 1;
                } else if (result5 < 1000) {
                    treeCount = 2;
                } else if (result5 < 1500) {
                    treeCount = 3;
                } else if (result5 < 2000) {
                    treeCount = 4;
                } else if (result5 < 2500) {
                    treeCount = 5;
                } else if (result5 < 3000) {
                    treeCount = 6;
                } else if (result5 < 3500) {
                    treeCount = 7;
                } else if (result5 < 4000) {
                    treeCount = 8;
                } else if (result5 < 4500) {
                    treeCount = 9;
                } else if (result5 < 5000) {
                    treeCount = 10;
                } else if (result5 < 5500) {
                    treeCount = 11;
                } else if (result5 < 6000) {
                    treeCount = 12;
                } else if (result5 < 6500) {
                    treeCount = 13;
                } else if (result5 < 7000) {
                    treeCount = 14;
                } else if (result5 < 7500) {
                    treeCount = 15;
                } else if (result5 < 8000) {
                    treeCount = 16;
                } else if (result5 < 8500) {
                    treeCount = 17;
                } else if (result5 < 9000) {
                    treeCount = 18;
                } else if (result5 < 9500) {
                    treeCount = 19;
                } else if (result5 < 10000) {
                    treeCount = 20;
                } else if (result5 < 10500) {
                    treeCount = 21;
                } else if (result5 < 11000) {
                    treeCount = 22;
                } else if (result5 < 11500) {
                    treeCount = 23;
                } else if (result5 < 12000) {
                    treeCount = 24;
                } else if (result5 < 12500) {
                    treeCount = 25;
                } else if (result5 < 13000) {
                    treeCount = 26;
                } else if (result5 < 13500) {
                    treeCount = 27;
                } else if (result5 < 14000) {
                    treeCount = 28;
                } else if (result5 < 14500) {
                    treeCount = 29;
                } else if (result5 < 15000) {
                    treeCount = 30;
                } else if (result5 < 15500) {
                    treeCount = 31;
                } else if (result5 < 16000) {
                    treeCount = 32;
                } else if (result5 < 16500) {
                    treeCount = 33;
                } else if (result5 < 17000) {
                    treeCount = 34;
                } else if (result5 < 17500) {
                    treeCount = 35;
                } else if (result5 < 18000) {
                    treeCount = 36;
                } else if (result5 < 18500) {
                    treeCount = 37;
                } else if (result5 < 19000) {
                    treeCount = 38;
                } else if (result5 < 19500) {
                    treeCount = 39;
                } else if (result5 < 20000) {
                    treeCount = 40;
                } else if (result5 < 20500) {
                    treeCount = 41;
                } else if (result5 < 21000) {
                    treeCount = 42;
                } else if (result5 < 21500) {
                    treeCount = 43;
                } else if (result5 < 22000) {
                    treeCount = 44;
                } else if (result5 < 22500) {
                    treeCount = 45;
                } else if (result5 < 23000) {
                    treeCount = 46;
                } else if (result5 < 23500) {
                    treeCount = 47;
                } else if (result5 < 24000) {
                    treeCount = 48;
                } else if (result5 < 24500) {
                    treeCount = 49;
                } else {
                    treeCount = 50;
                }

                agac.setText("Doğaya " + treeCount + " Ağaç Borcunuz Var");
            }
        });

        bagisYap.setVisibility(View.GONE);

        return rootView;
    }
}
