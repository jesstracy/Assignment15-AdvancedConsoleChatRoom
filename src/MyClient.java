import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by jessicatracy on 8/26/16.
 */
public class MyClient {
    public static void main(String[] args) {
        System.out.println("MyClient running...");

        MyClient myClient = new MyClient();

        try {
            // connect to server
            Socket clientSocket = new Socket("localhost", 8080);

            // set up input and output streams
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            //Ask for the user's name:
            Scanner myScanner = new Scanner(System.in);
            String userName = myClient.getUserName(myScanner);
            System.out.println(userName);

            // give server your name
            out.println(userName);

            boolean keepChatting = true;
            while (keepChatting) {
                // get user's message and send it to the chat room
                keepChatting = myClient.sendUserMessage(myScanner, in, out);
            }

            // close connection
            clientSocket.close();


        } catch(IOException exception) {
            exception.printStackTrace();
        }
    }

    public String getUserName(Scanner myScanner) {
        String userName = "name=";
        System.out.print("What is your name? ");
        userName += myScanner.nextLine();
        return userName;
    }

    public boolean sendUserMessage(Scanner myScanner, BufferedReader in, PrintWriter out) throws IOException {
        System.out.print("Write a message to send to the chat room, or write \"exit\" to exit: ");
        String userMessage = myScanner.nextLine();
        if (userMessage.equalsIgnoreCase("exit")) {
            return false;
        }
        out.println(userMessage);
        String serverResponse = in.readLine();
        System.out.println("Server's response: " + serverResponse);
        return true;
    }
}
