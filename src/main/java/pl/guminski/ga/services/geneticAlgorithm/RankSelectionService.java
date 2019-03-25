package pl.guminski.ga.services.geneticAlgorithm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.guminski.ga.models.GA.Individual;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RankSelectionService extends GeneticAlgorithmService{

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
        }
        double partialSum =0;
        for(int i=0; i<population.size();i++){
            partialSum += i*1.0;
            population.get(i).setRank(partialSum/sum);
        }
        return partialSum;
    }

    public List<Individual> makeSelection(List<Individual> initialPopulation){
        List<Individual> individuals = new ArrayList<>();
        List<Individual> tourPopulation = new ArrayList<>();
        int selectionSize = parametersService.getTour();
        Random random = new Random();
        while(tourPopulation.size() < selectionSize){
            int rouletteRandom = random.nextInt(initialPopulation.size());
            if(!tourPopulation.contains(initialPopulation.get(rouletteRandom)))
                tourPopulation.add(initialPopulation.get(rouletteRandom));
        }
        double partialSum = makePreparationForSelectionWithRank(tourPopulation);
        while(individuals.size() < selectionSize/2){
            double rouletteRandom = Math.random()*partialSum;
            tourPopulation.stream()
                    .filter(individual -> !individuals.contains(individual) && individual.getRank() >= rouletteRandom)
                    .findFirst().ifPresent(individuals::add);
        }
        return individuals;
    }

    public double getNormalizedFitness(Individual individual, double fitnessSum){
        return Math.exp(50*individual.getFitness()/Math.abs(fitnessSum));
    }

}
