/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.edu.esprit.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import tn.edu.esprit.entites.Message;

/**
 *
 * @author SOUISSI
 */
public class MessageService {
     public static boolean sendMessage(Message message, Connection connection) {
        boolean sent = false;
        try {
            // Insert the message into the database
            String sql = "INSERT INTO messages (from_id, to_id, content, date) VALUES (?, ?, ?, NOW())";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, message.getFromId());
            statement.setInt(2, message.getToId());
            statement.setString(3, message.getContent());
            int rows = statement.executeUpdate();
            sent = rows == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sent;
    }
}
