/****************************************************************************************
Author: Larissa Hahn
File name: chatserve.java
Last Modification Date: October 28, 2015
References: https://docs.oracle.com/javase/tutorial/essential/io/cl.html
  https://docs.oracle.com/javase/tutorial/networking/sockets/clientServer.html
  http://stackoverflow.com/questions/4067809/how-can-i-find-whitespace-space-in-a-string
Description: Server side of chat client which creates a server TCP Socket which then
  continuously listens to accept connections from clients (in this case using
  chatclient.py to connect to the chat server). When a connection has been accepted,
  the chat server gets the responseMessage from the client, displays it, gets a message
  from the chat server, sends it from the Server to the Client, and if at any point
  the Client or Server uses the "\quit" command, the connection is closed and the
  Server continues to listen for any new connections until a SIGINT is received, which
  ends the program.
****************************************************************************************/
import java.io.IOException;
import java.io.Console;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Scanner;
import java.net.*;

public class chatserve {
  public static void main(String[] args) throws IOException {

    // Check for the correct number of arguments (1)
    if (args.length != 1) {
      System.err.println(" usage: { java chatserve <port_number> }");
      System.exit(1);
    }

    // Get Port Number from command line as Integer
    int serverPort = Integer.parseInt(args[0]);

    // Create a new ServerSocket connection
    ServerSocket serverSocket = new ServerSocket(serverPort);

    // Inform chatserve user that Server is now listening for a connection
    System.out.println("\nWelcome to chatserve.");
    System.out.println("Port " + serverPort + ": chatserve now listening for connection.\n");

    // Get the chatserve handle from the command line
    Console c = System.console();
    String handleName = c.readLine("Enter a chatserve handle: ");

    // Do a character check and whitespace check on the handle name
    while ((handleName.length() > 10) || (containsWhiteSpace(handleName))) {
      System.out.println("\nNotice: 10 character maximum and no whitespace allowed. Try again.");
      handleName = c.readLine("Enter a chatserve handle: ");
    }

    // Format Message Header with Handle name
    String messageHeader = handleName + "> ";

    // Infinite Loop which waits for a connection from chatclient.py
    // Unless SIGINT is received
    while (true) {

      // Accept a connection from the client
      Socket clientSocket = serverSocket.accept();
      System.out.println("\nConnection with chatclient established.");
      System.out.println("Use \\quit command to end connection.\n");

      // Begin InputStream and OutputStream
      BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
      PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

      // Set up chatserve variables
      String clientQuit = "\\quit";
      String serverQuit = "\\quit";
      String message = " ";
      String responseMessage;

      // Set Up Scanner to Get Messages from Server to send to Client
      Scanner serverMessage = new Scanner(System.in);

      // Infinite loop until Client or Server uses \quit command
      while ((responseMessage = in.readLine()) != null) {
        // If \quit command sent from Client, end connection with this client
        if (responseMessage.equals(clientQuit)) {
          System.out.println("Connection ended by chatclient.");
          System.out.println("Port " + serverPort + ": chatserve now listening for new connection.");
          out.close();
          in.close();
          clientSocket.close();
          break;
        }

        // Display the Response Message from Client to Server
        System.out.println(responseMessage);

        // Prompt chatserve user to enter a reply to the Client
        System.out.print(messageHeader); // chatserve handle
        message = serverMessage.nextLine(); // get Server message

        // If the message is more than 500 characters, report an error
        while ((message.length()) > 500) {
          System.out.println("\nNotice: Messages must be 500 characters or less. Try again.\n");
          System.out.print(messageHeader); // chatserve handle
          message = serverMessage.nextLine(); // get Server message
        }

        // Send the Message from Server to Client
        out.println(messageHeader + message);

        // If \quit command sent from Server, end connection with this client
        if (message.equals(serverQuit)) {
          System.out.println("Connection ended by chatserve.");
          System.out.println("Port " + serverPort + ": chatserve now listening for new connection.");
          out.close();
          in.close();
          clientSocket.close();
          break;
        }
     }
    }
  }

  /*************************************************************************
  Function: containsWhiteSpace()
  Parameter: String
  Return: boolean
  Description: This function takes a String data type variable in as its only
  parameter and then first tests to make sure this String is not empty. Then
  it goes on to test the String to see if any of the individual characters
  are a whitespace character using the isWhitespace() function. If any single
  character in the String is whitespace, it will return true. If there is
  not a single character of whitespace in the String, it will return false.
  This is a good function to ensure that not an entire String is free of
  all whitespace.
  **************************************************************************/
  public static boolean containsWhiteSpace(final String testCode) {
    if (testCode != null) {
      // Iterates through each character in String testCode
      for (int i = 0; i < testCode.length(); i++) {
        //tests individual characters for isWhitespace()
        if (Character.isWhitespace(testCode.charAt(i))) {
          // Whitespace found -- not good
          return true;
        }
      }
    }
    // No whitespace -- desired result for this program
    return false;
  }
}
