import java.util.List;

public class MinMax {

    public static Player getPredictedWinner(State state) {
        State currentState = state;
        int stateEval = currentState.evaluate();

        while (stateEval != Integer.MAX_VALUE && stateEval != Integer.MIN_VALUE) {
            Coordinates bestPlacement = getBestPlacement(currentState);
            Player newPlayer = currentState.nextPlayerToPlay == Player.BLACK ? Player.WHITE : Player.BLACK;
            Grid newGrid = currentState.currentGrid.applyPlacement(newPlayer, bestPlacement);

            currentState = new State(newGrid, newPlayer);
            stateEval = currentState.evaluate();
        }

        return currentState.nextPlayerToPlay;
    }

    public static Coordinates getBestPlacement(State state) {
        // Get the minmax value for the next 3 turns
        MinMaxResult result = getMinMax(state, 3);
        List<Coordinates> possiblePlacements = state.currentGrid.getPossiblePlacements();

        /*
        // Go over all possible placements and return the first one with that minmax value
        for (Coordinates placement : possiblePlacements) {
            State childState = new State(
                    state.currentGrid.applyPlacement(state.nextPlayerToPlay, placement),
                    state.nextPlayerToPlay == Player.BLACK
                            ? Player.WHITE
                            : Player.BLACK);
            if (childState.evaluate() == result.value) return placement;
        }*/

        return result.coordinates;
    }

    public static MinMaxResult getMinMax(State state, int depth) {
        return getMinMax(state, depth, null);
    }

    public static MinMaxResult getMinMax(State state, int depth, Coordinates currPlacement) {
        if (depth == 1) {
            MinMaxResult result = new MinMaxResult();
            result.value = state.evaluate();
            result.coordinates = currPlacement;
            return result;
        }

        Grid currentGrid = state.currentGrid;
        Player nextPlayerToPlay = state.nextPlayerToPlay;

        List<Coordinates> possiblePlacements = currentGrid.getPossiblePlacements();
        MinMaxResult result = new MinMaxResult();
        result.value = nextPlayerToPlay == Player.BLACK ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        // Go over all the possible placements for the next player to play
        for (Coordinates placement : possiblePlacements) {
            // Calculate the new grid after the possible placement
            Grid newGrid = currentGrid.applyPlacement(nextPlayerToPlay, placement);

            // Generate a child state based on that placement
            State childState = new State(newGrid, nextPlayerToPlay == Player.BLACK ? Player.WHITE : Player.BLACK);

            // Get the min/max value based on which player's turn is next.
            MinMaxResult childMinMax = getMinMax(childState, depth - 1, placement);

            System.out.println(newGrid);
            System.out.println("Placement: " + placement.getRow() + "," + placement.getCol() + " Evaluation: " + childState.evaluate());

            if ((nextPlayerToPlay == Player.BLACK && result.value < childMinMax.value) ||
                    (nextPlayerToPlay == Player.WHITE && result.value > childMinMax.value)) {
                // If you found a better placement, save it.
                result.value = childMinMax.value;
                result.coordinates = childMinMax.coordinates;
            }
        }

        return result;
    }

    public static class MinMaxResult {
        public int value;
        public Coordinates coordinates;

        public MinMaxResult() {
        }
    }
}
