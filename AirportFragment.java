package com.uygulamam.carbon;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.uygulamam.carbonapp.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AirportFragment extends Fragment {

    private boolean isDoubleClicked = false;
    private int initialSonuc = 0;
    private double initialCO2 = 0.0;
    private Button btn_hesapla;
    private TextView txt_sonuc;
    private SearchView searchView1;
    private SearchView searchView2;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private List<String> airportList;
    private List<String> filteredList;
    private Map<String, String> airportLocations;

    private Button btn_donus;
    private double lastCalculatedCO2Ekonomi = 0.0;
    private int sonucEkonomi = 0;
    private Button btn_ekonomi;
    private Button btn_business;
    private Button btn_firstclass;
    private TextView txt_co2;
    private boolean isCO2Calculated = false;
    private double lastCalculatedCO2 = 0.0;
    private int sonuc = 0;
    private boolean isItemClicked = false;
    private double randomValue = 0.0;
    private Button lastClickedHesaplaButton;
    private View lastClickedGroupButton;
    private boolean isDoubled = false;
    private int lastCalculatedSonuc = 0;

    private int donusButtonClickCount = 0;

    private boolean areValuesUpdated = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_airport, container, false);

        searchView1 = view.findViewById(R.id.search1);
        searchView2 = view.findViewById(R.id.search2);
        listView = view.findViewById(R.id.listView1);
        txt_sonuc = view.findViewById(R.id.txt_sonuc);
        btn_hesapla = view.findViewById(R.id.btn_hesapla);
        txt_co2 = view.findViewById(R.id.txt_co2);
        btn_donus = view.findViewById(R.id.btn_donus);
        btn_ekonomi = view.findViewById(R.id.btn_ekonomi);
        btn_business = view.findViewById(R.id.btn_business);
        btn_firstclass = view.findViewById(R.id.btn_firstclass);
        airportList = new ArrayList<>();
        filteredList = new ArrayList<>();
        airportLocations = new HashMap<>();
        adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, filteredList);
        listView.setAdapter(adapter);

        loadAirportData();

        searchView1.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterAirportList(newText, searchView1);
                return true;
            }
        });

        searchView2.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterAirportList(newText, searchView2);

                return true;
            }

        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String airportName = filteredList.get(position);

                isItemClicked = true;

                if (searchView1.hasFocus()) {
                    searchView1.setQuery(airportName, false);
                } else if (searchView2.hasFocus()) {
                    searchView2.setQuery(airportName, false);
                }

                listView.setVisibility(View.INVISIBLE);
                filteredList.remove(0);
            }
        });

        searchView1.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && !isItemClicked) {
                    searchView1.setQuery("", false);
                }
                isItemClicked = false;

                listView.setVisibility(View.VISIBLE);
            }
        });

        searchView2.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && !isItemClicked) {
                    searchView2.setQuery("", false);
                }
                isItemClicked = false;

                listView.setVisibility(View.VISIBLE); // SearchView2'ye odaklanıldığında ListView'i yeniden görünür hale getir
            }
        });
        btn_hesapla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn_donus != null) {
                    btn_donus.setBackgroundColor(getResources().getColor(android.R.color.white));
                }
                // btn_hesapla'nın arka plan rengini mavi yap
                v.setBackgroundColor(getResources().getColor(R.color.your_mavi_color));
                lastClickedGroupButton = v;

                String airport1 = searchView1.getQuery().toString();
                String airport2 = searchView2.getQuery().toString();

                if (!TextUtils.isEmpty(airport1) && !TextUtils.isEmpty(airport2)) {
                    calculateDistance(airport1, airport2);
                    String sonucText = txt_sonuc.getText().toString();
                    if (!TextUtils.isEmpty(sonucText)) {
                        String distanceText = sonucText.replace("Mesafe: ", "");
                        sonuc = Integer.parseInt(distanceText.trim());

                        // Random bir sayı seçme ve 3.74 ile çarpma
                        randomValue = 0.293;
                        lastCalculatedCO2 = sonuc * randomValue;
                        isCO2Calculated = true;

                        String formattedCO2 = String.format("%.3f", lastCalculatedCO2 / 1000);
                        txt_co2.setText("CO2 Emisyonu: " + formattedCO2 + " t");

                        // Son hesaplamaların sonuçlarını sakla
                        lastCalculatedSonuc = sonuc;
                    }
                    btn_donus.setEnabled(true);
                }
            }
        });


        btn_donus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn_hesapla != null) {
                    btn_hesapla.setBackgroundColor(getResources().getColor(android.R.color.white));
                }
                // btn_donus'un arka plan rengini mavi yap
                v.setBackgroundColor(getResources().getColor(R.color.your_mavi_color));
                lastClickedGroupButton = v;

                donusButtonClickCount++;

                if (donusButtonClickCount >= 2) {
                    // 2 veya daha fazla tıklama durumunda son hesaplamaların sonuçlarını kullanarak ekrana yazdır
                    int doubledSonuc = lastCalculatedSonuc * 2;
                    txt_sonuc.setText("Mesafe: " + doubledSonuc);

                    // Aynı işlemi txt_co2 içinde yap
                    double doubledCO2 = lastCalculatedCO2 * 2;
                    String formattedDoubledCO2 = String.format("%.3f", doubledCO2 / 1000);
                    txt_co2.setText("CO2 Emisyonu: " + formattedDoubledCO2 + " t");
                } else {
                    // İlk tıklamalarda, yeni hesaplamaları yap ve son hesaplamaları sakla
                    if (sonuc > 0 && lastCalculatedCO2 != 0.0) {
                        int newSonuc = sonuc * 2;
                        double newCO2 = lastCalculatedCO2 * 2;

                        // Güncellenen değerleri ekrana yazdır
                        String newFormattedCO2 = String.format("%.3f", newCO2 / 1000);
                        txt_sonuc.setText("Mesafe: " + newSonuc);
                        txt_co2.setText("CO2 Emisyonu: " + newFormattedCO2 + " t");

                        // Güncellenen değerleri sonraki hesaplamalarda kullanmak için sakla
                        lastCalculatedSonuc = newSonuc;
                        lastCalculatedCO2 = newCO2;

                        // İkinci kez çarpmanın önüne geçmek için işlem yapıldığını işaretle
                        isDoubled = true;
                    } else {
                        // If no calculation has been made or values are zero, display the initial values
                        txt_sonuc.setText("Mesafe: " + initialSonuc);
                        String originalFormattedCO2 = String.format("%.3f", initialCO2 / 1000);
                        txt_co2.setText("CO2 Emisyonu: " + originalFormattedCO2 + " t");

                        // Reset the values to their initial state
                        sonuc = initialSonuc;
                        lastCalculatedSonuc = initialSonuc;
                        lastCalculatedCO2 = initialCO2;
                        donusButtonClickCount = 0;
                        isDoubled = false;
                        areValuesUpdated = false;
                        isCO2Calculated = false;
                    }
                }
            }
        });


        btn_ekonomi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn_business != null) {
                    btn_business.setBackgroundColor(getResources().getColor(android.R.color.white));
                }
                if (btn_firstclass != null) {
                    btn_firstclass.setBackgroundColor(getResources().getColor(android.R.color.white));
                }

                v.setBackgroundColor(getResources().getColor(R.color.your_mavi_color));
                lastClickedGroupButton = v;

                if (!isCO2Calculated || v.getId() == R.id.btn_ekonomi) {
                    String airport1 = searchView1.getQuery().toString();
                    String airport2 = searchView2.getQuery().toString();

                    if (!TextUtils.isEmpty(airport1) && !TextUtils.isEmpty(airport2)) {
                        calculateDistance(airport1, airport2);
                        String sonucText = txt_sonuc.getText().toString();
                        if (!TextUtils.isEmpty(sonucText)) {
                            String distanceText = sonucText.replace("Mesafe: ", "");
                            sonuc = Integer.parseInt(distanceText.trim());

                            // Rastgele bir sayı seçme, doğrudan 3.74 ile çarpma ve sonucu yazdırma
                            lastCalculatedCO2 = sonuc * 0.293;
                            isCO2Calculated = true;

                            String formattedCO2 = String.format("%.3f", lastCalculatedCO2 / 1000);
                            txt_co2.setText("CO2 Emisyonu: " + formattedCO2 + " t");
                        }
                        btn_donus.setEnabled(true);
                    }
                }
            }
        });


        btn_business.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn_ekonomi != null) {
                    btn_ekonomi.setBackgroundColor(getResources().getColor(android.R.color.white));
                }
                if (btn_firstclass != null) {
                    btn_firstclass.setBackgroundColor(getResources().getColor(android.R.color.white));
                }

                v.setBackgroundColor(getResources().getColor(R.color.your_mavi_color));
                lastClickedGroupButton = v;
                if (sonuc > 0) {

                    if (!isCO2Calculated) {
                        lastCalculatedCO2 = sonuc;
                        isCO2Calculated = true;
                    }

                    String formattedCO2 = String.format("%.3f", lastCalculatedCO2 / 1000);

                    double co2Business = lastCalculatedCO2 * 1.3123;

                    String formattedCO2Business = String.format("%.3f", co2Business / 1000);

                    txt_co2.setText("CO2 Emisyonu: " + formattedCO2Business + " t");
                }
            }
        });

        btn_firstclass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn_business != null) {
                    btn_business.setBackgroundColor(getResources().getColor(android.R.color.white));
                }
                if (btn_ekonomi != null) {
                    btn_ekonomi.setBackgroundColor(getResources().getColor(android.R.color.white));
                }

                v.setBackgroundColor(getResources().getColor(R.color.your_mavi_color));
                lastClickedGroupButton = v;

                if (sonuc > 0) {
                    if (!isCO2Calculated) {
                        lastCalculatedCO2 = sonuc;
                        isCO2Calculated = true;
                    }

                    String formattedCO2 = String.format("%.3f", lastCalculatedCO2 / 1000);

                    double co2FirstClass = lastCalculatedCO2 * 2.5;

                    String formattedCO2FirstClass = String.format("%.3f", co2FirstClass / 1000);

                    txt_co2.setText("CO2 Emisyonu: " + formattedCO2FirstClass + " t");
                }
            }
        });

        return view;
    }

    private void loadAirportData() {
        InputStream inputStream = null;
        BufferedReader reader = null;
        try {
            inputStream = requireContext().getAssets().open("airportsss.csv");
            reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length >= 2) {
                    String airportName = parts[0].split(",")[0];
                    String location = parts[1];
                    airportList.add(airportName);
                    airportLocations.put(airportName, location);
                }
            }
            adapter.notifyDataSetChanged();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void filterAirportList(String query, SearchView searchView) {
        filteredList.clear();
        if (TextUtils.isEmpty(query)) {
            filteredList.addAll(airportList);
        } else {
            for (String airportName : airportList) {
                if (airportName.toLowerCase().contains(query.toLowerCase())) {
                    filteredList.add(airportName);
                }
            }
        }

        adapter.notifyDataSetChanged();
        listView.setVisibility(View.VISIBLE); // SearchView'de filtreleme yapıldığında ListView'i yeniden görünür hale getir
    }

    private void calculateDistance(String airport1, String airport2) {
        if (airportLocations.containsKey(airport1) && airportLocations.containsKey(airport2)) {
            String location1 = airportLocations.get(airport1);
            String location2 = airportLocations.get(airport2);

            String[] latLong1 = location1.split(",");
            String[] latLong2 = location2.split(",");

            double latitude1 = Double.parseDouble(latLong1[0]);
            double longitude1 = Double.parseDouble(latLong1[1]);
            double latitude2 = Double.parseDouble(latLong2[0]);
            double longitude2 = Double.parseDouble(latLong2[1]);

            double distance = calculateDistanceBetweenLocations(latitude1, longitude1, latitude2, longitude2);
            int roundedDistance = (int) Math.round(distance);

            String resultText = "Mesafe: " + roundedDistance;
            txt_sonuc.setText(resultText);
        } else {
            Toast.makeText(requireContext(), "Geçersiz havaalanı ismi", Toast.LENGTH_SHORT).show();
        }
    }



    private double calculateDistanceBetweenLocations(double latitude1, double longitude1, double latitude2, double longitude2) {
        double earthRadius = 6371;
        double dLat = Math.toRadians(latitude2 - latitude1);
        double dLon = Math.toRadians(longitude2 - longitude1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(latitude1)) * Math.cos(Math.toRadians(latitude2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = earthRadius * c;
        return distance;
    }
}
