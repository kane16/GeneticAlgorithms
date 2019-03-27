package pl.guminski.ga.services.futoshiki;

import org.springframework.stereotype.Service;
import pl.guminski.ga.models.games.FutoshikiItem;
import pl.guminski.ga.models.games.SkyscraperItem;
import pl.guminski.ga.services.geneticAlgorithm.DataExtractionService;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CSPDataExtractorService {

    public SkyscraperItem getScascraperItemFromFile(File file) throws FileNotFoundException {
        SkyscraperItem skyscraperItem = new SkyscraperItem();
        Scanner sc = new Scanner(file);
        skyscraperItem.size = sc.nextInt();
        sc.nextLine();
        for(int i=0;i<4;i++){
            String[] items = sc.nextLine().split(";");
            List<Integer> boundValues = new ArrayList<>();
            for(int j=1;j<items.length;j++){
                boundValues.add(Integer.parseInt(items[j]));
            }
            switch (items[0]) {
                case "G":
                    skyscraperItem.topBound = boundValues;
                    break;
                case "D":
                    skyscraperItem.bottomBound = boundValues;
                    break;
                case "P":
                    skyscraperItem.rightBound = boundValues;
                    break;
                case "L":
                    skyscraperItem.leftBound = boundValues;
                    break;
            }
        }
        return skyscraperItem;
    }

    public FutoshikiItem getFutoshikiItemFromFile(File file) throws FileNotFoundException {
        FutoshikiItem futoshikiItem = new FutoshikiItem();
        Scanner sc = new Scanner(file);
        futoshikiItem.maxNumber = sc.nextInt();
        sc.nextLine();
        sc.nextLine();
        List<List<Integer>> futoshikiContent = new ArrayList<>(futoshikiItem.maxNumber);
        for(int i=0; i<futoshikiItem.maxNumber; i++){
            List<Integer> futoshikiRow = new ArrayList<>(futoshikiItem.maxNumber);
            String nextLine = sc.nextLine();
            String[] numbers = nextLine.split(";");
            for(int j=0 ; j<numbers.length; j++){
                futoshikiRow.add(Integer.parseInt(numbers[j]));
            }
            futoshikiContent.add(futoshikiRow);
        }
        sc.nextLine();
        futoshikiItem.contentTable = futoshikiContent;
        Map<Integer, String> constraints = new HashMap<>();
        int i=0;
        while(sc.hasNextLine()){
            constraints.put(i, sc.nextLine());
            i++;
        }
        futoshikiItem.constraints = constraints;
        return futoshikiItem;
    }

    public File getDataInputFile(String filename){
        File file = null;
        try{
            file = new File(Objects.requireNonNull(CSPDataExtractorService.class.getClassLoader()
                    .getResource("cspData/" + filename + ".txt"))
                    .getFile());
            file = new File(file.getAbsolutePath().replace("%20", " "));
        }catch (NullPointerException exc){
            System.out.println("Nie znaleziono pliku");
        }
        return file;
    }

    public List<String> getAllFilesOfGameFromInputDataFolder(String gameName){
        File file = null;
        try{
            file = new File(Objects.requireNonNull(DataExtractionService.class.getClassLoader()
                    .getResource("cspData"))
                    .getFile());
            file = new File(file.getAbsolutePath().replace("%20", " "));
        }catch (NullPointerException exc){
            System.out.println("Nie znaleziono folderu");
        }
        return Arrays.stream(file.listFiles())
                .filter(scenario -> scenario.getName().contains(gameName))
                .map(scenario -> scenario.getName().replace(".txt", ""))
                .collect(Collectors.toList());
    }

}
