package rs.raf.webprog.socket;


import rs.raf.webprog.mysql.domain.User;

import java.io.*;
import java.net.Socket;

public class ClientTwitter {

    private PrintWriter outToServer;
    private ObjectInputStream inFromServer;
    private Socket socket;
    private int portNumber;
    private static int clientId=0;
    private int id;
    private User loggedInUser;



    public ClientTwitter(int portNumber) throws IOException {
        id=++clientId;
        Socket socket = new Socket("localhost",portNumber);
//        inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));//new InputStreamReader(System.inFromServer)
        inFromServer = new ObjectInputStream(socket.getInputStream());

        outToServer = new PrintWriter(socket.getOutputStream(), true);

    }

    public int getId(){
        return id;
    }

    public boolean userExistInDB(String username) throws IOException, ClassNotFoundException {
        String command = Constants.USER_EXISTS_COMMAND+Constants.SEP+username;
//        System.outToServer.println("client sent:"+command);
        outToServer.println(command);
        Boolean success = (Boolean)inFromServer.readObject();
//        System.outToServer.println("client received: "+serverAnswer);
        return success;
    }

    public boolean registerUser(String username,String password) throws IOException, ClassNotFoundException {
        String command = Constants.REGISTER_COMMAND+Constants.SEP+username+Constants.SEP+password;
        outToServer.println(command);
        Boolean success = (Boolean)inFromServer.readObject();
        return success;
    }
    public boolean login(String username,String password) throws IOException, ClassNotFoundException {
        String command = Constants.LOGIN_COMMAND+Constants.SEP+username+Constants.SEP+password;
        outToServer.println(command);
        return (Boolean)inFromServer.readObject();
    }

    public boolean logout(String username) throws IOException, ClassNotFoundException {
        String command = Constants.LOGOUT_COMMAND+Constants.SEP+username;
        outToServer.println(command);
        return (Boolean)inFromServer.readObject();
    }
    public String getAllUsers() throws IOException, ClassNotFoundException {
        String command = Constants.GET_ALL_USERS_COMMAND;
        outToServer.println(command);
        return (String)inFromServer.readObject();
    }
    public String getUserByUsername(String username) throws IOException, ClassNotFoundException {
        String command = Constants.GET_USER_COMMAND+Constants.SEP+username;
        outToServer.println(command);
        return (String)inFromServer.readObject();
    }
    public boolean followUser(String follower,String followed) throws IOException, ClassNotFoundException {
        String command = Constants.FOLLOW_USER_COMMAND+Constants.SEP+follower+Constants.SEP+follower;
        outToServer.println(command);
        return (Boolean)inFromServer.readObject();
    }
    public boolean unFollowUser(String follower,String followed) throws IOException, ClassNotFoundException {
        String command = Constants.UNFOLLOW_USER_COMMAND+Constants.SEP+follower+Constants.SEP+follower;
        outToServer.println(command);
        return (Boolean)inFromServer.readObject();
    }
    public boolean tweet(String username,String tweet) throws IOException, ClassNotFoundException {
        String command = Constants.UNFOLLOW_USER_COMMAND+Constants.SEP+username+Constants.SEP+tweet;
        outToServer.println(command);
        return (Boolean)inFromServer.readObject();
    }
    public String getFollowedTweets(String username) throws IOException, ClassNotFoundException {
        String command = Constants.GET_FOLLOWED_TWEETS_COMMAND+Constants.SEP+username;
        outToServer.println(command);
        return (String)inFromServer.readObject();
    }

    public static void main(String[] args) throws IOException {
//        ClientTwitter clientTwitter = new ClientTwitter(8080);
//        clientTwitter.registerUser("user1","pass");

    }
}
