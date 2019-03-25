package pl.guminski.ga.models.treeModel;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.StringProperty;

public class TreeOutputTable extends RecursiveTreeObject<TreeOutputTable> {


    public int generation;

    public double rankFitness;

    public double rankStdDeviation;

    public double rouletteFitness;

    public double rouletteStdDeviation;

    public double greedyFitness;

    public double greedyStdDeviation;

    public double randomFitness;

    public double randomStdDeviation;

}
