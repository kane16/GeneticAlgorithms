package pl.guminski.ga.services.geneticAlgorithm;

import javafx.stage.FileChooser;
import javafx.stage.Window;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class ExcelExportService {


    private File exportFile;

    @Autowired
    SimulationService simulationService;

    public void chooseDirectory(Window window){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Zapisz dane do arkusza");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Arkusz excel", "*.xlsx"));
        this.exportFile = fileChooser.showSaveDialog(window);
    }

    public void exportToExcel(){
        String path = URLDecoder.decode(getClass().getResource("/inputData/template.xlsx").getPath(),
                StandardCharsets.UTF_8);
        File old = new File(path);
        try {
            Workbook workbook = new XSSFWorkbook(old.getAbsolutePath().replace("%20", " "));
            Sheet sheetOutput = workbook.getSheet("Algorytmy");
            exportOutputToExcel(sheetOutput);
            FileOutputStream fileOutputStream = new FileOutputStream(this.exportFile);
            workbook.write(fileOutputStream);
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void exportOutputToExcel(Sheet sheetOutput) {
        int row = 1;

        for(int i=0; i<simulationService.rankAlgorithmBestIndividuals.size();i++){
            sheetOutput.getRow(row).getCell(0).setCellValue(i);
            sheetOutput.getRow(row).getCell(1).setCellValue(simulationService.tournamentAlgorithmBestIndividuals.get(i).getFitness());
            sheetOutput.getRow(row).getCell(2).setCellValue(simulationService.tournamentAlgorithmBestIndividuals.get(i).getStdDeviation());
            sheetOutput.getRow(row).getCell(3).setCellValue(simulationService.rankAlgorithmBestIndividuals.get(i).getFitness());
            sheetOutput.getRow(row).getCell(4).setCellValue(simulationService.rankAlgorithmBestIndividuals.get(i).getStdDeviation());
            sheetOutput.getRow(row).getCell(5).setCellValue(simulationService.rouletteAlgorithmBestIndividuals.get(i).getFitness());
            sheetOutput.getRow(row).getCell(6).setCellValue(simulationService.rouletteAlgorithmBestIndividuals.get(i).getStdDeviation());
            sheetOutput.getRow(row).getCell(7).setCellValue(simulationService.greedyBestIndividual.get(i).getFitness());
            sheetOutput.getRow(row).getCell(8).setCellValue(simulationService.greedyBestIndividual.get(i).getStdDeviation());
            sheetOutput.getRow(row).getCell(9).setCellValue(simulationService.randomBestIndividual.get(i).getFitness());
            sheetOutput.getRow(row).getCell(10).setCellValue(simulationService.randomBestIndividual.get(i).getStdDeviation());

            row++;
        }
    }

}

