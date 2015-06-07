package rs.raf.webprog.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Database {
    private Connection connect = null;
    private Statement statement = null;
    private ResultSet resultSet = null;


    public Database() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager.getConnection("jdbc:mysql://localhost/twitter?user=root&password=root");
           // Statements allow to issue SQL queries to the database
            statement = connect.createStatement();
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
        statement.execute("INSERT INTO user VALUES ('" + username + "','" + password + "')");
    }

    public void followUser(String follower,String followed) throws SQLException {
        resultSet = statement.executeQuery("select * from followers WHERE follower='"+follower+"' AND follower='" + follower + "'");
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

    public List<String> getUserTweetsByFilter(String user, HashMap<String,String> filters) throws SQLException {
        List<String> result = new ArrayList<>();
        StringBuilder queryString = new StringBuilder("select * from tweet where user='"+user+"' ");
        while(filters.entrySet().iterator().hasNext()){
            Map.Entry<String,String> entry = filters.entrySet().iterator().next();
            queryString.append("AND ").append(entry.getKey()).append("='").append(entry.getValue()).append("'");
        }
        resultSet = statement.executeQuery(queryString.toString());
        return result;
    }


    public static void main(String[] args) throws SQLException, ParseException {
        Database database = new Database();
//        System.out.println("userExistInDB JA: "+ database.userExistInDB("JA2"));
//        database.addUser("JA2","pass");
//        database.addUser("JA","pass");
//        System.out.println("userExistInDB JA2: "+ database.userExistInDB("JA2"));
//        System.out.println("all users: "+ database.getAllUsersConcatenated());
//        database.followUser("JA2","JA");
//        database.tweetSomething("JA2","tweeterer3 333 ");

        database.followUser("JA2","JA");
        database.followUser("JA2","JA5");
//        database.unFollowUser("JA2", "JA");
        List<String> list = database.getFollowedFriends("JA2");
        for(String friend:list)System.out.print(friend+", ");

    }
}
