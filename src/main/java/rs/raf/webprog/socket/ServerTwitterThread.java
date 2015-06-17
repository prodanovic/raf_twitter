package rs.raf.webprog.socket;
import rs.raf.webprog.mysql.Database;

import java.net.*;
import java.io.*;
import java.sql.SQLException;
import java.text.ParseException;

public class ServerTwitterThread extends Thread {

    private Socket socket = null;
    private Database database;

    public ServerTwitterThread(Socket socket) {
        super("ServerTwitterThread");
        this.socket = socket;
        database = new Database();
    }

    public void run() {
        try{
//            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            ObjectOutputStream outToClient = new ObjectOutputStream(socket.getOutputStream());
            BufferedReader inFromClient  = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String inputLine, outputLine;

            while ((inputLine = inFromClient.readLine()) != null) {
                System.out.println("server received:"+inputLine);
                String[] commandParts = inputLine.split("_");
                String command = commandParts[0];

                switch (command){
                    case Constants.USER_EXISTS_COMMAND:{
                        Boolean success = userExistInDB(commandParts[1]);
                        outToClient.writeObject(success);
                        break;
                    }
                    case Constants.REGISTER_COMMAND:{
                        outToClient.writeObject(registerUser(commandParts[1],commandParts[2]));
                        break;
                    }
                    case Constants.LOGIN_COMMAND:{
                        outToClient.writeObject(login(commandParts[1], commandParts[2]));
                        break;
                    }
                    case Constants.LOGOUT_COMMAND:{
                        outToClient.writeObject(logout(commandParts[1]));
                        break;
                    }
                    case Constants.GET_ALL_USERS_COMMAND:{
                        outToClient.writeObject(getAllUsers());
                        break;
                    }
                    case Constants.GET_USER_COMMAND:{
                        outToClient.writeObject(getUserDetails(commandParts[1]));
                        break;
                    }
                    case Constants.FOLLOW_USER_COMMAND:{
                        outToClient.writeObject(followUser(commandParts[1],commandParts[2]));
                        break;
                    }
                    case Constants.UNFOLLOW_USER_COMMAND:{
                        outToClient.writeObject(unFollowUser(commandParts[1], commandParts[2]));
                        break;
                    }
                    case Constants.GET_FOLLOWED_COMMAND:{
                        outToClient.writeObject(getFollowed(commandParts[1]));
                        break;
                    }
                    case Constants.TWEET_COMMAND:{
                        outToClient.writeObject(tweet(commandParts[1], commandParts[2]));
                        break;
                    }
                    case Constants.GET_FOLLOWED_TWEETS_COMMAND:{
                        String user = commandParts[1];
                        String filterUser = commandParts[2].equals("*")?null:commandParts[2];
                        String startDate = commandParts[3].equals("*")?null:commandParts[3];
                        String endDate = commandParts[4].equals("*")?null:commandParts[4];
                        Integer count = Integer.parseInt(commandParts[5]);
                        outToClient.writeObject(getFollowedTweets(user, filterUser, startDate, endDate, count));
                        break;
                    }

                }
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public boolean userExistInDB(String username) throws SQLException {
        return database.userExistInDB(username);
    }
    public boolean registerUser(String username,String password) {
        try {
            database.addUser(username,password);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean login(String username,String password) throws SQLException {
          return  database.login(username,password);
    }
    public boolean logout(String username) throws SQLException {
        return  database.logout(username);
    }
    public String getAllUsers() throws SQLException {
        return  database.getAllUsersConcatenated();
    }
    public String getUserDetails(String username) throws SQLException {
        return  database.getUserDetails(username);
    }
    public boolean followUser(String follower, String followed) throws SQLException {
        return database.followUser(follower, followed);
    }
    public boolean unFollowUser(String follower, String followed) throws SQLException {
        return database.unFollowUser(follower, followed);
    }
    public String getFollowed(String username) throws SQLException {
        return database.getFollowedConcatenated(username);
    }

    public boolean tweet(String username, String tweet) throws SQLException, ParseException {
        database.tweetSomething(username, tweet);
        return true;
    }
    public String getFollowedTweets(String user, String filterUser, String startDate, String endDate, Integer count) throws SQLException {
        return database.getFollowedTweetsConcatenated(user, filterUser,startDate, endDate, count);
    }
}
