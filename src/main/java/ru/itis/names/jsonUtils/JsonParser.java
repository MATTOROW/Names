package ru.itis.names.jsonUtils;

import java.util.List;

public interface JsonParser {
    String transcriptNameFromRus(String rusname, String country);
    String transcriptNameFromLat(String latname, String country);
    List<String> variantsNameFromRus(String rusname, String country);
    List<String> variantsNameFromLat(String latname, String country);
}
