package pl.guminski.ga.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.guminski.ga.models.Individual;

import java.util.List;

@Service
public class RandomSelectionService {

    @Autowired
    ChromosomeGenerationService chromosomeGenerationService;

    public double generatePopulationAndFindBestFitness(int coordsSize,
                                                           int initialNumberOfPopulation,
                                                           int numberOfGenerations){
       return chromosomeGenerationService.generateRandomIndividuals(coordsSize,
                initialNumberOfPopulation*numberOfGenerations).stream()
                .mapToDouble(Individual::getFitness).max().orElse(0);
    }


}
