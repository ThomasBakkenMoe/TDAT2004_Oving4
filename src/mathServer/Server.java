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

            System.out.println("Thread handling " + connection.toString());

            try {
                BufferedReader connectionReader = new BufferedReader(new InputStreamReader(connection.getInputStream())); //Used to read the incoming information in the connection
                PrintWriter connectionWriter = new PrintWriter(connection.getOutputStream(), true); //Used to write information back to the client

                connectionWriter.println("Connection established! Welcome to JavaMath!");

                String incomingMessage = connectionReader.readLine();

                while (incomingMessage != null){
                    String[] inputParameters = incomingMessage.split(" ");

                    int result = 0;

                    switch (mathOperations.valueOf(inputParameters[0].toUpperCase())){
                        case ADD:
                            System.out.println("Addition");
                            result = Integer.parseInt(inputParameters[1]) + Integer.parseInt(inputParameters[2]);
                            System.out.println(result);
                            connectionWriter.println(result);
                            break;
                        case SUBTRACT:
                            System.out.println("Subtraction");
                            result = Integer.parseInt(inputParameters[1]) - Integer.parseInt(inputParameters[2]);
                            System.out.println(result);
                            connectionWriter.println(result);
                            break;
                        default:
                            connectionWriter.println("Invalid operation");
                    }

                    incomingMessage = connectionReader.readLine();
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
