package com.example.btlapi.Domain;

public class User {
    private int Id;
    private String Names;
    private String Passwords;
    private String Mobile;
    private String Addresss;

    public User(int id, String names, String passwords, String mobile, String addresss) {
        Id = id;
        Names = names;
        Passwords = passwords;
        Mobile = mobile;
        Addresss = addresss;
    }

    public User(String names, String passwords, String mobile, String addresss) {
        Names = names;
        Passwords = passwords;
        Mobile = mobile;
        Addresss = addresss;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getNames() {
        return Names;
    }

    public void setNames(String names) {
        Names = names;
    }

    public String getPasswords() {
        return Passwords;
    }

    public void setPasswords(String passwords) {
        Passwords = passwords;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getAddresss() {
        return Addresss;
    }

    public void setAddresss(String addresss) {
        Addresss = addresss;
    }
}
