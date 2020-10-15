import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    static Socket socket = null;
    static DataInputStream dis;
    static DataOutputStream dos;

    public static void main(String[] args) {
        getConnection("192.168.10.128", 10003);
        System.out.println("login: ");
        String login = new Scanner(System.in).nextLine();
        System.out.println("Password: ");
        String password = new Scanner(System.in).nextLine();
        registration(login + " " + password);


        try
        {
            while (true) {
                String map = getMessage();
                System.out.println(map);
                if (map.equals("You dead")) return;
                String step = new Scanner(System.in).next();//step
                sendMessage(step);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void registration(String string) {
        sendMessage(string);
        String message1 = getMessage();
        if (message1.equals("unregistred")) {
            System.out.println(message1);
            System.out.println("name: ");
            String name = new Scanner(System.in).nextLine();
            System.out.println("hero: ");
            String hero = new Scanner(System.in).nextLine();
            sendMessage(name + " " + hero);
        }else if(message1.equals("Ok")){
            System.out.println("Ok");
        }
    }


    private static String getMessage() {
        try {
            return dis.readUTF();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "error";
    }

    private static void sendMessage(String message) {
        try {
            dos.writeUTF(message);
            dos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String showMap(String map) {
        return map;
    }


    private static void getConnection(String ip, int port) {
        while (true) {
            try {
                socket = new Socket(ip, port);
                dis = new DataInputStream(socket.getInputStream());
                dos = new DataOutputStream(socket.getOutputStream());
                return;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static String makeStep() {
        while (true) {
            System.out.print("Выбери направление: ");
            String direction = new Scanner(System.in).nextLine();
            if (direction == "a" || direction == "s" || direction == "d" || direction == "w") {
                return direction;
            }
        }
    }

}
