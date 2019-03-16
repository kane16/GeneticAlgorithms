package pl.guminski.ga.services.SimulationTask;

import javafx.concurrent.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.guminski.ga.services.SimulationService;

import java.util.List;

public class SimulationTask extends Task<List<Integer>> {

    SimulationService simulationService;

    public SimulationTask(SimulationService simulationService){
        this.simulationService = simulationService;
    }

    @Override
    protected List<Integer> call() throws Exception {
        this.updateMessage("Populating model");
        this.updateProgress(5, 100);
        simulationService.populateModel();
        this.updateProgress(10, 100);
        return null;
    }
}
