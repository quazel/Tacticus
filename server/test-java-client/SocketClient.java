import java.net.Socket;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

public class SocketClient {

    static String hostname = "stevex86.com";
    static int port = 8000;

    public static void main(String[] args) {
        if (args.length > 1) {
            hostname = args[0];
            try {
                port = Integer.parseInt(args[0]);
            }
            catch (NumberFormatException e) {
                System.out.println("Looks like you didn't give me a valid port. Goodbye.");
            }
        }
        else {
            try {
                System.out.println("Using " + Charset.defaultCharset() + " charset.");
                Socket socket = new Socket(hostname, port);
                String message = "Hello, World!";
                sendBytes(message.getBytes(), socket);
                socket.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void sendBytes(byte[] toSend, Socket socket) throws IOException {
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        int len = toSend.length;
        dos.writeInt(len);
        dos.write(toSend);
    }

}
