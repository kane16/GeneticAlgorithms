package dataLoaderCheck;

import dataProcessorPackage.TTYInputFileDataReader;
import models.dataInput.NodeCoord;
import org.junit.Test;
import org.w3c.dom.Node;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static dataProcessing.TravellingSalesmanOptimisationCalculations.getDistanceBetweenCities;
import static dataProcessing.TravellingSalesmanOptimisationCalculations.getTotalDistanceFromChromosome;
import static org.junit.Assert.*;


public class DataLoaderTest {

    TTYInputFileDataReader inputFileDataReader = new TTYInputFileDataReader();

    @Test
    public void fileFoundTest(){
        assertNotNull(inputFileDataReader.getDataInputFile("easy_0"));
    }

    @Test
    public void checkCorrectnessOfDistanceMethod(){
        NodeCoord node1 = new NodeCoord(1, 5, 9);
        NodeCoord node2 = new NodeCoord(2, 2, 5);
        assertEquals(5, getDistanceBetweenCities(node1, node2), 0.1);
        assertEquals(5, getDistanceBetweenCities(node2, node1), 0.1);
    }

    @Test
    public void checkCorrectnessOfTotalDistanceMethod(){
        NodeCoord node1 = new NodeCoord(1, 5, 9);
        NodeCoord node2 = new NodeCoord(2, 2, 5);
        ArrayList<NodeCoord> nodes = new ArrayList<>();
        nodes.add(node1);
        nodes.add(node2);
        nodes.add(node1);
        assertEquals(10, getTotalDistanceFromChromosome(nodes), 0.1);
        nodes.add(node2);
        assertEquals(20, getTotalDistanceFromChromosome(nodes), 0.1);
    }

}
