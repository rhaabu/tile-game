package game;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Spritesheet {
    private String path;
    private BufferedImage image;

    public static Spritesheet textures = new Spritesheet("/textures/Spritesheet.png");

    private Spritesheet(String path) {
        this.path = path;
        load(path);
    }

    private void load(String path){
        try {
            image = ImageIO.read(this.getClass().getResource(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BufferedImage getImage() {
        return image;
    }
}
