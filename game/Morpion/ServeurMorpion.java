import java.io.*;
import java.net.*;

public class ServeurMorpion {
    public static void main(String[] args) {
        final int PORT = 8080;

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("En attente des joueurs...");
            Socket joueur1 = serverSocket.accept();
            System.out.println("Joueur 1 connecté.");
			out1.println("j1");
            Socket joueur2 = serverSocket.accept();
            System.out.println("Joueur 2 connecté.");
			out2.println("j2");

            PrintWriter out1 = new PrintWriter(joueur1.getOutputStream(), true);
            BufferedReader in1 = new BufferedReader(new InputStreamReader(joueur1.getInputStream()));
            PrintWriter out2 = new PrintWriter(joueur2.getOutputStream(), true);
            BufferedReader in2 = new BufferedReader(new InputStreamReader(joueur2.getInputStream()));

            while (true)
            {
                String move1 = in1.readLine();
                if (move1 == null) break;
                out2.println(move1);

                String move2 = in2.readLine();
                if (move2 == null) break;
                out1.println(move2);
            }

            joueur1.close();
            joueur2.close();
        }
        catch (IOException e) { e.printStackTrace(); }
    }
}
