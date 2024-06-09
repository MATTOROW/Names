package ru.itis.names.jsonUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.itis.names.csvUtils.Field1;
import ru.itis.names.csvUtils.Field2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class JSON_Parser implements JsonParser {

    private File file;
    private File file2;
    private ArrayList<Field1> al1;
    private ArrayList<Field2> al2;

    public JSON_Parser(String str1, String str2) {
        this.file = new File(str1);
        this.file2 = new File(str2);
        try {
            readJSON();
            readJSON2();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void readJSON() throws IOException {
        ObjectMapper om = new ObjectMapper();
        al1 = om.readValue(file, new TypeReference<ArrayList<Field1>>() {});
    }

    private void readJSON2() throws IOException {
        ObjectMapper om = new ObjectMapper();
        al2 = om.readValue(file2, new TypeReference<ArrayList<Field2>>() {});
    }

    public ArrayList<Field1> getAl1() {
        return al1;
    }

    public ArrayList<Field2> getAl2() {
        return al2;
    }

    public String transcriptNameFromRus(String rusname, String country) {
        Field1 rezult = null;
        for (Field1 field1 : al1){
            if (field1.getCountry().equals(country) && field1.getNamerus().equals(rusname)){
                rezult = field1;
                break;
            }
        }
        if (rezult == null) return null;
        return rezult.getNamelat().entrySet().stream().max(Map.Entry.comparingByValue()).get().getKey();
    }

    @Override
    public String transcriptNameFromLat(String latname, String country) {
        Field2 rezult = null;
        for (Field2 field2 : al2){
            if (field2.getCountry().equals(country) && field2.getNamelat().equals(latname)){
                rezult = field2;
                break;
            }
        }
        if (rezult == null) return null;
        return rezult.getNamerus().entrySet().stream().max(Map.Entry.comparingByValue()).get().getKey();
    }

    @Override
    public List<String> variantsNameFromRus(String rusname, String country) {
        Field1 rezult = null;
        for (Field1 field1 : al1){
            if (field1.getCountry().equals(country) && field1.getNamerus().equals(rusname)){
                rezult = field1;
                break;
            }
        }
        if (rezult == null) return null;
        return rezult.getNamelat().entrySet().stream().sorted(Map.Entry.comparingByValue()).map((a) -> a.getKey()).collect(Collectors.toList());
    }

    @Override
    public List<String> variantsNameFromLat(String latname, String country) {
        Field2 rezult = null;
        for (Field2 field2 : al2){
            if (field2.getCountry().equals(country) && field2.getNamelat().equals(latname)){
                rezult = field2;
                break;
            }
        }
        if (rezult == null) return null;
        return rezult.getNamerus().entrySet().stream().sorted(Map.Entry.comparingByValue()).map((a) -> a.getKey()).collect(Collectors.toList());
    }
}
