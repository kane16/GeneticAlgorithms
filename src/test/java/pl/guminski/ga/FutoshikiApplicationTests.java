package pl.guminski.ga;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.guminski.ga.services.futoshiki.CSPDataExtractorService;

import java.io.FileNotFoundException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FutoshikiApplicationTests {

    @Autowired
    CSPDataExtractorService CSPDataExtractorService;

    @Test
    public void testFutoshikiDataExtraction() throws FileNotFoundException {
        CSPDataExtractorService.getFutoshikiItemFromFile(
                CSPDataExtractorService.getDataInputFile("futoshiki_4_0"));
    }

}
