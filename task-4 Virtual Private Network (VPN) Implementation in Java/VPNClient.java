// VPNClient.java
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.net.Socket;
import java.util.Base64;
import java.util.Scanner;

public class VPNClient {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12345;
    private static SecretKey secretKey;

    public static void main(String[] args) {
        try {
            // Load the secret key (ensure it matches the server's key)
            String encodedKey = args[0];
            byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
            secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");

            Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            Scanner scanner = new Scanner(System.in);
            System.out.println("Connected to VPN server. Type your messages:");

            while (true) {
                System.out.print("Enter message: ");
                String message = scanner.nextLine();
                String encryptedMessage = EncryptionUtil.encrypt(message, secretKey);
                out.println(encryptedMessage);

                String response = in.readLine();
                String decryptedResponse = EncryptionUtil.decrypt(response, secretKey);
                System.out.println("Server response: " + decryptedResponse);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

