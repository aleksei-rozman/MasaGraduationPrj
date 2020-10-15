import World.Persons.*;
import World.World;
import World.Direction;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class ServerThread implements Runnable {
    Socket socket = null;
    World world;
    Personage personage;
    DataInputStream dis;
    DataOutputStream dos;
    public ServerThread(Socket socket, World world) {
        this.world = world;
        this.socket = socket;
        try {
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            System.out.println("EROROR init inputs and outputs");
        }
    }
    public void createPersonageFromBD(String name, String type, String id, int x, int y){
        if (type.equals("warrior")) {
            personage = new Warrior(name);
            personage.id = id;
            personage.x = x;
            personage.y = y;
        }

        if (type.equals("priest")) {
            personage = new Priest(name);
            personage.id = id;
            personage.x = x;
            personage.y = y;
        }

        if (type.equals("mage")) {
            personage = new Mage(name);
            personage.id = id;
            personage.x = x;
            personage.y = y;
        }
    }
    public void createPersonage(String name, String type, String id){
        int x = new Random().nextInt(30);
        int y = new Random().nextInt(30);
        createPersonageFromBD(name, type, id, x, y);
    }

    public void run() {
        String [] enterArray = inputString().split(" "); // connection parsing login passwd
        String [] id = null;
        try {
            id = DBSQL.validate(enterArray[0], enterArray[1]).split(" ");
            System.out.println(Arrays.toString(id));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(id[1].equals("0")){// no reg
            outputString("unregistred");
            String[] param = inputString().split(" ");
            System.out.println(Arrays.toString(param));
            String name = param[0];
            String type = param[1];
            createPersonage(name, type, id[0]);
            DBSQL.createCharacter(id[0], name.charAt(0), type, personage.x, personage.y);
        }else {
            outputString("ok");
            ArrayList list = null;
            try {
                list = DBSQL.getCharacter(enterArray[0], enterArray[1]);
                System.out.println(list);
            } catch (SQLException e) {
                System.out.println("get character");
            }
            createPersonageFromBD (
                    list.get(1).toString(),
                    list.get(2).toString(),
                    list.get(0).toString(),
                    Integer.valueOf(list.get(3).toString()),
                    Integer.valueOf(list.get(4).toString())
                    );
        }
        world.addPersonage(personage);

        while (true) {
            try {
                if (personage.hp <= 0) {
                    throw new Exception();
                }
                outputString(world.getMap());
                char step = inputString().charAt(0); // where make move
                System.out.println(personage.id + " " + step);
                Direction direction = getDirection(step);
                world.move(personage, direction);
            }
            catch (Exception e) {
                world.delete(personage);
                outputString("You dead");
                try {
                    socket.close();
                } catch (IOException e1) {
                    System.out.println("Not close socket");
                }
                return;
            }
        }
    }

    public Direction getDirection(char step){
        switch (step) {
            case 'w':
                return Direction.LEFT;
            case 'a':
                return Direction.UP;
            case 's':
                return Direction.RIGHT;
            case 'd':
                return Direction.DOWN;
        }
        return Direction.UP;
    }
    public void outputString ( String s) {
        try {
            dos.writeUTF(s);
            dos.flush();
        } catch (IOException e) {
            System.out.println("EROROR output");
        }

    }
    public String inputString ()  {
        String string = "";
        try {
            string = dis.readUTF();
        } catch (IOException e) {
            System.out.println("EROROR input");
        }
        return string;
    }
}