package ru.itis.names;

import ru.itis.names.csvUtils.CsvToJsonParser1;
import ru.itis.names.jsonUtils.JSON_Parser;

public class Main {
    public static void main(String[] args) {
        CsvToJsonParser1 p = new CsvToJsonParser1();
        p.generateJSON("names.csv");
        JSON_Parser j = new JSON_Parser("Task1.json", "Task2.json");
        System.out.println(j.variantsNameFromRus("ДЖАХОНГИР", "TJK"));
    }
}