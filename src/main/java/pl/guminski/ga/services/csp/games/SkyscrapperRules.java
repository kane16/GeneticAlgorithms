package pl.guminski.ga.services.csp.games;

import pl.guminski.ga.models.games.Node;

import java.util.Arrays;
import java.util.List;

public class SkyscrapperRules extends Rules{

    public List<Integer> bottom;
    public List<Integer> top;
    public List<Integer> left;
    public List<Integer> right;

    public SkyscrapperRules(List<Integer> bottom, List<Integer> top, List<Integer> left,
                            List<Integer> right, int[][] board){
        this.bottom = bottom;
        this.left = left;
        this.top = top;
        this.right = right;
        this.board = board;
    }

    public boolean isConstraintsFulfilled(Node node){
        int bottomConstraint = bottom.get(node.column);
        int topConstraint = top.get(node.column);
        int leftConstraint = left.get(node.row);
        int rightConstraint = right.get(node.row);

        for(int i=0 ; i<board.length ; i++){
            if(node.value == board[node.row][i]){
                return false;
            }
            if(node.value == board[i][node.column]){
                return false;
            }
        }
        int[][] newBoard = new int[board.length][board.length];
        for(int i=0; i<board.length; i++){
            newBoard[i] = board[i].clone();
        }
        newBoard[node.row][node.column]=node.value;
        if(!isTopConstraintFulfilled(topConstraint, newBoard, node.value, node.column, node.row)
        || !isBottomConstraintFulfilled(bottomConstraint, newBoard, node.value, node.column, node.row)
        || !isLeftConstraintFulfilled(leftConstraint, newBoard, node.value, node.column, node.row)
        || !isRightConstraintFulfilled(rightConstraint, newBoard, node.value, node.column, node.row)) {
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
