package rs.raf.webprog.console;

import rs.raf.webprog.socket.ClientTwitter;
import rs.raf.webprog.socket.ServerTwitter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;


public class ClientConsole {


    public void work() throws IOException {
//        ServerTwitter serverTwitter = new ServerTwitter(8080);

        ClientTwitter clientTwitter= new ClientTwitter(8080);
        System.out.println("Welcome to RAF client.");

        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));

        boolean work = true;
        while(work){

            System.out.println("Please enter one of the following commands:\n" +
                    "[1] Register as a new user.\n" +
                    "[2] Already registered? Please login.\n" +
                    "[3] Exit.");
            String command = console.readLine();

            int c = Integer.parseInt(command);
            switch (c){
                case 1:  {
                    System.out.println("Enter username:");
                    String username= console.readLine();
                    System.out.println("Enter password:");
                    String password= console.readLine();
                    while(!clientTwitter.registerUser(username,password)){
                        System.out.println("Username already taken. Try again.");
                        System.out.println("Enter username:");
                        username= console.readLine();
                        System.out.println("Enter password:");
                        password= console.readLine();
                    }
                    System.out.println("Congratulations. You are registered with RAF Twitter. ");
                    break;
                }
                case 2: {
                    System.out.println("Enter username:");
                    String username= console.readLine();
                    System.out.println("Enter password:");
                    String password= console.readLine();
                    System.out.println("You are logged in RAF Twitter./n " +
                            "Please enter one of the following commands:\n" +
                            "[1] Search for user by username.\n" +
                            "[2] Follow user by username.\n" +
                            "[3] List all users.\n" +
                            "[4] Tweet something.\n");
                    int c2 = Integer.parseInt(command);
                    switch(c2){
                        case 1:
                    }
                    break;
                }
                case 3: {
                    System.exit(0);
                }
            }
        }
    }

    public static void main (String args[]) throws IOException {
        ClientConsole clientConsole = new ClientConsole();
        clientConsole.work();
    }
}
