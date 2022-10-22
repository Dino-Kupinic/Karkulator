/*
 * @author  : Dino Kupinic
 * @date    : 22.10.2022
 * File:   CalculatorApplication.java
 *
 * @details
 *   Main class
 */

package com.example.karkulator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class CalculatorApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CalculatorApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 370, 470);

        scene.getStylesheets().addAll(Objects.requireNonNull(getClass().getResource("styles/styles.css")).toExternalForm());
        scene.setFill(Color.BLACK);
        stage.setTitle("Calculator");
        stage.setResizable(false);
        stage.setScene(scene);

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}