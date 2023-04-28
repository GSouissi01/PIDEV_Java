/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entity.Credit;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Administrateur
 */
public interface ICreditService {
    
     public void ajouterCredit(Credit e) throws SQLException;
       public List<Credit> AfficherCredit() throws SQLException;
        public void SupprimerCredit(Credit e) throws SQLException;
         public void modifierCredit(Credit e) throws SQLException, NoSuchAlgorithmException ;
    
    
}
