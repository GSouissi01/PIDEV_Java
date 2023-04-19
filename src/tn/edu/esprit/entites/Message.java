/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.edu.esprit.entites;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 *
 * @author SOUISSI
 */
public class Message {
    private int id;
    private int fromId;
    private int toId;
    private String content;
    private LocalDateTime date;

    public Message(int id, int fromId, int toId, String content, LocalDateTime date) {
        this.id = id;
        this.fromId = fromId;
        this.toId = toId;
        this.content = content;
        this.date = date;
    }

    

    public Message(int fromId, int toId, String content, LocalDateTime date) {
        this(0, fromId, toId, content, date);
    }

    public int getId() {
        return id;
    }

    public int getFromId() {
        return fromId;
    }

    public int getToId() {
        return toId;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getDate() {
        return date;
    }
     public String getFormattedTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return date.format(formatter);
    }
}

