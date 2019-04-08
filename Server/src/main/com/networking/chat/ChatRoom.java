package com.networking.chat;

import java.io.BufferedReader;

import com.networking.chat.Client;
public class ChatRoom {

    private int connected = 0;
    private Client[] clients;

    //sends requests to clients
    public ChatRoom(Client[] clients){
        this.clients = clients;
        this.connected = 1;
        String ringmsg = "Ringing";
        //send request message
        for(int i = 1; i < clients.length; i++){
            if(clients[i] == null){
                System.out.println("Could not connect to client");
                continue;
            }
            String msg = "Talk request from " +
                         clients[0].getName() +
                         "@" + clients[0].getIPAddr() +
                         ". Respond with \"accept " +
                         clients[0].getName() +
                         "@" + clients[0].getIPAddr() +
                         "\"";
            clients[i].sendToClient(msg);
            ringmsg += " " + clients[i].getName();
        }
        //send update message to head client
        clients[0].sendToClient(ringmsg );
    }

    //accepts clients
    public void acceptRequest(Client client){
        client.setChatRoom(this);
        connected++;
        this.castMessage(client, "Talk connection established with " + client.getName());
    }

    //casts message
    public void castMessage(Client client, String message){
        for(Client c : this.clients){
            if(c != null && !c.equals(client) && c.getChatRoom() != null 
                && c.getChatRoom().equals(this))
                c.sendToClient(client.getName() + ": " + message);
        }
    }

    //work on client disconnect
    public void clientDisconnect(Client client){
        connected--;
        client.setChatRoom(null);
        this.castMessage(client, "Connection Terminated with " + client.getName());
        //if(connected == 0){
        // reference does not exists since no clients do
        //}
    }
}