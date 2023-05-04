/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.edu.esprit.gui;

import tn.edu.esprit.entities.User;
import tn.edu.esprit.services.ServiceUser;

/**
 *
 * @author SOUISSI
 */
public class UserController {
    private ServiceUser userService;
    private User currentUser;

    public UserController() {
        userService = new ServiceUser();
    }

    public boolean authenticate(String email, String password) {
        User user = userService.login(email, password);
        if (user != null) {
            currentUser = user;
            return true;
        } else {
            return false;
        }
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void logout() {
        currentUser = null;
    }
}