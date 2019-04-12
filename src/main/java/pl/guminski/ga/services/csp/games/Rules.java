package pl.guminski.ga.services.csp.games;

import pl.guminski.ga.models.games.Node;

public abstract class Rules {

    public int[][] board;

    public abstract boolean isConstraintsFulfilled(Node node);

}
