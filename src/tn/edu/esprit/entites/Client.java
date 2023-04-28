/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.edu.esprit.entites;

/**
 *
 * @author SOUISSI
 */
import javafx.scene.layout.VBox;

import java.io.*;
import static java.lang.System.out;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Logger;
import tn.edu.esprit.gui.ClientController;

public class Client {

    private Socket socket=null;
    private BufferedReader bufferedReader=null;
    private BufferedWriter bufferedWriter=null;
    public Client(Socket socket) {
        
        try{
            this.socket = socket;
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            out.println("Hello from client");

        }catch(IOException e){
            System.out.println("Error creating Client!");
            e.printStackTrace();
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    private void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter){
        try{
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            if (socket != null) {
                socket.close();
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }


    public void sendMessageToServer(String messageToServer) {
        try{
            
            bufferedWriter.write(messageToServer);
            bufferedWriter.newLine();
            bufferedWriter.flush();
            
        }catch(IOException e){
            e.printStackTrace();
            System.out.println("Error sending message to server...");
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    public void receiveMessageFromServer(VBox vbox_messages) {
        ClientController cc = new ClientController();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(socket.isConnected()){
                    try{
                        String messageFromServer = bufferedReader.readLine();
                        cc.addLabel(messageFromServer, vbox_messages);
                    }catch (IOException e){
                        e.printStackTrace();
                        System.out.println("Error receiving message from the Server!");
                        closeEverything(socket, bufferedReader, bufferedWriter);
                        break;
                    }
                }
            }
        }).start();
    }

}
