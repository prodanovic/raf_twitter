package rs.raf.webprog.socket;
import rs.raf.webprog.mysql.Database;

import java.net.*;
import java.io.*;
import java.sql.SQLException;

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
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String inputLine, outputLine;

            while ((inputLine = in.readLine()) != null) {
                System.out.println("server received:"+inputLine);

                outputLine = parseAndExecuteCommand(inputLine)?"OK":"NO";
                System.out.println("server returning:"+outputLine);
                out.println(outputLine);
                if (inputLine.equals("Bye"))
                    break;
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean parseAndExecuteCommand(String fullCommand) throws SQLException {
        String[] commandParts = fullCommand.split("_");

        if (commandParts[0].equals(Constants.USER_EXISTS_COMMAND)){
//            try{
//                registerUser(commandParts[1],commandParts[2]);
//            }
            return userExistInDB(commandParts[1]);
        }

        return false;
    }
    public boolean userExistInDB(String username) throws SQLException {
        return database.userExistInDB(username);
    }
    public boolean registerUser(String username,String password){

        return false;
    }

}
