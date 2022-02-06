package com.example.phonesekur;

import java.util.List;

public class User {
    private String userName;
    private String email;
    private String phoneNumber;
    private String password;
    private String country;
    private List<String> blockedPhoneNumber;

    public User(){

    }
    public User(
            String userName,
            String email,
            String phoneNumber,
            String password,
            String country,
            List<String> blockedPhoneNumber){
        this.userName = userName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.country = country;
        this.blockedPhoneNumber = blockedPhoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public String getCountry() {
        return country;
    }

    public List<String> getBlockedPhoneNumber() {
        return blockedPhoneNumber;
    }
}
