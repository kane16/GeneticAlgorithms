package pl.guminski.ga.services.futoshiki;

import org.springframework.stereotype.Service;
import pl.guminski.ga.models.futoshiki.FutoshikiItem;
import pl.guminski.ga.services.geneticAlgorithm.DataExtractionService;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CSPDataExtractorService {

    public FutoshikiItem getFutoshikiItemFromFile(File file) throws FileNotFoundException {
        FutoshikiItem futoshikiItem = new FutoshikiItem();
        Scanner sc = new Scanner(file);
        futoshikiItem.maxNumber = sc.nextInt();
        sc.nextLine();
        sc.nextLine();
        String[][] futoshikiContent = new String[futoshikiItem.maxNumber][futoshikiItem.maxNumber];
        for(int i=0; i<futoshikiItem.maxNumber; i++){
            String nextLine = sc.nextLine();
            String[] numbers = nextLine.split(";");
            futoshikiContent[i] = numbers;
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
