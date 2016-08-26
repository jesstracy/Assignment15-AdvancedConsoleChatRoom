import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by jessicatracy on 8/26/16.
 */
public class MyServer {
    private ArrayList<String> messageArrayList = new ArrayList<String>();

    public void startServer() {
        try {
            ServerSocket serverListener = new ServerSocket(8080);
            System.out.println("Waiting for a connection...");

            while (true) {
                Socket clientSocket = serverListener.accept();
//                System.out.println("Connection found!");
                //start Thread on this connection
                ConnectionHandler myHandler = new ConnectionHandler(clientSocket);
//                handleIncomingConnection(clientSocket);
                Thread newHandlingThread = new Thread(myHandler);
                newHandlingThread.start();
            }

        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public ArrayList<String> getMessageArrayList() {
        return messageArrayList;
    }

    public void setMessageArrayList(ArrayList<String> messageArrayList) {
        this.messageArrayList = messageArrayList;
    }

    public void addMessageToMessageArrayList(String message) {
        messageArrayList.add(message);
    }
}
