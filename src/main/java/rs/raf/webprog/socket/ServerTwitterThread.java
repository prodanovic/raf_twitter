package rs.raf.webprog.socket;
import java.net.*;
import java.io.*;

public class ServerTwitterThread extends Thread {

    private Socket socket = null;

    public ServerTwitterThread(Socket socket) {
        super("ServerTwitterThread");
        this.socket = socket;
    }

    public void run() {

        try{
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String inputLine, outputLine;

            while ((inputLine = in.readLine()) != null) {
                System.out.println("server inputLine:"+inputLine);

                outputLine = "OK "+inputLine;
                out.println("server sending back:"+outputLine);
                if (inputLine.equals("Bye"))
                    break;
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean parseAndExecuteCommand(String fullCommand){
        String[] commandParts = fullCommand.split("_");
        if (commandParts[0].equals(Constants.REGISTER_COMMAND)){
//            try{
//                registerUser(commandParts[1],commandParts[2]);
//            }
            return false ;
        }
        return false;
    }

//    public boolean registerUser(String username,String password){
//
//
//    }

}
