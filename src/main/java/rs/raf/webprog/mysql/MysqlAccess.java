package rs.raf.webprog.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MysqlAccess {
    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;


    public MysqlAccess() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager.getConnection("jdbc:mysql://localhost/twitter?user=root&password=");
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
        statement.execute("INSERT INTO followers VALUES ('" + followed + "','" + follower + "')");
    }

    public static void main(String[] args) throws SQLException {
        MysqlAccess mysqlAccess = new MysqlAccess();
        System.out.println("userExistInDB JA: "+mysqlAccess.userExistInDB("JA2"));
//        mysqlAccess.addUser("JA2","pass");

        System.out.println("userExistInDB JA: "+mysqlAccess.userExistInDB("JA2"));
        System.out.println("all users: "+mysqlAccess.getAllUsersConcatenated());
        mysqlAccess.followUser("","");


    }
}
