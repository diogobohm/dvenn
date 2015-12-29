/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.diogobohm.dvenn;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 *
 * @author diogo
 */
public class LogicListener implements ChangeListener<String> {

    public BooleanProperty validExpressionProperty = new SimpleBooleanProperty(true);
    private String var1 = "a", var2 = "b", var3 = "c", var4 = "d";
    private final String andVar = ".", orVar = "+", not = "'";
    
    @Override
    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        System.out.println("Changed from " + oldValue + " to " + newValue);
        validExpressionProperty.setValue(evaluate(newValue));
    }
    
    private Boolean evaluate(String expression) {
        expression = expression.toLowerCase();
        expression = expression.replace('´', '\'');
        //Take out blankspaces
        //expression = expression.replace(' ', '');

        getVariables(expression);
        System.out.println(var1 + " " + var2 + " " + var3 + " " + var4);


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
    
    private void getVariables(String expression) {
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
    
    private int countOccurencesOf(String word, char regex) {
        int counter = 0;
        for (int x = 0; x < word.length(); x++) {
            if (word.charAt(x) == regex) {
                counter++;
            }
        }
        return counter;
    }
}
