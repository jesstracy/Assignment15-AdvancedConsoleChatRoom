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
        MyServer myServer = new MyServer();

        System.out.println("New connection from " + clientSocket.getInetAddress().getHostAddress());

        //Read in info from client
        BufferedReader inputFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        //Print output to client
        PrintWriter outputToClient = new PrintWriter(clientSocket.getOutputStream(), true);

        String inputLine = inputFromClient.readLine();
        String userNameFromClient = null;
        boolean validUserName = true;

        if (!inputLine.startsWith("name=")) {
            outputToClient.println("You did not send your name in the form of \"name=name-of-client\". Reconnect and try again.");
            validUserName = false;
        } else {
//           outputToClient.println("Message received! :-)");
            userNameFromClient = inputLine.split("=")[1];
//           System.out.println("ON SERVER SIDE-> userName is " + userNameFromClient);
        }

        while(((inputLine = inputFromClient.readLine()) != null) && validUserName) {
            if (inputLine.equalsIgnoreCase("history")) {
//                for (String message : myServer.getMessageArrayList()) {
//                    outputToClient.println(message);
//                }
                for (Message message : myServer.getSetOfMessages()) {
                    outputToClient.println(message.getMessageContent());
                }
                outputToClient.println("Tx:History.End");
            } else {
                Message clientMessage = new Message();
                String myOutput = "On " + clientMessage.getDayOfWeek() + " at " + clientMessage.getTime() + ", " + userNameFromClient + " said: ";
                myOutput += inputLine;
                System.out.println("* " + myOutput);
                // add the user's message to the arrayList of messages in myServer
//                myServer.addMessageToMessageArrayList(myOutput);

                // Set the message content
                clientMessage.setMessageContent(myOutput);
                // add the message to arrayList of messages
                myServer.addMessageToSetOfMessages(clientMessage);

                outputToClient.println("Your message was received! " + myOutput);
                outputToClient.println("Tx:History.End");
            }
        }
    }
}

