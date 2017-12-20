import java.util.List;

public class MinMax {

    public static MinMaxResult getBestPlacement(State state, int depth) {
        Grid currentGrid = state.currentGrid;
        Player nextPlayerToPlay = state.nextPlayerToPlay;

        List<Coordinates> possiblePlacements = currentGrid.getPossiblePlacements(nextPlayerToPlay);
        MinMaxResult result = new MinMaxResult(
                nextPlayerToPlay == Player.BLACK
                        ? Integer.MIN_VALUE
                        : Integer.MAX_VALUE,
                new Coordinates());

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
            if ((nextPlayerToPlay == Player.BLACK && result.value < stateEvaluation) ||
                    (nextPlayerToPlay == Player.WHITE && result.value > stateEvaluation)) {
                // If you haven't reached last depth yet, keep checking child states.
                result.value = depth > 0 ? getBestPlacement(childState, depth - 1).value : stateEvaluation;
                result.coordinates = placement;
            }
        }

        return result;
    }
}
