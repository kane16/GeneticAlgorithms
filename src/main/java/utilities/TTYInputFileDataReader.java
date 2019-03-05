package utilities;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class TTYInputFileDataReader {

    public List<File> getAllFilesFromInputDataFolder(){
        File file = null;
        try{
            file = new File(Objects.requireNonNull(getClass().getClassLoader()
                    .getResource("inputData"))
                    .getFile());
            file = new File(file.getAbsolutePath().replace("%20", " "));
        }catch (NullPointerException exc){
            System.out.println("Nie znaleziono folderu");
        }
        return Arrays.asList(file.listFiles());
    }

    public File getDataInputFile(String filename){
        File file = null;
        try{
            file = new File(Objects.requireNonNull(getClass().getClassLoader()
                    .getResource("inputData/" + filename + ".ttp"))
                    .getFile());
            file = new File(file.getAbsolutePath().replace("%20", " "));
        }catch (NullPointerException exc){
            System.out.println("Nie znaleziono pliku");
        }
        return file;
    }



}
