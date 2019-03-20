package pl.guminski.ga.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class CrossoverService {

    @Autowired
    ParametersService parametersService;

    public List<Integer> performPMXAndGetCrossedChromosomes(List<Integer> chromosome1,
                                                            List<Integer> chromosome2){
        Random r = new Random();
        int cutpointLeft = r.nextInt(chromosome1.size());
        int cutpointRight = r.nextInt(chromosome1.size());
        while(cutpointLeft >= cutpointRight){
            cutpointRight = r.nextInt(chromosome1.size());
        }
        return swapAndRepairChromosome(chromosome1,
                chromosome2.subList(cutpointLeft, cutpointRight),
                cutpointLeft, cutpointRight);
    }


    public List<Integer> swapAndRepairChromosome(List<Integer> chromosome1, List<Integer> swapSegment,
                                                 int cutpointLeft, int cutpointRight){
        int i = cutpointLeft;
        while(i<cutpointRight){
            chromosome1.set(chromosome1.indexOf(swapSegment.get(i-cutpointLeft)), chromosome1.get(i));
            chromosome1.set(i, swapSegment.get(i-cutpointLeft));
            i++;
        }
        return chromosome1;
    }

}
