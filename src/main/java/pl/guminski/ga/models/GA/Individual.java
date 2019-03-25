package pl.guminski.ga.models.GA;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Individual {

    private int index;
    private int generation;
    private List<Integer> chromosome;
    private double fitness;
    private double fitnessNormalized;
    private double rank;
    private double rouletteSum;

}
