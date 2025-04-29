import java.io.*;
import java.net.*;

public class ClientMorpion {
	private static final int PORT = 5000;

	public static void main(String[] args) {
		final String SERVER_ADDRESS = args[0];
		try {
			InetAddress hostName = InetAddress.getByName(SERVER_ADDRESS);
			Socket socket = new Socket(SERVER_ADDRESS, PORT);
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			System.out.println("Connecté au serveur Puissance 4.");

			int coupAutre;
			Morpion ctrl = new Morpion();
			if (in.readLine().equals("j1"))
			{
				System.out.println("j1");
				while (!ctrl.aGagner())
				{
					boolean changement = false;
					while (!changement)
					{
						changement = ctrl.getAJoue();
						try
						{
							Thread.sleep(50);
						} catch (InterruptedException e)
						{
							System.out.println(e);
						}
					}
					//out.println(ctrl.getInt());
					ctrl.setAJoue(false);
					ctrl.setAJoue(false);
					try
					{
						coupAutre = Integer.parseInt(in.readLine());
						//ctrl.placerJeton(coupAutre);
					} catch (IOException | NumberFormatException e)
					{
						System.out.println("Votre adversaire à gagner");
						break;
					}
					//ctrl.majIHM();
					ctrl.setAJoue(true);
				}
			}
			else
			{
				System.out.println("j2");
				while (!ctrl.aGagner())
				{
					ctrl.setAJoue(false);
					try
					{
						coupAutre = Integer.parseInt(in.readLine());
						//ctrl.placerJeton(coupAutre);
					} catch (IOException | NumberFormatException e)
					{
						System.out.println("Votre adversaire à gagner");
						break;
					}
					ctrl.setAJoue(true);
					//ctrl.majIHM();
					boolean changement = false;
					while (!changement)
					{
						changement = ctrl.getAJoue();
						try
						{
							Thread.sleep(50);
						} catch (InterruptedException e)
						{
							System.out.println(e);
						}
					}
					//out.println(ctrl.getInt());
					ctrl.setAJoue(false);
				}
			}
			socket.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

}
