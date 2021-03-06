package pl.guminski.ga.services.geneticAlgorithm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.guminski.ga.models.GA.Individual;

import java.util.*;

@Service
public class CrossoverService {

    @Autowired
    ParametersService parametersService;

    public List<Integer> getRandomChromosome(List<Individual> individuals, List<Individual> crossoverIndividuals){
        Random r = new Random();
        int index1 = r.nextInt(individuals.size());
        Individual individual = individuals.get(index1);
        while(crossoverIndividuals.contains(individual)){
            index1 = r.nextInt(individuals.size());
        }
        return individuals.get(index1).getChromosome();
    }

    public List<Integer> performPMXAndGetCrossedChromosomes(List<List<Integer>> chromosomes){
        List<Integer> chromosome1 = chromosomes.get(0);
        List<Integer> chromosome2 = chromosomes.get(1);
        Random r = new Random();
        if(r.nextDouble() >= parametersService.getPx())
            return chromosome1;
        int cutpointLeft = r.nextInt(chromosome1.size()-1);
        int cutpointRight = r.nextInt(chromosome1.size());
        while(cutpointLeft >= cutpointRight){
            cutpointRight = r.nextInt(chromosome1.size());
        }
        return swapAndRepairChromosome(chromosome1,
                chromosome2.subList(cutpointLeft, cutpointRight),
                cutpointLeft, cutpointRight);
    }

    public List<Integer> checkCrossoverMechanism(List<Integer> chromosome1, List<Integer> chromosome2){
        Random r = new Random();
        int cutpointLeft = r.nextInt(chromosome1.size()-1);
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
