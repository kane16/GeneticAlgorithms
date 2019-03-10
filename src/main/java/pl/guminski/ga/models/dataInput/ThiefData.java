package pl.guminski.ga.models.dataInput;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ThiefData {

    private double minSpeed;
    private double maxSpeed;
    private long knapsackCapacity;
    private double knapsackLoad;
    private double rentingRatio;
    private long profit;

    public double getCurrentSpeed(){
        return maxSpeed - knapsackLoad*(maxSpeed-minSpeed)/knapsackCapacity;
    }

}
