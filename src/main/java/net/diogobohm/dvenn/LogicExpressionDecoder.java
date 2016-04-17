/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.diogobohm.dvenn;

import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import net.diogobohm.dvenn.image.ImageManager;

/**
 *
 * @author diogo
 */
public class LogicExpressionDecoder {
    
    private static final String bin1A = "01", bin1An = "10",
            bin2A = "0110", bin2An = "1001",
            bin2B = "0011", bin2Bn = "1100",
            bin3A = "01001101", bin3An = "10110010",
            bin3B = "00101011", bin3Bn = "11010100",
            bin3C = "00010111", bin3Cn = "11101000",
            bin4A = "0111101110100000", bin4An = "1000010001011111",
            bin4B = "0010110011101100", bin4Bn = "1101010011010100",
            bin4C = "0001101011011010", bin4Cn = "1110010100100101",
            bin4D = "0000001110101111", bin4Dn = "1111110001010000";
    private final String andVar = ".", orVar = "+", not = "'";
    private String var1 = "a", var2 = "b", var3 = "c", var4 = "d";
    
    public boolean[] decodeDiagram(String validExpression) {
        String expression = validExpression.toLowerCase();
        
        return fromBinaryString(
                navigate(transfer("(" + expression + ")",
                        getVariableNumber(expression)),
                        getVariableNumber(expression)));
    }
    
    private boolean[] fromBinaryString(String word) {
        boolean[] ret = new boolean[word.length()];
        
        for (int x = 0; x < word.length(); x++) {
            if (word.charAt(x) == '0') {
                ret[x] = false;
            } else {
                ret[x] = true;
            }
        }

        System.out.println(String.format("Converted %s to %s", word, ret));
        
        return ret;
    }
    
    private String transfer(String expression, int variableNumber) {
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
    
    private int getVariableNumber(String expression) {
        int x = 0;
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
            
        return x;
    }
    
    private String navigate(String expression, int varNumber) {
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
    
    private String solve(String expression, int varNumber) {
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
    
    private String booleanNot(String arg1) {
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

    private String booleanAnd(String arg1, String arg2) {
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

    private String booleanOr(String arg1, String arg2) {
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
}
