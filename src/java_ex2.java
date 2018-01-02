import java.io.*;

public class java_ex2 {
    public static void main(String[] args) {
        try {
            File file = new File("input2.txt");
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

            // Calculate minmax algorithm
            State state = new State(grid, Player.BLACK);
            MinMax minMax = new MinMax(3);
            Player predictedWinner = minMax.getPredictedWinner(state);

            // Write output file
            file = new File("output.txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));

            // Write 'B' or 'W' based on whether you got MAX_VALUE or MIN_VALUE
            writer.write(predictedWinner == Player.BLACK ? "B" : predictedWinner == Player.WHITE ? "W" : "");
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
