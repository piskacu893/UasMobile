package com.example.projek_uas;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private DatabaseHelper databaseHelper;
    private ListView listViewMedications;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> medicationList;
    private ArrayList<Medication> medicationDetailsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();

        if (currentUser == null) {
            Intent intent = new Intent(MainActivity.this, Login.class);
            startActivity(intent);
            finish();
            return;
        }

        // Inisialisasi Database SQLite
        databaseHelper = new DatabaseHelper(this);
        listViewMedications = findViewById(R.id.list_view_medications);
        medicationList = new ArrayList<>();
        medicationDetailsList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, R.layout.list_item_medication, R.id.text_view_medication_name, medicationList);
        listViewMedications.setAdapter(adapter);

        // Membaca data dari SQLite
        loadMedicationsFromDatabase();

        Button buttonAddMedication = findViewById(R.id.button_add_medication);
        buttonAddMedication.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddMedicationActivity.class);
            startActivity(intent);
        });

        Button buttonShowMap = findViewById(R.id.button_show_map);
        buttonShowMap.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MapActivity.class);
            startActivity(intent);
        });

        listViewMedications.setOnItemClickListener((parent, view, position, id) -> {
            Medication medication = medicationDetailsList.get(position);
            Intent intent = new Intent(MainActivity.this, MedicationDetailActivity.class);
            intent.putExtra("name", medication.getName());
            intent.putExtra("requirement", medication.getRequirement());
            intent.putExtra("address", medication.getAddress());
            startActivity(intent);
        });
    }

    private void loadMedicationsFromDatabase() {
        medicationList.clear();
        medicationDetailsList.clear();
        ArrayList<Medication> medications = databaseHelper.getAllMedications();

        for (Medication medication : medications) {
            medicationList.add(medication.getName());
            medicationDetailsList.add(medication);
        }
        adapter.notifyDataSetChanged();
    }
}
