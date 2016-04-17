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

import java.awt.*;
import javax.swing.*;

public class Dvenn implements Runnable {

    private static final String bin1A = "01", bin1An = "10",
            bin2A = "0110", bin2An = "1001",
            bin2B = "0011", bin2Bn = "1100",
            bin3A = "01001101", bin3An = "10110010",
            bin3B = "00101011", bin3Bn = "11010100",
            bin3C = "00010111", bin3Cn = "11101000",
            bin4A = "0111101110100000", bin4An = "1000010001011111",
            bin4B = "0010110011101100", bin4Bn = "1101010011010100",
            bin4C = "0001101011011010", bin4Cn = "1110010100100101",
            bin4D = "0000001110101111", bin4Dn = "1111110001010000",
            andVar = ".", orVar = "+", not = "'";
    private static String var1 = "a", var2 = "b", var3 = "c", var4 = "d";
    private static JRadioButton varAuto, var1way, var2way, var3way, var4way;
    private static final Color ok = Color.decode("#a5f8b1"),
            nok = Color.decode("#f8bda5");
    JLabel status;
    Thread thread;
    Screen diagram;
    JTextField textField;
    
    public Dvenn() {
        JFrame frame = new JFrame("Diagramas de Venn");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 405);
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBounds(0, 0, frame.WIDTH, frame.HEIGHT);

        diagram = new Screen();
        panel.add(diagram, BorderLayout.CENTER);

        JPanel panel2 = new JPanel(new GridLayout(4, 0));

        textField = new JTextField();
        textField.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
        textField.setText("(a'+b'+c').(a+b+c)");
        panel2.add(textField);

        JPanel graphSelectPanel = new JPanel();
        JLabel titleGraphSelect = new JLabel("Variáveis no gráfico:");
        graphSelectPanel.add(titleGraphSelect);

        varAuto = new JRadioButton("Automático", true);
        graphSelectPanel.add(varAuto);
        var1way = new JRadioButton("1", false);
        graphSelectPanel.add(var1way);
        var2way = new JRadioButton("2", false);
        graphSelectPanel.add(var2way);
        var3way = new JRadioButton("3", false);
        graphSelectPanel.add(var3way);
        var4way = new JRadioButton("4", false);
        graphSelectPanel.add(var4way);

        ButtonGroup varGraphSelection = new ButtonGroup();
        varGraphSelection.add(varAuto);
        varGraphSelection.add(var1way);
        varGraphSelection.add(var2way);
        varGraphSelection.add(var3way);
        varGraphSelection.add(var4way);

        panel2.add(graphSelectPanel);

        JLabel func = new JLabel("Use \" " + andVar + " \" para E, use \" " + orVar + " \" para OU, use \" " + not + " \" para NEGAÇÃO");
        func.setHorizontalAlignment(JLabel.CENTER);
        panel2.add(func);

        status = new JLabel("");
        status.setHorizontalAlignment(JLabel.CENTER);
        status.setForeground(Color.RED);
        panel2.add(status);

        panel.add(panel2, BorderLayout.SOUTH);

