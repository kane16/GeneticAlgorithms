package pl.guminski.ga.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.guminski.ga.exceptions.NoItemFoundInCity;
import pl.guminski.ga.models.Individual;
import pl.guminski.ga.models.dataInput.Item;
import pl.guminski.ga.models.dataInput.NodeCoord;

import java.util.ArrayList;
import java.util.List;

@Service
public class GreedySelectionService {

    @Autowired
    DistanceCalculationService distanceCalculationService;

    @Autowired
    OptimizationService optimizationService;

    @Autowired
    ParametersService parametersService;

    public Individual getGreedyBestFitnessChromosome(){
        Individual individual = new Individual();
        List<Integer> chromosome = new ArrayList<>();
        int bestCity = 1;
        chromosome.add(bestCity);
        List<NodeCoord> cities = new ArrayList<>(parametersService.getDataInputContainer().getNodeCoordList());
        NodeCoord currentCity = cities.get(bestCity);
        cities.remove(0);
        while(parametersService.getDataInputContainer().getNodeCoordList().size() > chromosome.size()){
            bestCity = getGreedyBestTimeCity(currentCity, bestCity, cities);
            chromosome.add(bestCity);
        }
        individual.setChromosome(chromosome);
        individual.setFitness(optimizationService.getFitnessValue(parametersService.getDataInputContainer(), individual.getChromosome()));
        return individual;
    }

    private int getGreedyBestTimeCity(NodeCoord currentCity, int currentCityIndex, List<NodeCoord> cities) { ;
        long nextCityIndex = cities.get(0).getIndex();
        double distance = distanceCalculationService.getDistanceBetweenCities(currentCity, cities.get(0));
        NodeCoord cityToDelete = cities.get(0);
        for(NodeCoord city: cities){
            double nextDistance = distanceCalculationService.getDistanceBetweenCities(currentCity, city);
            if(distance > nextDistance){
                distance = nextDistance;
                nextCityIndex = city.getIndex();
                cityToDelete = city;
            }
        }
        cities.remove(cityToDelete);
        return (int) nextCityIndex;
    }

}
