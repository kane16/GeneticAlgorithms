package pl.guminski.ga;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.guminski.ga.models.games.SkyscraperItem;
import pl.guminski.ga.services.csp.CSPDataExtractorService;
import pl.guminski.ga.services.csp.games.Skyscrapper;

import java.io.FileNotFoundException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SkyscrapperTest {

    @Autowired
    CSPDataExtractorService dataExtractorService;

    Skyscrapper skyscrapper = new Skyscrapper();

    @Test
    public void testSkyscrapperForConstraints() throws FileNotFoundException {
        SkyscraperItem skyscraperItem = dataExtractorService.getScascraperItemFromFile("skyscrapper_4_0");
        assertTrue(skyscrapper.isConstraintsFulfilled(skyscraperItem.bottomBound, skyscraperItem.topBound,
                skyscraperItem.leftBound, skyscraperItem.rightBound, skyscraperItem.board, 4, 1, 1));
        skyscraperItem.board[1][1] = 4;
        assertFalse(skyscrapper.isConstraintsFulfilled(skyscraperItem.bottomBound, skyscraperItem.topBound,
                skyscraperItem.leftBound, skyscraperItem.rightBound, skyscraperItem.board, 4, 1, 2));
        skyscraperItem.board[1][0] = 5;
        assertFalse(skyscrapper.isConstraintsFulfilled(skyscraperItem.bottomBound, skyscraperItem.topBound,
                skyscraperItem.leftBound, skyscraperItem.rightBound, skyscraperItem.board, 3, 1, 2));
    }

}
