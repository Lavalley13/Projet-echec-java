import java.util.*;

public class Player{
	private Plateau plateau; // plateau en jeu
	private int couleur; // Blanc = 0, Noir = 1
	private String nom; // nom du joueur
	private int numPiecesRest; // Nombre total de piece restante par joueur(max=16)

	// Constructeur par défaut
	public Player(){
	}

	// Constructeur pour choisir la couleur
	public Player(int couleur){
		this.couleur = couleur;
	}
	// Constructeur por nom + couleur
	public Player(int couleur, String nom){
		this.couleur = couleur;
		this.nom = nom;
	}

	// get Plateau
	private Plateau getPlateau(){
		return plateau;
	}

	// get couleur
	public int getCouleur(){
		return couleur;
	}

	// set couleur
	public void setCouleur(int couleur){
		this.couleur = couleur;
	}

	// Retourne couleur ennemie
	public int getEnnemieCouleur(){
		if(getCouleur() == 0){
			return 1;
		} else {
			return 0;
		}
	}

	// get nom
	public String getNom(){
		return nom;
	}

	// set nom
	public void setNom(String nom){
		this.nom = nom;
	}

	// get le nombre piece restantes
	public int getNumPiecesRest(){
		ArrayList<Piece> pieces = getPlateau().getPieces(); // get les piece en jeu
		int numRest = 0;
		for(Piece p : pieces){
			if(p.getCouleur() == couleur){ // Si meme couleur, incrémente numRest
				numRest++;
			}
		}
		return numRest;
	}

}
