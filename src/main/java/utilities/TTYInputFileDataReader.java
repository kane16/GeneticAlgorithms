package utilities;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class TTYInputFileDataReader {

    public static List<File> getAllFilesFromInputDataFolder(){
        File file = null;
        try{
            file = new File(Objects.requireNonNull(TTYInputFileDataReader.class.getClassLoader()
                    .getResource("inputData"))
                    .getFile());
            file = new File(file.getAbsolutePath().replace("%20", " "));
        }catch (NullPointerException exc){
            System.out.println("Nie znaleziono folderu");
        }
        return Arrays.asList(file.listFiles());
    }

    public static File getDataInputFile(String filename){
        File file = null;
        try{
            file = new File(Objects.requireNonNull(TTYInputFileDataReader.class.getClassLoader()
                    .getResource("inputData/" + filename + ".ttp"))
                    .getFile());
            file = new File(file.getAbsolutePath().replace("%20", " "));
        }catch (NullPointerException exc){
            System.out.println("Nie znaleziono pliku");
        }
        return file;
    }



}
