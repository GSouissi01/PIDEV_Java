/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.edu.esprit.services;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author ghada
 */
public interface IProduct <T>{
    public void ajouter(T p) throws SQLException;
    public void modifier(T p) throws SQLException;
    public void supprimer(int id) throws SQLException;
    public List<T> getAll() throws SQLException;
}
