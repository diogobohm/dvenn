/*
 * Copyright 2009 Diogo Böhm
 * 
 * This file is part of DVenn.
 * 
 * DVenn is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * DVenn is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * DVenn. If not, see http://www.gnu.org/licenses/.
 */
package net.diogobohm.dvenn.legacy;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import net.diogobohm.dvenn.image.ImageManager;

public class Screen extends JLabel {

    boolean[] OneVarVennVector, TwoVarVennVector, ThreeVarVennVector, FourVarVennVector;
    ImageIcon[] vennOne, vennTwo, vennThree, vennFour;
    private int varNumber;
    private String[] variables = {"a", "b", "c", "d"};
    private Font innerFont = new Font(Font.SANS_SERIF, Font.BOLD, 20);
    private final ImageManager manager = new ImageManager();

    public Screen() {
        setBackground(Color.WHITE);
        setOpaque(true);

        OneVarVennVector = new boolean[2];
        vennOne = new ImageIcon[3];
        for (int x = 0; x < OneVarVennVector.length; x++) {
            OneVarVennVector[x] = false;
            vennOne[x] = manager.getImageIcon("1way-" + (x + 1) + ".png");
        }
        vennOne[2] = manager.getImageIcon("1way-screen.png");

        TwoVarVennVector = new boolean[4];
        vennTwo = new ImageIcon[5];
        for (int x = 0; x < TwoVarVennVector.length; x++) {
            TwoVarVennVector[x] = false;
            vennTwo[x] = manager.getImageIcon("2way-" + (x + 1) + ".png");
        }
        vennTwo[4] = manager.getImageIcon("2way-screen.png");

        ThreeVarVennVector = new boolean[8];
        vennThree = new ImageIcon[9];
        for (int x = 0; x < ThreeVarVennVector.length; x++) {
            ThreeVarVennVector[x] = false;
            vennThree[x] = manager.getImageIcon("3way-" + (x + 1) + ".png");
        }
        vennThree[8] = manager.getImageIcon("3way-screen.png");

        FourVarVennVector = new boolean[16];
        vennFour = new ImageIcon[17];
        for (int x = 0; x < FourVarVennVector.length; x++) {
            FourVarVennVector[x] = false;
            vennFour[x] = manager.getImageIcon("4way-" + (x + 1) + ".png");
        }
        vennFour[16] = manager.getImageIcon("4way-screen.png");

        this.setPreferredSize(new Dimension(getWidth(), getHeight()));
    }

    public void setArray(boolean[] array) {
        switch (varNumber) {
            case 1:
                this.OneVarVennVector = array;
            case 2:
                this.TwoVarVennVector = array;
            case 3:
                this.ThreeVarVennVector = array;
            case 4:
                this.FourVarVennVector = array;
        }
    }

    public void setVariableNames(String var1, String var2, String var3, String var4) {
        variables[0] = var1;
        variables[1] = var2;
        variables[2] = var3;
        variables[3] = var4;
    }

    public void setVariableNumber(int number) {
        this.varNumber = number;
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(getForeground());
        g.setFont(innerFont);
        switch (varNumber) {
            case 1:
                for (int x = 0; x < OneVarVennVector.length; x++) {
                    if (OneVarVennVector[x]) {
                        g.drawImage(vennOne[x].getImage(), 0, 0, getWidth(), getHeight(), vennOne[x].getImageObserver());
                    }
                }
                g.drawImage(vennOne[2].getImage(), 0, 0, getWidth(), getHeight(), vennOne[2].getImageObserver());
                //Write variable names
                g.drawString(variables[0], Math.round(getWidth() / 2) - 10, Math.round(getHeight() / 2));
                break;
            case 2:
                for (int x = 0; x < TwoVarVennVector.length; x++) {
                    if (TwoVarVennVector[x]) {
                        g.drawImage(vennTwo[x].getImage(), 0, 0, getWidth(), getHeight(), vennTwo[x].getImageObserver());
                    }
                }
                g.drawImage(vennTwo[4].getImage(), 0, 0, getWidth(), getHeight(), vennTwo[4].getImageObserver());
                //Write variable names
                g.drawString(variables[0], Math.round(getWidth() / 4) - 10, Math.round(getHeight() / 2));
                g.drawString(variables[1], Math.round(3 * (getWidth() / 4)) - 10, Math.round(getHeight() / 2));
                break;
            case 3:
                for (int x = 0; x < ThreeVarVennVector.length; x++) {
                    if (ThreeVarVennVector[x]) {
                        g.drawImage(vennThree[x].getImage(), 0, 0, getWidth(), getHeight(), vennThree[x].getImageObserver());
                    }
                }
                g.drawImage(vennThree[8].getImage(), 0, 0, getWidth(), getHeight(), vennThree[8].getImageObserver());
                //Write variable names
                g.drawString(variables[0], Math.round(getWidth() / 2) - 10, Math.round(getHeight() / 3) - 10);
                g.drawString(variables[1], Math.round(getWidth() / 3) - 10, Math.round(2 * (getHeight() / 3)) + 10);
                g.drawString(variables[2], Math.round(2 * (getWidth() / 3)), Math.round(2 * (getHeight() / 3)) + 10);
                break;
            case 4:
                for (int x = 0; x < FourVarVennVector.length; x++) {
                    if (FourVarVennVector[x]) {
                        g.drawImage(vennFour[x].getImage(), 0, 0, getWidth(), getHeight(), vennFour[x].getImageObserver());
                    }
                }
                g.drawImage(vennFour[16].getImage(), 0, 0, getWidth(), getHeight(), vennFour[16].getImageObserver());
                g.drawString(variables[0], Math.round(getWidth() / 5), Math.round(getHeight() / 2));
                g.drawString(variables[1], Math.round(getWidth() / 3), Math.round(getHeight() / 5));
                g.drawString(variables[2], Math.round(3 * (getWidth() / 5)), Math.round(getHeight() / 5));
                g.drawString(variables[3], Math.round(3 * (getWidth() / 4)), Math.round(getHeight() / 2));
                break;
        }
    }
}
