package com.example.olio_harjoitusty;

public class Points implements Comparable {

    private String name;
    private int total;

    public Points(String name, int total){
        this.name = name;
        this.total = total;
    }

    public String getNimi(){
        return name;
    }

    public int getTotal(){
        return total;
    }


    // Method for ordering Drivers by points.
    @Override
    public int compareTo(Object o) {
        int comparePoints=((Points)o).getTotal();
        /* For Ascending order*/
        //return this.total-comparePoints;

        /* For Descending order do like this */
        return comparePoints-this.total;
    }

}
