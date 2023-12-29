// file yang lama

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.function.Consumer;

public class Client extends Thread {
    String IP;
    Integer Port;
    Boolean isConnected;
    Socket socketClient;

    ObjectOutputStream out;
    ObjectInputStream in;

    private Consumer<Serializable> callback;

    Client(Consumer<Serializable> call)
    {
        isConnected = false;
        callback = call;
    }

    public void run() {
        try {
            socketClient = new Socket(IP, Port);
            out = new ObjectOutputStream(socketClient.getOutputStream());
            in = new ObjectInputStream(socketClient.getInputStream());
            socketClient.setTcpNoDelay(true);
        }
        catch(Exception e) {
            System.out.println("Unable to connect to the server.");
        }
        isConnected = true;
        // Listening for messages
        while(true) {

            try {
                String message = in.readObject().toString();
                // Accept a message and pass it along to the call
                callback.accept(message);
            }
            catch(Exception e) {}
        }
    }
    // to test the send file
    public void send(MorraInfo data) {
        try {
            out.writeObject(data);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void setIP(String IP)
    {
        this.IP = IP;
    }

    public void setPort(Integer Port) {
        this.Port = Port;
    }
}
