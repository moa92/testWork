import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;


import java.io.IOException;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;

import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;


public class AppTest {


    @Tag("Test 1 - 000a4364-9379-41aa-8677-833398f5d79d")
    @Test
    void testCalcOne() throws IOException {

        System.out.println("======TEST ONE EXECUTED=======");
        List<ResponseFieldsGeneral> csvList = Steps.getDataFromCSV("C:\\Users\\UX500314\\IdeaProjects\\testEpam\\src\\test\\resources\\testdata\\v2\\000a4364-9379-41aa-8677-833398f5d79d.csv");
        List<ResponseFieldsGeneral> jsonList = Steps.getDataFromJson("C:/Users/UX500314/IdeaProjects/testEpam/src/test/resources/testdata/v1/000a4364-9379-41aa-8677-833398f5d79d.json");
        SoftAssertions softAssertions = new SoftAssertions();

        softAssertions.assertThat(jsonList.size()).isEqualTo(csvList.size());
        softAssertions.assertThat(jsonList)
                .as("Failed on message verification")
                .containsExactlyInAnyOrderElementsOf(csvList);

        softAssertions.assertAll();
    }

    @Tag("PROD")
    @Disabled
    @Test
    void testCalcTwo()
    {


    }

}
