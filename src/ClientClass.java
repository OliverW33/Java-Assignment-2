import java.net.*;
import java.io.*;

public class ClientClass {

    public static void main(String args[]) throws IOException { //catches IO exception if thrown

        String hostname = "localhost"; //declaring local loopback address instantiated to the current PC
        int port = 12345; //declaring port number for multiplexing service at that desired address

        try (Socket newSocket = new Socket(hostname, port)) { //try statement declaring new Socket passing in the hostname and port as a parameter

            System.out.println("Welcome Client! You are connected to port " + port); //sys out - regarding port connection

            System.out.println("Converse in this window | type 'exit' to terminate the server"); //sys out - regarding instructions

            String newServerMessage; //Instantiate new String variable

            BufferedReader newReader = new BufferedReader(new InputStreamReader(newSocket.getInputStream())); //declaring new Buffer Reader object to read text from a character input stream

            PrintWriter newOutput = new PrintWriter(newSocket.getOutputStream()); //declaring new Print Writer object to write formatted data to the output stream

            BufferedReader readUserInput = new BufferedReader(new InputStreamReader(System.in)); //declare new Buffer Reader object to read User's input and store value as readInput

            while ((newServerMessage = readUserInput.readLine()) != null) //whilst the program is running, do this - Set relevant variable to equal the stored contents in readInput (Buffer Reader), in result for the value to be read
            {
                if (newServerMessage.length() < 1234)
                {
                    newOutput.println(newServerMessage); //Call Print Writer to write the formatted variable and print the line to the console
                    newOutput.flush(); //call flush method to Print Writer to force any buffered output bytes to their intended destination upon call
                }
                else
                {
                    System.out.println("Server cannot process message - must be less than 1000 characters");
                }

                String userMessage = ""; //Instantiate new String variable

                if (newServerMessage != null) //Comparison of 2 strings - if userInput does not equal 'exit'
                {
                    userMessage = newReader.readLine(); //Set relevant variable to equal the stored contents in readInput (Buffer Reader), in result for the value to be read
                    System.out.println(userMessage); //Server's response
                }
            }
            newReader.close(); //close reader/in stream
            newOutput.close(); //close writer/out stream

        } catch (UnknownHostException uhe) { //UHE exception caught
            System.out.println("A unknown host has been found " + hostname);
            System.exit(0);
        }
    }

}
