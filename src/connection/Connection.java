package connection;

import server.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Connection extends Thread {
    private BufferedReader in;
    private PrintWriter out;
    private Socket socket;

    public Connection(Socket socket) {
        this.socket = socket;
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        String word;
        try {
            while (true) {
                word = in.readLine();
                if (word.equals("stop")) {
                    break;
                }
                for (Connection vr : Server.connections) {
                    vr.sendMessage(word);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String msg) {
        out.write(msg + "\n");
        out.flush();
    }
}
