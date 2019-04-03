package com.networking.chat;

import java.io.BufferedOutputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;

import com.networking.server.Server;
import com.networking.chat.ChatRoom;
import com.networking.chat.client.ClientInput;;

public class Client{

    private final int ID;

    private String name;
    private Server server;
    private Socket socket;

    private BufferedOutputStream out;
    private ChatRoom chatRoom;
    
    //create new client and new client input.
    public Client(int id,Socket socket, Server server) throws IOException{
        this.ID = id;
        this.name = "Client" + id;
        this.socket = socket;
        this.server = server;

        new ClientInput(this, new BufferedReader(
                        new InputStreamReader(socket.getInputStream())));
        out = new BufferedOutputStream(socket.getOutputStream());

    }

    //reads message from client, if it was a command
    // of for a message for a chatroom
    public void readClient(String message){
        if(chatRoom == null){
            //looking for command
            String[] commands = message.split(" ");
            if(commands[0].equalsIgnoreCase("talk")){
                //creates a chat room with one other client
                Client[] clients = new Client[2];
                clients[0] = this;

                int id = Integer.parseInt(commands[1].substring(6));
                clients[1] = server.getClient(id);
                if(clients[1] == null){
                    this.sendToClient(clients[1].getName() + " not logged in!");
                    return;
                }else if(clients[1].chatRoom != null){
                    this.sendToClient(clients[1].getName() + " is already in a Chat Room!");
                    return;
                }
                this.chatRoom = new ChatRoom(clients);
            }else if(commands[0].equalsIgnoreCase("conference") &&
                commands[1].equalsIgnoreCase("talk")){
                //create a chat room with multiple clients
                Client[] clients = new Client[commands.length - 1];
                clients[0] = this;
                for(int i = 2; i < commands.length; i++){
                    int id = Integer.parseInt(commands[i].substring(6));
                    clients[i] = server.getClient(id);
                    if(clients[i] == null){
                        this.sendToClient(clients[i].getName() + " not logged in!");
                        return;
                    }else if(clients[i].chatRoom != null){
                        this.sendToClient(clients[i].getName() + " is already in a Chat Room!");
                        return;
                    }
                }
                this.chatRoom = new ChatRoom(clients);
            }else if(commands[0].equalsIgnoreCase("accept")){
                String[] clientinfo = commands[1].split("@");
                Client reqclient = this.server.getClientByIpAndName(clientinfo[0], clientinfo[1]);
                if(reqclient == null || reqclient.chatRoom == null){
                    this.sendToClient("Could not find " + clientinfo[0] + "'s Chat Room!'");
                }else{
                    reqclient.chatRoom.acceptRequest(this);
                }
            }

        }else{
            //the only command during a chat
            if(message.equals("Exit"))
                chatRoom.clientDisconnect(this);
            else
                //send message if it is not a command
                chatRoom.castMessage(this, message);
        }
    }

    public void sendToClient(String message){
        //send message to client
        char[] characters = message.toCharArray();
        for(char byt : characters)
            try{
                out.write(byt);
            }catch(IOException e){
                e.printStackTrace();
            }
        out.flush();
    }

    // get the client's current chat room
    public ChatRoom getChatRoom(){
        return chatRoom;
    }

    // set the client's current chat room
    public void setChatRoom(ChatRoom chatRoom){
        this.chatRoom = chatRoom;
    }

    //get the name of the client
    public String getName(){
        return this.name;
    }

    //get the ip addr of the client
    public String getIPAddr(){
        return this.socket.getLocalAddress().getHostAddress();
    }

    //gets the client's ID
    public int getID(){
        return this.ID;
    }

    //close client from chat room, output and server
    public void closeClient(){
        if(this.chatRoom != null)
            chatRoom.clientDisconnect(this);
        try{
            out.close();
        }catch(IOException e){
            e.printStackTrace();
        }
        server.closeClient(this);
    }

}