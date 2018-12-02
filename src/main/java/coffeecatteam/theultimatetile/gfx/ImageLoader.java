package coffeecatteam.theultimatetile.gfx;

import coffeecatteam.coffeecatutils.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class ImageLoader {

    public static BufferedImage loadImage(String path) {
        try {
            URL file = ImageLoader.class.getResource(path);
            if (file == null)
                file = ImageLoader.class.getResource("/assets/textures/missing.png");
            return ImageIO.read(file);
        } catch (IOException e) {
            Logger.print(e);
        }
        return null;
    }
}
