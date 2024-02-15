import io.qameta.allure.Description;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;



public class BaseTest {
    @Description(value = "Test 1 - check the number and consistency of files in a folder")
    @Test
    public void testFilesInFolders() {
        SoftAssertions softAssertions = new SoftAssertions();
        String csvFolderPath = "src/test/resources/testdata/v2";
        File csvFolder = new File(csvFolderPath);
        String jsonFolderPath = "src/test/resources/testdata/v1";
        File jsonFolder = new File(jsonFolderPath);

        Steps.validateFolder(csvFolder);
        Steps.validateFolder(jsonFolder);

        List<File> csvFiles = Steps.getCsvFiles(csvFolder);
        List<File> jsonFiles = Steps.getJsonFiles(jsonFolder);

        softAssertions.assertThat(jsonFiles.size())
                .as("Failed length verification")
                .isEqualTo(csvFiles.size());

        softAssertions.assertThat(jsonFiles.stream().map(e -> e.getName().replaceFirst("\\.json$", "")))
                .as("Failed name verification")
                .containsExactlyInAnyOrderElementsOf(csvFiles.stream().map(e -> e.getName().replaceFirst("\\.csv$", "")).collect(Collectors.toList()));
        softAssertions.assertAll();
    }

    static Stream<Arguments> filesProvider() throws IOException {
        File jsonFolder = new File("src/test/resources/testdata/v1");
        File csvFolder = new File("src/test/resources/testdata/v2");
        File[] jsonFiles = jsonFolder.listFiles();
        File[] csvFiles = csvFolder.listFiles();

        if (jsonFiles != null && csvFiles != null) {
            List<Arguments> argumentsList = new ArrayList<>();
            List<ResponseFieldsGeneral> jsonList = new ArrayList<>();
            List<ResponseFieldsGeneral> csvList = new ArrayList<>();
            for (File jsonFile : jsonFiles) {
                for (File csvFile : csvFiles) {
                    if (jsonFile.getName().replaceFirst("\\.json$", "").equals(csvFile.getName().replaceFirst("\\.csv$", ""))) {
                        jsonList = Steps.getDataFromJson(jsonFile.getAbsolutePath());
                        csvList = Steps.getDataFromCSV(csvFile.getAbsolutePath());
                        argumentsList.add(Arguments.of(jsonList, csvList, csvFile.getName()));
                    }
                }
            }
            return argumentsList.stream();
        }
        return Stream.empty();
    }
    @Description(value = "Test 2 - checks the contents of the file")
    @ParameterizedTest
    @MethodSource("filesProvider")
    void testFileProcessing(List<ResponseFieldsGeneral> jsonList, List<ResponseFieldsGeneral> csvList, String fileName) {
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(jsonList.size()).as("Failed size verification").isEqualTo(csvList.size());
        softAssertions.assertThat(jsonList)
                .as("Failed verification for file: " + fileName)
                .containsExactlyInAnyOrderElementsOf(csvList);
        softAssertions.assertAll();
    }
}
