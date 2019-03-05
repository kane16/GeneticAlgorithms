package dataProcessing;

import models.dataInput.NodeCoord;
import models.dataInput.ThiefData;

import java.util.List;

public class TravellingSalesmanOptimisationCalculations {

    public static double getDistanceBetweenCities(NodeCoord node1, NodeCoord node2){
        return Math.pow(Math.pow(node2.getX() - node1.getX(), 2) +
                Math.pow(node2.getY() - node1.getY(), 2), 0.5);
    }

    public static double getTimeToCoverTheDistance(ThiefData thiefData, double distance){
        return distance/thiefData.getCurrentSpeed();
    }

    public static double getTotalDistanceFromChromosome(List<NodeCoord> nodes){
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

}
