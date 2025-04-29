import java.io.*;
import java.net.*;

public class ClientMorpion
{
    public static void main(String[] args)
    {
        final int PORT = 5000;

        final String SERVER_ADDRESS = args[0];
        try
        {
            Socket         socket = new Socket(SERVER_ADDRESS, PORT);
            PrintWriter    out    = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in     = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("Connecté au serveur Morpion.");

            String  role = in.readLine();
            System.out.println("Vous êtes : " + role);
            Morpion ctrl = new Morpion(socket, out);

            if (role.equals("j1"))
            {
                ctrl.setMonSigne('X');
                ctrl.setTour(true);
            }
            else
            {
                ctrl.setMonSigne('O');
                ctrl.setTour(false);
            }

            // Thread pour écouter les messages du serveur
            new Thread(() ->
            {
                try
                {
                    while (true)
                    {
                        if (in.ready())
                        {
                            String msg = in.readLine();
                            if (msg == null) break;
                            if (msg.startsWith("CHAT:"))
                                ctrl.logChat.append(msg.substring(5) + "\n");
                            else
                            {
                                // Traiter le mouvement de jeu
                                int move = Integer.parseInt(msg);
                                ctrl.receiveMove(move);
                                ctrl.setTour(true);
                            }
                        }
                    }
                } catch (IOException e) { e.printStackTrace(); }
            }).start();

            // Boucle principale pour envoyer les mouvements de jeu
            while (true)
            {
                if (ctrl.getAJoue())
                {
                    int move = ctrl.getDernierMouvement();
                    out.println(move);
                    ctrl.setAJoue(false);
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
    }
}