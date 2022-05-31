package pl.zadanie;

import java.io.*;
import java.net.Socket;

public class ClientThread extends Thread{
    private Socket socket;
    private PrintWriter writer;
     Server server;
     private String username;
    public ClientThread(Socket socket,Server server) {
        this.socket = socket;
        this.server = server;
    }
    public void send(String message){
        writer.println(message);
    }
    private boolean isCommand(String message){
        return(message != null && !message.isEmpty() && message.startsWith("$"));
    }
    private void runCommand(String message){
        if(message.startsWith("$login")){
            username = message.split(" ")[1];
        }
      //  else if(message.startsWith("$broadcast")){
        //    server.broadcast(message.split(" ",2),this));
      //  }
    }

    public void run(){
        try {
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
             writer = new PrintWriter(outputStream,true);
            while(true){
                String message = reader.readLine();
                if(isCommand(message)){
                    runCommand(message);
                }
                server.broadcast(message,this);
                System.out.println(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
