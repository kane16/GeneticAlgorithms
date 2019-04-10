package pl.guminski.ga.services.csp.games;

import java.util.List;

public class FutoshikiRules extends Rules {

    public List<String> constraints;

    public FutoshikiRules(List<String> constraints, int[][] board){
        this.constraints = constraints;
        this.board = board;
    }

    public boolean isConstraintsFulfilled(int value, int row, int column){
        for(int i=0 ; i<board.length ; i++){
            if(value == board[row][i]){
                return false;
            }
            if(value == board[i][column]){
                return false;
            }
        }
        int[][] newBoard = new int[board.length][board.length];
        for(int i=0; i<board.length; i++){
            newBoard[i] = board[i].clone();
        }
        newBoard[row][column] = value;
        if(constraints.stream().anyMatch(constraint -> !isConstraintFulfilled(constraint, newBoard))) {
            return false;
        }
        return true;
    }

    private boolean isConstraintFulfilled(String constraint, int[][] board) {
        int value1 = board[Character.getNumericValue(constraint.charAt(0))-1]
                [Character.getNumericValue(constraint.charAt(1))-1];
        int value2 = board[Character.getNumericValue(constraint.charAt(3))-1]
                [Character.getNumericValue(constraint.charAt(4))-1];
        if(value1 == 0 || value2 ==0){
            return true;
        }
        return value1 < value2;
    }

}
