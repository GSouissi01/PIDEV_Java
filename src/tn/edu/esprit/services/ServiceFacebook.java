/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.edu.esprit.services;

/**
 *
 * @author SOUISSI
 */
import tn.edu.esprit.entites.User;
import tn.edu.esprit.services.IFacebook;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import tn.edu.esprit.entites.User;
import tn.edu.esprit.utils.Database;

public class ServiceFacebook implements IFacebook{
    Connection cnx;
    public ServiceFacebook() {
         cnx = Database.getInstance().getCnx();
       
    }
    
    Statement ste;
    PreparedStatement prepste;

    @Override
    public boolean InscriptionFB(User u) {
        ServiceUser su= new ServiceUser();
        try {
            if (su.checkexistance(u.getEmail())==0)
            {
                String ps="INSERT INTO `user` ( `email`, `password`, `nom`, `prenom`, `tel`, `nom_sup`, `adresse_sup`, `role`, `is_banned`)"
                        + " VALUES ('"+u.getEmail()+"', 'NULL','NULL', NULL, NULL, NULL, NULL, 'USER', '0');";
                System.out.println("dkhal");
                
                
                
                
                prepste = cnx.prepareStatement(ps);
                
                
                prepste.executeUpdate(ps);
            }
           
        

            return true;
      
} catch (SQLException ex) {
            Logger.getLogger(ServiceFacebook.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;

    }
}
