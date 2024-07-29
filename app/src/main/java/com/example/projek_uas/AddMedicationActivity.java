package com.example.projek_uas;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddMedicationActivity extends AppCompatActivity {

    private EditText editTextMedicationName;
    private EditText editTextRequirement;
    private EditText editTextAddress;
    private Button buttonSave;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medication);

        // Initialize UI elements
        editTextMedicationName = findViewById(R.id.edit_text_medication_name);
        editTextRequirement = findViewById(R.id.edit_text_requirement);
        editTextAddress = findViewById(R.id.edit_text_address);
        buttonSave = findViewById(R.id.button_save);

        // Initialize SQLite Database Helper
        databaseHelper = new DatabaseHelper(this);

        buttonSave.setOnClickListener(v -> saveMedication());
    }

    private void saveMedication() {
        // Get user input
        String medicationName = editTextMedicationName.getText().toString().trim();
        String requirement = editTextRequirement.getText().toString().trim();
        String address = editTextAddress.getText().toString().trim();

        // Check if all fields are filled
        if (medicationName.isEmpty() || requirement.isEmpty() || address.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create Medication object without latitude and longitude
        Medication medication = new Medication(medicationName, requirement, address);

        // Save medication to SQLite database
        databaseHelper.addMedication(medication);
        Toast.makeText(AddMedicationActivity.this, "Medication added successfully", Toast.LENGTH_SHORT).show();
        finish(); // Close activity after successful save
    }
}
