package pl.guminski.ga;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.guminski.ga.models.games.FutoshikiItem;
import pl.guminski.ga.services.csp.CSPDataExtractorService;
import pl.guminski.ga.services.csp.games.Futoshiki;

import java.io.FileNotFoundException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FutoshikiTest {

    @Autowired
    CSPDataExtractorService dataExtractorService;

    Futoshiki futoshiki = new Futoshiki();

    @Test
    public void testFutoshikiForConstraints() throws FileNotFoundException {
        FutoshikiItem futoshikiItem = dataExtractorService.getFutoshikiItemFromFile("futoshiki_4_0");
        assertTrue(futoshiki.isConstraintsFulfilled(futoshikiItem.constraints, futoshikiItem.contentTable, 2, 1, 1));
        assertFalse(futoshiki.isConstraintsFulfilled(futoshikiItem.constraints, futoshikiItem.contentTable, 3, 3, 3));
        futoshikiItem.contentTable[2][3] = 2;
        assertFalse(futoshiki.isConstraintsFulfilled(futoshikiItem.constraints, futoshikiItem.contentTable, 5, 3, 3));
    }


}
