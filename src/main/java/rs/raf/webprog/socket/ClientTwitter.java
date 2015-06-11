package rs.raf.webprog.socket;


import rs.raf.webprog.mysql.Database;
import rs.raf.webprog.mysql.domain.User;

import javax.xml.crypto.Data;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientTwitter {

    private PrintWriter out;
    private BufferedReader in;
    private Socket socket;
    private int portNumber;
    private static int clientId=0;
    private int id;
    private User loggedInUser;



    public ClientTwitter(int portNumber) throws IOException {
        id=++clientId;
        Socket socket = new Socket("localhost",portNumber);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));//new InputStreamReader(System.in)
        out = new PrintWriter(socket.getOutputStream(), true);

    }

    public int getId(){
        return id;
    }

    public boolean userExistInDB(String username)throws IOException{
        String command = Constants.USER_EXISTS_COMMAND+Constants.SEP+username;
        System.out.println("client sent:"+command);
        out.println(command);
        String serverAnswer = in.readLine();
        System.out.println("client received: "+serverAnswer);
        return serverAnswer.equals("OK")?true:false;
    }

    public boolean registerUser(String username,String password) throws IOException {
        String command = Constants.REGISTER_COMMAND+Constants.SEP+username+Constants.SEP+password;
        System.out.println("  to server:"+command);
        out.println(command);
        String serverAnswer = in.readLine();
        System.out.println("server returned: "+serverAnswer);
        return serverAnswer.equals("OK")?true:false;
    }


    public static void main(String[] args) throws IOException {
        ClientTwitter clientTwitter = new ClientTwitter(8080);
        clientTwitter.registerUser("user1","pass");

    }
}
