// Java implementation of  Server side
// It contains two classes : Server and ClientHandler
// Save file as Server.java
package Server;


// Java implementation of  Server side
// It contains two classes : Server and ClientHandler
// Save file as Server.java

import java.io.*;
import java.util.*;
import java.net.*;

// Server class
public class Server {

    static Vector<ClientHandler> ar = new Vector<>();

    //    static GamePlay gamePlay= new GamePlay(new Barrier(),new Snake());
    static Barrier barrier = new Barrier();
    // counter for clients
    static int i = 0;
    static boolean isStart = false;
    static boolean haveWinner = false;
    static Integer whenWin = 3;
    static Integer numCon = 2;


    public static void finishGame() throws IOException {
        for (ClientHandler m : ar) {
            m.dos.writeUTF("finish");
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        // server is listening on port 5056
        ServerSocket ss = new ServerSocket(5056);

        // running infinite loop for getting
        // client request
        while (true) {
            Socket s = null;

            try {
                // socket object to receive incoming client requests

                s = ss.accept();

                System.out.println("A new client is connected : " + s);

                // obtaining input and out streams
                DataInputStream dis = new DataInputStream(s.getInputStream());
                DataOutputStream dos = new DataOutputStream(s.getOutputStream());

                System.out.println("Assigning new thread for this client");

                // create a new thread object
                ClientHandler client = new ClientHandler(s, "client " + i, dis, dos);
                Thread t = new Thread(client);
                i++;
                ar.add(client);

                // Invoking the start() method
                t.start();

                if (ar.size() == numCon) {
                    System.out.println("Game is ready to start");
                    break;
                }

            } catch (Exception e) {
                s.close();
                e.printStackTrace();
            }
        }
        for (ClientHandler m : ar) {
            m.dos.writeUTF("Enjoy Playing Snake Game");
            m.dos.flush();
            ObjectOutputStream objGame = new ObjectOutputStream(m.dos);
            objGame.writeObject(barrier);
            objGame.flush();
            isStart = true;
        }
        while (!haveWinner) {
            Thread.sleep(100);
            for (ClientHandler m : ar) {
                if (m.Score == whenWin) {
                    System.out.println(m.name + "is Win");
                    haveWinner = true;
//                    m.dos.writeUTF("You Win");
                    m.dos.writeUTF("winner");
                    m.dos.flush();
                    finishGame();
                    break;
                }
            }
        }
    }
}

// ClientHandler class
class ClientHandler extends Thread {
    final DataInputStream dis;
    final DataOutputStream dos;
    final Socket s;
    String name;
    Integer Score = 0;

    // Constructor
    public ClientHandler(Socket s, String name, DataInputStream dis, DataOutputStream dos) {
        this.s = s;
        this.name = name;
        this.dis = dis;
        this.dos = dos;
    }

    @Override
    public void run() {
        String received;
        while (true) {
            try {
                if (!Server.isStart) {
                    // Ask user what he wants
                    dos.writeUTF("Wait until another player is going to be connect");
                    dos.flush();
                } else {
                    dos.writeUTF("Go And Play Your Game Not Chat with Server");
                    dos.flush();
                }
                // receive the answer from client
                received = dis.readUTF();
                if (Integer.parseInt(received) >= 1) {
                    Score = Integer.parseInt(received);
                }

                if (received.equals("Exit")) {
                    System.out.println("Client " + this.s + " sends exit...");
                    System.out.println("Closing this connection.");
                    this.s.close();
                    System.out.println("Connection closed");
                    break;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            // closing resources
            this.dis.close();
            this.dos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}