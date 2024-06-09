package ru.itis.names.csvUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.*;
import java.util.*;

public class CsvToJsonParser1 implements CsvParser {
    @Override
    public void generateJSON(String csvFileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(new File(csvFileName)))) {
            reader.readLine();
            List<String> jsonData = new ArrayList<>();
            while (reader.ready()) {
                jsonData.add(reader.readLine());
            }
            Map<String, Map<String, Map<String, Integer>>> processedDataRToLat = new HashMap<>();
            Map<String, Map<String, Map<String, Integer>>> processedDataLatToR = new HashMap<>();
            for (String str : jsonData) {
                String[] strData = new String[7];
                int strDataI = 0;
                int prev = 0;
                str = str + ";";
                for (int i = 0; i < str.length() && strDataI < 7; ++i) {
                    if (str.charAt(i) == '"') {
                        ++i;
                        while (str.charAt(i) != '"' || str.charAt(i + 1) != ';') {
                            ++i;
                        }
                        ++i;
                    }
                    if (str.charAt(i) == ';') {
                        strData[strDataI] = str.substring(prev, i);
                        prev = i + 1;
                        ++strDataI;
                    }
                }
                String country = strData[6];
                for (int i = 0; i < 6; i += 2) {
                    String[] rusnames = strData[i].replaceAll("\"", "").replaceAll("(\\W-\\W)|(\\-$)", "").split("\s");
                    String[] latnames = strData[i + 1].replaceAll("\"", "").replaceAll("(\\W-\\W)|(\\-$)", "").split("\s");
                    for (int j = 0; j < rusnames.length; ++j) {
                        String namerus = rusnames[j];
                        if (j >= latnames.length) {
                            break;
                        }
                        String namelat = latnames[j];
                        if (namerus.length() != 0 && namelat.length() != 0) {
                            if (processedDataRToLat.get(country) != null) {
                                if (processedDataRToLat.get(country).get(namerus) != null) {
                                    Map<String, Integer> map = processedDataRToLat.get(country).get(namerus);
                                    map.put(namelat, map.getOrDefault(namelat, 0) + 1);
                                } else {
                                    Map<String, Integer> map = new HashMap<>();
                                    map.put(namelat, 1);
                                    processedDataRToLat.get(country).put(namerus, map);
                                }
                            } else {
                                Map<String, Map<String, Integer>> map = new HashMap<>();
                                Map<String, Integer> map2 = new HashMap<>();
                                map2.put(namelat, 1);
                                map.put(namerus, map2);
                                processedDataRToLat.put(country, map);
                            }
                        }
                        if (namelat.length() != 0 && namerus.length() != 0) {
                            if (processedDataLatToR.get(country) != null) {
                                if (processedDataLatToR.get(country).get(namelat) != null) {
                                    Map<String, Integer> map = processedDataLatToR.get(country).get(namelat);
                                    map.put(namerus, map.getOrDefault(namerus, 0) + 1);
                                } else {
                                    Map<String, Integer> map = new HashMap<>();
                                    map.put(namerus, 1);
                                    processedDataLatToR.get(country).put(namelat, map);
                                }
                            } else {
                                Map<String, Map<String, Integer>> map = new HashMap<>();
                                Map<String, Integer> map2 = new HashMap<>();
                                map2.put(namerus, 1);
                                map.put(namelat, map2);
                                processedDataLatToR.put(country, map);
                            }
                        }
                    }
                }
            }
            List<Field1> l1 = new ArrayList<>();
            List<Field2> l2 = new ArrayList<>();
            for (Map.Entry entry: processedDataRToLat.entrySet()) {
                String country = (String) entry.getKey();
                for (Map.Entry entry1: (Set<Map.Entry>) ((HashMap)entry.getValue()).entrySet()) {
                    l1.add(new Field1(country, (String) entry1.getKey(), (HashMap) entry1.getValue()));
                }
            }
            for (Map.Entry entry: processedDataLatToR.entrySet()) {
                String country = (String) entry.getKey();
                for (Map.Entry entry1: (Set<Map.Entry>) ((HashMap)entry.getValue()).entrySet()) {
                    l2.add(new Field2(country, (String) entry1.getKey(), (HashMap) entry1.getValue()));
                }
            }
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            BufferedWriter f1 = new BufferedWriter(new FileWriter("Task1.json"));
            BufferedWriter f2 = new BufferedWriter(new FileWriter("Task2.json"));
            f1.write(ow.writeValueAsString(l1));
            f2.write(ow.writeValueAsString(l2));
            f1.flush();
            f2.flush();
            f1.close();
            f2.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
