package org;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import com.google.gson.Gson;
import org.tasks.TaskHandler;

public class ClientMessageHandler implements Runnable {

    Socket connectionSocket;
    BufferedReader reader;
    BufferedWriter writer;
    Gson gson;

    public ClientMessageHandler(Socket connectionSocket) {
        this.connectionSocket = connectionSocket;
        this.gson = new Gson();

        try {
            reader = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(connectionSocket.getOutputStream()));
        } catch (IOException e) {
            return;
        }
    }

    public void run() {
        String clientRequest = null;
        try {
            clientRequest = reader.readLine();
            if (clientRequest != null) {
                String serverResponse = TaskHandler.handleMessage(gson, clientRequest);
                sendReply(serverResponse);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            reader.close();
            writer.close();
            connectionSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendReply(String response) {

        try {
            writer.write(response);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
