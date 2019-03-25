import java.net.ServerSocket;
import java.util.List;


// server accepts clients, then sends
// the client to run on another thread. Object maintenace is also
// done here, for continue
public class Server{

    private static final int PORT = 12461;

    private ServerSocket server;
    private List<Client> clients = new ArrayList<Client>();

    //server constructor to start loop for client
    public Server(){
        server = new ServerSocket(PORT);
        init();
    }

    // initializes server
    private void init(){
        while(server.isClosed()){
            Socket clientSocket = socket.accept();
            int id = getNewClientId();
            Client client = new Client(id, clientSocket, this);
            clients.add(id, new Client(client));
        }
    }

    // creates a new clients id. This id will be the number of the client
    private int getNewClientID(){
        for(int i = 0; i < clients.length; i++){
            if(clients.get(i) == null)
                return i;
        }
    }

    //get the client based on given ID
    public Client getClient(int index){
        if(clients.get(index) != null)
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
            if(client.getName().equals(name) &&
                client.getIPAddr().equals(ip)){
                    c = client;
                    break;
                }
        }
        return c;
    }

}