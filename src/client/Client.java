package client;

import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Client {

    private Socket socket;
    private BufferedReader in;
    private BufferedWriter out;
    private BufferedReader inputUser;
    private String nickname;
    private Date date;
    private String time;
    private SimpleDateFormat dateFormat;

    public static String ipAddr = "localhost";
    public static int port = 8081;

    public static void main(String[] args) {
        new Client(ipAddr, port);
    }

    public Client(String ipAddr, int port) {
        this.ipAddr = ipAddr;
        this.port = port;
        try {
            this.socket = new Socket(ipAddr, port);
        } catch (IOException e) {
            System.err.println("Socket failed");
        }

        try {
            inputUser = new BufferedReader(new InputStreamReader(System.in));
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.pressNickname();
            new ReadMsg().start();
            new WriteMsg().start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void pressNickname() {
        System.out.print("Enter your nickname: ");
        try {
            nickname = inputUser.readLine();
            out.write(nickname + " is connected" + "\n");
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private class ReadMsg extends Thread {
        @Override
        public void run() {
            String str;
            try {
                while (true) {
                    str = in.readLine();
                    if (str.equals("stop")) {
                        break;
                    }
                    System.out.println(str);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public class WriteMsg extends Thread {
        @Override
        public void run() {
            while (true) {
                String userWord;
                try {
                    date = new Date();
                    dateFormat = new SimpleDateFormat("HH:mm:ss");
                    time = dateFormat.format(date);
                    userWord = inputUser.readLine();
                    if (userWord.equals("stop")) {
                        out.write("stop" + "\n");
                        break;
                    } else {
                        out.write("(" + time + ") " + nickname + ": " + userWord + "\n");
                    }
                    out.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}


