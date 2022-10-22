/*
 * @author  : Dino Kupinic
 * @date    : 22.10.2022
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

    @FXML
    private Button buttonEquals, buttonClear, buttonDivide, buttonMultiply, buttonMinus, buttonPlus, buttonClearOne, buttonPlusMinus;
    @FXML
    private TextField textField, textFieldFull;

    private final int NUMBERS_CONSTANT = 200, OPERATOR_CONSTANT = 100;
    private final double[] numbers = new double[NUMBERS_CONSTANT];
    private final String[] operators = new String[OPERATOR_CONSTANT];
    private String currentNumber = "", history = "";
    private int i = 0, j = 0, a = 0, b = 0;

    @FXML
    private void onNumberPressed(ActionEvent event) {
        String valueNumber = ((Button) event.getSource()).getText();
        if (numbers[i] <= 999999999) {
            currentNumber += valueNumber;
            numbers[i] = Double.parseDouble(currentNumber);
            textField.setText(textField.getText() + valueNumber);
            history += valueNumber;
            textFieldFull.setText(history);
        }
        System.out.println(Arrays.toString(numbers));
    }

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
                i++; j++;
                System.out.println(Arrays.toString(operators));
                textField.setText("");
                history += valueOperator;
            } else {
                numbers[i] *= -1;
                history += numbers[i] * 2;
            }
        } else {
            double solution = calculate(numbers, operators);
            textField.setText(String.valueOf(solution));
            history += valueOperator;
            textFieldFull.setText(history);
        }
    }

    public double calculate(double[] numbers, String[] operators) {
        for (i = a; i < numbers.length - 1; i++) {
            if (operators[b] == null) {
                return numbers[i];
            } else {
                switch (operators[b]) {
                    case "+":
                        numbers[i+1] = numbers[i] + numbers[i+1];
                        a++;
                        operators[b] = "";
                        b++;
                        break;
                    case "-":
                        numbers[i+1] = numbers[i] - numbers[i+1];
                        a++;
                        operators[b] = "";
                        b++;
                        break;
                    case "*":
                        numbers[i+1] = numbers[i] * numbers[i+1];
                        a++;
                        operators[b] = "";
                        b++;
                        break;
                    case "/":
                        numbers[i+1] = numbers[i] / numbers[i+1];
                        a++;
                        operators[b] = "";
                        b++;
                        break;
                }
            }
        }
        return numbers[i++];
    }

    @FXML
    private void onClearPressed(ActionEvent event) {
        String valueClear = ((Button)event.getSource()).getText();
        if (valueClear.equals("AC")) {
            Arrays.fill(numbers, 0.0);
            Arrays.fill(operators, null);
            currentNumber = "";
            i = 0; j = 0; a = 0; b = 0;
            textField.setText("");
            textFieldFull.setText("");
            history = "";
        } else {
            numbers[i] = 0.0;
            currentNumber = "";
            textField.setText("");
            if (numbers[i] == 0.0) {
                history = "";
                textFieldFull.setText("");
            }
        }
    }

    @FXML
    private void onInfoButtonPressed() throws IOException {
        Parent part = FXMLLoader.load(CalculatorApplication.class.getResource("info.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(part);
        scene.getStylesheets().addAll(Objects.requireNonNull(getClass().getResource("styles/info.css")).toExternalForm());
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void onExitButtonPressed() {
        System.exit(0);
    }
}
