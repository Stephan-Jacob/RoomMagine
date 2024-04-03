import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ExportTXT {

    public static void exportGridToTXT(int marimeMatrice, String[][] matrice, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(String.valueOf(marimeMatrice));
            writer.newLine();

            for (int i = 0; i < marimeMatrice; i++) {
                for (int j = 0; j < marimeMatrice; j++) {
                    if(matrice[i][j] == null)
                    {
                        writer.write("0");
                    }
                    else
                    {
                        writer.write(matrice[i][j]);
                    }
                    if (j < marimeMatrice - 1) {
                        writer.write(",");
                    }
                }
                writer.newLine();
            }

            System.out.println("Grid exportat la " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}