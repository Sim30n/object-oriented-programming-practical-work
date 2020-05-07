package com.example.olio_harjoitusty;


public class ResView {
    private String nimi;
    private String aika;
    private String pts;

    /*Class for the result list view*/

    public ResView(String nimi, String aika, String pts) {
        this.nimi = nimi;
        this.aika = aika;
        this.pts = pts;
    }

    public String getNimi() {
        return nimi;
    }

    public String getAika() {
        return aika;
    }

    public String getPts() {
        return pts;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    public void setAika(String aika) {
        this.aika = aika;
    }

    public void setPts(String pts) {
        this.pts = pts;
    }
}
