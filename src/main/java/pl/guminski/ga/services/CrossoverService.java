package pl.guminski.ga.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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


    public List<Integer> swapAndRepairChromosome(List<Integer> chromosome, List<Integer> swapSegment,
                                                 int cutpointLeft, int cutpointRight){
        int i = cutpointLeft;
        List<Integer> crossChromosome = new ArrayList<>(chromosome);
        Map<Integer, Integer> toReplaceMap = new HashMap<>();
        while(i<cutpointRight){
            int index = chromosome.indexOf(swapSegment.get(i-cutpointLeft));
            if(index < cutpointLeft || index >= cutpointRight)
                toReplaceMap.put(index, 0);
            i++;
        }
        i=cutpointLeft;
        for(Integer key: toReplaceMap.keySet()){
            while(i < cutpointRight){
                if(!swapSegment.contains(chromosome.get(i))){
                    crossChromosome.set(key, crossChromosome.get(i));
                    i++;
                    break;
                }
                i++;
            }
        }
        i=cutpointLeft;
        while(i<cutpointRight){
            crossChromosome.set(i, swapSegment.get(i-cutpointLeft));
            i++;
        }
        return crossChromosome;
    }

}
