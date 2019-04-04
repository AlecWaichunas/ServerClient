package com.networking.chat.client;

import java.io.BufferedReader;
import java.io.IOException;

import com.networking.chat.Client;

public class ClientInput implements Runnable{
    
    private Client client;
    private BufferedReader input;

    public ClientInput(Client client, BufferedReader input){
        this.client = client;
        this.input = input;

        //start the new thread
        (new Thread(this)).start();
    }

    public void run(){
        String data;
        try{
            while((data = input.readLine()) != null && data.length() > 0){
                client.readClient(data);
            }
        }catch(IOException e){
            e.printStackTrace();
        }

        closeClient();
    }

    private void closeClient(){
        try{
            input.close();
        }catch(IOException e){
            e.printStackTrace();
        }
        this.client.closeClient();
    }

}