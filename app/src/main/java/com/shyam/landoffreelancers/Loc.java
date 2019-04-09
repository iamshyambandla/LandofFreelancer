package com.shyam.landoffreelancers;

public class Loc {
    private double lat;

    public Loc() {
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
    public Loc(double lat, double lang) {
        this.lat = lat;
        this.lang = lang;
    }


}
