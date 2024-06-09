package ru.itis.names.csvUtils;

import java.util.Map;

public class Field1 {
    private String country;
    private String namerus;
    private Map<String, Integer> namelat;

    public Field1() {
    }

    public Field1(String country, String namerus, Map<String, Integer> variants) {
        this.country = country;
        this.namerus = namerus;
        this.namelat = variants;
    }

    public String getCountry() {
        return country;
    }

    public String getNamerus() {
        return namerus;
    }

    public Map<String, Integer> getNamelat() {
        return namelat;
    }
}
