/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Entities.Contrat;
import Services.ServiceContrat;
import Services.ServiceCategorie;

/**
 *
 * @author Safe
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
      //  Services.ServiceCategorie sc = new ServiceCategorie();
      Services.ServiceContrat sa = new ServiceContrat();
           sa.add(new Contrat("test","test","test","test"));
    }
    
}
