import java.io.*;
import java.net.*;

public class ServeurMorpion {
	public static void main(String[] args) {
		final int PORT = 8080;

		try (ServerSocket serverSocket = new ServerSocket(PORT)) {
			System.out.println("Le serveur est en attente de connexions...");

			Socket joueur1Socket = serverSocket.accept();
			System.out.println("Joueur 1 connecté : " + joueur1Socket);
			Socket joueur2Socket = serverSocket.accept();
			System.out.println("Joueur 2 connecté : " + joueur2Socket);

			PrintWriter joueur1Out = new PrintWriter(joueur1Socket.getOutputStream(), true);

			BufferedReader joueur1In = new BufferedReader(new InputStreamReader(joueur1Socket.getInputStream()));

			PrintWriter joueur2Out = new PrintWriter(joueur2Socket.getOutputStream(), true);

			BufferedReader joueur2In = new BufferedReader(new InputStreamReader(joueur2Socket.getInputStream()));
			joueur1Out.println("j1");
			joueur2Out.println("j2");
				while (true)
				{
					try{
					int coupJoueur1 = Integer.parseInt(joueur1In.readLine());
					joueur2Out.println(coupJoueur1);
					}catch(IOException | NumberFormatException e) {break;}

					try{
					int coupJoueur2 = Integer.parseInt(joueur2In.readLine());
					joueur1Out.println(coupJoueur2);
					}catch(IOException | NumberFormatException e) {break;}
				}
			joueur1Socket.close();
			joueur2Socket.close();
		
		} catch (IOException e) { e.printStackTrace();}
	}
}
