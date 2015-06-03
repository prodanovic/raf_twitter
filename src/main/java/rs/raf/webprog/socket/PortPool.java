package rs.raf.webprog.socket;

import java.util.ArrayList;
import java.util.List;


public class PortPool {

    private static List<Integer> portNumbers = new ArrayList<Integer>();

    public static void push(Integer portNumber){
        portNumbers.add(portNumber);
    }

    public static Integer pop(){
        return portNumbers.get(0);
    }

}
