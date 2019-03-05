package models.dataInput;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    public double getCurrentSpeed(){
        return maxSpeed - knapsackLoad*(maxSpeed-minSpeed)/knapsackCapacity;
    }

}
