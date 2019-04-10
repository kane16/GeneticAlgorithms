package pl.guminski.ga.services.csp.games;

public abstract class Rules {

    public int[][] board;

    public abstract boolean isConstraintsFulfilled(int value, int row, int column);

}
