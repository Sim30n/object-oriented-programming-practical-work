package com.example.olio_harjoitusty;

import java.util.ArrayList;

public class Pisteet implements Comparable {

    private String name;
    private int total;

    public Pisteet(String name, int total){
        this.name = name;
        this.total = total;
    }

    public String getNimi(){
        return name;
    }

    public int getTotal(){
        return total;
    }

    @Override
    public int compareTo(Object o) {
        int comparePoints=((Pisteet)o).getTotal();
        /* For Ascending order*/
        //return this.total-comparePoints;

        /* For Descending order do like this */
        return comparePoints-this.total;
    }

}
