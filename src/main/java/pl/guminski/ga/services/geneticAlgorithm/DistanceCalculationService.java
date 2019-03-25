package pl.guminski.ga.services.geneticAlgorithm;

import org.springframework.stereotype.Service;
import pl.guminski.ga.models.GA.dataInput.NodeCoord;

import java.util.List;

@Service
public class DistanceCalculationService {

    public Integer generationNumber;


    public double getDistanceBetweenCities(NodeCoord node1, NodeCoord node2){
        return Math.pow(Math.pow(node2.getX() - node1.getX(), 2) +
                Math.pow(node2.getY() - node1.getY(), 2), 0.5);
    }

    public double getTimeToCoverTheDistance(double thiefSpeed, double distance){
        return distance/thiefSpeed;
    }

    public double getTotalDistanceFromChromosome(List<NodeCoord> nodes){
        double distance = 0;
        NodeCoord startNode = nodes.get(0);
        NodeCoord previousNode = nodes.get(0);
        for(int i=0; i<nodes.size(); i++){
            NodeCoord node = nodes.get(i);
            distance += getDistanceBetweenCities(previousNode, node);
            previousNode = nodes.get(i);
            if(i == nodes.size() - 1){
                distance += getDistanceBetweenCities(node, startNode);
            }
        }
        return distance;
    }

    public NodeCoord getNextCity(List<NodeCoord> coordList, long currentCityIndex){
        if(currentCityIndex+1>coordList.size()){
            return coordList.get(0);
        }else return coordList.get((int)currentCityIndex);
    }

}
