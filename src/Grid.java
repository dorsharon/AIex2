import java.util.ArrayList;
import java.util.List;

public class Grid {
    private static final int[] DX = {-1, 0, 1, -1, 1, -1, 0, 1};
    private static final int[] DY = {-1, -1, -1, 0, 0, 1, 1, 1};

    private Cell[][] cells;
    private int size;

    public Grid(int size) {
        this.cells = new Cell[size][size];
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public Cell getCell(int i, int j) {
        return cells[i][j];
    }

    public Cell[][] getAllCells() {
        return cells;
    }

    public void setCell(int i, int j, char type) {
        cells[i][j] = new Cell(i, j, type);
    }

    public int getCountOfCellType(CellType cellType) {
        int count = 0;
        for (Cell[] row : cells) {
            for (Cell cell : row) {
                if (cell.cellType == cellType) count++;
            }
        }

        return 0;
    }

    public List<Coordinates> getPossiblePlacements(Player player) {
        List<Coordinates> possible = new ArrayList<>();

        for (Cell[] row : cells) {
            for (Cell cell : row) {
                if (isValidPlacement(player, cell.coordinates)) {
                    possible.add(cell.coordinates);
                }
            }
        }

        return possible;
    }

    public Boolean isValidPlacement(Player player, Coordinates coordinates) {
        int row = coordinates.getRow(), col = coordinates.getCol();

        // If the cell already has a black/white piece on it, it's not a valid placement.
        if (cells[row][col].cellType != CellType.EMPTY) {
            return false;
        }


        /*
        if ((row > 0 && cells[row - 1][col].cellType != CellType.EMPTY) || // Check up
                (row < size - 1 && cells[row + 1][col].cellType != CellType.EMPTY) || // Check down
                (col > 0 && cells[row][col - 1].cellType != CellType.EMPTY) || // Check left
                (col < size - 1 && cells[row][col + 1].cellType != CellType.EMPTY)) { // Check right
            return true;
        }

        if ((row > 0 && col > 0 && cells[row - 1][col - 1].cellType != CellType.EMPTY) || // Check left-up
                (row > 0 && col < size - 1 && cells[row - 1][col + 1].cellType != CellType.EMPTY) || // Check right-up
                (row < size - 1 && col > 0 && cells[row + 1][col - 1].cellType != CellType.EMPTY) || // Check left-down
                (row < size - 1 && col < size - 1 && cells[row + 1][col + 1].cellType != CellType.EMPTY)) { // Check left-up
            return true;
        }

        return false;*/

        // Check if on each side of the given coordinates there's an opponent's piece and then one
        // of the player's pieces. If so, return true.
        for (int i = 0; i < DX.length; i++) {
            // Before checking each direction, reset the variables.
            boolean sawOpponentPiece = false;
            row = coordinates.getRow();
            col = coordinates.getCol();

            for (int j = 0; j < size; j++) {
                // Progress in the currently checked direction
                row += DX[i];
                col += DY[i];

                // If we've stepped outside of the board limits, stop.
                if (row < 0 || row > size - 1 || col < 0 || col > size - 1) {
                    break;
                }

                Cell cell = cells[row][col];
                if (cell.cellType == CellType.EMPTY) {
                    // If the cell is empty, move on.
                    break;
                } else if ((cell.cellType == CellType.BLACK && player != Player.BLACK) ||
                        (cell.cellType == CellType.WHITE && player != Player.WHITE)) {
                    // If you're seeing an opponent piece, mark that you saw.
                    sawOpponentPiece = true;
                } else if (sawOpponentPiece) {
                    // If you've already seen an opponent piece and now see a player's piece, it's a valid placement.
                    return true;
                } else {
                    break;
                }
            }
        }
        return false;
    }
}
