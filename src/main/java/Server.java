import World.Persons.*;
import World.World;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;

public class Server {
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        Socket socket = null;
        try {
            DBSQL.delTable();
        } catch (SQLException e) {
            System.out.println("ERROR");
        }
        World world = new World();
        try{
            serverSocket = new ServerSocket(10003);
        }catch (IOException e){
            System.out.println("ERROR create socket");
        }

        while (true) {
            try {
                socket = serverSocket.accept();
            } catch (IOException e) {
                System.out.println("error socket");
            }
            Thread sThread = new Thread(new ServerThread(socket, world));
            sThread.start();
            System.out.println("We have new connection on port : " + socket.getLocalPort());
        }
    }
}

