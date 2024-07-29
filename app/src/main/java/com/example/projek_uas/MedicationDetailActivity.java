package com.example.projek_uas;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class MedicationDetailActivity extends AppCompatActivity implements OnMapReadyCallback {

    private TextView textViewName;
    private TextView textViewRequirement;
    private TextView textViewAddress;
    private GoogleMap mMap;
    private DatabaseReference databaseReference;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication_detail);

        textViewName = findViewById(R.id.text_view_name);
        textViewRequirement = findViewById(R.id.text_view_requirement);
        textViewAddress = findViewById(R.id.text_view_address);

        // Initialize SupportMapFragment
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_view);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        } else {
            Log.e("MedicationDetailActivity", "SupportMapFragment is null");
        }

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String requirement = intent.getStringExtra("requirement");
        String address = intent.getStringExtra("address");

        textViewName.setText(name);
        textViewRequirement.setText(requirement);
        textViewAddress.setText(address);

        // Initialize Firebase Database
        databaseReference = FirebaseDatabase.getInstance("https://projek-uas-533ed-default-rtdb.firebaseio.com/").getReference("medications");

        // Retrieve and show location on map based on address
        retrieveLocation(address);
    }

    private void retrieveLocation(String address) {
        String apiKey = "AIzaSyD3N6pYunMU47T2hfLjAOOJWxFBh89aPQs";
        try {
            String encodedAddress = URLEncoder.encode(address, "UTF-8");
            String urlString = "https://maps.googleapis.com/maps/api/geocode/json?address=" + encodedAddress + "&key=" + apiKey;

            new Thread(() -> {
                try {
                    URL url = new URL(urlString);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");

                    int responseCode = connection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        InputStream inputStream = connection.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                        StringBuilder response = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            response.append(line);
                        }
                        reader.close();
                        connection.disconnect();

                        // Log response JSON
                        Log.d("MedicationDetailActivity", "API Response: " + response.toString());

                        // Parse hasil JSON untuk mendapatkan latitude dan longitude
                        JSONObject jsonObject = new JSONObject(response.toString());
                        JSONArray results = jsonObject.getJSONArray("results");
                        if (results.length() > 0) {
                            JSONObject location = results.getJSONObject(0).getJSONObject("geometry").getJSONObject("location");
                            double latitude = location.getDouble("lat");
                            double longitude = location.getDouble("lng");

                            LatLng latLng = new LatLng(latitude, longitude);

                            runOnUiThread(() -> {
                                if (mMap != null) {
                                    mMap.clear();
                                    mMap.addMarker(new MarkerOptions().position(latLng).title("Medication Location"));
                                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                                }
                            });
                        } else {
                            Log.e("MedicationDetailActivity", "No results found for the address");
                        }
                    } else {
                        Log.e("MedicationDetailActivity", "HTTP error code: " + responseCode);
                    }
                } catch (Exception e) {
                    Log.e("MedicationDetailActivity", "Error retrieving location", e);
                }
            }).start();
        } catch (Exception e) {
            Log.e("MedicationDetailActivity", "Error encoding address", e);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }
}
