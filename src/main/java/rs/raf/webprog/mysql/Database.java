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
        resultSet = statement.executeQuery("select * from user;");
        ArrayList<String> list = getAllUsers();
        StringBuilder result = new StringBuilder();
        for(String l:list)result.append(", "+l);
        return "("+result.toString().replaceFirst(", ","")+")";
    }


    public void addUser(String username,String password) throws SQLException {
        if(!userExistInDB(username))
        statement.execute("INSERT INTO user VALUES ('" + username + "','" + password + "')");
    }

    public void followUser(String follower,String followed) throws SQLException {
        resultSet = statement.executeQuery("select * from followers WHERE follower='"+follower+"' AND followed='" + followed + "'");
        if(!resultSet.next() && userExistInDB(follower) && userExistInDB(followed))
            statement.execute("INSERT INTO followers VALUES ('" + followed + "','" + follower + "')");
    }

    public void unFollowUser(String follower,String followed) throws SQLException {
        statement.execute("DELETE FROM followers WHERE followed='" + followed + "' AND follower='" + follower + "'");
    }
    public List<String> getFollowedFriends(String user) throws SQLException {
        List<String> result = new ArrayList<>();
        resultSet = statement.executeQuery("select * from followers WHERE follower='"+user+"'");
        while (resultSet.next()){
            result.add(resultSet.getString(1));
        }
        return result;
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




    public static void main(String[] args) throws SQLException, ParseException {
        Database database = new Database();
//        System.out.println("userExistInDB JA: "+ database.userExistInDB("JA2"));
        database.addUser("JA5","pass");
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
        List<String> list2 = database.getFollowedTweets("JA2", "JA", "2015-06-11 19:02:28.0",null,0);
        for(String tweet:list2)System.out.println(tweet);


    }
}
