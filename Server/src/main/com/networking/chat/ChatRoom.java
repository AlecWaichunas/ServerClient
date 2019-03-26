package com.networking.chat;


public class ChatRoom {

    private int connected = 0;
    private Client[] clients;

    //sends requests to clients
    public ChatRoom(Client[] clients){
        this.clients = clients;
        this.connected = 1;
        //send update message to head client
        clients[i].sendToClient("");
        //send request message
        for(int i = 1; i < connected; i++){
            String msg = "Talk request from" +
                         clients[0].getName() +
                         "@" + clients[0].getIPAddr() +
                         ". Respond with \"accept" +
                         clients[0].getName() +
                         "@" + clients[0].getIPAddr() +
                         "\"";
            clients[i].sendToClient(msg);
        }
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
            if(!c.equals(client) && c.getChatRoom().equals(this))
                c.sendToClient(c.getName() + ": " + message);
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