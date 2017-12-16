public class Cell {
    public CellType cellType;
    public Coordinates coordinates;

    public Cell(int row, int col, char cellType) {
        switch (cellType) {
            case 'B':
                this.cellType = CellType.BLACK;
                break;
            case 'W':
                this.cellType = CellType.WHITE;
                break;
            case 'E':
                this.cellType = CellType.EMPTY;
                break;
        }

        this.coordinates = new Coordinates(row, col);
    }
}
