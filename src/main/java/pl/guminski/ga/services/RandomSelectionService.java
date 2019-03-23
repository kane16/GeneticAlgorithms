package pl.guminski.ga.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.guminski.ga.models.Individual;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RandomSelectionService {

    @Autowired
    ChromosomeGenerationService chromosomeGenerationService;

    @Autowired
    SimulationService simulationService;

    @Autowired
    ParametersService parametersService;

    public Individual generatePopulationAndFindBestFitness(){
       List<Individual> individuals = chromosomeGenerationService.generateRandomIndividuals(simulationService.getPopulation().get(0)
                       .getChromosome().size(),
                simulationService.getPopulation().size()*parametersService.getGenerationNumber()).stream()
                .sorted(Comparator.comparing(Individual::getFitness).reversed()).collect(Collectors.toList());
       return individuals.get(0);
    }


}
