import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.function.Consumer;



public class Server{

	private int count = 1;
	private ArrayList<ClientThread> clients = new ArrayList<ClientThread>();
	private TheServer server;
	private Consumer<Serializable> callback;
	private ArrayList<Integer> clientLists = new ArrayList<>();
	private ArrayList<Integer> numClients = new ArrayList<>();
	
	ClientInfo clientInfo;
	
	Server(Consumer<Serializable> call){
	
		callback = call;
		server = new TheServer();
		server.start();
	}
	
	//Overwriting constructor.
	Server()
	{
		
	}
	
	
	public class TheServer extends Thread{
		
		private Object lock = new Object();
		
		public void run() {
		
			try(ServerSocket mysocket = new ServerSocket(5555);){
		    System.out.println("Server is waiting for a client!");
		  
				synchronized(lock)
				{
				    while(true) {
				
						ClientThread c = new ClientThread(mysocket.accept(), count);
						
						clientLists.add(count);
						
						callback.accept("client has connected to server: " + "client #" + count);
						clients.add(c);
						c.start();
						
						count++;
						
					    }
				}//end of lock
			}//end of try
				catch(Exception e) {
					callback.accept("Server socket did not launch");
				}
			}//end of while
		}
	

		class ClientThread extends Thread{
			
		
			Socket connection;
			int count;
			ObjectInputStream in;
			ObjectOutputStream out;
			
			private Object lock = new Object();
			
			
			ClientThread(Socket s, int count){
				this.connection = s;
				this.count = count;	
			}
			
			public synchronized void updateClients(ClientInfo message) {
				
				
					for (int i = 0; i < clients.size(); i++) {
						ClientThread t = clients.get(i);
						try {
							t.out.reset();
							t.out.writeObject(message);
						} catch (Exception e) {
						}
					}
			}
			
			public synchronized void updateClientsSingle(ClientInfo message, ArrayList<Integer>numClients)
			{
				for(int i = 0; i < numClients.size(); i++)
				{
					int index = numClients.get(i);
					
					ClientThread t = clients.get(index);
					try {
						t.out.reset();
						t.out.writeObject(message);
					}catch(Exception e){}
				}
			}
			
			public synchronized void updateClientsFirst(ClientInfo message)
			{
				ClientThread t = clients.get(clients.size() - 1);
				try {
					t.out.reset();
					t.out.writeObject(message);
				}catch(Exception e) {}
			}
			
			public void run(){

					try {
						in = new ObjectInputStream(connection.getInputStream());
						out = new ObjectOutputStream(connection.getOutputStream());
						connection.setTcpNoDelay(true);
					} catch (Exception e) {
						System.out.println("Streams not open");
					}
						
					clientInfo = new ClientInfo();
					
					clientInfo.setClientList(clientLists);
					
					clientInfo.setMessage("new client on server: client #" + count );
					
					updateClients(clientInfo);
					
					clientInfo.setMessage(" your client number is: #" + count);
					
					updateClientsFirst(clientInfo);
					
					while (true) {
						try {
		
							ClientInfo clientTemp2 = (ClientInfo) in.readObject();
		
							callback.accept("client: " + count + " sent: " + clientTemp2.getMessage());
		
							String str = clientTemp2.getMessage();
		
							String[] splitStr = messageProcess(clientTemp2.getMessage());
		
							
		
							if (splitStr[0].contains("/pm")) {
		
								if (splitStr[1].contains(",")) {
		
									String[] numberClients = splitStr[1].split(",");
		
									for (String i : numberClients) {
										//Check if the clients is in the Sever
										Integer temp;
										try {
											temp = Integer.valueOf(i) - 1;
										} catch (Exception e) {
											// TODO: handle exception
											callback.accept("Format error");
											continue;
										}
		
										try {
											clients.get(temp);
		
											numClients.add(temp);
										} catch (IndexOutOfBoundsException e) {
											callback.accept("Server clients# " + (temp + 1) + " does not exists");
											continue;
										}
									}
		
								} else {
									Integer index;
									try {
										index = Integer.parseInt(splitStr[1]) - 1;
									} catch (Exception e) {
										callback.accept(e.getLocalizedMessage());
										continue;
									}
		
									numClients.add(index);
								}
		
								str = "";
		
								for (int i = 2; i < splitStr.length; i++) {
									str += splitStr[i];
									str += " ";
								}
		
								clientInfo.setMessage("client #" + count + " said: " + str);
								updateClientsSingle(clientInfo, numClients);
							} else {
								clientInfo.setMessage("client #" + count + " said: " + clientTemp2.getMessage());
		
								updateClients(clientInfo);
							}
		
						} catch (Exception e) {
		
							synchronized(lock)
							{
								callback.accept("OOOOPPs...Something wrong with the socket from client: " + count
										+ "....closing down!");
			
								clientInfo.setMessage("Client #" + count + " has left the server!");
			
								clientInfo.removeClient(count);
			
								updateClients(clientInfo);
			
								clients.remove(this);
								
								break;
							}
						} //end of try-catch
					}
			 
			}//end of run
			
			
		}//end of client thread
		
		
		public String[] messageProcess(String str)
		{
			return str.split("\\s+");
		}
}


	
	

	
