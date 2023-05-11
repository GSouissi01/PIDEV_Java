/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.edu.esprit.entities;

import org.mindrot.jbcrypt.BCrypt;


 


public class PasswordHasher {
 
  public static String hashPassword(String password) {
    String salt = BCrypt.gensalt(12);
    String hashedPassword = BCrypt.hashpw(password, salt);
    return hashedPassword;
  }
 
  public static boolean verifyPassword(String password, String hashedPassword) {
    return BCrypt.checkpw(password, hashedPassword.replaceFirst("\\$2y\\$", "\\$2a\\$"));
  }
}
