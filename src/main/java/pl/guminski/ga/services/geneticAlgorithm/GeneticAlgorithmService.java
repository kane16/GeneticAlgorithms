package pl.guminski.ga.services.geneticAlgorithm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.guminski.ga.models.GA.Individual;
import pl.guminski.ga.models.GA.dataInput.DataInputContainer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
abstract public class GeneticAlgorithmService {

    @Autowired
    CrossoverService crossoverService;

    @Autowired
    ParametersService parametersService;

    @Autowired
    OptimizationService optimizationService;

    public List<Individual> runAlgorithmAndFindBestSolutionInGeneration(List<Individual> initialPopulation) throws CloneNotSupportedException {
        DataInputContainer dataInputContainer = (DataInputContainer) parametersService.getDataInputContainer().cloneObject();

        List<Individual> bestFittedGenerationIndividuals = new ArrayList<>();
        int i = 0;

        while(i < parametersService.getGenerationNumber()){
            i++;
            List<Individual> currentPopulation;
            currentPopulation = prepareSelection(initialPopulation);
            List<Individual> crossoverPopulation = new ArrayList<>();
            while(crossoverPopulation.size() < initialPopulation.size()*0.9){
                Individual individual = new Individual();
                individual.setChromosome(crossoverService.performPMXAndGetCrossedChromosomes(
                        makeSelection(currentPopulation)));
                individual.setGeneration(i);
                crossoverPopulation.add(individual);
            }
            List<Individual> orderedPopulation =
                    initialPopulation.stream().sorted(Comparator.comparing(Individual::getFitness).reversed())
                            .collect(Collectors.toList());
            for(int j=0 ; j<initialPopulation.size()*0.1;j++){
                crossoverPopulation.add(orderedPopulation.get(i));
            }
            currentPopulation = crossoverPopulation;
            currentPopulation.forEach(individual ->
                    {
                        individual.setChromosome(
                                optimizationService.performMutationOperationAndGetMutatedChromosome(
                                        individual.getChromosome()));
                        individual.setFitness(optimizationService.getFitnessValue(
                                parametersService.getDataInputContainer(), individual.getChromosome()));
                    });
            Individual bestFittedIndividual = currentPopulation.get(0);
            for(Individual individual: currentPopulation){
                if(individual.getFitness() > bestFittedIndividual.getFitness()){
                    bestFittedIndividual = individual;
                }
            }
            bestFittedGenerationIndividuals.add(bestFittedIndividual);
            initialPopulation = currentPopulation;
        }

        parametersService.setDataInputContainer(dataInputContainer);
        return bestFittedGenerationIndividuals;
    }

    public abstract List<Individual> prepareSelection(List<Individual> initialPopulation);

    public abstract List<List<Integer>> makeSelection(List<Individual> preparedPopulation);

}
