import java.io.*;
import java.net.Socket;

/**
 * Created by jurko on 29/07/16.
 */
public class Client {

    public static final int PORT = 5000;

    public static void main(String args[]) {
        sendFile("172.16.0.7", "/home/jurko/Desktop/test-text.txt");
    }

    private static void sendFile(String localAddress, String filePath) {
        byte fileToBeSent[] = getByteRepresentationOfFile(filePath);
        OutputStream serverStream = generateOutputStream(localAddress);
        writeByteArrayToOutputStream(fileToBeSent, serverStream);
    }

    private static void writeByteArrayToOutputStream(byte[] fileToBeSent, OutputStream serverStream)  {
        try {
            serverStream.write(fileToBeSent, 0, fileToBeSent.length);
            serverStream.flush();
        } catch (IOException e) {
            System.out.print("There was an error writing to the output stream.");
        }
    }

    private static OutputStream generateOutputStream(String localAddress) {
        try {
            Socket socket = new Socket(localAddress, PORT);
            OutputStream socketOutputStream = socket.getOutputStream();
            return socketOutputStream;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static byte[] getByteRepresentationOfFile(String filePath) {
        File fileToBeSent = new File(filePath);
        byte fileByteArray[] = new byte[getFileLength(fileToBeSent)];
        convertFileToByteArray(fileToBeSent, fileByteArray);
        return fileByteArray;
    }

    static void convertFileToByteArray(File fileToBeSent, byte[] fileByteArray) {
        try {
            FileInputStream fis = new FileInputStream(fileToBeSent);
            BufferedInputStream bis = new BufferedInputStream(fis);
            bis.read(fileByteArray, 0, fileByteArray.length);
        } catch (java.io.IOException e) {
            System.out.println("Problem converting file to byte array.");
        }
    }

    static int getFileLength(File fileToBeSent) {
        return (int) fileToBeSent.length();
    }


}
