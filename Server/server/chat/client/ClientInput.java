import java.io.BufferedReader;

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
        while((data = input.readLine()) != null){
            client.readClient(data);
        }
        closeClient();
    }

    private void closeClient(){
        input.close();
        this.client.closeClient();
    }

}