import java.io.*;
import java.util.List;

public class java_ex2 {
    public static void main(String[] args) {
        try {
            File file = new File("input.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));
            String s;

            Grid grid = new Grid(5);

            // Define the grid cells
            for (int i = 0; i < 5; i++) {
                s = br.readLine();
                for (int j = 0; j < 5; j++) {
                    grid.setCell(i, j, s.charAt(j));
                }
            }

            List<Coordinates> p = grid.getPossiblePlacements(Player.BLACK);
            for (Coordinates c : p) {
                State state = new State(grid.applyPlacement(Player.BLACK, c), Player.WHITE);
                System.out.println("for " + c.getRow()+"," + c.getCol() + ": " + state.evaluate());
            }

            // Write output file
            file = new File("output.txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            //writer.write(searchResult.toString());
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
