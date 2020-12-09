package com.example.bloodonation1.Models;

public class RemoteRequestModel {

    private String bloodType;
    private String name;
    private String age;
    private String phone;

    public RemoteRequestModel() {
    }

    public RemoteRequestModel( String bloodType, String name, String age, String phone) {
        this.bloodType = bloodType;
        this.name = name;
        this.age = age;
        this.phone = phone;
    }



    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
