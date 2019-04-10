package pl.guminski.ga;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.guminski.ga.models.games.inputmodels.FutoshikiItem;
import pl.guminski.ga.services.csp.CSPDataExtractorService;
import pl.guminski.ga.services.csp.CSPGameSimulation;
import pl.guminski.ga.services.csp.games.FutoshikiRules;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FutoshikiRulesTest {

    @Autowired
    CSPDataExtractorService dataExtractorService;

    public FutoshikiRulesTest() throws FileNotFoundException {
    }

    @Test
    public void testFutoshikiForConstraints() throws FileNotFoundException {
        FutoshikiItem futoshikiItem = dataExtractorService.getFutoshikiItemFromFile("futoshiki_4_0");
        FutoshikiRules futoshiki = new FutoshikiRules(futoshikiItem.constraints, futoshikiItem.contentTable);
        CSPGameSimulation cspGameSimulation = new CSPGameSimulation(futoshiki);
        List<int[][]> solutions = cspGameSimulation.runGameAndFindSolutions();
        for(int[][] solution: solutions){
            System.out.println(Arrays.toString(solution[0]));
        }
    }


}
