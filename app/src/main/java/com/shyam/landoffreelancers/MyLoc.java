package com.shyam.landoffreelancers;

public class MyLoc {
    private double lat;

    public MyLoc() {
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLang() {
        return lang;
    }

    public void setLang(double lang) {
        this.lang = lang;
    }

    private double lang;
    public MyLoc(double lat, double lang) {
        this.lat = lat;
        this.lang = lang;
    }


}
