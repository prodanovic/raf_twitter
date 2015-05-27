package rs.raf.webprog.socket;

import java.io.Console;
import java.io.IOException;


public class Client {



    public static void main (String args[]) throws IOException {
        Client client = new Client();

        Console console = System.console();
        System.out.println("Welcome to RAF client.");


        boolean work = true;
        while(work){
            String command = console.readLine("Please enter one of the following commands:\n" +
                    "[1] Register as a new user.\n" +
                    "[2] Already registered? Please login.\n" +
                    "[3] Exit.");
            int c = Integer.parseInt(command);
            switch (c){
                case 1:  System.out.println("1");break;
                case 2:  System.out.println("2");break;
            }
        }



    }
}
