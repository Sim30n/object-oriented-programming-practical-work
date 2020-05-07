package com.example.olio_harjoitusty;

public class ResViewPts {
    private String nimi;
    private String pts;

    /*Class for the points list view*/

    public ResViewPts(String nimi, String pts) {
        this.nimi = nimi;
        this.pts = pts;
    }

    public String getNimi() {
        return nimi;
    }

    public String getPts() {
        return pts;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    public void setPts(String pts) {
        this.pts = pts;
    }
}
