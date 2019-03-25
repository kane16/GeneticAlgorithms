package pl.guminski.ga.models.GA;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StatisticsIndividual {

    private int generation;
    private double fitness;
    private double fitnessSum;
    private double stdDeviation;

}
