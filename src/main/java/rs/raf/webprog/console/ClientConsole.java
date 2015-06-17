package rs.raf.webprog.console;

import rs.raf.webprog.mysql.Database;
import rs.raf.webprog.socket.ClientTwitter;
import rs.raf.webprog.socket.ServerTwitter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.List;


public class ClientConsole {


    public void work() throws IOException, SQLException, ClassNotFoundException {
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
                    while(clientTwitter.userExistInDB(username)){
                        System.out.println("Username already taken. Try again.");
                        System.out.println("Enter username:");
                        username= console.readLine();
                    }
                    System.out.println("Enter password:");
                    String password= console.readLine();
                    if(clientTwitter.registerUser(username,password))
                        System.out.println("Congratulations. You are registered with RAF Twitter. ");
                    break;
                }
                case 2: {
                    System.out.println("Enter username:");
                    String username= console.readLine();
                    System.out.println("Enter password:");
                    String password= console.readLine();
                    while(!clientTwitter.login(username,password)){
                        System.out.println("Wrong username and/or password. Please try again. " );
                        System.out.println("Enter username:");
                        username= console.readLine();
                        System.out.println("Enter password:");
                        password= console.readLine();
                    }
                    boolean loggedIn=true;
                    while(loggedIn){
                        System.out.println("Hi " +username+".\n"+
                                "Please enter one of the following commands:\n" +
                                "[1] List all users in the system.\n" +
                                "[2] Search for user by username.\n" +
                                "[3] Follow user.\n" +
                                "[4] Un-follow user.\n" +
                                "[5] Tweet something.\n"+
                                "[6] Search user tweets.\n"+
                                "[7] Logout." );
                        command = console.readLine();
                        int c2 = Integer.parseInt(command);
                        switch(c2){
                            case 1:{
                                System.out.println("All registered users: "+clientTwitter.getAllUsers());
                                break;
                            }
                            case 2:{
                                System.out.print("Enter username: ");
                                String searchedUser = console.readLine();
                                System.out.println(clientTwitter.getUserByUsername(searchedUser));
                                break;
                            }
                            case 3:{
                                System.out.print("Which user would you like to follow? ");
                                String followUser = console.readLine();
                                if(clientTwitter.followUser(username,followUser))
                                    System.out.println("You are now following "+ clientTwitter.getFollowedUsers(username)+".");
                                else System.out.println("User "+ followUser+" doesn't exist.");
                                break;
                            }
                            case 4:{
                                System.out.println("You are following users: " + clientTwitter.getFollowedUsers(username) );
                                System.out.print("Which user would you like to un-follow? ");
                                String followUser = console.readLine();
                                if(clientTwitter.unFollowUser(username,followUser))
                                    System.out.println("You are no longer following "+ followUser+".");
                                else System.out.println("User "+ followUser+" doesn't exist.");
                                break;
                            }
                            case 5:{
                                System.out.println("Gukni nesto golube. ");
                                String tweet = console.readLine();
                                clientTwitter.tweet(username,tweet);
                                break;
                            }
                            case 6:{
                                System.out.println("You follow: "+clientTwitter.getFollowedUsers(username));
                                System.out.println("Which friends's tweets would you like to see?(leave blank for all)");
                                String filterUser = console.readLine();
                                if(filterUser.isEmpty())filterUser="*";
                                System.out.println("From how long back, in format yyyy-MM-dd HH:mm:ss?(leave blank for all)");
                                String startDate = console.readLine();
                                if(startDate.isEmpty())startDate="*";
                                System.out.println("How many tweets?(0 for all)");
                                Integer count = Integer.parseInt(console.readLine());
                                String tweetString= clientTwitter.getFollowedTweets(username, filterUser, startDate, "*", count);
                                if(tweetString.isEmpty())
                                    System.err.println("Sorry, no tweets. ");
                                else {
                                    System.out.println("User\tTweet\tWhen");
                                    System.out.print(tweetString);
                                }
                                break;
                            }
                            case 7:{
                                clientTwitter.logout(username);
                                loggedIn=false;
                                break;
                            }
                        }
                        System.out.println();
                        try {
                            int i=0;
                            while(i++<15){
                                Thread.sleep(200);
                                System.out.print("-");
                            }
                            System.out.println();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    break;
                }
                case 3: {

                    System.exit(0);
                }
            }
        }
    }

    public static void main (String args[]) throws IOException, SQLException, ClassNotFoundException {
        ClientConsole clientConsole = new ClientConsole();
        clientConsole.work();
    }
}
