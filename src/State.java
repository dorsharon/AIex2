import java.util.List;

public class State {

    public Grid currentGrid;
    public Player nextPlayerToPlay;

    public State(Grid currentGrid, Player nextPlayerToPlay) {
        this.currentGrid = currentGrid;
        this.nextPlayerToPlay = nextPlayerToPlay;
    }

    public int getMinMax(int depth) {
        List<Coordinates> possiblePlacements = currentGrid.getPossiblePlacements(nextPlayerToPlay);
        int minMaxValue = nextPlayerToPlay == Player.BLACK ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        // Go over all the possible placements for the next player to play
        for (Coordinates placement : possiblePlacements) {
            // Calculate the new grid after the possible placement
            Grid newGrid = currentGrid.applyPlacement(nextPlayerToPlay, placement);
            System.out.println(newGrid);

            // Generate a child state based on that placement
            State childState = new State(newGrid, nextPlayerToPlay == Player.BLACK ? Player.WHITE : Player.BLACK);

            // Evaluate the child state.
            int stateEvaluation = childState.evaluate();

            System.out.println("for " + placement.getRow() + "," + placement.getCol() + ": " + childState.evaluate());

            // Get the min/max value based on which player's turn is next.
            if (nextPlayerToPlay == Player.BLACK && minMaxValue < stateEvaluation) {
                // If you haven't reached last depth yet, keep checking child states.
                minMaxValue = depth > 0 ? childState.getMinMax(depth - 1) : stateEvaluation;
            } else if (nextPlayerToPlay == Player.WHITE && minMaxValue > stateEvaluation) {
                minMaxValue = depth > 0 ? childState.getMinMax(depth - 1) : stateEvaluation;
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
