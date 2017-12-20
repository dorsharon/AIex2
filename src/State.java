import java.util.List;

public class State {

    public Grid currentGrid;
    public Player nextPlayerToPlay;

    public State(Grid currentGrid, Player nextPlayerToPlay) {
        this.currentGrid = currentGrid;
        this.nextPlayerToPlay = nextPlayerToPlay;
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
