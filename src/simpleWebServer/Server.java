package simpleWebServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    public static void main(String[]args) throws IOException {

        //Setup server and connections
        final int PORT_NR = 80; //When connection to the server; you only have to write 'localhost'
        ServerSocket serverSocket = new ServerSocket(PORT_NR);

        System.out.println("Server running! Port number: " + PORT_NR);
        Socket connection = serverSocket.accept();
        System.out.println("Connection to client established!");

        //Set up read and write
        BufferedReader connectionReader = new BufferedReader(new InputStreamReader(connection.getInputStream())); //Used to read the incoming information in the connection
        PrintWriter connectionWriter = new PrintWriter(connection.getOutputStream(), true); //Used to write information back to the client


        String incomingMessage = connectionReader.readLine();

        ArrayList<String> header = new ArrayList<>();
        while(!incomingMessage.equals("")){
            header.add(incomingMessage);
            System.out.println(incomingMessage);
            incomingMessage = connectionReader.readLine();
        }

        //Not the most clean solution, this is why we use JavaScript for these things
        connectionWriter.println("HTTP/1.0 200 OK\r");
        connectionWriter.println("Content-Type: text/html; charset=utf-8\r");
        connectionWriter.println("Server: SimpleWebServer\r");
        connectionWriter.println("\r\n");
        connectionWriter.println("\r\n");

        connectionWriter.println("<HTML>\r\n<BODY>\r\n");
        connectionWriter.println("<h1> Hilsen. Du har koblet deg opp til min enkle web-tjener </h1>\r\n");
        connectionWriter.println("Header fra klient er: \r\n");
        connectionWriter.println("<ul>\r");
        for(String line : header){
            connectionWriter.println("<li>" + line + "</li>\r");
        }

        connectionWriter.println("</ul>\r");
        connectionWriter.println("</BODY>\r\n</HTML>\r");
        connectionWriter.println("\r\n");

        connection.close();
    }
}