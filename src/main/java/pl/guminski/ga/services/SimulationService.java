package pl.guminski.ga.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.guminski.ga.models.Individual;

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

    public void populateModel(){
        population = chromosomeGenerationService.generateRandomIndividuals(parametersService
                .getDataInputContainer().getNodeCoordList().size(), parametersService.getPop_size());
    }


    public List<Individual> getPopulation() {
        return population;
    }

    public void setPopulation(List<Individual> population) {
        this.population = population;
    }
}
