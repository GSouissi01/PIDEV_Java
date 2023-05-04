/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.edu.esprit.gui;

import javafx.animation.AnimationTimer;
import javafx.scene.shape.Circle;

public class MaAnimation extends AnimationTimer {

    private Circle circle;
    private double velocity = 2;

    public MaAnimation(Circle circle) {
        this.circle = circle;
    }

    @Override
    public void handle(long now) {
        double x = circle.getLayoutX();
        double newX = x + velocity;

        if (newX + circle.getRadius() > circle.getScene().getWidth() || newX - circle.getRadius() < 0) {
            velocity = -velocity;
        }

        circle.setLayoutX(newX);
    }
}

