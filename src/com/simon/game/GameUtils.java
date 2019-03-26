package com.simon.game;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

public class GameUtils {
    // 工具类最好将构造器私有化。
    private GameUtils() {

    }

    public static Image getImage(String path) {
        BufferedImage bi = null;
        try {
            URL u = GameUtils.class.getClassLoader().getResource(path);
            bi = ImageIO.read(u);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bi;
    }
}
