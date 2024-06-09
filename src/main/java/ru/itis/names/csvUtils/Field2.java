package ru.itis.names.csvUtils;

import java.util.Map;

public class Field2 {
    private String country;
    private String namelat;
    private Map<String, Integer> namerus;

    public Field2() {
    }

    public Field2(String country, String namelat, Map<String, Integer> namerus) {
        this.country = country;
        this.namelat = namelat;
        this.namerus = namerus;
    }

    public String getCountry() {
        return country;
    }

    public String getNamelat() {
        return namelat;
    }

    public Map<String, Integer> getNamerus() {
        return namerus;
    }
}
