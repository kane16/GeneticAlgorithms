package pl.guminski.ga.services;

import org.springframework.stereotype.Service;
import pl.guminski.ga.models.dataInput.DataInputContainer;

@Service
public class ParametersService {

    private double Pm = 0.01;
    private double Px = 0.7;
    private int pop_size = 100;
    private int gen = 100;
    private int tour = 5;
    private DataInputContainer dataInputContainer;

    public double getPm() {
        return Pm;
    }

    public void setPm(double pm) {
        Pm = pm;
    }

    public double getPx() {
        return Px;
    }

    public void setPx(double px) {
        Px = px;
    }

    public int getPop_size() {
        return pop_size;
    }

    public void setPop_size(int pop_size) {
        this.pop_size = pop_size;
    }

    public int getTour() {
        return tour;
    }

    public void setTour(int tour) {
        this.tour = tour;
    }

    public int getChromosome_size() {
        return dataInputContainer.getNodeCoordList().size();
    }

    public DataInputContainer getDataInputContainer() {
        return dataInputContainer;
    }

    public void setDataInputContainer(DataInputContainer dataInputContainer) {
        this.dataInputContainer = dataInputContainer;
    }
}
