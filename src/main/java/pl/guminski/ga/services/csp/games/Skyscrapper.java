package pl.guminski.ga.services.csp.games;

import java.util.Arrays;
import java.util.List;

public class Skyscrapper {

    public boolean isConstraintsFulfilled(List<Integer> bottom, List<Integer> top, List<Integer> left,
                                          List<Integer> right, int[][] board, int value, int row, int column){
        int bottomConstraint = bottom.get(column);
        int topConstraint = top.get(column);
        int leftConstraint = left.get(row);
        int rightConstraint = right.get(row);

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
        newBoard[row][column]=value;
        if(!isTopConstraintFulfilled(topConstraint, newBoard, value, column, row)
        || !isBottomConstraintFulfilled(bottomConstraint, newBoard, value, column, row)
        || !isLeftConstraintFulfilled(leftConstraint, newBoard, value, column, row)
        || !isRightConstraintFulfilled(rightConstraint, newBoard, value, column, row)) {
            return false;
        }
        board = newBoard;
        return true;
    }

    private boolean isBottomConstraintFulfilled(int bottomConstraint, int[][] board, int value, int column, int row) {
        if(bottomConstraint == 0){
            return true;
        }
        int maxValue = 0;
        int visible = 0;
        for (int i=board.length-1; i>=0 ; i--) {
            if (board[i][column] == 0) {
                return true;
            }
            if (board[i][column] > maxValue) {
                visible++;
                maxValue = board[i][column];
            }
            if (visible > bottomConstraint) {
                return false;
            }
        }
        return visible == bottomConstraint;
    }

    private boolean isTopConstraintFulfilled(int topConstraint, int[][] board, int value, int column, int row) {
        if(topConstraint == 0){
            return true;
        }
        int maxValue = 0;
        int visible = 0;
        for (int i=0; i<board.length; i++) {
            if (board[i][column] == 0) {
                return true;
            }
            if (board[i][column] > maxValue) {
                visible++;
                maxValue = board[i][column];
            }
            if (visible > topConstraint) {
                return false;
            }
        }
        return visible == topConstraint;
    }

    private boolean isLeftConstraintFulfilled(int leftConstraint, int[][] board, int value, int column, int row){
        if(leftConstraint == 0){
            return true;
        }
        int maxValue = 0;
        int visible = 0;
        for(int i=0; i<board.length; i++){
            if(board[row][i] == 0){
                return true;
            }
            if(board[row][i] > maxValue){
                visible++;
                maxValue = board[row][i];
            }
            if(visible > leftConstraint){
                return false;
            }
        }
        return visible == leftConstraint;
    }

    private boolean isRightConstraintFulfilled(int rightConstraint, int[][] board, int value, int column, int row){
        if(rightConstraint == 0){
            return true;
        }
        int maxValue = 0;
        int visible = 0;
        boolean wasZero = false;
        for(int i=board.length-1; i>=0; i--){
            if(board[row][i] == 0){
                wasZero = true;
            }
            if(board[row][i] > maxValue){
                visible++;
                maxValue = board[row][i];
            }
            if(visible > rightConstraint){
                return false;
            }
        }
        return visible == rightConstraint || wasZero;
    }



}
