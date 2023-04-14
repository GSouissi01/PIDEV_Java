/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.edu.esprit.services;

import java.util.List;

/**
 *
 * @author azizbramli
 */
public interface ipromotion<T> {
        public void insert(T o);
        public void supprimer(int i);
    public void delete(int i);
   public List<T> displayAll();
   public T displayById(int id);
   public T getOneByName(String nom);

    
  public void update(T os);
    
}
