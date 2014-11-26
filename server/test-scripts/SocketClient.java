import java.net.Socket;
import java.io.InputStream;
import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

public class SocketClient {

    static String hostname = "localhost";
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
                String packet = "{\"packet_type\":0, \"timestamp\":0, \"contents\": \"Ping!\"}";
                sendBytes(packet.getBytes(), socket);
                InputStream in = socket.getInputStream();
                BufferedInputStream bin = new BufferedInputStream(in);
                int length = 0;
                for (int i = 0; i < 4; i++) {
                    int next = bin.read();
                    length += next << (24 - (i * 8));
                }
                System.out.println(length);
                byte[] data = new byte[length];
                bin.read(data, 0, length);
                System.out.println(new String(data, "UTF-8"));
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void sendBytes(byte[] toSend, Socket socket) throws IOException {
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        dos.write(toSend);
    }

}
