package dataProcessorPackage;

import java.io.File;
import java.util.Objects;

public class TTYInputFileDataReader {

    public File getDataInputFile(String filename){
        File file = null;
        try{
            file = new File(Objects.requireNonNull(getClass().getClassLoader()
                    .getResource("inputData/" + filename + ".ttp"))
                    .getFile());
        }catch (NullPointerException exc){
            System.out.println("Nie znaleziono pliku");
        }
        return file;
    }



}
