package pl.zadanie;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server extends Socket {
    private ServerSocket serverSocket;
    List<ClientThread> clients = new ArrayList<>();
    public Server(int port) {
        try {
            this.serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void broadcast(String message, ClientThread sender){
        for(ClientThread receiver : clients){
            if(receiver != sender){
                receiver.send(message);
            }
        }
    }

    public void listen(){
        while(true){
            try {
                Socket clientSocket = serverSocket.accept();
                ClientThread clientThread =  new ClientThread(clientSocket, this);
                clients.add(clientThread);
                clientThread.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
