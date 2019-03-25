package pl.guminski.ga;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.guminski.ga.services.futoshiki.FutoshikiDataExtractor;

import java.io.FileNotFoundException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FutoshikiApplicationTests {

    @Autowired
    FutoshikiDataExtractor futoshikiDataExtractor;

    @Test
    public void testFutoshikiDataExtraction() throws FileNotFoundException {
        futoshikiDataExtractor.getFutoshikiItemFromFile(
                futoshikiDataExtractor.getDataInputFile("futoshiki_4_0"));
    }

}
