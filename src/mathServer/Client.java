package mathServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws IOException {

        String host = "localhost";
        int port = 4000;

        Scanner scanner = new Scanner(System.in);
        Socket connection = new Socket(host, port);

        BufferedReader connectionReader = new BufferedReader(new InputStreamReader(connection.getInputStream())); //Used to read the incoming information from the server
        PrintWriter connectionWriter = new PrintWriter(connection.getOutputStream(), true); //Used to write information to the server

        String incomingMessage = connectionReader.readLine();

        while (incomingMessage != null){
            System.out.println(incomingMessage);

            System.out.println("Enter math operation (only ADD and SUBTRACT currently supported)");
            String operation = scanner.nextLine();

            System.out.println("Enter first number");
            String firstNumber = scanner.nextLine();

            System.out.println("Enter second number");
            String secondNumber = scanner.nextLine();

            connectionWriter.println(operation + " " + firstNumber + " " + secondNumber);

            incomingMessage = connectionReader.readLine();
        }
    }
}
