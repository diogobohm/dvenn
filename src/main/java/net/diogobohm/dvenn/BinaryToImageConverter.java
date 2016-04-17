/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.diogobohm.dvenn;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Paint;
import net.diogobohm.dvenn.image.ImageManager;

/**
 *
 * @author diogo
 */
public class BinaryToImageConverter {
    
    private final ImageManager imageManager;
    
    public BinaryToImageConverter(ImageManager imageManager) {
        this.imageManager = imageManager;
    }
    
    public Image render(boolean[] binaryArray) {
        Canvas canvas = new Canvas(400, 300);
        GraphicsContext graphics = canvas.getGraphicsContext2D();
        
        graphics.drawImage(imageManager.getImage("2way-screen.png"), 0, 0);
        
        paintCanvas(graphics, binaryArray);
        
        return canvas.snapshot(null, null);
    }

    private void paintCanvas(GraphicsContext graphics, boolean[] binaryArray) {
        boolean[] OneVarVennVector, TwoVarVennVector, ThreeVarVennVector, FourVarVennVector;
        Image[] vennOne, vennTwo, vennThree, vennFour;
        int varNumber = 3;
        String[] variables = {"a", "b", "c", "d"};
        
        OneVarVennVector = new boolean[2];
        vennOne = new Image[3];
        for (int x = 0; x < OneVarVennVector.length; x++) {
            OneVarVennVector[x] = false;
            vennOne[x] = imageManager.getImage("1way-" + (x + 1) + ".png");
        }
        vennOne[2] = imageManager.getImage("1way-screen.png");

        TwoVarVennVector = new boolean[4];
        vennTwo = new Image[5];
        for (int x = 0; x < TwoVarVennVector.length; x++) {
            TwoVarVennVector[x] = false;
            vennTwo[x] = imageManager.getImage("2way-" + (x + 1) + ".png");
        }
        vennTwo[4] = imageManager.getImage("2way-screen.png");

        ThreeVarVennVector = new boolean[8];
        vennThree = new Image[9];
        for (int x = 0; x < ThreeVarVennVector.length; x++) {
            ThreeVarVennVector[x] = false;
            vennThree[x] = imageManager.getImage("3way-" + (x + 1) + ".png");
        }
        vennThree[8] = imageManager.getImage("3way-screen.png");

        FourVarVennVector = new boolean[16];
        vennFour = new Image[17];
        for (int x = 0; x < FourVarVennVector.length; x++) {
            FourVarVennVector[x] = false;
            vennFour[x] = imageManager.getImage("4way-" + (x + 1) + ".png");
        }
        vennFour[16] = imageManager.getImage("4way-screen.png");
        
        graphics.setFill(Paint.valueOf("white"));
        graphics.fillRect(0, 0, getWidth(), getHeight());
        
        graphics.setFill(Paint.valueOf("black"));
        
        switch (varNumber) {
            case 1:
                OneVarVennVector = binaryArray;
            case 2:
                TwoVarVennVector = binaryArray;
            case 3:
                ThreeVarVennVector = binaryArray;
            case 4:
                FourVarVennVector = binaryArray;
        }
        
        switch (varNumber) {
            case 1:
                for (int x = 0; x < OneVarVennVector.length; x++) {
                    if (OneVarVennVector[x]) {
                        graphics.drawImage(vennOne[x], 0, 0);
                    }
                }
                graphics.drawImage(vennOne[2], 0, 0);
                //Write variable names
                graphics.strokeText(variables[0], Math.round(getWidth() / 2) - 10, Math.round(getHeight() / 2));
                break;
            case 2:
                for (int x = 0; x < TwoVarVennVector.length; x++) {
                    if (TwoVarVennVector[x]) {
                        graphics.drawImage(vennTwo[x], 0, 0);
                    }
                }
                graphics.drawImage(vennTwo[4], 0, 0);
                //Write variable names
                graphics.strokeText(variables[0], Math.round(getWidth() / 4) - 10, Math.round(getHeight() / 2));
                graphics.strokeText(variables[1], Math.round(3 * (getWidth() / 4)) - 10, Math.round(getHeight() / 2));
                break;
            case 3:
                for (int x = 0; x < ThreeVarVennVector.length; x++) {
                    if (ThreeVarVennVector[x]) {
                        graphics.drawImage(vennThree[x], 0, 0);
                    }
                }
                graphics.drawImage(vennThree[8], 0, 0);
                //Write variable names
                graphics.strokeText(variables[0], Math.round(getWidth() / 2) - 10, Math.round(getHeight() / 3) - 10);
                graphics.strokeText(variables[1], Math.round(getWidth() / 3) - 10, Math.round(2 * (getHeight() / 3)) + 10);
                graphics.strokeText(variables[2], Math.round(2 * (getWidth() / 3)), Math.round(2 * (getHeight() / 3)) + 10);
                break;
            case 4:
                for (int x = 0; x < FourVarVennVector.length; x++) {
                    if (FourVarVennVector[x]) {
                        graphics.drawImage(vennFour[x], 0, 0);
                    }
                }
                graphics.drawImage(vennFour[16], 0, 0);
                graphics.strokeText(variables[0], Math.round(getWidth() / 5), Math.round(getHeight() / 2));
                graphics.strokeText(variables[1], Math.round(getWidth() / 3), Math.round(getHeight() / 5));
                graphics.strokeText(variables[2], Math.round(3 * (getWidth() / 5)), Math.round(getHeight() / 5));
                graphics.strokeText(variables[3], Math.round(3 * (getWidth() / 4)), Math.round(getHeight() / 2));
                break;
        }
    }
    
    private int getWidth() {
        return 400;
    }
    
    private int getHeight() {
        return 300;
    }
}
