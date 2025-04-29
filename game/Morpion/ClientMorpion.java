
import java.io.*;
import java.net.*;

public class ClientMorpion {
    private static final int PORT = 5000;

    public static void main(String[] args) {
        final String SERVER_ADDRESS = args[0];
        try {
            Socket socket = new Socket(SERVER_ADDRESS, PORT);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("Connecté au serveur Morpion.");

            String role = in.readLine();
            System.out.println("Vous êtes : " + role);
            Morpion ctrl = new Morpion();

            if (role.equals("j1")) {
                ctrl.setMySymbol('X');
                ctrl.setTurn(true);
            } else {
                ctrl.setMySymbol('O');
                ctrl.setTurn(false);
            }

            while (true) {
                if (ctrl.getAJoue()) {
                    int move = ctrl.getInt();
                    out.println(move);
                    ctrl.setAJoue(false);
                } else {
                    if (in.ready()) {
                        String msg = in.readLine();
                        if (msg == null) break;
                        int move = Integer.parseInt(msg);
                        ctrl.receiveMove(move);
                        ctrl.setTurn(true);
                    } else {
                        Thread.sleep(50);
                    }
                }
            }
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
