import java.io.*;

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

            System.out.println(grid.getPossiblePlacements());

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
