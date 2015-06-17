package rs.raf.webprog.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.*;

public class Database {
    private Connection connection = null;
    private Statement statement = null;
    private ResultSet resultSet = null;


    public Database() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost/twitter?user=root&password=");
           // Statements allow to issue SQL queries to the database
            statement = connection.createStatement();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean userExistInDB(String username) throws SQLException {
        resultSet = statement.executeQuery("select * from user where username='"+username+"'");
        return resultSet.next();
    }

    public ArrayList<String> getAllUsers() throws SQLException {
        resultSet = statement.executeQuery("select * from user;");
        ArrayList<String> result = new ArrayList<>();
        while(resultSet.next()){
            result.add(resultSet.getString("username"));
        }
        return result;
    }

    public String getAllUsersConcatenated() throws SQLException {
        return getListAsString(getAllUsers());
    }

    private String getListAsString(List<String> list) throws SQLException {
        StringBuilder result = new StringBuilder();
        for(String l:list)result.append(", "+l);
        return "("+result.toString().replaceFirst(", ","")+")";
    }
    public String getUserDetails(String username) throws SQLException {
        resultSet = statement.executeQuery("select * from user where username='"+username+"'");
        if(resultSet.next()){
            return "User :"+resultSet.getString(1);
        }
        else return "Searched user does not exist.";
    }


    public void addUser(String username,String password) throws SQLException {
        if(!userExistInDB(username))
        statement.execute("INSERT INTO user VALUES ('" + username + "','" + password + "','0')");
    }

    public boolean isUserLoggedIn(String username) throws SQLException {
        if(userExistInDB(username)){
            resultSet = statement.executeQuery("select * from user where username='"+username+"'");
            resultSet.next();
            return resultSet.getBoolean(3);
        }
        else return false;
    }

    public boolean login(String username,String password) throws SQLException {
        resultSet = statement.executeQuery("select * from user where username='"+username+"' AND password='"+password+"'");
        if(resultSet.next()){
            statement.executeUpdate("update user SET loggedIn='1' where username='"+username+"'");
            return true;
        }
        else return false;
    }
    public boolean logout(String username) throws SQLException {
        resultSet = statement.executeQuery("select * from user where username='"+username+"'");
        if(resultSet.next()){
            statement.executeUpdate("update user SET loggedIn='0' where username='"+username+"'");
            return true;
        }
        else return false;
    }

    public boolean followUser(String follower,String followed) throws SQLException {
        resultSet = statement.executeQuery("select * from followers WHERE follower='"+follower+"' AND followed='" + followed + "'");
        if(!resultSet.next() && userExistInDB(follower) && userExistInDB(followed)){
            statement.execute("INSERT INTO followers VALUES ('" + followed + "','" + follower + "')");
            return true;
        }
        else return false;

    }
    public boolean unFollowUser(String follower,String followed) throws SQLException {
        if(userExistInDB(follower) && userExistInDB(followed)){
            statement.execute("DELETE FROM followers WHERE followed='" + followed + "' AND follower='" + follower + "'");
            return true;
        }
        else return false;
    }
    public List<String> getFollowedFriends(String username) throws SQLException {
        List<String> result = new ArrayList<>();
        resultSet = statement.executeQuery("select * from followers WHERE follower='"+username+"'");
        while (resultSet.next()){
            result.add(resultSet.getString(1));
        }
        return result;
    }
    public String getFollowedConcatenated(String username) throws SQLException {
        return getListAsString(getFollowedFriends(username));
    }

    public void tweetSomething(String user, String tweet) throws SQLException, ParseException {
        statement.execute("INSERT INTO tweet VALUES ('" + user + "','" + tweet + "',NOW())");
    }

    public List<String> getUserTweets(String user, String startDate, String endDate, Integer count) throws SQLException {
        List<String> tweets = new ArrayList<>();
        StringBuilder queryString = new StringBuilder("select * from tweet where username='"+user+"' ");
        if(startDate!=null)queryString.append(" AND createdAt>=").append("'"+startDate+"'");
        if(endDate!=null)queryString.append(" AND createdAt<=").append("'"+endDate+"'");
        if(count>0)queryString.append(" LIMIT ").append(count);

        resultSet = statement.executeQuery(queryString.toString());
        while(resultSet.next())tweets.add(resultSet.getString(1)+"\t\t"+resultSet.getString(2)+"\t\t"+resultSet.getString(3));
        return tweets;
    }

    public List<String> getFollowedTweets(String user, String filterUser, String startDate, String endDate, Integer count) throws SQLException {
        List<String> tweets = new ArrayList<>();
        StringBuilder queryString = new StringBuilder("SELECT t.username, t.tweet, t.createdAt FROM tweet t, followers f " +
                " WHERE f.follower='"+user+"' AND f.followed= t.username");
        if(filterUser!=null)queryString.append(" AND t.username=").append("'"+filterUser+"'");
        if(startDate!=null)queryString.append(" AND createdAt>=").append("'"+startDate+"'");
        if(endDate!=null)queryString.append(" AND createdAt<=").append("'"+endDate+"'");
        if(count>0)queryString.append(" LIMIT ").append(count);

        resultSet = statement.executeQuery(queryString.toString());
        while(resultSet.next())tweets.add(resultSet.getString(1)+"\t\t"+resultSet.getString(2)+"\t\t"+resultSet.getString(3));
        return tweets;
    }
    public String getFollowedTweetsConcatenated(String user, String filterUser, String startDate, String endDate, Integer count) throws SQLException {
        StringBuilder result  = new StringBuilder();
        List<String> tweets = getFollowedTweets(user,filterUser, startDate,endDate,count);
        for(String tweet:tweets)result.append(tweet+"\n");
        return result.toString();
    }





    public static void main(String[] args) throws SQLException, ParseException {
        Database database = new Database();
//        System.out.println("userExistInDB JA: "+ database.userExistInDB("JA2"));
        database.addUser("JA7","pass");
        System.out.println("JA7 logged: "+ database.isUserLoggedIn("JA7"));
        System.out.println("wrong login: "+ database.login("JA7","wpass"));
        System.out.println("login: "+ database.login("JA7","pass"));
        System.out.println("JA7 logged: "+ database.isUserLoggedIn("JA7"));

        database.addUser("JA","pass");
        database.addUser("JA2","pass");
//        System.out.println("userExistInDB JA2: "+ database.userExistInDB("JA2"));
//        System.out.println("all users: "+ database.getAllUsersConcatenated());

//        database.tweetSomething("JA","333  1 ");
//        database.tweetSomething("JA2","333 2 ");
//        database.tweetSomething("JA5","333 3");
//        database.tweetSomething("JA5","333 4");



        database.followUser("JA2","JA");
        database.followUser("JA2","JA5");
//        database.unFollowUser("JA2", "JA");
//        List<String> list = database.getFollowedFriends("JA2");
//        for(String friend:list)System.out.print(friend+", ");


//        List<String> list = database.getUserTweets("JA5", "2015-06-06 15:58:52", "2015-06-09 00:58:07", 4);
//        for(String tweet:list)System.out.println(tweet);
        List<String> list2 = database.getFollowedTweets("JA2", "JA", "2015-06-11 19:02:28.0", null, 0);
        for(String tweet:list2)System.out.println(tweet);


    }
}
