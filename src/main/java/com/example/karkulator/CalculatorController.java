/*
 * @author  : Dino Kupinic
 * @date    : 22.10.2022, 07.11.2022
 * File:   CalculatorController.java
 *
 * @details
 *   Controller for Calculator GUI interaction
 *   Controller for calculation
 */

package com.example.karkulator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

public class CalculatorController {
    /**
     * FXML Variablen
     */
    @FXML
    private Button buttonEquals, buttonClear, buttonDivide, buttonMultiply, buttonMinus, buttonPlus, buttonClearOne, buttonPlusMinus;
    @FXML
    private TextField textField, textFieldFull;

    /**
     * Variablen/Arrays zur Berechnung und speichern des Velaufs
     */
    private final int NUMBERS_CONSTANT = 200, OPERATOR_CONSTANT = 100;
    private final double[] numbers = new double[NUMBERS_CONSTANT];
    private final String[] operators = new String[OPERATOR_CONSTANT];
    private String currentNumber = "", history = "";

    /**
     * Zahlen zum Durchiterieren
     */
    private int arrayZahl = 0;
    private int operatorZahl = 0;
    private int j = 0;
    private int i = 0;

    /**
     * Funktion um per grafischer Betätigung die Zahl zu speichern
     * @param event => Sucht sich den Wert vom jeweiligen Button der gedrückt wurde
     */
    @FXML
    private void onNumberPressed(ActionEvent event) {
        String valueNumber = ((Button) event.getSource()).getText();
        if (numbers[arrayZahl] <= 999999999) {
            currentNumber += valueNumber;
            numbers[arrayZahl] = Double.parseDouble(currentNumber);
            textField.setText(textField.getText() + valueNumber);
            history += valueNumber;
            textFieldFull.setText(history);
        }
        System.out.println(Arrays.toString(numbers));
    }

    /**
     * Funktion um per grafischer Betätigung den Operator zu speichern
     * @param event => Sucht sich den Wert vom jeweiligen Button der gedrückt wurde
     */
    @FXML
    private void onOperatorPressed(ActionEvent event) {
        String valueOperator = ((Button)event.getSource()).getText();

        if (history.contains("=")) {
            history = history.substring(0, history.length() - 1);
        }
        currentNumber = "";

        if (!valueOperator.equals("=") && numbers[0] != 0.0) {
            if (!valueOperator.equals("+/-")) {
                operators[j] = valueOperator;
                arrayZahl++; j++;
                System.out.println(Arrays.toString(operators));
                textField.setText("");
                history += valueOperator;
            } else {
                numbers[arrayZahl] *= -1;
                history += numbers[arrayZahl] * 2;
            }
        } else {
            double solution = calculate(numbers, operators);
            textField.setText(String.valueOf(solution));
            history += valueOperator;
            textFieldFull.setText(history);
        }
    }

    /**
     * Funktion zur Berechnung
     * Prinzipiell ist der Algorithmus so aufgebaut, dass man 2 Arrays hat durch welche man durchiteriert und dabei
     * immer Stellenweise weiterrückt
     * @param numbers => das Array mit den ganzen Zahlen
     * @param operators => das Array mit den ganzen Operatoren
     * @return => gibt den Wert zurück
     */
    public double calculate(double[] numbers, String[] operators) {
        for (arrayZahl = operatorZahl; arrayZahl < numbers.length - 1; arrayZahl++) {
            if (operators[i] == null) {
                return numbers[arrayZahl];
            } else {
                switch (operators[i]) {
                    case "+":
                        numbers[arrayZahl +1] = numbers[arrayZahl] + numbers[arrayZahl +1];
                        break;
                    case "-":
                        numbers[arrayZahl +1] = numbers[arrayZahl] - numbers[arrayZahl +1];
                        break;
                    case "*":
                        numbers[arrayZahl +1] = numbers[arrayZahl] * numbers[arrayZahl +1];
                        break;
                    case "/":
                        numbers[arrayZahl +1] = numbers[arrayZahl] / numbers[arrayZahl +1];
                        break;
                }
                operatorZahl++;
                operators[i] = "";
                i++;
            }
        }
        return numbers[arrayZahl++];
    }

    /**
     * Funktion um das Programm zurückzusetzen
     * @param event => entweder AC oder CE
     */
    @FXML
    private void onClearPressed(ActionEvent event) {
        String valueClear = ((Button)event.getSource()).getText();
        if (valueClear.equals("AC")) {
            Arrays.fill(numbers, 0.0);
            Arrays.fill(operators, null);
            currentNumber = "";
            arrayZahl = 0; j = 0; operatorZahl = 0; i = 0;
            textField.setText("");
            textFieldFull.setText("");
            history = "";
        } else {
            numbers[arrayZahl] = 0.0;
            currentNumber = "";
            textField.setText("");
            if (numbers[arrayZahl] == 0.0) {
                history = "";
                textFieldFull.setText("");
            }
        }
    }

    /**
     * Funktion um ein Fenster zu öffnen welches ein paar Infos über das Programm zeigt
     * @throws IOException => wird geworfen, falls info.fxml nicht exestiert
     */
    @FXML
    private void onInfoButtonPressed() throws IOException {
        Parent part = FXMLLoader.load(Objects.requireNonNull(CalculatorApplication.class.getResource("info.fxml")));
        Stage stage = new Stage();
        Scene scene = new Scene(part);
        scene.getStylesheets().addAll((Objects.requireNonNull(getClass().getResource("styles/info.css"))).toExternalForm());
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Funktion um auf Betätigung des Exit-Buttons das Programm zu schließen
     */
    @FXML
    private void onExitButtonPressed() {
        System.exit(0);
    }
}
