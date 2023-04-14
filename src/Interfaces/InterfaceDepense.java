/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;



import entities.Depense;
import java.util.List;

public interface InterfaceDepense {
    
    public void ajouterDepense(Depense d);
    public boolean modifierDepense(Depense d);
    public void supprimerDepense(int id);
    public List<Depense> afficherDepenses();
    public List<Depense> afficherDepensesParMois(String mois);
    
}

