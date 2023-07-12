package com.example.carbonapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

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

    private EditText karaulasımı;
    private EditText havaulasımı;
    private EditText elektrik;
    private EditText isinma;

    private EditText toplam;
    private  double top;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_hesapla, container, false);

        sıfırText = rootView.findViewById(R.id.sıfırText);
        sıfırText2 = rootView.findViewById(R.id.sıfırText2);
        sıfırText3 = rootView.findViewById(R.id.sıfırText3);
        sıfırText4 = rootView.findViewById(R.id.sıfırText4);
        agac=rootView.findViewById(R.id.agac);

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

        toplam=rootView.findViewById(R.id.toplam);

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

                double result = sifirtValue * coefficient/10;
                karaulasımı.setText(String.format("%.2f ", result)+" Kg");
                havaulasımı.setText(String.format("%.2f ", result2)+" Kg");

                double result3 = sifirtValue3 * 47.8/100;
                DecimalFormat decimalFormat = new DecimalFormat("#");
                elektrik.setText(String.format("%.2f ",result3) + " Kg");

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
                double result4 = (sifirtValue4 * coefficient2)/100;
                isinma.setText(decimalFormat.format(result4) + " Kg ");

                double result5 = result + result2 + result3 + result4/100;
                toplam.setText(decimalFormat.format(result5));

                if (result5 > 0 && result5 < 500) {
                    agac.setText("Doğaya 1 Ağaç Borcunuz Var");
                }

                else if(result5>500 && result5<1000 ){
                    agac.setText("Doğaya 2 Ağaç Borcunuz Var");
                }
                else if(result5>1000 && result5<1500 ){
                    agac.setText("Doğaya 3 Ağaç Borcunuz Var");
                }
                else if(result5>1500 && result5<2000 ){
                    agac.setText("Doğaya 4 Ağaç Borcunuz Var");
                }
                else if(result5>2000 && result5<2500 ){
                    agac.setText("Doğaya 5 Ağaç Borcunuz Var");
                }
                else if(result5>2500 && result5<3000 ){
                    agac.setText("Doğaya 6 Ağaç Borcunuz Var");
                }
                else if(result5>3000 && result5<3500){
                    agac.setText("Doğaya 7 Ağaç Borcunuz Var");
                }
                else if(result5>3500 && result5<4000 ){
                    agac.setText("Doğaya 8 Ağaç Borcunuz Var");
                }
                else if(result5>4000 && result5<4500 ){
                    agac.setText("Doğaya 9 Ağaç Borcunuz Var");
                }
                else if(result5>4500 && result5<5000 ){
                    agac.setText("Doğaya 10 Ağaç Borcunuz Var");
                }
                else if(result5>5000 && result5<5500 ){
                    agac.setText("Doğaya 11 Ağaç Borcunuz Var");
                }
                else if(result5>5500 && result5<6000 ){
                    agac.setText("Doğaya 12 Ağaç Borcunuz Var");
                }
                else if(result5>6000 && result5<6500 ){
                    agac.setText("Doğaya 13 Ağaç Borcunuz Var");
                }
                else if(result5>6500 && result5<7000 ){
                    agac.setText("Doğaya 14 Ağaç Borcunuz Var");
                }
                else if(result5>7000 && result5<7500 ){
                    agac.setText("Doğaya 15 Ağaç Borcunuz Var");
                }
                else if(result5>7500 && result5<8000 ){
                    agac.setText("Doğaya 16 Ağaç Borcunuz Var");
                }
                else if(result5>8000 && result5<8500 ){
                    agac.setText("Doğaya 17 Ağaç Borcunuz Var");
                }
                else if(result5>8500 && result5<9000 ){
                    agac.setText("Doğaya 18 Ağaç Borcunuz Var");
                }
                else if(result5>9000 && result5<9500 ){
                    agac.setText("Doğaya 19 Ağaç Borcunuz Var");
                }
                else if(result5>9500 && result5<10000 ){
                    agac.setText("Doğaya 20 Ağaç Borcunuz Var");
                }
                else if(result5>10000 && result5<10500 ){
                    agac.setText("Doğaya 21 Ağaç Borcunuz Var");
                }
                else if(result5>10500 && result5<11000 ){
                    agac.setText("Doğaya 22 Ağaç Borcunuz Var");
                }
                else if(result5>11000 && result5<11500 ){
                    agac.setText("Doğaya 23 Ağaç Borcunuz Var");
                }
                else if(result5>11500 && result5<12000 ){
                    agac.setText("Doğaya 24 Ağaç Borcunuz Var");
                }
                else if(result5>12000 && result5<12500 ){
                    agac.setText("Doğaya 25 Ağaç Borcunuz Var");
                }
                else if(result5>12500 && result5<13000 ){
                    agac.setText("Doğaya 26 Ağaç Borcunuz Var");
                }

                else if(result5>13000 && result5<13500 ){
                    agac.setText("Doğaya 27 Ağaç Borcunuz Var");
                }

                else if(result5>13500 && result5<14000 ){
                    agac.setText("Doğaya 2 Ağaç Borcunuz Var");
                }
                else if(result5>14000 && result5<14500){
                    agac.setText("Doğaya 28 Ağaç Borcunuz Var");
                }

                else if(result5>14500 && result5<15000 ){
                    agac.setText("Doğaya 29 Ağaç Borcunuz Var");
                }
                else if(result5>15000 && result5<15500 ){
                    agac.setText("Doğaya 30 Ağaç Borcunuz Var");
                }

                else if(result5>15500 && result5<16000 ){
                    agac.setText("Doğaya 31 Ağaç Borcunuz Var");
                }
                else if(result5>16000 && result5<16500 ){
                    agac.setText("Doğaya 32 Ağaç Borcunuz Var");
                }

                else if(result5>16500 && result5<17000){
                    agac.setText("Doğaya 33 Ağaç Borcunuz Var");
                }

                else if(result5>17000 && result5<17500 ){
                    agac.setText("Doğaya 34 Ağaç Borcunuz Var");
                }
                else if(result5>17500 && result5<18000 ){
                    agac.setText("Doğaya 35 Ağaç Borcunuz Var");
                }
                else if(result5>18000 && result5<18500){
                    agac.setText("Doğaya 36 Ağaç Borcunuz Var");
                }
                else if(result5>18500 && result5<19000 ){
                    agac.setText("Doğaya 37 Ağaç Borcunuz Var");
                }
                else if(result5>19000 && result5<19500){
                    agac.setText("Doğaya 2 Ağaç Borcunuz Var");
                }
                else if(result5>19500 && result5<20000){
                    agac.setText("Doğaya 38 Ağaç Borcunuz Var");
                }
                else if(result5>20000 && result5<20500 ){
                    agac.setText("Doğaya 39 Ağaç Borcunuz Var");
                }
                else if(result5>20500 && result5<21000 ){
                    agac.setText("Doğaya 40 Ağaç Borcunuz Var");
                }
                else if(result5>21000 && result5<21500 ){
                    agac.setText("Doğaya 41 Ağaç Borcunuz Var");
                }

                else if(result5>21500 && result5<22000 ){
                    agac.setText("Doğaya 42 Ağaç Borcunuz Var");
                }
                else if(result5>22000 && result5<22500 ){
                    agac.setText("Doğaya 43 Ağaç Borcunuz Var");
                }

                else if(result5>22500 && result5<23000 ){
                    agac.setText("Doğaya 44 Ağaç Borcunuz Var");
                }
                else if(result5>23000 && result5<23500 ){
                    agac.setText("Doğaya 2 Ağaç Borcunuz Var");
                }
                else if(result5>23500 && result5<24000 ){
                    agac.setText("Doğaya 45 Ağaç Borcunuz Var");
                }

                else if(result5>24000 && result5<24500 ){
                    agac.setText("Doğaya 46 Ağaç Borcunuz Var");
                }

                else if(result5>24500 && result5<25000 ){
                    agac.setText("Doğaya 47 Ağaç Borcunuz Var");
                }
                else if(result5>25000 && result5<25500 ){
                    agac.setText("Doğaya 48 Ağaç Borcunuz Var");
                }
                else if(result5>25500 && result5<26000 ){
                    agac.setText("Doğaya 49 Ağaç Borcunuz Var");
                }
                else if(result5>26000 ){
                    agac.setText("Doğaya 50 Ağaç Borcunuz Var");
                }

            }


        });

        return rootView;
    }
}
