import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by jurko on 29/07/16.
 */
public class Server {

    public static final int PORT = 5000;
    public static final int TWENTY_MEGABYTE_SIZE = 20 * 1024 * 1024;

    public static void main(String[] args) {
        ServerSocket recievingServerSocket = createServerSocket();
        Socket incomingSocket = fetchIncomingSocket(recievingServerSocket);
        byte[] incomingBytesFromSocket = new byte[TWENTY_MEGABYTE_SIZE];
        int bytesRead = readFileFromSocket(incomingSocket, incomingBytesFromSocket);
        System.out.println(bytesRead);
        saveFileToLocation("/home/jurko/Desktop/incoming-test-file.txt", incomingBytesFromSocket, bytesRead);
    }

    private static void saveFileToLocation(String filePath, byte[] incomingFile, int bytesToWrite) {
        try {
            FileOutputStream fileOutputStream =  new FileOutputStream(filePath);
            fileOutputStream.write(incomingFile, 0, bytesToWrite);
            fileOutputStream.close();
        } catch (IOException e) {
            System.out.println("Error saving file to location");
        }
    }

    private static int readFileFromSocket(Socket incomingSocket, byte[] incomingBytesFromSocket) {
        try {
            InputStream socketStream = incomingSocket.getInputStream();

            int bytesRead = socketStream.read(incomingBytesFromSocket, 0, incomingBytesFromSocket.length);
            socketStream.close();
            return bytesRead;
        } catch (IOException e) {
            System.out.println("Error reading file from socket");
        }

        return -1;
    }

    private static Socket fetchIncomingSocket(ServerSocket recievingServerSocket) {
        try {
            return recievingServerSocket.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static ServerSocket createServerSocket() {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            return serverSocket;
        } catch (IOException e) {
            System.out.println("Unable to create server socket.");
        }
        return null;
    }
}