        frame.add(panel);
        frame.setVisible(true);
        thread = new Thread(this);
        thread.start();
    }

    public void getVariables(String expression) {
        var1 = var2 = var3 = var4 = "?";
        for (int x = 0; x < expression.length(); x++) {
            if ((expression.charAt(x) != '(' && expression.charAt(x) != ')')
                    && (expression.charAt(x) != orVar.charAt(0) && expression.charAt(x) != andVar.charAt(0))
                    && (expression.charAt(x) != not.charAt(0) && expression.charAt(x) != var1.charAt(0))
                    && (expression.charAt(x) != var2.charAt(0) && expression.charAt(x) != var3.charAt(0))
                    && expression.charAt(x) != var4.charAt(0)) {
                if (var1.equals("?")) {
                    var1 = "" + expression.charAt(x);
                } else if (var2.equals("?")) {
                    var2 = "" + expression.charAt(x);
                } else if (var3.equals("?")) {
                    var3 = "" + expression.charAt(x);
                } else if (var4.equals("?")) {
                    var4 = "" + expression.charAt(x);
                }
            }
        }
    }

    public int getVariableNumber(String expression) {
        int x = 0;
        if (varAuto.isSelected()) {
            if (expression.contains(var1)) {
                x++;
            }
            if (expression.contains(var2)) {
                x++;
            }
            if (expression.contains(var3)) {
                x++;
            }
            if (expression.contains(var4)) {
                x++;
            }
        } else if (var1way.isSelected()) {
            x = 1;
        } else if (var2way.isSelected()) {
            x = 2;
        } else if (var3way.isSelected()) {
            x = 3;
        } else if (var4way.isSelected()) {
            x = 4;
        }

        return x;
    }

    public int countOccurencesOf(String word, char regex) {
        int counter = 0;
        for (int x = 0; x < word.length(); x++) {
            if (word.charAt(x) == regex) {
                counter++;
            }
        }
        return counter;
    }

    public boolean evaluate(String expression) {
        expression = expression.toLowerCase();
        expression = expression.replace('´', '\'');
        //Take out blankspaces
        //expression = expression.replace(' ', '');

        getVariables(expression);
        System.out.println(var1 + " " + var2 + " " + var3 + " " + var4);

        //if (diagramVariableNumber==AUTO)
        diagram.setVariableNumber(getVariableNumber(expression));
        diagram.setVariableNames(var1, var2, var3, var4);

        for (int x = 0; x < expression.length(); x++) {
            if ((expression.charAt(x) != '(' && expression.charAt(x) != ')')
                    && (expression.charAt(x) != var1.charAt(0) && expression.charAt(x) != var2.charAt(0))
                    && (expression.charAt(x) != var3.charAt(0) && expression.charAt(x) != var4.charAt(0))
                    && expression.charAt(x) != not.charAt(0)
                    && (expression.charAt(x) != andVar.charAt(0) && expression.charAt(x) != orVar.charAt(0))) {
                return false;
            }
        }
        if (!expression.isEmpty()
                && countOccurencesOf(expression, '(') == countOccurencesOf(expression, ')')
                && (!expression.contains(andVar + ")") && !expression.contains(orVar + ")"))
                && (!expression.contains(andVar + andVar) && !expression.contains(orVar + orVar))
                && (!expression.contains(andVar + orVar) && !expression.contains(orVar + andVar))
                && (!expression.contains(andVar + not) && !expression.contains(orVar + not) && !expression.contains(not + "("))
                && (!expression.contains(not + var1) && !expression.contains(not + var2))
                && (!expression.contains(not + var3) && !expression.contains(not + var4))
                && (!expression.contains(")" + var1) && !expression.contains(")" + var2))
                && (!expression.contains(")" + var3) && !expression.contains(")" + var4))
                && (!expression.contains(var1 + "(") && !expression.contains(var2 + "("))
                && (!expression.contains(var3 + "(") && !expression.contains(var4 + "("))
                && (!expression.contains(var1 + var1) && !expression.contains(var1 + var2))
                && (!expression.contains(var1 + var3) && !expression.contains(var1 + var4))
                && (!expression.contains(var2 + var1) && !expression.contains(var2 + var2))
                && (!expression.contains(var2 + var3) && !expression.contains(var2 + var4))
                && (!expression.contains(var3 + var1) && !expression.contains(var3 + var2))
                && (!expression.contains(var3 + var3) && !expression.contains(var3 + var4))
                && (!expression.contains(var4 + var1) && !expression.contains(var4 + var2))
                && (!expression.contains(var4 + var3) && !expression.contains(var4 + var4))
                && (!expression.startsWith(andVar) && !expression.startsWith(orVar))
                && (!expression.endsWith(andVar) && !expression.endsWith(orVar))) {
            return true;
        } else {
            return false;
        }
    }

    public boolean[] fromBinaryString(String word) {
        boolean[] ret = new boolean[word.length()];
        for (int x = 0; x < word.length(); x++) {
            if (word.charAt(x) == '0') {
                ret[x] = false;
            } else {
                ret[x] = true;
            }
        }

        return ret;
    }

    public String transfer(String expression, int variableNumber) {
        expression = expression.replaceAll("´", not);

        switch (variableNumber) {
            case 1:
                expression = expression.replaceAll(var1 + "'", bin1An);
                expression = expression.replaceAll(var1, bin1A);
                break;

            case 2:
                expression = expression.replaceAll(var1 + "'", bin2An);
                expression = expression.replaceAll(var1, bin2A);
                expression = expression.replaceAll(var2 + "'", bin2Bn);
                expression = expression.replaceAll(var2, bin2B);
                break;

            case 3:
                expression = expression.replaceAll(var1 + "'", bin3An);
                expression = expression.replaceAll(var1, bin3A);
                expression = expression.replaceAll(var2 + "'", bin3Bn);
                expression = expression.replaceAll(var2, bin3B);
                expression = expression.replaceAll(var3 + "'", bin3Cn);
                expression = expression.replaceAll(var3, bin3C);
                break;

            case 4:
                expression = expression.replaceAll(var1 + "'", bin4An);
                expression = expression.replaceAll(var1, bin4A);
                expression = expression.replaceAll(var2 + "'", bin4Bn);
                expression = expression.replaceAll(var2, bin4B);
                expression = expression.replaceAll(var3 + "'", bin4Cn);
                expression = expression.replaceAll(var3, bin4C);
                expression = expression.replaceAll(var4 + "'", bin4Dn);
                expression = expression.replaceAll(var4, bin4D);
                break;

        }
        return expression;
    }

    public String navigate(String expression, int varNumber) {
        int start = 0, end, x;
        while (expression.contains("(")) {
            start = 0;
            end = 0;
            for (x = 0; x < expression.length(); x++) {
                if (expression.charAt(x) == '(') {
                    start = x;
                } else if (expression.charAt(x) == ')') {
                    end = x;
                    expression = expression.substring(0, start) + solve(
                            expression.substring(start + 1, end), varNumber)
                            + expression.substring(end + 1, expression.length());
                    break;
                }
            }
        }
        return expression;
    }

    public String booleanNot(String arg1) {
        String ret = "";
        for (int x = 0; x < arg1.length(); x++) {
            if (arg1.charAt(x) == '1') {
                ret += "0";
            } else {
                ret += "1";
            }
        }
        return ret;
    }

    public String booleanAnd(String arg1, String arg2) {
        String ret = "";
        for (int x = 0; x < arg1.length(); x++) {
            if (arg1.charAt(x) == '1' && arg2.charAt(x) == '1') {
                ret += "1";
            } else {
                ret += "0";
            }
        }
        return ret;
    }

    public String booleanOr(String arg1, String arg2) {
        String ret = "";
        for (int x = 0; x < arg1.length(); x++) {
            if (arg1.charAt(x) == '1' || arg2.charAt(x) == '1') {
                ret += "1";
            } else {
                ret += "0";
            }
        }
        return ret;
    }

    public String solve(String expression, int varNumber) {
        int start, end, length;

        switch (varNumber) {
            case 1:
                length = 2;
                break;
            case 2:
                length = 4;
                break;
            case 3:
                length = 8;
                break;
            case 4:
                length = 16;
                break;
            default:
                length = 8;
        }

        if (expression.length() > length) {
            if (expression.contains(not)) {
                start = expression.indexOf(not) - length;
                expression = expression.substring(0, start)
                        + booleanNot(expression.substring(start, expression.indexOf(not)))
                        + expression.substring(expression.indexOf(not) + 1, expression.length());
            } else if (expression.contains(andVar)) {
                start = expression.indexOf(andVar) - length;
                end = expression.indexOf(andVar) + (length + 1);
                expression = expression.substring(0, start)
                        + booleanAnd(expression.substring(start, expression.indexOf(andVar)), expression.substring(expression.indexOf(andVar) + 1, end))
                        + expression.substring(end, expression.length());
            } else if (expression.contains(orVar)) {
                start = expression.indexOf(orVar) - length;
                end = expression.indexOf(orVar) + (length + 1);
                expression = expression.substring(0, start)
                        + booleanOr(expression.substring(start, expression.indexOf(orVar)), expression.substring(expression.indexOf(orVar) + 1, end))
                        + expression.substring(end, expression.length());
            }
            expression = solve(expression, varNumber);
        }

        return expression;
    }

    public void run() {
        boolean end = false;
        String expression = "";
        while (!end) {
            if (!expression.equals(textField.getText())) {
                expression = textField.getText();
                if (evaluate(expression)) {
                    status.setText("");
                    textField.setBackground(ok);
                    try {
                        diagram.setArray(fromBinaryString(navigate(transfer("(" + expression.toLowerCase() + ")", getVariableNumber(expression)), getVariableNumber(expression))));
                    } catch (Exception e) {
                        status.setText("Expressão inválida!");
                        textField.setBackground(nok);
                    }
                    diagram.repaint();
                    textField.repaint();
                } else {
                    status.setText("Expressão inválida!");
                    textField.setBackground(nok);
                    textField.repaint();
                }
            }
            try { thread.sleep(1000); }
            catch (InterruptedException ex) {}
        }
    }
}
