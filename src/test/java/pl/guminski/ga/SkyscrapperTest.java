package pl.guminski.ga;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.guminski.ga.models.games.inputmodels.SkyscraperItem;
import pl.guminski.ga.services.csp.CSPDataExtractorService;
import pl.guminski.ga.services.csp.games.SkyscrapperRules;

import java.io.FileNotFoundException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SkyscrapperTest {

    @Autowired
    CSPDataExtractorService dataExtractorService;

    public SkyscrapperTest() throws FileNotFoundException {
    }

    @Test
    public void testSkyscrapperForConstraints() throws FileNotFoundException {
        SkyscraperItem skyscraperItem = dataExtractorService.getScascraperItemFromFile("skyscrapper_4_0");
        SkyscrapperRules skyscrapperRules = new SkyscrapperRules(skyscraperItem.bottomBound, skyscraperItem.topBound,
                skyscraperItem.leftBound, skyscraperItem.rightBound, skyscraperItem.board);
    }

}
