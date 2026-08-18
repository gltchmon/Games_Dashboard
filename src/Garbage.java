import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Garbage {


    private void makeIcon(String imgPath, JButton button){
        BufferedImage icon =  new BufferedImage(20,20,BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = icon.createGraphics();
        try {
            generateImage(imgPath, g);
        } catch (IOException e) {
            System.err.println("Could not generate image: \n" + e.getMessage());
        }
        button.setIcon(new ImageIcon(icon));

    }

    private void generateImage(String imgPath, Graphics2D g) throws IOException {
        BufferedImage image = ImageIO.read(new File(imgPath));
        System.out.println(image);
        g.drawImage(image,0,0,null);
    }
}
