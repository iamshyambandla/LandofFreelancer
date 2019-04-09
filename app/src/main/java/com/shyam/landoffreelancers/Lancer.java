package com.shyam.landoffreelancers;

public class Lancer {
    private String uid;
    private String cost;
    private String number;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;
    public String getProfession() {
        return Profession;
    }

    public void setProfession(String profession) {
        Profession = profession;
    }

    private String Profession;
    public String getTimings() {
        return timings;
    }

    public void setTimings(String timings) {
        this.timings = timings;
    }

    private String timings;

    public Lancer() {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String email;
    private String status;
    public Lancer(String name,String uid, String cost, String number, String email, String status,String timings,String Profession) {
        this.uid = uid;
        this.cost = cost;
        this.number = number;
        this.email = email;
        this.status = status;
        this.Profession=Profession;
        this.name=name;
    }
}
