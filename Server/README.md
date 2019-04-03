### CS440 Lab 1

Alec Waichunas
Angel Galeana
April 4th, 2019
Prof. Bidyut Gupta  

#### Introduction


#### Execution
The Server must be compiled to run. To compile the server, you can use any Java supported IDE to immediately start it, otherwise you can compile it through a CLI with javac and run it with java.

#### The Server Program
The Server Program consists of 4 classes. Each with their own purpose to connect to the client, and accept different commands through it.

The entry class of the program is the server class. It creates a server socket using Java's net API. The server then calls an init method, which goes into a loop that will accept client connections, assign them an ID and create a Client object. 
````java
while(!server.isClosed()){
 try{
  Socket clientSocket = server.accept();
  int id = this.getNewClientID();
  Client client = new Client(id, clientSocket, this);
  clients.add(id, client);
 }catch(IOException e){
  //could not connect to client
  e.printStackTrace();
 }
}
````

After the client is accepted and created the client class starts up. In the client's class constructor it creates an object called ClientInput, which is another class. This class creates a seperate thread and listens to the client socket for incoming connections.
````java
String data;
try{
 while((data = input.readLine()) != null)
  client.readClient(data);
 }catch(IOException e){
  e.printStackTrace();
}
````
Once the client has sent a message and the readClient method is ran in the Client class, the program checks to see if the client has a chatroom. If the client is in a chatroom the message will be sent to the the other clients through the ChatRoom object, otherwise the message is tested for being a command.
````java
if(chatRoom == null){
 //looking for command
 String[] commands = message.split(" ");
 if(commands[0].equalsIgnoreCase("talk")){
  //creates a chat room with one other client
  .
  .
  .
  this.chatRoom = new ChatRoom(clients);
 }else if(commands[0].equalsIgnoreCase("conference") &&
  commands[1].equalsIgnoreCase("talk")){
  //create a chat room with multiple clients
  .
  .
  .
  this.chatRoom = new ChatRoom(clients);
 }else if(commands[0].equalsIgnoreCase("accept")){
  //check to see if chatroom/client exists and accept request
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
````
If the client creates a chatroom with one or multiple clients, it will create a new chatroom object from the ChatRoom class, that will send a message to the other clients. The other clients will not be assigned the chatroom until they accept the connection. Once the client has accepted the connection and sends a message the chatroom class will cast the message to the other clients.

````java
for(Client c : this.clients){
 if(!c.equals(client) && c.getChatRoom().equals(this))
  c.sendToClient(client.getName() + ": " + message);
}
````


#### The Client Program

#### Error detection within the programs
Java is very easy to work with, and to detect errors between the sockets, they can simply be surrounded with try and catch blocks. These blocks will catch the errors and can deal with each error seperately. 

#### Known Bugs
There is a bug that was not caught when creating the program. 
When the client is waiting on the server for the response there is no timeout.
This could leave the client in a hanging state. 
To fix this a simple timeout function could be made with a goroutine to timeout after x amount of time.
 
#### Packages
There are a few java packages that were used to create this program.
The net package was used to let the server listen on a port, and for the client to connect to the server.
The io package was used to read and write data from the client and server.
The util package was used to create linked list to create track of the clients in the server.

#### Possible Improvements

 
#### Conclusion

