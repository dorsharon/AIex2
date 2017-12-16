import java.util.List;

public class State {

    public Grid currentGrid;
    public Player nextPlayerToPlay;

    public State(Grid currentGrid, Player nextPlayerToPlay) {
        this.currentGrid = currentGrid;
        this.nextPlayerToPlay = nextPlayerToPlay;
    }

    public int getMinMax() {
        List<Coordinates> possiblePlacements = currentGrid.getPossiblePlacements(nextPlayerToPlay);
        int minMaxValue = nextPlayerToPlay == Player.BLACK ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        // Go over all the possible placements for the next player to play
        for (Coordinates c : possiblePlacements) {
            // Evaluate the state of each possible placement.
            State state = new State(currentGrid.applyPlacement(Player.BLACK, c), Player.WHITE);
            int stateEvaluation = state.evaluate();

            System.out.println("for " + c.getRow() + "," + c.getCol() + ": " + state.evaluate());

            // Get the min/max value based on which player's turn is next.
            if (nextPlayerToPlay == Player.BLACK && minMaxValue < stateEvaluation) {
                minMaxValue = stateEvaluation;
            } else if (nextPlayerToPlay == Player.WHITE && minMaxValue > stateEvaluation) {
                minMaxValue = stateEvaluation;
            }
        }

        return minMaxValue;
    }

    public int evaluate() {
        Boolean gameOver = currentGrid.getCountOfCellType(CellType.EMPTY) == 0;
        int blackCount = currentGrid.getCountOfCellType(CellType.BLACK);
        int whiteCount = currentGrid.getCountOfCellType(CellType.WHITE);

        if (gameOver) {
            // If the game is over (board is full)
            if (blackCount == whiteCount) return 0;
            else return blackCount > whiteCount ? Integer.MAX_VALUE : Integer.MIN_VALUE;
        } else {
            return (blackCount - whiteCount) + currentGrid.getCountOfCellTypeOnEdges(CellType.BLACK);
        }
    }
}
