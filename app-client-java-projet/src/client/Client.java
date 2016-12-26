package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import java.util.TimerTask;

import javax.swing.Timer;

/**
 * Classe client
 * 
 * @author hassa
 *
 */
public class Client implements Runnable {
	private Socket s;

	// certification BretteSoft "Guerrier des steppes"
	private Timer session;
	//La dur�e d'une session sans activit� dure 10 minute
	private static final int DUREE_SESSION = 600000;

	public Client(String Host, int Port) throws UnknownHostException, IOException {
		// Cree une socket pour communiquer avec le service se trouvant sur la
		// machine host au port PORT
		s = new Socket(Host, Port);

		new Thread(this).start();
	}

	@Override
	public void run() {

		try {
			// Debut de la session
			// Gere le temps d'activit� d'un utilisateur
			ActionListener activit� = new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					throw new DelaiSessionDepasserException();
				}
			};
			session = new Timer(DUREE_SESSION, activit�);
			session.setRepeats(false);
			session.start();

			// Cree les streams pour lire et ecrire du texte dans cette socket
			BufferedReader sin = new BufferedReader(new InputStreamReader(s.getInputStream()));
			PrintWriter sout = new PrintWriter(s.getOutputStream(), true);
			// Cree le stream pour lire du texte a partir du clavier
			// (on pourrait aussi utiliser Scanner)
			BufferedReader clavier = new BufferedReader(new InputStreamReader(System.in));
			// Informe l'utilisateur de la connection
			System.out.println("Connect� au serveur " + s.getInetAddress() + ":" + s.getPort());

			String line = "";
			String l;
			while (true) {

				while (sin.ready()) {
					line += sin.readLine() + "\n";
				}

				if (line.contains("erreur")) {
					System.err.println("> ERREUR RECOMMENCER\n" + line);
					System.err.flush();
				} else if (line.contains("resaOk")) {
					line = line.replace("resaOk", "");
					System.out.println("> " + line);
					System.out.flush();
					break;
				} else {

					System.out.println("> " + line);
					System.out.flush();
				}

				l = /* getAction(line)+"\n"+ */clavier.readLine();
				// si il ya une activit� de la part de l'utilisateur on
				// reinitialise le timer
				session.restart();

				line = "";
				if (l.equals(""))
					break;
				// envoie au serveur
				sout.println(l);

			}
		}

		catch (IOException e) {
			System.out.println("Connection fermee par le serveur");
		}
		System.out.println("Aurevoir");
		// Refermer dans tous les cas la socket
		try {
			if (s != null)
				s.close();
		} catch (IOException e2) {
			;
		}
	}

	/**
	 * Retourne une action contenu dans un message
	 */
	private static String getAction(String line) {
		// TODO Auto-generated method stub
		if (line.contains("erreur")) {
			line = line.replace("erreur", "");
		}
		return line.substring(0, line.indexOf("\n"));

	}

	/**
	 * Fonction appel�e juste avant que le garbage collector supprime l'objet
	 */
	protected void finalize() throws Throwable {
		s.close();
	}
}
