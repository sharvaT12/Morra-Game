import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Objects;
import java.util.function.Consumer;

import javafx.application.Platform;
import javafx.scene.control.ListView;
/*
 */
public class Server {
	// this is the global variable for running the game
	Integer player1ID_temp = 0;
	String passVal1Guess_temp= "";
	Integer p1Points_temp = 0;
	// the finger guessed by the player
	Integer passVal1Play_temp = 0;
	Integer passVal2Play_temp = 0;
	Integer player2ID_temp = 0;
	String passVal2Guess_temp = "";
	Integer p2Points_temp = 0;
	Integer submitted = 0;
	Integer playerConnected = 0;
	// the player
	int count = 1;
	ArrayList<ClientThread> clients = new ArrayList<ClientThread>();
	TheServer server;
	private Consumer<Serializable> callback;
	Integer Port;

	// starting the constructor for the server variable
	Server(Consumer<Serializable> call, Integer port){
		callback = call;
		this.Port = port;
		server = new TheServer();
		server.start();
	}

	// setting the port value
	public void setPort(Integer Port) {
		this.Port = Port;
	}


	public class TheServer extends Thread {
		// when we running the run
		public void run(){
			try(ServerSocket mysocket = new ServerSocket(Port);){
		    System.out.println("Server is waiting for a client on port : " + Port);
			// while the value is true
		    while(true) {
				// it mentioned to the listview that the client has been connected
				ClientThread c = new ClientThread(mysocket.accept(), count);
				callback.accept("client has connected to server: " + "client #" + count);
				clients.add(c);
				c.start();
				count++;
			    }
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
			// seeting the guess if it's false
			Boolean guess1_temp = false;
			Boolean guess2_temp = false;

			Integer total = 0;
			
			ClientThread(Socket s, int count){
				this.connection = s;
				this.count = count;	
			}
			// processing the logic of the game
			public void playGame() throws IOException, ClassNotFoundException {
				total = passVal1Play_temp + passVal2Play_temp;
				// if the guess of player 1 and total is equal it will be true
				if(Objects.equals(passVal1Guess_temp, String.valueOf(total))){
					guess1_temp = true;
				}
				// if the player 2 and total is equal it will be true
				else if(Objects.equals(passVal2Guess_temp, String.valueOf(total))){
					guess2_temp = true;
				}
				// if not equal it will not be true
				else if(!Objects.equals(passVal1Guess_temp, String.valueOf(total))){
					guess1_temp = false;
				}
				// if not equal it will not be true
				else if(!Objects.equals(passVal2Guess_temp, String.valueOf(total))){
					guess2_temp = false;
				}

				// if both of player guessed it correctly they will get 0 points
				if(guess1_temp == guess2_temp){
					p1Points_temp += 0;
					p2Points_temp += 0;
				}
				else if(guess1_temp && !guess2_temp){
					p1Points_temp += 1;
					p2Points_temp += 0;
				}
				else if(guess2_temp && !guess1_temp) {
					p1Points_temp += 0;
					p2Points_temp += 1;
				}
			}

			//updating the client to the update client
			public void printmessage(){
				updateClients("client #" + player1ID_temp + " choose hand : " + passVal1Play_temp); //this is int
				updateClients("client #" + player1ID_temp + " guess total : " + passVal1Guess_temp); // this is string
				updateClients("client #" + player2ID_temp + " choose hand : " + passVal2Play_temp); // this is int
				updateClients("client #" + player2ID_temp + " guess total : " + passVal2Guess_temp); // this is string
				updateClients("client #" + player1ID_temp + " has total points of " + p1Points_temp);
				updateClients("client #" + player2ID_temp + " has total points of " + p2Points_temp);
			}

			// updating the client
			public void updateClients(String message) {
				for(int i = 0; i < clients.size(); i++) {
					ClientThread t = clients.get(i);
					try {
					 t.out.writeObject(message);
					}
					catch(Exception ignored) {}
				}
			}

			
			public void run(){
					
				try {
					in = new ObjectInputStream(connection.getInputStream());
					out = new ObjectOutputStream(connection.getOutputStream());
					connection.setTcpNoDelay(true);	
				}
				catch(Exception e) {
					System.out.println("Streams not open");
				}
				// player connected mention how many players are there
				playerConnected++;
				updateClients("new client on server: client # "+count);
				callback.accept("Total player connected : " + playerConnected); //this is int
				callback.accept("client #" + player1ID_temp + " choose hand : " + passVal1Play_temp); //this is int
				callback.accept("client #" + player1ID_temp + " guess total : " + passVal1Guess_temp); // this is string
				callback.accept("client #" + player2ID_temp + " choose hand : " + passVal2Play_temp); // this is int
				callback.accept("client #" + player2ID_temp + " guess total : " + passVal2Guess_temp); // this is string
				callback.accept("client #" + player1ID_temp + " has total points of " + p1Points_temp);
				callback.accept("client #" + player2ID_temp + " has total points of " + p2Points_temp);
				//anyone won the game

				// are they playing again


				// when it connects it goes ^^ and current state of count is 1
				// count 2
				while(true) {
					// masuk count 1
					// kalo gua re submit count 1
					if(count == 1) {
						try {
							MorraInfo data = (MorraInfo) in.readObject();

							// store the value in MorraInfo
							data.player1ID = count;
							data.passVal1Play = data.p1Guess;
							data.passVal1Guess = data.p1TotalGuess;

//							 store locally
							player1ID_temp = 1;
							passVal1Guess_temp = String.valueOf(data.p1TotalGuess); // this is string
							passVal1Play_temp = data.p1Guess; // this is int

//							player1ID_temp = 1;
//							passVal1Guess_temp = String.valueOf(8); // this is string
//							passVal1Play_temp = 3; // this is int

//							System.out.println("local reach in try client ID: " + player1ID_temp);
//							System.out.println("local client Guess total: " + passVal1Guess_temp);
//							System.out.println("local client play hand: " + passVal1Play_temp);

							callback.accept("client: " + count + " sent: " + data.passVal1Play);
							callback.accept("client " + count + " said: " + data.p1TotalGuess);
							submitted++;
//							playGame();
							//updateClients("client #" + count + " said: " + data.p1TotalGuess);

						} catch (Exception e) {
							// minus the count and the player connected
							count--;
							playerConnected--;
							callback.accept("OOOOPPs...Something wrong with the socket from client: " + count + "....closing down!");
							updateClients("Client #" + count + " has left the server!");
							System.out.println("Count value (b) error: " + count);
							clients.remove(this);
							break;
						}
					}
					// taking the input for the second player
					else if(count == 2){
						try {
							MorraInfo data = (MorraInfo) in.readObject();

							// store the value to MorraInfo
							data.player2ID = 2;
							data.passVal2Play = data.p1Guess;
							data.passVal2Guess = data.p1TotalGuess;

							// store the value locally
							player2ID_temp = count;
							passVal2Guess_temp = String.valueOf(data.p1TotalGuess); // this is string
							passVal2Play_temp = data.p1Guess; // this is int

							callback.accept("client: " + count + " sent: " + data.passVal2Play);
							callback.accept("client " + count + " said: " + data.passVal2Guess);

							System.out.println("calling play game");
							submitted++;
//							playGame();
//							printmessage();

						} catch (Exception e) {
							count--;
							playerConnected--;
							callback.accept("OOOOPPs...Something wrong with the socket from client: " + count + "....closing down!");
							updateClients("Client #" + count + " has left the server!");
							clients.remove(this);
							System.out.println("Count value in 2 (d) error : " + count);
							break;
						}
					}
					// to check if the player is connected
					// test cases handling for the third client
					else{
						updateClients("Client #" + count + " is waiting for another client to leave!");
						break;
					}
					if(submitted == 2) {
						// to check if somebody has won the game
						if(p1Points_temp == 2){
							updateClients("Client 1 won");
							break;
						}
						else if (p2Points_temp== 2){
							updateClients("Client 2 won");
							break;
						}
						// load the function for them to play again
						try {
							playGame();
						} catch (IOException | ClassNotFoundException e) {
							e.printStackTrace();
						}
						// print out the message to the client
						printmessage();
						// reset the value of the input for the people to 0
						submitted = 0;
					}
				}
			}//end of run
		}//end of client thread
}


	
	

	
