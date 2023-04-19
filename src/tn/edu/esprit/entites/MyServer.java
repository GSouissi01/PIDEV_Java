/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.edu.esprit.entites;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import tn.edu.esprit.thread.ClientHandler;
/**
 *
 * @author SOUISSI
 */


public class MyServer {

    private static final int PORT = 8000;

    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Server started on port " + PORT);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("New client connected: " + clientSocket.getInetAddress().getHostAddress());
            // Create a new thread to handle this client
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true); // create PrintWriter
            Thread clientThread = new Thread(new ClientHandler(clientSocket, out)); // pass PrintWriter to ClientHandler
            clientThread.start();
        }
    }
}


