import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ExportJPG {

    public static void exportGridToJPG(Component gridPanel, String filePath) {
        int width = gridPanel.getWidth();
        int height = gridPanel.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        gridPanel.paint(g);
        g.dispose();

        try {
            File output = new File(filePath);
            ImageIO.write(image, "jpg", output);
            System.out.println("Matrice exportata la " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
