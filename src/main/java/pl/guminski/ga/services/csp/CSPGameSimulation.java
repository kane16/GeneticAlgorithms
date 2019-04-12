package pl.guminski.ga.services.csp;

import pl.guminski.ga.models.games.Node;
import pl.guminski.ga.services.csp.games.Rules;

import java.util.ArrayList;
import java.util.List;


public class CSPGameSimulation {

    Rules rules;

    List<int[][]> solutions = new ArrayList<>();

    int backtrackCounter;

    public CSPGameSimulation(Rules rules){
        this.rules = rules;
    }

    public List<int[][]> runGameAndFindSolutions(){

        runBactrackingAlgorithm();

        return solutions;
    }

    public void runBactrackingAlgorithm(){
        for(int i=0 ; i<rules.board.length ;i++){
            solveWithBacktracking(new Node(i+1, 0, 0));
        }
    }

    private void solveWithBacktracking(Node node) {
        if(isLastCell(node.column, node.row) && rules.isConstraintsFulfilled(node)){
            assignAndCopyBoard(node);
        }else if(rules.isConstraintsFulfilled(node)){
            assignAndFindSolution(node);
        }else backtrackCounter++;
    }

    private void assignAndFindSolution(Node node) {
        rules.board[node.row][node.column] = node.value;
        getNextFreeCellSolution(node.row, node.column);
        rules.board[node.row][node.column] = 0;
    }

    private void getNextFreeCellSolution(int currentRow, int currentColumn){
        if(isValueAlreadyInCell(currentColumn, currentRow) && isLastCell(currentColumn, currentRow)){
            copyBoardAndAddToSolution();
        }else if(isValueAlreadyInCell(currentColumn, currentRow) && isLastCellInRow(currentColumn)){
            getNextFreeCellSolution(currentRow+1, 0);
        }else if(!isValueAlreadyInCell(currentColumn, currentRow)) {
            findRecursivelyCellSolution(currentRow, currentColumn);
        }else getNextFreeCellSolution(currentRow, currentColumn+1);
    }

    private boolean isValueAlreadyInCell(int column, int row) {
        return rules.board[row][column] != 0;
    }

    private boolean isLastCellInRow(int column) {
        return column == rules.board.length - 1;
    }

    private boolean isLastCell(int column, int row) {
        return isLastCellInRow(column) && isLastRow(row);
    }

    private boolean isLastRow(int row) {
        return row == rules.board.length-1;
    }

    private void assignAndCopyBoard(Node node) {
        rules.board[node.row][node.column] = node.value;
        copyBoardAndAddToSolution();
        rules.board[node.row][node.column] = 0;
    }

    private void findRecursivelyCellSolution(int row, int column) {
        for (int i = 0; i < rules.board.length; i++) {
            Node node = new Node(i + 1, row, column);
            solveWithBacktracking(node);
        }
    }

    private void copyBoardAndAddToSolution() {
        int[][] solutionBoard = new int[rules.board.length][rules.board.length];
        for (int i = 0; i < rules.board.length; i++) {
            solutionBoard[i] = rules.board[i].clone();
        }
        solutions.add(solutionBoard);
    }

}
