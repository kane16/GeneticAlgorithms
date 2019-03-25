package pl.guminski.ga.services.geneticAlgorithm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.guminski.ga.models.GA.Individual;
import pl.guminski.ga.models.GA.StatisticsIndividual;

import java.util.ArrayList;
import java.util.List;

@Service
public class SimulationService {

    @Autowired
    DistanceCalculationService distanceCalculationService;

    @Autowired
    ChromosomeGenerationService chromosomeGenerationService;

    @Autowired
    ParametersService parametersService;

    private List<Individual> population;

    public List<StatisticsIndividual> rankAlgorithmBestIndividuals;
    public List<StatisticsIndividual> rouletteAlgorithmBestIndividuals;
    public List<StatisticsIndividual> tournamentAlgorithmBestIndividuals;
    public StatisticsIndividual randomBestIndividual;
    public StatisticsIndividual greedyBestIndividual;

    public void populateModel(){
        population = chromosomeGenerationService.generateRandomIndividuals(parametersService
                .getDataInputContainer().getNodeCoordList().size(), parametersService.getPop_size());
    }

    public List<StatisticsIndividual> getStatisticalIndividualFromList(List<Individual> individuals, boolean isSingle){
        List<StatisticsIndividual> statisticsIndividuals = new ArrayList<>();
        if(isSingle){
            double fitnessAvg = individuals.stream().filter(individual -> individual.getGeneration()==0)
                    .mapToDouble(Individual::getFitness).average().orElse(0);
            long individualsCount = individuals.stream().filter(individual -> individual.getGeneration()==0)
                    .count();
            double stdDev = Math.sqrt(individuals.stream().filter(individual -> individual.getGeneration()==0)
                    .mapToDouble(individual -> Math.pow(individual.getFitness()-fitnessAvg,2)).sum()/(individualsCount-1));
            StatisticsIndividual statisticsIndividual = new StatisticsIndividual();
            statisticsIndividual.setFitness(fitnessAvg);
            statisticsIndividual.setStdDeviation(stdDev);
            statisticsIndividual.setGeneration(0);
            statisticsIndividuals.add(statisticsIndividual);
            return statisticsIndividuals;
        }
        for(int i=1 ; i<parametersService.getGenerationNumber()+1; i++){
            final int generation = i;
            double fitnessAvg = individuals.stream().filter(individual -> individual.getGeneration()==generation)
                    .mapToDouble(Individual::getFitness).average().orElse(0);
            long individualsCount = individuals.stream().filter(individual -> individual.getGeneration()==generation)
                    .count();
            double stdDev = Math.sqrt(individuals.stream().filter(individual -> individual.getGeneration()==generation)
                    .mapToDouble(individual -> Math.pow(individual.getFitness()-fitnessAvg,2)).sum()/(individualsCount-1));
            StatisticsIndividual statisticsIndividual = new StatisticsIndividual();
            statisticsIndividual.setFitness(fitnessAvg);
            statisticsIndividual.setStdDeviation(stdDev);
            statisticsIndividual.setGeneration(generation);
            statisticsIndividuals.add(statisticsIndividual);
        }
        return statisticsIndividuals;
    }


    public List<Individual> getPopulation() {
        return population;
    }

    public void setPopulation(List<Individual> population) {
        this.population = population;
    }
}
