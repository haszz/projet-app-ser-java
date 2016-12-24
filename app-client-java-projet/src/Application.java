
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
/**
 * Classe client
 * @author hassa
 *
 */
class Application {
		private final static int PORT = 1000;
		private final static String HOST = "localhost"; 
	
	public static void main(String[] args) {
		Socket s = null;		
		try {
			// Cree une socket pour communiquer avec le service se trouvant sur la
			// machine host au port PORT
			s = new Socket(HOST, PORT);
			// Cree les streams pour lire et ecrire du texte dans cette socket
			BufferedReader sin = new BufferedReader (new InputStreamReader(s.getInputStream ( )));
			PrintWriter sout = new PrintWriter (s.getOutputStream ( ), true);
			// Cree le stream pour lire du texte a partir du clavier 
			// (on pourrait aussi utiliser Scanner)
			BufferedReader clavier = new BufferedReader(new InputStreamReader(System.in));			
			// Informe l'utilisateur de la connection
			System.out.println("Connect� au serveur " + s.getInetAddress() + ":"+ s.getPort());
			
			String line = "";
			String l;
			while(true) {
				
				
				while(sin.ready()){
					line +=sin.readLine()+"\n";
					}
				
				if(line.contains("erreur")){
					System.err.println("> ERREUR RECOMMENCER\n" +line);
					System.err.flush();	
				}else if(line.contains("resaOk")){
					line = line.replace("resaOk", "");
					System.out.println("> " +line);
					System.out.flush();	
					break;
				}
				else{
				
					System.out.println("> " +line);
					System.out.flush();	
				}
				l = getAction(line)+"\n"+clavier.readLine();
				line="";
				if (l.equals("")) break;
				// envoie au serveur
				sout.println(l);
				
			}
		}
		
		catch (IOException e) {System.out.println("Connection fermee par le serveur");}
		System.out.println("Aurevoir");
		// Refermer dans tous les cas la socket
		try {
if (s != null) s.close(); } 
		catch (IOException e2) { ; }		
	}

	/**
	 * Retourne une action contenu dans un message
	 */
	private static String getAction(String line) {
		// TODO Auto-generated method stub
		if(line.contains("erreur")){
			line =line.replace("erreur", "");
		}
		return line.substring(0,line.indexOf("\n"));
	 
	}
}
