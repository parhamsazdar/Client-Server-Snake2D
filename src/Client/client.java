// Java implementation for a client
// Save file as Client.java
package Client;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.Scanner;

// Client class
public class client {
    static boolean isStart = false;
    static GamePlay gamePlay;

    public static void main(String[] args) throws IOException {
        try {
            Scanner scn = new Scanner(System.in);

            // getting localhost ip
            InetAddress ip = InetAddress.getByName("localhost");

            // establish the connection with server port 5056
            Socket s = new Socket(ip, 5056);

            // obtaining input and out streams
            DataInputStream dis = new DataInputStream(s.getInputStream());
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());

            // the following loop performs the exchange of
            // information between client and client handler
            while (true) {
                String receive = dis.readUTF();

                if (receive.equals("Enjoy Playing Snake Game")) {
                    ObjectInputStream objGame = new ObjectInputStream(dis);
                    Barrier barrier = (Barrier) objGame.readObject();
                    Snake snake = new Snake();
                    gamePlay = new GamePlay(barrier, snake);


                    JFrame obj = new JFrame();

                    obj.setBounds(10, 10, 905, 700);
                    obj.setBackground(Color.DARK_GRAY);
                    obj.setResizable(false);
                    obj.setVisible(true);
                    obj.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    obj.add(gamePlay);
                    isStart = true;
                    System.out.println(receive);

                }
                if (receive.equals("winner")) {
                    gamePlay.isWinner = true;
                }
                if (receive.equals("finish")) {
                    gamePlay.isFinish = true;
                }
                if (isStart) {
                    Thread.sleep(100);
                    Integer Score = gamePlay.getScore();
                    dos.writeUTF(String.valueOf(Score));
                }

                if (receive.equals("Exit")) {
                    System.out.println("Closing this connection : " + s);
                    s.close();
                    System.out.println("Connection closed");
                    break;
                }

            }

            // closing resources
            scn.close();
            dis.close();
            dos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}