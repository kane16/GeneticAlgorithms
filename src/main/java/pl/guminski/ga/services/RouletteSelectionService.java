package pl.guminski.ga.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.guminski.ga.models.Individual;

import java.util.*;

@Service
public class RouletteSelectionService {

    private double rouletteSum;

    @Autowired
    ParametersService parametersService;

    public void setRouletteMap(List<Individual> orderedPopulation){
        double sum = 0;
        for(Individual individual: orderedPopulation){
            sum += individual.getFitness();
            individual.setRouletteSum(sum);
        }
        this.rouletteSum = sum;
    }

    public List<Individual> makeSelectionWithRoulette(List<Individual> population){
        setRouletteMap(population);
        List<Individual> individuals = new ArrayList<>();
        int selectionSize = parametersService.getPop_size()/parametersService.getTour();
        while(individuals.size() < selectionSize){
            double rouletteRandom = Math.random()*rouletteSum;
            population.stream()
                    .filter(individual -> !individuals.contains(individual) && individual.getRouletteSum() >= rouletteRandom)
                    .findFirst().ifPresent(individuals::add);
        }
        return individuals;
    }

}
