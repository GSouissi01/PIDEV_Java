/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.edu.esprit.gui;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class calculatorController {
    @FXML private Pane titlePane;
    @FXML private ImageView btnMinimize, btnClose;
    @FXML private Label lblResult;

    private double x, y;
    private double num1 = 0;
    private String operator = "+";
    @FXML
    private Pane btn7;
    @FXML
    private Pane btn8;
    @FXML
    private Pane btn9;
    @FXML
    private Pane btnPlus;
    @FXML
    private Pane btn4;
    @FXML
    private Pane btn5;
    @FXML
    private Pane btn6;
    @FXML
    private Pane btnMinus;
    @FXML
    private Pane btn1;
    @FXML
    private Pane btn2;
    @FXML
    private Pane btn3;
    @FXML
    private Pane btnMultiply;
    @FXML
    private Pane btnClear;
    @FXML
    private Pane btn0;
    @FXML
    private Pane btnEquals;
    @FXML
    private Pane btnDivide;
    private Button close;
    
    
public void init(Stage stage) {
        
    titlePane.setOnMousePressed(mouseEvent -> {
        x = mouseEvent.getSceneX();
        y = mouseEvent.getSceneY();
    });
    titlePane.setOnMouseDragged(mouseEvent -> {
        stage.setX(mouseEvent.getScreenX()-x);
        stage.setY(mouseEvent.getScreenY()-y);
    });

    // Corrige le code pour le bouton close
         close.setStyle("-fx-background-image: url('file:///C:/Users/azizb/Downloads/close.png');-fx-background-size: 100% 100%;");

  

    close.setOnAction(event -> {
        Stage stageToClose = (Stage) close.getScene().getWindow();
        stageToClose.close();
    });

  
}

    @FXML
    void onNumberClicked(MouseEvent event) {
        int value = Integer.parseInt(((Pane)event.getSource()).getId().replace("btn",""));
        lblResult.setText(Double.parseDouble(lblResult.getText())==0?String.valueOf((double)value):String.valueOf(Double.parseDouble(lblResult.getText())*10+value));
    }

    @FXML
    void onSymbolClicked(MouseEvent event) {
        String symbol = ((Pane)event.getSource()).getId().replace("btn","");
        if(symbol.equals("Equals")) {
            double num2 = Double.parseDouble(lblResult.getText());
           switch (operator) {
    case "+":
        lblResult.setText((num1+num2) + "");
        break;
    case "-":
        lblResult.setText((num1-num2) + "");
        break;
    case "*":
        lblResult.setText((num1*num2) + "");
        break;
    case "/":
        lblResult.setText((num1/num2) + "");
        break;
}

            operator = ".";
        }
        else if(symbol.equals("Clear")) {
            lblResult.setText(String.valueOf(0.0));
            operator = ".";
        }
        else {
         switch (symbol) {
    case "Plus":
        operator = "+";
        break;
    case "Minus":
        operator = "-";
        break;
    case "Multiply":
        operator = "*";
        break;
    case "Divide":
        operator = "/";
        break;
}

            num1 = Double.parseDouble(lblResult.getText());
            lblResult.setText(String.valueOf(0.0));
        }
    }
}