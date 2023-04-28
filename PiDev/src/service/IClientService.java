/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entity.Client;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Administrateur
 */
public interface IClientService {
          public void modifierClient(Client e) throws SQLException, NoSuchAlgorithmException ;
public void SupprimerClient(Client e) throws SQLException ;
 public List<Client> AfficherClient() throws SQLException;
 public void ajouterClient(Client e) throws SQLException ;
}
