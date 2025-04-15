package com.example.clinicalapplication.models;

public abstract class Person {
    protected String id;
    protected String name;
    protected String address;
    protected String phone;

    public Person(String id, String name, String address, String phone) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

    // Getters and setters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getAddress() { return address; }
    public String getPhone() { return phone; }

    public void setId(String id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setAddress(String address) { this.address = address; }
    public void setPhone(String phone) { this.phone = phone; }
}
