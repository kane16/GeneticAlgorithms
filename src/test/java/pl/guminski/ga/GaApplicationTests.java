package pl.guminski.ga;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.guminski.ga.models.Individual;
import pl.guminski.ga.models.dataInput.DataInputContainer;
import pl.guminski.ga.models.dataInput.NodeCoord;
import pl.guminski.ga.services.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static pl.guminski.ga.services.ChromosomeGenerationService.getRandomChromosomeStringRepresentation;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GaApplicationTests {

    @Autowired
    DataExtractionService dataExtractionService;

    @Autowired
    DistanceCalculationService distanceCalculationService;

    @Autowired
    ChromosomeGenerationService chromosomeGenerationService;

    @Autowired
    OptimizationService optimizationService;

    @Autowired
    SimulationService simulationService;

    @Autowired
    ParametersService parametersService;

    @Autowired
    RouletteSelectionService rouletteSelectionService;

    @Autowired
    CrossoverService crossoverService;

    @Autowired
    GreedySelectionService greedySelectionService;

    @Autowired
    RankSelectionService rankSelectionService;

    @Test
    public void fileFoundTest(){
        assertNotNull(dataExtractionService.getDataInputFile("easy_0"));
    }

    @Test
    public void filesInResourceFolderFoundTest(){
        List<String> files = dataExtractionService.getAllFilesFromInputDataFolder();

        assertNotNull(files);
        assertEquals(17, files.size());

    }

    @Test
    public void checkCorrectnessOfDistanceMethod(){
        NodeCoord node1 = new NodeCoord(1, 5, 9);
        NodeCoord node2 = new NodeCoord(2, 2, 5);
        assertEquals(5, distanceCalculationService.getDistanceBetweenCities(node1, node2), 0.1);
        assertEquals(5, distanceCalculationService.getDistanceBetweenCities(node2, node1), 0.1);
    }

    @Test
    public void checkCorrectnessOfTotalDistanceMethod(){
        NodeCoord node1 = new NodeCoord(1, 5, 9);
        NodeCoord node2 = new NodeCoord(2, 2, 5);
        ArrayList<NodeCoord> nodes = new ArrayList<>();
        nodes.add(node1);
        nodes.add(node2);
        nodes.add(node1);
        assertEquals(10, distanceCalculationService.getTotalDistanceFromChromosome(nodes), 0.1);
        nodes.add(node2);
        assertEquals(20, distanceCalculationService.getTotalDistanceFromChromosome(nodes), 0.1);
    }

    @Test
    public void checkCorrectDataUpload(){
        DataInputContainer container = dataExtractionService.getDataInputContainerFromFile(
                dataExtractionService.getDataInputFile("easy_0"));

        assertEquals("berlin52-TTP", container.getProblemName());
        assertEquals(4046, container.getThiefData().getKnapsackCapacity());
        assertEquals(0.1, container.getThiefData().getMinSpeed(), 0.01);
        assertEquals(1, container.getThiefData().getMaxSpeed(), 0.01);
        assertEquals(0.31, container.getThiefData().getRentingRatio(), 0.01);
        assertEquals(565.0, container.getNodeCoordList().get(0).getX(), 0.1);
        assertEquals(575.0, container.getNodeCoordList().get(0).getY(), 0.1);
        assertEquals(1, container.getNodeCoordList().get(0).getIndex());
        assertEquals(1, container.getItems().get(0).getIndex());
        assertEquals(101, container.getItems().get(0).getProfit());
        assertEquals(1, container.getItems().get(0).getWeight());
        assertEquals(2, container.getItems().get(0).getNode());
        assertEquals(52, container.getNodeCoordList().size());
        assertEquals(51, container.getItems().size());
    }

    @Test
    public void testChromosomeGeneration(){
        StringBuilder chromosome = new StringBuilder();
        System.out.println(getRandomChromosomeStringRepresentation(
                chromosomeGenerationService.getRandomChromosome(60)));
    }

    @Test
    public void testProfit(){
        DataInputContainer container = dataExtractionService.getDataInputContainerFromFile(
                dataExtractionService.getDataInputFile("easy_0"));
        System.out.println(optimizationService.getTotalProfitFromChromosome(container,
                chromosomeGenerationService.getRandomChromosome(container.getNodeCoordList().size())));
    }

    @Test
    public void testTotalTime(){
        DataInputContainer container = dataExtractionService.getDataInputContainerFromFile(
                dataExtractionService.getDataInputFile("easy_0"));
        System.out.println(optimizationService.getTotalTimeFromChromosome(container,
                chromosomeGenerationService.getRandomChromosome(container.getNodeCoordList().size())));
    }

    @Test
    public void testMutation(){
        List<Integer> chromosome = chromosomeGenerationService.getRandomChromosome(10);
        System.out.println(getRandomChromosomeStringRepresentation(chromosome));
        System.out.println(getRandomChromosomeStringRepresentation(
                optimizationService.performMutationOperationAndGetMutatedChromosome(chromosome)));
    }

    @Test
    public void testRouletteSelection(){
        parametersService.setDataInputContainer(dataExtractionService.getDataInputContainerFromFile(
                dataExtractionService.getDataInputFile("easy_0")));
        simulationService.populateModel();
        rouletteSelectionService.makeSelection(simulationService.getPopulation());
    }

    @Test
    public void testCrossover(){
        ArrayList<Integer> chromosome =  new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
        ArrayList<Integer> chromosome2 = new ArrayList<>(Arrays.asList(2, 8, 9, 1, 7, 3, 4, 5, 6));
        int i=0;
        List<Integer> output = null;
        while(i<1000){
            output = crossoverService.performPMXAndGetCrossedChromosomes(chromosome, chromosome2);
            i++;
        }
        System.out.println(Arrays.toString(chromosome.toArray()));
        System.out.println(Arrays.toString(chromosome2.toArray()));
        System.out.println(Arrays.toString(output.toArray()));
    }

    @Test
    public void testFitnessNoralized(){
        parametersService.setDataInputContainer(dataExtractionService.getDataInputContainerFromFile(
                dataExtractionService.getDataInputFile("easy_0")));
        simulationService.populateModel();
        List<Individual> population = rouletteSelectionService.makeSelection(simulationService.getPopulation());
        System.out.println(Arrays.toString(simulationService.getPopulation().stream().
                map(Individual::getRouletteSum).toArray()));
    }

    @Test
    public void testGreedySelection(){
        parametersService.setDataInputContainer(dataExtractionService.getDataInputContainerFromFile(
                dataExtractionService.getDataInputFile("easy_0")));
        Individual individual = greedySelectionService.getGreedyBestFitnessChromosome();
        System.out.println(Arrays.toString(individual.getChromosome().toArray()));
        System.out.println(individual.getFitness());
    }

    @Test
    public void testRankSelection(){
        parametersService.setDataInputContainer(dataExtractionService.getDataInputContainerFromFile(
                dataExtractionService.getDataInputFile("easy_0")));
        simulationService.populateModel();
        rankSelectionService.makeSelection(simulationService.getPopulation());
    }



}
