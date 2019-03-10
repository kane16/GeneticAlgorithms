package pl.guminski.ga.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.guminski.ga.exceptions.NoItemFoundInCity;
import pl.guminski.ga.models.dataInput.DataInputContainer;
import pl.guminski.ga.models.dataInput.Item;
import pl.guminski.ga.models.dataInput.NodeCoord;
import pl.guminski.ga.models.dataInput.ThiefData;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OptimizationService {

    @Autowired
    DistanceCalculationService distanceCalculationService;

    public Item getGreedyBestItemProfitFromCity(List<Item> items, ThiefData thiefData, long cityIndex){
        return items.stream()
                .filter(item -> item.getIndex()==cityIndex &&
                        item.getWeight() <= thiefData.getKnapsackCapacity()-thiefData.getKnapsackLoad())
                .max(Comparator.comparing(Item::getProfit)).orElseThrow(NoItemFoundInCity::new);
    }

    public long getTotalProfitFromChromosome(DataInputContainer dataInputContainer, List<Integer> chromosome){
        ThiefData thiefData = dataInputContainer.getThiefData();
        List<NodeCoord> cities = chromosome.stream()
                .map(gen -> dataInputContainer.getNodeCoordList().get(gen-1))
                .collect(Collectors.toList());
        List<Item> items = dataInputContainer.getItems();
        cities.forEach(city -> {
            try{
                Item item = getGreedyBestItemProfitFromCity(items, thiefData, city.getIndex());
                thiefData.setKnapsackLoad(thiefData.getKnapsackLoad()+item.getWeight());
                thiefData.setProfit(thiefData.getProfit()+item.getProfit());
            }catch (NoItemFoundInCity exc){
            }
        } );
        return thiefData.getProfit();
    }

    public double getTotalTimeFromChromosome(DataInputContainer dataInputContainer, List<Integer> chromosome){
        double time = 0;
        ThiefData thiefData = dataInputContainer.getThiefData();
        List<NodeCoord> cities = dataInputContainer.getNodeCoordList();
        List<Item> items = dataInputContainer.getItems();
        for(int i=0; i<chromosome.size(); i++){
            NodeCoord currentCity = cities.get(chromosome.get(i)-1);
            NodeCoord nextCity;
            if(i == chromosome.size()-1)
                nextCity = cities.get(chromosome.get(0)-1);
            else nextCity = cities.get(chromosome.get(i+1)-1);
            try{
                Item item = getGreedyBestItemProfitFromCity(items, thiefData, chromosome.get(i)-1);
                thiefData.setKnapsackLoad(thiefData.getKnapsackLoad()+item.getWeight());
                thiefData.setProfit(thiefData.getProfit()+item.getProfit());
            }catch (NoItemFoundInCity exc){

            }
            time += distanceCalculationService.getTimeToCoverTheDistance(thiefData,
                    distanceCalculationService.getDistanceBetweenCities(nextCity, currentCity));
        }

        return time;
    }

}
