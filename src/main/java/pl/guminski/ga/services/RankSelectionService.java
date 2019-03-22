package pl.guminski.ga.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.guminski.ga.models.Individual;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RankSelectionService {

    @Autowired
    ParametersService parametersService;

    public double makePreparationForSelectionWithRank(List<Individual> population){
        double fitnessSum = population.stream().mapToDouble(Individual::getFitness).sum();
        population.forEach(individual ->
                individual.setFitnessNormalized(getNormalizedFitness(individual, fitnessSum)));
        population = population.stream().sorted(Comparator.comparing(Individual::getFitnessNormalized))
                .collect(Collectors.toList());
        int sum = 0;
        for(int i=0; i<population.size();i++){
            sum += i;
            population.get(i).setRank(i);
        }
        double partialSum =0;
        for(int i=0; i<population.size();i++){
            partialSum += i*1.0;
            population.get(i).setRank(partialSum/sum);
        }
        return partialSum;
    }

    public List<Individual> makeSelection(List<Individual> population){
        double partialSum = makePreparationForSelectionWithRank(population);
        population = sortIndividualsBySum(population);
        List<Individual> individuals = new ArrayList<>();
        int selectionSize = parametersService.getPop_size()/parametersService.getTour();
        while(individuals.size() < selectionSize){
            double rouletteRandom = Math.random()*partialSum;
            population.stream()
                    .filter(individual -> !individuals.contains(individual) && individual.getRank() >= rouletteRandom)
                    .findFirst().ifPresent(individuals::add);
        }
        return individuals;
    }

    private List<Individual> sortIndividualsBySum(List<Individual> individuals) {
        return individuals.stream().sorted(Comparator.comparing(Individual::getRank))
                .collect(Collectors.toList());
    }

    public double getNormalizedFitness(Individual individual, double fitnessSum){
        return Math.exp(50*individual.getFitness()/Math.abs(fitnessSum));
    }

}
