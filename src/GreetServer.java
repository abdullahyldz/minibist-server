
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class GreetServer implements Runnable{
    private ServerSocket serverSocket;

    public GreetServer(int port) {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("TCP server waiting for client on port " + port);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                Socket connectionSocket = serverSocket.accept();
                System.out.println(" THE CLIENT" + " " + connectionSocket.getInetAddress()
                    + ":" + connectionSocket.getPort() + " IS CONNECTED ");
                new Thread(new GreetServerOperator(connectionSocket)).start();
            } catch (IOException ex) {
                System.err.println("Server aborted:" + ex);
            }
        }
    }
}