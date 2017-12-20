import java.util.List;

public class MinMax {

    public static Player getPredictedWinner(State state) {
        State currentState = state;
        int stateEval = currentState.evaluate();

        while (stateEval != Integer.MAX_VALUE && stateEval != Integer.MIN_VALUE) {
            Coordinates bestPlacement = getBestPlacement(state);
            Player newPlayer = currentState.nextPlayerToPlay == Player.BLACK ? Player.WHITE : Player.BLACK;
            Grid newGrid = currentState.currentGrid.applyPlacement(newPlayer, bestPlacement);

            currentState = new State(newGrid, newPlayer);
            stateEval = currentState.evaluate();
        }

        return currentState.nextPlayerToPlay;
    }

    public static Coordinates getBestPlacement(State state) {
        int minmax = getMinMax(state, 3);
        List<Coordinates> possiblePlacements = state.currentGrid.getPossiblePlacements();

        for (Coordinates placement : possiblePlacements) {
            State childState = new State(
                    state.currentGrid.applyPlacement(state.nextPlayerToPlay, placement),
                    state.nextPlayerToPlay == Player.BLACK
                            ? Player.WHITE
                            : Player.BLACK);
            if (childState.evaluate() == minmax) return placement;
        }

        return null;
    }

    public static int getMinMax(State state, int depth) {
        if (depth == 1) {
            return state.evaluate();
        }

        Grid currentGrid = state.currentGrid;
        Player nextPlayerToPlay = state.nextPlayerToPlay;

        List<Coordinates> possiblePlacements = currentGrid.getPossiblePlacements();
        int result =
                nextPlayerToPlay == Player.BLACK
                        ? Integer.MIN_VALUE
                        : Integer.MAX_VALUE;

        // Go over all the possible placements for the next player to play
        for (Coordinates placement : possiblePlacements) {
            // Calculate the new grid after the possible placement
            Grid newGrid = currentGrid.applyPlacement(nextPlayerToPlay, placement);
            System.out.println(newGrid);

            // Generate a child state based on that placement
            State childState = new State(newGrid, nextPlayerToPlay == Player.BLACK ? Player.WHITE : Player.BLACK);

            // Get the min/max value based on which player's turn is next.
            int childMinMax = getMinMax(childState, depth - 1);

            System.out.println("for " + placement.getRow() + "," + placement.getCol() + ": " + childState.evaluate());

            if ((nextPlayerToPlay == Player.BLACK && result < childMinMax) ||
                    (nextPlayerToPlay == Player.WHITE && result > childMinMax)) {
                // If you found a better placement, save it.
                result = childMinMax;
            }
        }

        return result;
    }
}
