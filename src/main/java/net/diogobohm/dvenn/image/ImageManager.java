/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.diogobohm.dvenn.image;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * Handles all the image files for the DVenn application.
 * @author diogo
 */
public class ImageManager {
    
    public Image getImage(String name) {
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(ImageManager.class.getResource(name).openStream());
        } catch (IOException ex) {
            Logger.getLogger(ImageManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return SwingFXUtils.toFXImage(bufferedImage, null);
    }
    
    public ImageIcon getImageIcon(String name) {
        return new ImageIcon(getResource(name));
    }
    
    private URL getResource(String name) {
        return ImageManager.class.getResource(name);
    }
}
