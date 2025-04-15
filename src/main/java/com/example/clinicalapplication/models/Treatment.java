package com.example.clinicalapplication.models;

public class Treatment {
    private String name;
    private String areaOfExpertise;
    private String timeSlot;

    public Treatment(String name, String areaOfExpertise, String timeSlot) {
        this.name = name;
        this.areaOfExpertise = areaOfExpertise;
        this.timeSlot = timeSlot;
    }

    public String getName() {
        return name;
    }

    public String getAreaOfExpertise() {
        return areaOfExpertise;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    @Override
    public String toString() {
        return name + " (" + timeSlot + ")";
    }
}
