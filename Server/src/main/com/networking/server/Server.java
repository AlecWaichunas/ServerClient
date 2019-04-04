package com.networking.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.ArrayList;

import com.networking.chat.Client;


// server accepts clients, then sends
// the client to run on another thread. Object maintenace is also
// done here, for continue
public class Server{

    private static final int PORT = 12461;

    private ServerSocket server;
    private List<Client> clients = new ArrayList<Client>();

    //server constructor to start loop for client
    public Server(){
        try {
            server = new ServerSocket(PORT);
            init();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    // initializes server
    private void init(){
        while(!server.isClosed()){
            try{
                Socket clientSocket = server.accept();
                int id = this.getNewClientID();
                System.out.println(id);
                Client client = new Client(id, clientSocket, this);
                System.out.println("Connected client");
                clients.add(id, client);
            }catch(IOException e){
                //could not connect to client
                e.printStackTrace();
            }
        }
    }

    // creates a new clients id. This id will be the number of the client
    private int getNewClientID(){
        int id = -1;
        for(int i = 0; i < clients.size(); i++){
            if(clients.get(i) == null)
                id = i;
        }
        if(id == -1){
            id = clients.size();
        }
        return id;
    }

    //get the client based on given ID
    public Client getClient(int index){
        if(clients.size() > index && clients.get(index) != null)
            return clients.get(index);
        return null;
    }

    // gets rid of reference to the client, when it is closed
    // other operations can be added when closing a client here.
    public void closeClient(Client client){
        if(clients.get(client.getID()) != null){
            clients.add(client.getID(), null);
        }
    }

    // gets client by name and ip address.
    public Client getClientByIpAndName(String name, String ip){
        Client c = null;
        for(Client client : clients){
            if(client.getName().equalsIgnoreCase(name) &&
                client.getIPAddr().equals(ip)){
                    c = client;
                    break;
                }
        }
        return c;
    }

    public static void main(String args[]){
        new Server();
    }

}