package mathServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    private static class ConnectionHandler extends Thread{
        //Private class for dealing with incoming connections
        //These are the actual threads that are being spun up

        Socket connection = null;

        ConnectionHandler(Socket connection){
            this.connection = connection;
        }

        enum mathOperations{
            ADD,
            SUBTRACT
        }

        @Override
        public void run() {

            System.out.println("Thread handling " + connection);

            try {
                BufferedReader connectionReader = new BufferedReader(new InputStreamReader(connection.getInputStream())); //Used to read the incoming information in the connection
                PrintWriter connectionWriter = new PrintWriter(connection.getOutputStream(), true); //Used to write information back to the client

                connectionWriter.println("Connection established\nWelcome to JavaMath!");

                String[] inputParameters = connectionReader.readLine().split(" ");


                switch (mathOperations.valueOf(inputParameters[0])){
                    case ADD:
                        connectionWriter.println(Integer.parseInt(inputParameters[1]) + Integer.parseInt(inputParameters[2]));
                        break;
                    case SUBTRACT:
                        connectionWriter.println(Integer.parseInt(inputParameters[1]) - Integer.parseInt(inputParameters[2]));
                        break;
                    default:
                        connectionWriter.println("Invalid operation");
                }

                connection.close();

            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        ArrayList<ConnectionHandler> connections = new ArrayList<>(); //A list of all connections

        //Initialize server/API
        final int PORT_NR = 4000;
        ServerSocket serverSocket = new ServerSocket(PORT_NR);

        System.out.println("Server running! Port number: " + PORT_NR);

        //It would probably be smart to have a way of neatly shutting off the server
        while (true){
            ConnectionHandler newConnection = new ConnectionHandler(serverSocket.accept());
            connections.add(newConnection);
            newConnection.start();
        }


    }
}
