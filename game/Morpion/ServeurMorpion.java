import java.io.*;
import java.net.*;

public class ServeurMorpion
{
    public static void main(String[] args)
    {
        final int PORT = 5000;

        try (ServerSocket serverSocket = new ServerSocket(PORT))
        {
            System.out.println("En attente des joueurs...");
            Socket joueur1 = serverSocket.accept();
            System.out.println("Joueur 1 connecté.");
            Socket joueur2 = serverSocket.accept();
            System.out.println("Joueur 2 connecté.");

            PrintWriter    out1 = new PrintWriter(joueur1.getOutputStream(), true);
            BufferedReader in1  = new BufferedReader(new InputStreamReader(joueur1.getInputStream()));
            PrintWriter    out2 = new PrintWriter(joueur2.getOutputStream(), true);
            BufferedReader in2  = new BufferedReader(new InputStreamReader(joueur2.getInputStream()));

            out1.println("j1");
            out2.println("j2");

            // Thread pour relayer les messages de joueur1 à joueur2
            new Thread(() ->
            {
                try
                {
                    while (true)
                    {
                        String msg = in1.readLine();
                        if (msg == null) break;
                        out2.println(msg);
                    }
                } catch (IOException e) { e.printStackTrace(); }
            }).start();

            // Thread pour relayer les messages de joueur2 à joueur1
            new Thread(() ->
            {
                try
                {
                    while (true)
                    {
                        String msg = in2.readLine();
                        if (msg == null) break;
                        out1.println(msg);
                    }
                }
                catch (IOException e) { e.printStackTrace(); }
            }).start();
        }
        catch (IOException e) { e.printStackTrace(); }
    }
}