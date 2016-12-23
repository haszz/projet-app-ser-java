package abonne;

/**
 * 
 * @author guydo
 * represent a User registered
 */
public class Abonne {
	private String nom;
	private  String prenom;
	private int id;
	
	/**
	 * constructor
	 * @param nom
	 * @param prenom
	 * @param id
	 */
	public Abonne(String nom, String prenom, int id) {
		this.nom = nom;
		this.prenom = prenom;
		this.id = id;
	}
	
	/**
	 * retrieve the last name
	 * @return
	 */
	public String getNom() {
		return nom;
	}
	
	/**
	 * retrieve the Id
	 * @return
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * retrive the first name
	 * @return
	 */
	public String getPrenom() {
		// TODO Auto-generated method stub
		return prenom;
	}
	
	@Override
	public String toString(){
		return getNom() + " " + getPrenom() + " " + getId();
	}
}
