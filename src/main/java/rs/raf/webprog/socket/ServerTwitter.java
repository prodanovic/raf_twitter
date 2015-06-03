package rs.raf.webprog.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class ServerTwitter {


    private int portNumber;

    public ServerTwitter(int portNumber) {
        this.portNumber = portNumber;
    }

    public void work(){
        boolean listening = true;
        try {
            ServerSocket serverSocket = new ServerSocket(portNumber);
            while (listening) {
                new ServerTwitterThread(serverSocket.accept()).start();
            }
        } catch (IOException e) {
            System.err.println("Could not listen on port " + portNumber);
            System.exit(-1);
        }
    }

    public static void main(String[] args) throws IOException {
        ServerTwitter serverTwitter = new ServerTwitter(8080);
        serverTwitter.work();


    }

}
