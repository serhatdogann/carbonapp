package com.example.carbonapp;

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

    private Button btn_hesapla;
    private TextView txt_sonuc;
    private SearchView searchView1;
    private SearchView searchView2;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private List<String> airportList;
    private List<String> filteredList;
    private Map<String, String> airportLocations;

    private boolean isItemClicked = false;

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
                Toast.makeText(requireContext(), "" + airportName, Toast.LENGTH_SHORT).show();

                isItemClicked = true;

                if (searchView1.hasFocus()) {
                    searchView1.setQuery(airportName, false);
                } else if (searchView2.hasFocus()) {
                    searchView2.setQuery(airportName, false);
                }

                listView.setVisibility(View.GONE); // Item seçildikten sonra ListView'i gizle
            }
        });

        searchView1.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && !isItemClicked) {
                    searchView1.setQuery("", false);
                }
                isItemClicked = false;

                listView.setVisibility(View.VISIBLE); // SearchView1'e odaklanıldığında ListView'i yeniden görünür hale getir
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
                String airport1 = searchView1.getQuery().toString();
                String airport2 = searchView2.getQuery().toString();

                if (!TextUtils.isEmpty(airport1) && !TextUtils.isEmpty(airport2)) {
                    calculateDistance(airport1, airport2);
                }
            }
        });

        return view;
    }

    private void loadAirportData() {
        InputStream inputStream = null;
        BufferedReader reader = null;
        try {
            inputStream = requireContext().getAssets().open("airportss.csv");
            reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length >= 2) {
                    String airportName = parts[0].split(",")[0]; // 1. sütunu alıyoruz
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


        } else {
            Toast.makeText(requireContext(), "Geçersiz havaalanı ismi", Toast.LENGTH_SHORT).show();
        }
    }

    private double calculateDistanceBetweenLocations(double latitude1, double longitude1, double latitude2, double longitude2) {
        double earthRadius = 6371; // Dünya yarıçapı (km)
        double dLat = Math.toRadians(latitude2 - latitude1);
        double dLon = Math.toRadians(longitude2 - longitude1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(latitude1)) * Math.cos(Math.toRadians(latitude2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = earthRadius * c;
        int roundedDistance = (int) Math.round(distance);

        txt_sonuc.setText("Mesafe: " + roundedDistance + " km");

        return distance;

    }
}
