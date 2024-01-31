import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.*;

import static java.lang.String.format;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;


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

                ResponseFieldsGeneral responseEndpointOne =  new ResponseFieldsGeneral(dateOfRequest, observationDate, valueForObservationDate);

                if (!responseEndpointOne.getValueForObservationDate().equals("null") ) {responseFieldsGenerals.add(responseEndpointOne);}

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


//    public static List<ResponseFieldsGeneral> readMultipleCSVFiles(String[] fileNames) throws IOException {
//        List<ResponseFieldsGeneral> pojos = new ArrayList<>();
//        for (String fileName : fileNames) {
//            try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
//                String line;
//                while ((line = br.readLine()) != null) {
//                    String[] values = line.split(",");
//                    ResponseFieldsGeneral pojo = new ResponseFieldsGeneral();
//                    pojo.setName(values[0]);
//                    pojo.setMobile(Integer.parseInt(values[1]));
//                    // set other fields here
//                    pojos.add(pojo);
//                }
//            }
//        }
//        return pojos;
//    }


