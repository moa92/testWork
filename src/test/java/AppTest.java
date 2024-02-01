import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;


public class AppTest {
    @Tag("Test 1 - 000a4364-9379-41aa-8677-833398f5d79d")
    @Test
    void testCalcOne() throws IOException {
        List<ResponseFieldsGeneral> jsonList = Steps.getDataFromJson("src/test/resources/testdata/v1/000a4364-9379-41aa-8677-833398f5d79d.json");
        List<ResponseFieldsGeneral> csvList = Steps.getDataFromCSV("src\\test\\resources\\testdata\\v2\\000a4364-9379-41aa-8677-833398f5d79d.csv");
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(jsonList.size()).as("Failed verification").isEqualTo(csvList.size());
        softAssertions.assertThat(jsonList)
                .as("Failed verification")
                .containsExactlyInAnyOrderElementsOf(csvList);
        softAssertions.assertAll();
    }

}
