package pl.guminski.ga.services.geneticAlgorithm;

import org.springframework.stereotype.Service;

import java.math.RoundingMode;
import java.text.DecimalFormat;

@Service
public class FormatService {

    public DecimalFormat decimalFormat;

    public DecimalFormat integerFormat;

    public void setDecimalFormat(){
        decimalFormat = new DecimalFormat("#,##0.000");
        decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
    }

    public void setIntegerFormat(){
        integerFormat = new DecimalFormat("#,##0");
        integerFormat.setRoundingMode(RoundingMode.HALF_UP);
    }

}
