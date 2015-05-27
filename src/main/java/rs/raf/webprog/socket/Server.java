package rs.raf.webprog.socket;

/**
 * Created with IntelliJ IDEA.
 * User: S
 * Date: 5/28/15
 * Time: 12:51 AM
 * To change this template use File | Settings | File Templates.
 */
public class Server {

    private static Server server  =null;

    public static Server getServer(){
        if(server ==null){
            server  = new Server();
        }
        return server ;
    }
}
