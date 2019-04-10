package pl.guminski.ga.services.csp;

import pl.guminski.ga.models.games.Node;
import pl.guminski.ga.services.csp.games.Rules;

import java.util.ArrayList;
import java.util.List;


public class CSPGameSimulation {

    CSPDataExtractorService dataExtractorService = new CSPDataExtractorService();

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
        if(isValueAlreadyInCell(node)){

        }else if(isLastCell(node) && rules.isConstraintsFulfilled(node.value, node.row, node.column)){
            assignAndCopyBoard(node);
        }else if(isLastCellInRow(node) && rules.isConstraintsFulfilled(node.value, node.row, node.column)){
            assignValueAndCheckNextRowChildren(node);
        }else if(rules.isConstraintsFulfilled(node.value, node.row, node.column)){
            assignAndFindSolution(node);
        }else backtrackCounter++;
    }

    private void assignValueAndCheckNextRowChildren(Node node) {
        rules.board[node.row][node.column] = node.value;
        for (int i = 0; i < rules.board.length; i++) {
            Node childNode = new Node(i + 1, node.row+1, 0);
            solveWithBacktracking(childNode);
        }
        rules.board[node.row][node.column] = 0;
    }

    private boolean isValueAlreadyInCell(Node node) {
        return rules.board[node.column][node.row] != 0;
    }

    private boolean isLastCellInRow(Node node) {
        return node.column == rules.board.length - 1;
    }

    private boolean isLastCell(Node node) {
        return isLastCellInRow(node) && isLastRow(node);
    }

    private boolean isLastRow(Node node) {
        return node.row == rules.board.length-1;
    }

    private void assignAndFindSolution(Node node) {
        rules.board[node.row][node.column] = node.value;
        findSolutionNextChildNodes(node);
        rules.board[node.row][node.column] = 0;
    }

    private void assignAndCopyBoard(Node node) {
        rules.board[node.row][node.column] = node.value;
        copyBoardAndAddToSolution();
        rules.board[node.row][node.column] = 0;
    }

    private void findSolutionNextChildNodes(Node node) {
        for (int i = 0; i < rules.board.length; i++) {
            Node childNode = new Node(i + 1, node.row, node.column + 1);
            solveWithBacktracking(childNode);
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
