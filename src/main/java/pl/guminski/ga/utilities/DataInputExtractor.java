package pl.guminski.ga.utilities;

import pl.guminski.ga.models.dataInput.DataInputContainer;
import pl.guminski.ga.models.dataInput.Item;
import pl.guminski.ga.models.dataInput.NodeCoord;
import pl.guminski.ga.models.dataInput.ThiefData;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataInputExtractor {

    public static DataInputContainer getDataInputContainerFromFile(File file){
        DataInputContainer dataInputContainer = new DataInputContainer();
        try {
            Scanner scanner = new Scanner(file);
            Pattern pattern = Pattern.compile("PROBLEM NAME:\\s+([^\\s]+)");
            Matcher matcher = pattern.matcher(scanner.nextLine());
            if(matcher.find()){
                dataInputContainer.setProblemName(matcher.group(1));
            }
            for(int i=0 ; i<3; i++){
                scanner.nextLine();
            }
            ThiefData thiefData = new ThiefData();
            pattern = Pattern.compile("CAPACITY OF KNAPSACK:\\s+(\\d+)");
            matcher = pattern.matcher(scanner.nextLine());
            if(matcher.find()){
                thiefData.setKnapsackCapacity(Long.parseLong(matcher.group(1)));
                dataInputContainer.setThiefData(thiefData);
            }
            pattern = Pattern.compile("MIN SPEED:\\s+([^\\s]+)");
            matcher = pattern.matcher(scanner.nextLine());
            if(matcher.find()){
                thiefData.setMinSpeed(Double.parseDouble(matcher.group(1)));
            }
            pattern = Pattern.compile("MAX SPEED:\\s+([^\\s]+)");
            matcher = pattern.matcher(scanner.nextLine());
            if(matcher.find()){
                thiefData.setMaxSpeed(Double.parseDouble(matcher.group(1)));
            }
            pattern = Pattern.compile("RENTING RATIO:\\s+([^\\s]+)");
            matcher = pattern.matcher(scanner.nextLine());
            if(matcher.find()){
                thiefData.setRentingRatio(Double.parseDouble(matcher.group(1)));
            }
            for(int i=0 ; i<2; i++){
                scanner.nextLine();
            }
            List<NodeCoord> nodes = new ArrayList<>();
            String line = scanner.nextLine();
            pattern = Pattern.compile("([^\\s]+)\\s+([^\\s]+)\\s+([^\\s]+)");
            while(!line.startsWith("ITEMS")){
                matcher = pattern.matcher(line);
                if(matcher.find()){
                    NodeCoord node = new NodeCoord(
                            Long.parseLong(matcher.group(1)),
                            Double.parseDouble(matcher.group(2)),
                            Double.parseDouble(matcher.group(3)));
                    nodes.add(node);
                }

                line = scanner.nextLine();
            }
            dataInputContainer.setNodeCoordList(nodes);
            pattern = Pattern.compile("([^\\s]+)\\s+([^\\s]+)\\s+([^\\s]+)\\s+([^\\s]+)");
            List<Item> items = new ArrayList<>();
            while(scanner.hasNextLine()){
                line = scanner.nextLine();
                matcher = pattern.matcher(line);
                if(matcher.find()){
                    Item item = new Item(
                            Long.parseLong(matcher.group(1)),
                            Long.parseLong(matcher.group(2)),
                            Long.parseLong(matcher.group(3)),
                            Long.parseLong(matcher.group(4))
                    );
                    items.add(item);
                }
            }
            dataInputContainer.setItems(items);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return dataInputContainer;
    }

}
