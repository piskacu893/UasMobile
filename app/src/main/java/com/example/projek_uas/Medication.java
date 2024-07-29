package com.example.projek_uas;

public class Medication {
    private String name;
    private String requirement;
    private String address;

    // Constructor for saving to Firebase
    public Medication(String name, String requirement, String address) {
        this.name = name;
        this.requirement = requirement;
        this.address = address;
    }

    // Default constructor required for calls to DataSnapshot.getValue(Medication.class)
    public Medication() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRequirement() {
        return requirement;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
