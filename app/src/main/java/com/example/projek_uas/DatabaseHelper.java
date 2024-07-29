package com.example.projek_uas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "medications.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_MEDICATIONS = "medications";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_REQUIREMENT = "requirement";
    private static final String COLUMN_ADDRESS = "address";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_MEDICATIONS_TABLE = "CREATE TABLE " + TABLE_MEDICATIONS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_REQUIREMENT + " TEXT,"
                + COLUMN_ADDRESS + " TEXT" + ")";
        db.execSQL(CREATE_MEDICATIONS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEDICATIONS);
        onCreate(db);
    }

    public void addMedication(Medication medication) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, medication.getName());
        values.put(COLUMN_REQUIREMENT, medication.getRequirement());
        values.put(COLUMN_ADDRESS, medication.getAddress());

        db.insert(TABLE_MEDICATIONS, null, values);
        db.close();
    }

    public ArrayList<Medication> getAllMedications() {
        ArrayList<Medication> medicationList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_MEDICATIONS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Medication medication = new Medication(
                        cursor.getString(cursor.getColumnIndex(COLUMN_NAME)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_REQUIREMENT)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_ADDRESS))
                );
                medicationList.add(medication);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return medicationList;
    }
}
