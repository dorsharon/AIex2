import java.util.List;

public class MinMax {
    private int initialDepth;

    public MinMax(int initialDepth) {
        this.initialDepth = initialDepth;
    }

    public Player getPredictedWinner(State state) {
        State currentState = state;
        int stateEval = currentState.evaluate();
        Boolean maximizingPlayer = true;

        // While the game isn't ovver
        while (stateEval != Integer.MAX_VALUE && stateEval != Integer.MIN_VALUE) {
            // Play the best possible move
            Coordinates bestPlacement = getBestPlacement(currentState);
            Grid newGrid = currentState.currentGrid.applyPlacement(currentState.nextPlayerToPlay, bestPlacement);

            // Move onto the next turn in the game
            Player newPlayer = currentState.nextPlayerToPlay == Player.BLACK ? Player.WHITE : Player.BLACK;
            currentState = new State(newGrid, newPlayer);
            maximizingPlayer = !maximizingPlayer;
            stateEval = currentState.evaluate();
        }

        // Return the winner
        return stateEval == Integer.MAX_VALUE ? Player.BLACK : Player.WHITE;
    }

    public Coordinates getBestPlacement(State state) {
        List<Coordinates> possiblePlacements = state.currentGrid.getPossiblePlacements();
        Coordinates bestPlacement = null;
        Boolean maximizingPlayer = state.nextPlayerToPlay == Player.BLACK;
        Boolean valueInitialized = false;
        int value = 0;

        for (Coordinates placement : possiblePlacements) {
            // Calculate the new grid after the possible placement
            Grid newGrid = state.currentGrid.applyPlacement(state.nextPlayerToPlay, placement);

            // Generate a child state based on that placement
            State childState = new State(newGrid,
                    state.nextPlayerToPlay == Player.BLACK ? Player.WHITE : Player.BLACK);

            // Calculate the minmax value on the next depth level
            int result = getMinMax(childState, initialDepth - 1, !maximizingPlayer);

            if (!valueInitialized ||
                    (maximizingPlayer && value < result) ||
                    (!maximizingPlayer && value > result)) {
                value = result;
                bestPlacement = placement;
                valueInitialized = true;
            }
        }
        return bestPlacement;
    }

    public int getMinMax(State state, int depth, Boolean maximizingPlayer) {
        Grid currentGrid = state.currentGrid;
        Player nextPlayerToPlay = state.nextPlayerToPlay;

        if (depth == 0 ||
                state.evaluate() == Integer.MAX_VALUE ||
                state.evaluate() == Integer.MIN_VALUE)
            return state.evaluate();

        List<Coordinates> possiblePlacements = currentGrid.getPossiblePlacements();
        int result = nextPlayerToPlay == Player.BLACK ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        Boolean valueInitialized = false;

        // Go over all the possible placements for the next player to play
        for (Coordinates placement : possiblePlacements) {
            // Calculate the new grid after the possible placement
            Grid newGrid = currentGrid.applyPlacement(nextPlayerToPlay, placement);

            // Generate a child state based on that placement
            State childState = new State(newGrid, nextPlayerToPlay == Player.BLACK ? Player.WHITE : Player.BLACK);

            // Get the min/max value based on which player's turn is next.
            int childMinMax = getMinMax(childState, depth - 1, !maximizingPlayer);

            if(depth==1) {
                System.out.println(newGrid);
                System.out.println("Placement: " +
                        (nextPlayerToPlay == Player.BLACK ? "BLACK " : "WHITE ") +
                        (maximizingPlayer ? "MAX " : "MIN ") +
                        +placement.getRow() + "," + placement.getCol() + "\n" +
                        "Evaluation: " + childState.evaluate() + "\n" +
                        "Depth: " + depth + "\n\n");
            }

            if (!valueInitialized ||
                    (maximizingPlayer && result < childMinMax) ||
                    (!maximizingPlayer && result > childMinMax)) {
                // If you found a better placement, save it.
                result = childMinMax;
                valueInitialized = true;
            }
        }

        return result;
    }
}
