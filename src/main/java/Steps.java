import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.stream.Stream;


public class Steps {

    public static List<ResponseFieldsGeneral> getDataFromJson(String filePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<ResponseFieldsGeneral> responseFieldsGenerals = new ArrayList<>();
        JsonNode rootNode = mapper.readTree(new File(filePath));

        Iterator<Map.Entry<String, JsonNode>> fieldsIterator = rootNode.fields();
        while (fieldsIterator.hasNext()) {
            Map.Entry<String, JsonNode> field = fieldsIterator.next();
            String dateOfRequest = field.getKey();
            JsonNode observationNode = field.getValue();
            Iterator<Map.Entry<String, JsonNode>> observationFieldsIterator = observationNode.fields();
            while (observationFieldsIterator.hasNext()) {
                Map.Entry<String, JsonNode> observationField = observationFieldsIterator.next();
                String observationDate = observationField.getKey();
                String valueForObservationDate = observationField.getValue().asText();
                ResponseFieldsGeneral responseGeneratedObject = new ResponseFieldsGeneral(dateOfRequest, observationDate, valueForObservationDate);
                if (!responseGeneratedObject.getValueForObservationDate().equals("null")) {
                    responseFieldsGenerals.add(responseGeneratedObject);
                }
            }
        }
        return responseFieldsGenerals;
    }

    public static List<ResponseFieldsGeneral> getDataFromCSV(String file) {
        List<ResponseFieldsGeneral> responseList = new ArrayList<>();
        Path pathToFile = Paths.get(file);
        try (BufferedReader br = Files.newBufferedReader(pathToFile)) {
            String row = br.readLine();
            while (row != null) {
                String[] attributes = row.split(",");
                ResponseFieldsGeneral cust = getOneData(attributes);
                responseList.add(cust);
                row = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return responseList;
    }

    static void validateFolder(File folder) {
        Objects.requireNonNull(folder, "Folder must not be null.");
        if (!folder.exists() || !folder.isDirectory()) {
            throw new IllegalArgumentException("Invalid folder path: " + folder.getPath());
        }
    }

    static List<File> getCsvFiles(File folder) {
        List<File> csvFiles = new ArrayList<>();
        File[] files = folder.listFiles((dir, name) -> name.endsWith(".csv"));
        if (files != null) {
            csvFiles.addAll(Arrays.asList(files));
        }
        return csvFiles;
    }

    static List<File> getJsonFiles(File folder) {
        List<File> jsonFiles = new ArrayList<>();
        File[] files = folder.listFiles((dir, name) -> name.endsWith(".json"));
        if (files != null) {
            jsonFiles.addAll(Arrays.asList(files));
        }
        return jsonFiles;
    }

    private static ResponseFieldsGeneral getOneData(String[] attributes) {
        String dateOfRequest, observationDate, valueForObservationDate;
        try {
            dateOfRequest = Instant.ofEpochSecond(Integer.parseInt(attributes[1])).toString();
        } catch (Exception e) {
            dateOfRequest = null;
        }
        try {
            observationDate = Instant.ofEpochSecond(Integer.parseInt(attributes[0])).toString();

        } catch (Exception e) {
            observationDate = null;
        }
        try {
            valueForObservationDate = attributes[2];
        } catch (Exception e) {
            valueForObservationDate = null;
        }
        return new ResponseFieldsGeneral(dateOfRequest, observationDate, valueForObservationDate);
    }
}