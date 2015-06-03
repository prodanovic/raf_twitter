package rs.raf.webprog.socket;


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


    public ClientTwitter(int portNumber) throws IOException {
        id=++clientId;
        Socket socket1 = new Socket("localhost",portNumber);
        in = new BufferedReader(new InputStreamReader(System.in));
        out = new PrintWriter(socket1.getOutputStream(), true);
    }

    public int getId(){
        return id;
    }
    public void registerUser(String username,String password){
        String command = Constants.REGISTER_COMMAND+Constants.SEP+username+Constants.SEP+password;
        System.out.println(id+" sent to server:"+command);
        out.println(command);
    }

    public Socket getSocket() {
        return socket;
    }

    public static void main(String[] args) throws IOException {
        Socket socket1 = new Socket("localhost",8080);
        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(socket1.getOutputStream(), true);
        while(true){
            System.out.println("enter");
            String read = console.readLine();
            System.out.println(read);
            out.println(read);

        }
    }
}
