package pl.guminski.ga.services.futoshiki;

import org.springframework.stereotype.Service;
import pl.guminski.ga.models.futoshiki.FutoshikiItem;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

@Service
public class FutoshikiDataExtractor {

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
            file = new File(Objects.requireNonNull(FutoshikiDataExtractor.class.getClassLoader()
                    .getResource("cspData/" + filename + ".txt"))
                    .getFile());
            file = new File(file.getAbsolutePath().replace("%20", " "));
        }catch (NullPointerException exc){
            System.out.println("Nie znaleziono pliku");
        }
        return file;
    }

}
