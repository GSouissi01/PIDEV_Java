/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.edu.esprit.services;

import java.sql.SQLException;
import java.util.List;
import tn.edu.esprit.entities.User;
/**
 *
 * @author ghada
 */
public interface IUser <T>{
    public void ajouter(T p) throws SQLException;
    public User login(String email, String password) throws SQLException;
    public void modifier(T p) throws SQLException;
    public void supprimerUser(int id) throws SQLException;
   // public List<T> getAll() throws SQLException;
}
