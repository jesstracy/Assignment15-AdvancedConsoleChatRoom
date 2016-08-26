import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by jessicatracy on 8/26/16.
 */
public class ConnectionHandler implements Runnable {
    Socket clientSocket;

    public ConnectionHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void run() {
        try {
            handleIncomingConnection(clientSocket);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void handleIncomingConnection(Socket clientSocket) throws IOException {

        System.out.println("New connection from " + clientSocket.getInetAddress().getHostAddress());

        //Read in info from client
        BufferedReader inputFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        //Print output to client
        PrintWriter outputToClient = new PrintWriter(clientSocket.getOutputStream(), true);

        String inputLine = inputFromClient.readLine();
        String userNameFromClient = null;
        boolean validUserName = true;

        if (!inputLine.startsWith("name=")) {
            outputToClient.println("You did not send input in the form of \"name=name-of-client\". Reconnect and try again.");
            validUserName = false;
        } else {
//           outputToClient.println("Message received! :-)");
            userNameFromClient = inputLine.split("=")[1];
//           System.out.println("ON SERVER SIDE-> userName is " + userNameFromClient);
        }

        while(((inputLine = inputFromClient.readLine()) != null) && validUserName) {
            String myOutput = userNameFromClient + " said: ";
//                inputLine = inputFromClient.readLine();
            myOutput += inputLine;
            System.out.println("* " + myOutput);
            outputToClient.println("Your message was received! " + myOutput);
        }
    }
}

